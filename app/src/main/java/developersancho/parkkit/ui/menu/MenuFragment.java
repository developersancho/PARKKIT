package developersancho.parkkit.ui.menu;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.github.florent37.rxgps.RxGps;
import com.google.gson.Gson;

import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.R;
import developersancho.parkkit.BR;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentMenuBinding;
import developersancho.parkkit.ui.bankatm.BankAtmActivity;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.ui.carservice.CarServiceActivity;
import developersancho.parkkit.ui.carwash.CarWashActivity;
import developersancho.parkkit.ui.gasstation.GasStationActivity;
import developersancho.parkkit.ui.park.ParkActivity;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.CommonUtils;
import developersancho.parkkit.utils.GpsUtils;
import developersancho.parkkit.utils.MessageUtils;
import developersancho.parkkit.utils.NavigationUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class MenuFragment extends BaseFragment<FragmentMenuBinding, MenuViewModel> implements IMenuNavigator {

    private MenuViewModel viewModel;
    private FragmentMenuBinding binding;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_menu;
    }

    @Override
    public MenuViewModel getViewModel() {
        viewModel = ViewModelProviders.of(this).get(MenuViewModel.class);
        return viewModel;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.setNavigator(this);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = getViewDataBinding();
        setUpUI();
        findLocation();
        findLocationAddress();
    }

    private void setUpUI() {
        binding.homeDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavigationUtils.menuActivity.getDrawerLayout().openDrawer((int) GravityCompat.START);
            }
        });
    }

    @Override
    public void openParkActivity() {
        if (checkGPS()) return;
        Intent intent = ParkActivity.newIntent(getActivity());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openGasStationActivity() {
        if (checkGPS()) return;
        Intent intent = GasStationActivity.newIntent(getActivity());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openCarWashActivity() {
        if (checkGPS()) return;
        Intent intent = CarWashActivity.newIntent(getActivity());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openCarServiceActivity() {
        if (checkGPS()) return;
        Intent intent = CarServiceActivity.newIntent(getActivity());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openBankAtmActivity() {
        if (checkGPS()) return;
        Intent intent = BankAtmActivity.newIntent(getActivity());
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private boolean checkGPS() {
        if (!GpsUtils.isGpsEnabled(getContext())) {
            MessageUtils.showAlertDialog(getContext(), getString(R.string.warning_gps), getString(R.string.message_gps));
            return true;
        }
        return false;
    }

    private void findLocation() {
        new RxGps(getActivity()).locationHight()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    //you've got the location
                    UserLocation userLocation = new UserLocation();
                    userLocation.setLatitude(location.getLatitude());
                    userLocation.setLongitude(location.getLongitude());
                    viewModel.setUserLocation(new Gson().toJson(userLocation));
                    Log.d("Location: ", String.valueOf(viewModel.getUserLocation().getLatitude()) + ", "
                            + String.valueOf(viewModel.getUserLocation().getLongitude()));
                    AppLogger.d("Location", String.valueOf(viewModel.getUserLocation().getLatitude()) + ", "
                            + String.valueOf(viewModel.getUserLocation().getLongitude()));
                    //Toast.makeText(this, mainViewModel.getUserLocation().getLatitude() + ", " + mainViewModel.getUserLocation().getLongitude(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    if (throwable instanceof RxGps.PermissionException) {
                        //the user does not allow the permission
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (throwable instanceof RxGps.PlayServicesNotAvailableException) {
                        //the user do not have play services
                        Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void findLocationAddress() {
        binding.loader.setVisibility(View.VISIBLE);
        RxGps rxGps = new RxGps(getActivity());
        rxGps.locationHight().flatMapMaybe(rxGps::geocoding)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(address -> {
                    String addr = CommonUtils.getAddressText(address);
                    binding.editSearch.setText(addr);
                    binding.loader.setVisibility(View.INVISIBLE);
                }, throwable -> {
                    if (throwable instanceof RxGps.PermissionException) {
                        binding.loader.setVisibility(View.INVISIBLE);
                    } else if (throwable instanceof RxGps.PlayServicesNotAvailableException) {
                        binding.loader.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
