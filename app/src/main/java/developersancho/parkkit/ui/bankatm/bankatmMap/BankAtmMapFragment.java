package developersancho.parkkit.ui.bankatm.bankatmMap;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.R;
import developersancho.parkkit.BR;
import developersancho.parkkit.data.model.api.bankatm.BankAtm;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.databinding.FragmentBankAtmMapBinding;
import developersancho.parkkit.utils.AppLogger;


public class BankAtmMapFragment extends BaseFragment<FragmentBankAtmMapBinding, BankAtmMapViewModel> implements IBankAtmMapNavigator,
        OnMapReadyCallback {

    private FragmentBankAtmMapBinding binding;
    private BankAtmMapViewModel viewModel;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private UserLocation userLocation;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_bank_atm_map;
    }

    @Override
    public BankAtmMapViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(BankAtmMapViewModel.class);
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
        subscribeToBankAtmList();
    }

    private void subscribeToBankAtmList() {
        userLocation = viewModel.getUserLocation();
        viewModel.fetchBankAtmFromRoom().observe(this, bankAtms -> {
            displayDataOnMap(bankAtms);
        });

    }

    private void displayDataOnMap(List<BankAtm> bankAtms) {
        mMap.clear();
        for (BankAtm bankAtm : bankAtms) {
            LatLng place = new LatLng(bankAtm.getxCoor(), bankAtm.getyCoor());
            MarkerOptions options = new MarkerOptions();
            options.position(place)
                    .title(bankAtm.getName())
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
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapBankAtm);
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
