package developersancho.parkkit.ui.carservice.carserviceMap;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.R;
import developersancho.parkkit.BR;
import developersancho.parkkit.data.model.api.carservice.CarService;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.FragmentCarServiceMapBinding;
import developersancho.parkkit.ui.base.BaseFragment;
import developersancho.parkkit.utils.AppLogger;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarServiceMapFragment extends BaseFragment<FragmentCarServiceMapBinding, CarServiceMapViewModel> implements ICarServiceMapNavigator,
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnMarkerClickListener, GoogleMap.OnMapClickListener {

    private FragmentCarServiceMapBinding binding;
    private CarServiceMapViewModel viewModel;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private UserLocation userLocation;
    private RelativeLayout layoutBottomSheet;
    private BottomSheetBehavior sheetBehavior;
    private TextView txtNameMarker, txtDistanceMarker, txtAddressMarker, txtTypeMarker;
    private Button btnShareMarker;
    private ImageView imgLogoMarker;
    private FloatingActionButton fabRoad;
    private Marker mSelectedMarker;
    private List<CarService> itemList = new ArrayList<>();

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_car_service_map;
    }

    @Override
    public CarServiceMapViewModel getViewModel() {
        return viewModel = ViewModelProviders.of(this).get(CarServiceMapViewModel.class);
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
        initBottomSheetDetail(view);
        setUpUI();
        subscribeToCarServiceList();
    }

    private void subscribeToCarServiceList() {
        userLocation = viewModel.getUserLocation();
        viewModel.fetchCarServiceFromRoom().observe(this, carServices -> {
            displayDataOnMap(carServices);
        });

    }

    private void displayDataOnMap(List<CarService> carServices) {
        mMap.clear();
        itemList = carServices;
        for (CarService carService : carServices) {
            LatLng place = new LatLng(carService.getxCoor(), carService.getyCoor());
            MarkerOptions options = new MarkerOptions();
            options.position(place)
                    .title(carService.getName())
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
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapCarService);
        mapFragment.getMapAsync(this);
    }

    private void initBottomSheetDetail(View rootView) {
        layoutBottomSheet = (RelativeLayout) rootView.findViewById(R.id.bottom_detail);
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        txtNameMarker = (TextView) rootView.findViewById(R.id.txtNameMarker);
        txtDistanceMarker = (TextView) rootView.findViewById(R.id.txtDistanceMarker);
        txtAddressMarker = (TextView) rootView.findViewById(R.id.txtAddressMarker);
        txtTypeMarker = (TextView) rootView.findViewById(R.id.txtTypeMarker);
        imgLogoMarker = (ImageView) rootView.findViewById(R.id.imgLogoMarker);
        btnShareMarker = (Button) rootView.findViewById(R.id.btnShareMarker);
        fabRoad = (FloatingActionButton) rootView.findViewById(R.id.road_fab);

        btnShareMarker.setOnClickListener(v -> {
            String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?daddr=%f,%f (%s)",
                    Double.valueOf(mSelectedMarker.getPosition().latitude),
                    Double.valueOf(mSelectedMarker.getPosition().longitude),
                    mSelectedMarker.getTitle());

            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String ShareSub = "Here is my location";
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, uri);
            startActivity(Intent.createChooser(sharingIntent, getString(R.string.shareVia)));
        });

        fabRoad.setOnClickListener(v -> {
            double lat = mSelectedMarker.getPosition().latitude;
            double lng = mSelectedMarker.getPosition().longitude;

            /*String uri = String.format(Locale.ENGLISH, "geo:%f,%f", lat, lng);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent.createChooser(intent, getResources().getString(R.string.chooseMapApp)));*/

            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Open Google Maps?")
                    .setCancelable(true)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            String latitude = String.valueOf(lat);
                            String longitude = String.valueOf(lng);
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

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setOnMapClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        mSelectedMarker = marker;
        if (marker.getTitle().equals(getString(R.string.locationUserTitle))) return;
        showBottomSheetDialog(mSelectedMarker);
    }

    private void showBottomSheetDialog(Marker mSelectedMarker) {
        for (CarService item : itemList) {
            if (item.getName().equals(mSelectedMarker.getTitle())) {
                txtNameMarker.setText(item.getName());
                txtAddressMarker.setText(item.getAddress());
                DecimalFormat df = new DecimalFormat("#.##");
                txtDistanceMarker.setText(String.valueOf(df.format(Double.valueOf(item.getDistance()))) + " Km");
                imgLogoMarker.setBackground(getResources().getDrawable(R.drawable.car_plant));
                txtTypeMarker.setText(item.getBrand());
            }
        }

        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
    }

    @Override
    public void handleError(Throwable throwable) {
        Toast.makeText(getContext(), "HATA : " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        AppLogger.e(throwable.getMessage());
    }
}
