package developersancho.parkkit.ui.gasstation.gasList;


import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.View;
import android.widget.Toast;
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
        Toast.makeText(getContext(), "Address: " + gas.getAddress(), Toast.LENGTH_SHORT).show();
    }
}
