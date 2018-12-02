package developersancho.parkkit.ui.park.parkMap;


import android.Manifest;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.api.parking.Park;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentParkMapBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class ParkMapFragment extends BaseFragment<FragmentParkMapBinding, ParkMapViewModel> implements IParkMapNavigator,
        OnMapReadyCallback {

    private FragmentParkMapBinding binding;
    private ParkMapViewModel viewModel;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_park_map;
    }

    @Override
    public ParkMapViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(ParkMapViewModel.class);
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
        subscribeToParkList();
    }

    private void subscribeToParkList() {
        userLocation = viewModel.getUserLocation();
        String distance = AppConstants.DISTANCE;
        String lat = String.valueOf(userLocation.getLatitude());
        String lng = String.valueOf(userLocation.getLongitude());
        /*viewModel.fetchParks(distance, lat, lng).observe(this, parks -> {
            displayDataOnMap(parks);
        });*/
        viewModel.fetchParksFromRoom().observe(this, parks -> {
            displayDataOnMap(parks);
        });

    }

    private void displayDataOnMap(List<Park> parks) {
        mMap.clear();
        for (Park park : parks) {
            LatLng place = new LatLng(park.getxCoor(), park.getyCoor());
            MarkerOptions options = new MarkerOptions();
            options.position(place)
                    .title(park.getName())
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

        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(place, 14));
    }

    private void setUpUI() {
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapPark);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        /*mMap.addMarker(new MarkerOptions()
                .position(new LatLng(37.4233438, -122.0728817))
                .title("LinkedIn")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(37.4233438, -122.0728817), 10));*/
    }

    @Override
    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "HATA : " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        AppLogger.e(throwable.getMessage());
    }
}
