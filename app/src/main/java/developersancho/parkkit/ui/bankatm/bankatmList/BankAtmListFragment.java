package developersancho.parkkit.ui.bankatm.bankatmList;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.bankatm.BankAtm;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentBankAtmListBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.NetworkUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BankAtmListFragment extends BaseFragment<FragmentBankAtmListBinding, BankAtmListViewModel>
        implements IBankAtmListNavigator, BankAtmListAdapter.BankAtmAdapterListener {

    private FragmentBankAtmListBinding binding;
    private BankAtmListViewModel viewModel;
    private BankAtmListAdapter adapter;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bank_atm_list;
    }

    @Override
    public BankAtmListViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(BankAtmListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        adapter = new BankAtmListAdapter(getContext());
        adapter.setBankAtmAdapterListener(this::onBankAtmClicked);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
        subscribeToBankAtmList();
    }

    private void setUp() {
        binding.recyclerViewBankAtm.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewBankAtm.setHasFixedSize(true);
        binding.recyclerViewBankAtm.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewBankAtm.setAdapter(adapter);
    }

    private void subscribeToBankAtmList() {
        userLocation = viewModel.getUserLocation();
        String distance = AppConstants.DISTANCE;
        String lat = String.valueOf(userLocation.getLatitude());
        String lng = String.valueOf(userLocation.getLongitude());

        if (NetworkUtils.isNetworkConnected(getContext())) {
            viewModel.fetchBankAtmInsertRoom(distance, lat, lng).observe(this, bankAtms -> {
                adapter.setBankAtms(bankAtms);
            });
        } else {
            viewModel.fetchBankAtmFromRoom().observe(this, bankAtms -> {
                adapter.setBankAtms(bankAtms);
            });
        }

    }

    @Override
    public void onBankAtmClicked(BankAtm bankAtm) {
        showDetailDialog(bankAtm);
    }

    private void showDetailDialog(BankAtm item) {
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

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "HATA : " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        AppLogger.e(throwable.getMessage());
    }

    @Override
    public void showNotFoundMessage() {
        AppUtils.showDataNotFoundAlertToUser(getContext());
    }
}
