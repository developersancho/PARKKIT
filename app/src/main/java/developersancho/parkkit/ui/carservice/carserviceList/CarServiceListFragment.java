package developersancho.parkkit.ui.carservice.carserviceList;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.carservice.CarService;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentCarServiceListBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.MessageUtils;
import developersancho.parkkit.utils.NetworkUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarServiceListFragment extends BaseFragment<FragmentCarServiceListBinding, CarServiceListViewModel>
        implements ICarServiceListNavigator, CarServiceListAdapter.CarServiceAdapterListener {

    private FragmentCarServiceListBinding binding;
    private CarServiceListViewModel viewModel;
    private CarServiceListAdapter adapter;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car_service_list;
    }

    @Override
    public CarServiceListViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(CarServiceListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        adapter = new CarServiceListAdapter(getContext());
        adapter.setCarServiceAdapterListener(this::onCarServiceClicked);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
        subscribeToCarServiceList();
    }

    private void setUp() {
        binding.recyclerViewCarService.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCarService.setHasFixedSize(true);
        binding.recyclerViewCarService.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewCarService.setAdapter(adapter);
    }

    private void subscribeToCarServiceList() {
        userLocation = viewModel.getUserLocation();
        String distance = AppConstants.DISTANCE;
        String lat = String.valueOf(userLocation.getLatitude());
        String lng = String.valueOf(userLocation.getLongitude());

        if (NetworkUtils.isNetworkConnected(getContext())) {
            viewModel.fetchCarServicesInsertRoom(distance, lat, lng).observe(this, carServices -> {
                adapter.setCarServices(carServices);
            });
        } else {
            viewModel.fetchCarServicesFromRoom().observe(this, carServices -> {
                adapter.setCarServices(carServices);
            });
        }

    }

    @Override
    public void onCarServiceClicked(CarService carService) {
        Toast.makeText(getContext(), "Address: " + carService.getAddress(), Toast.LENGTH_SHORT).show();
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
