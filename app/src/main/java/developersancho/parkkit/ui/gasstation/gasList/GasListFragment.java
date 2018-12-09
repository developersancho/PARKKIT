package developersancho.parkkit.ui.gasstation.gasList;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.gasstation.Gas;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentGasListBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.NetworkUtils;

public class GasListFragment extends BaseFragment<FragmentGasListBinding, GasListViewModel>
        implements IGasListNavigator, GasListAdapter.GasAdapterListener {

    private FragmentGasListBinding binding;
    private GasListAdapter adapter;
    private GasListViewModel viewModel;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_gas_list;
    }

    @Override
    public GasListViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(GasListViewModel.class);
        return viewModel;
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "HATA : " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        AppLogger.e(throwable.getMessage());
    }

    @Override
    public void showNotFoundMessage() {
        AppUtils.showDataNotFoundAlertToUser(getContext());
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        adapter = new GasListAdapter(getContext());
        adapter.setGasAdapterListener(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
        subscribeToGasList();
    }


    private void setUp() {
        binding.recyclerViewGas.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewGas.setHasFixedSize(true);
        binding.recyclerViewGas.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewGas.setAdapter(adapter);
    }

    private void subscribeToGasList() {
        userLocation = viewModel.getUserLocation();
        String distance = AppConstants.DISTANCE;
        String lat = String.valueOf(userLocation.getLatitude());
        String lng = String.valueOf(userLocation.getLongitude());

        if (NetworkUtils.isNetworkConnected(getContext())) {
            viewModel.fetchGasesInsertRoom(distance, lat, lng).observe(this, gases -> {
                adapter.setGases(gases);
            });
        } else {
            viewModel.fetchGasesFromRoom().observe(this, gases -> {
                adapter.setGases(gases);
            });
        }

    }

    @Override
    public void onGasClicked(Gas gas) {
        showDetailDialog(gas);
    }

    private void showDetailDialog(Gas item) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View detail_view = layoutInflater.inflate(R.layout.dialog_detail, null);
        TextView txtNameDialog = (TextView) detail_view.findViewById(R.id.txtNameDialog);
        TextView txtAddressDialog = (TextView) detail_view.findViewById(R.id.txtAddressDialog);
        TextView txtDistanceDialog = (TextView) detail_view.findViewById(R.id.txtDistanceDialog);
        TextView txtTypeDialog = (TextView) detail_view.findViewById(R.id.txtTypeDialog);
        Button btnShareDialog = (Button) detail_view.findViewById(R.id.btnShareDialog);
        Button btnRoadDialog = (Button) detail_view.findViewById(R.id.btnRoadDialog);
        ImageView imgLogoDialog = (ImageView) detail_view.findViewById(R.id.imgLogoDialog);
        dialog.setView(detail_view);

        txtNameDialog.setText(item.getName());
        txtAddressDialog.setText(item.getAddress());
        DecimalFormat df = new DecimalFormat("#.##");
        txtDistanceDialog.setText(String.valueOf(df.format(Double.valueOf(item.getDistance()))) + " Km");
        imgLogoDialog.setBackground(getResources().getDrawable(R.drawable.accountant));
        txtTypeDialog.setText(item.getBrand());

        btnShareDialog.setOnClickListener(v -> {
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",
                    item.getxCoor(),
                    item.getyCoor(),
                    item.getName());

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String ShareSub = getString(R.string.shareVia);
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, uri);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.shareVia)));
        });

        btnRoadDialog.setOnClickListener(v -> {
            String latitude = String.valueOf(item.getxCoor());
            String longitude = String.valueOf(item.getyCoor());
            Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");

            try {
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
            } catch (NullPointerException e) {
                Log.e("MAP", "onClick: NullPointerException: Couldn't open map." + e.getMessage());
                Toast.makeText(getActivity(), "Couldn't open map", Toast.LENGTH_SHORT).show();
            }
        });

        dialog.show();
    }
}
