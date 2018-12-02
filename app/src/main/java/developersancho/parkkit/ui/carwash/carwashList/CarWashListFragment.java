package developersancho.parkkit.ui.carwash.carwashList;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.carwash.CarWash;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentCarWashListBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.ui.main.MainActivity;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.MessageUtils;
import developersancho.parkkit.utils.NetworkUtils;


public class CarWashListFragment extends BaseFragment<FragmentCarWashListBinding, CarWashListViewModel>
        implements ICarWashListNavigator, CarWashListAdapter.CarWashAdapterListener {

    public static final String TAG = CarWashListFragment.class.getSimpleName();
    private FragmentCarWashListBinding binding;
    private CarWashListAdapter adapter;
    private CarWashListViewModel viewModel;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car_wash_list;
    }

    @Override
    public CarWashListViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(CarWashListViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
        adapter = new CarWashListAdapter(getContext());
        adapter.setCarWashAdapterListener(this::onCarWashClicked);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUp();
        subscribeToCarWashList();
    }

    private void setUp() {
        binding.recyclerViewCarWash.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewCarWash.setHasFixedSize(true);
        binding.recyclerViewCarWash.setItemAnimator(new DefaultItemAnimator());
        binding.recyclerViewCarWash.setAdapter(adapter);
    }

    private void subscribeToCarWashList() {
        userLocation = viewModel.getUserLocation();
        String distance = AppConstants.DISTANCE;
        String lat = String.valueOf(userLocation.getLatitude());
        String lng = String.valueOf(userLocation.getLongitude());

        if (NetworkUtils.isNetworkConnected(getContext())) {
            viewModel.fetchCarWashesInsertRoom(distance, lat, lng).observe(this, carWashes -> {
                adapter.setCarWashes(carWashes);
            });
        } else {
            viewModel.fetchCarWashesFromRoom().observe(this, carWashes -> {
                adapter.setCarWashes(carWashes);
            });
        }

    }

    @Override
    public void onCarWashClicked(CarWash carWash) {
        Toast.makeText(getContext(), "Address: " + carWash.getAddress(), Toast.LENGTH_SHORT).show();
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
