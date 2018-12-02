package developersancho.parkkit.ui.carwash.carwashMap;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.carwash.CarWash;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentCarWashMapBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppLogger;


public class CarWashMapFragment extends BaseFragment<FragmentCarWashMapBinding, CarWashMapViewModel> implements ICarWashMapNavigator,
        OnMapReadyCallback {

    private FragmentCarWashMapBinding binding;
    private CarWashMapViewModel viewModel;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car_wash_map;
    }

    @Override
    public CarWashMapViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(CarWashMapViewModel.class);
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
        subscribeToCarWashList();
    }

    private void subscribeToCarWashList() {
        userLocation = viewModel.getUserLocation();
        viewModel.fetchCarWashFromRoom().observe(this, carWashes -> {
            displayDataOnMap(carWashes);
        });

    }

    private void displayDataOnMap(List<CarWash> carWashes) {
        mMap.clear();
        for (CarWash carWash : carWashes) {
            LatLng place = new LatLng(carWash.getxCoor(), carWash.getyCoor());
            MarkerOptions options = new MarkerOptions();
            options.position(place)
                    .title(carWash.getName())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(options);
        }

        pinMyLocation();
    }

    private void pinMyLocation() {
        LatLng place = new LatLng(userLocation.getLatitude(), userLocation.getLongitude());
        MarkerOptions options = new MarkerOptions();
        options.position(place)
                .title(getString(R.string.locationUserTitle))
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
        mMap.addMarker(options);
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(place)
                .zoom(15f)
                .bearing(0f)
                .tilt(45f)
                .build();

        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void setUpUI() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapCarWash);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "HATA : " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        AppLogger.e(throwable.getMessage());
    }
}
