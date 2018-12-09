package developersancho.parkkit.ui.main;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import developersancho.parkkit.BR;
import developersancho.parkkit.R;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.databinding.ActivityMainBinding;
import developersancho.parkkit.service.GpsBR;
import developersancho.parkkit.service.InternetBR;
import developersancho.parkkit.ui.bankatm.BankAtmActivity;
import developersancho.parkkit.ui.base.BaseActivity;
import developersancho.parkkit.ui.carservice.CarServiceActivity;
import developersancho.parkkit.ui.carwash.CarWashActivity;
import developersancho.parkkit.ui.gasstation.GasStationActivity;
import developersancho.parkkit.ui.park.ParkActivity;
import developersancho.parkkit.utils.AppConstants;
import developersancho.parkkit.utils.AppLogger;
import developersancho.parkkit.utils.AppUtils;
import developersancho.parkkit.utils.CommonUtils;
import developersancho.parkkit.utils.GpsUtils;
import developersancho.parkkit.utils.MessageUtils;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.github.florent37.rxgps.RxGps;
import com.google.gson.Gson;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements IMainNavigator {

    private MainViewModel mainViewModel;
    private ActivityMainBinding activityMainBinding;
    private InternetBR internetBR = new InternetBR();
    private GpsBR gpsBR = new GpsBR();

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        return mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = getViewDataBinding();
        mainViewModel.setNavigator(this);
        setToolbar();
        registerReceiverInternet();
        registerReceiverGps();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!GpsUtils.isGpsEnabled(MainActivity.this)) {
            AppUtils.showGPSDisabledAlertToUser(MainActivity.this);
        }
        findLocation();
        findLocationAddress();
        DebugDB.getAddressLog();
    }

    private void registerReceiverGps() {
        IntentFilter filter = new IntentFilter(AppConstants.GPS_ACTION);
        registerReceiver(gpsBR, filter);
    }


    private void registerReceiverInternet() {
        IntentFilter filter = new IntentFilter(AppConstants.CONNECTIVITY_ACTION);
        registerReceiver(internetBR, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(internetBR);
        unregisterReceiver(gpsBR);
    }

    private void findLocation() {
        new RxGps(this).locationHight()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(location -> {
                    //you've got the location
                    UserLocation userLocation = new UserLocation();
                    userLocation.setLatitude(location.getLatitude());
                    userLocation.setLongitude(location.getLongitude());
                    mainViewModel.setUserLocation(new Gson().toJson(userLocation));
                    AppLogger.d("Location", mainViewModel.getUserLocation().getLatitude() + ", " + mainViewModel.getUserLocation().getLongitude());
                    //Toast.makeText(this, mainViewModel.getUserLocation().getLatitude() + ", " + mainViewModel.getUserLocation().getLongitude(), Toast.LENGTH_SHORT).show();
                }, throwable -> {
                    if (throwable instanceof RxGps.PermissionException) {
                        //the user does not allow the permission
                        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    } else if (throwable instanceof RxGps.PlayServicesNotAvailableException) {
                        //the user do not have play services
                        Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void findLocationAddress() {
        activityMainBinding.loader.setVisibility(View.VISIBLE);
        RxGps rxGps = new RxGps(this);
        rxGps.locationHight().flatMapMaybe(rxGps::geocoding)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(address -> {
                    String addr = CommonUtils.getAddressText(address);
                    activityMainBinding.editSearch.setText(addr);
                    activityMainBinding.loader.setVisibility(View.INVISIBLE);
                }, throwable -> {
                    if (throwable instanceof RxGps.PermissionException) {
                        activityMainBinding.loader.setVisibility(View.INVISIBLE);
                    } else if (throwable instanceof RxGps.PlayServicesNotAvailableException) {
                        activityMainBinding.loader.setVisibility(View.INVISIBLE);
                    }
                });
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //toolbar.setTitle("PARKKIT");
        toolbar.setNavigationIcon(R.drawable.ic_dashboard_24dp);
        /*LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.toolbar_custom_view, null);
        toolbar.addView(mCustomView, new Toolbar.LayoutParams(Gravity.CENTER));*/

        /*setSupportActionBar(toolbar);
        View logo = getLayoutInflater().inflate(R.layout.toolbar_custom_view, toolbar);*/
    }

    @Override
    public void openParkActivity() {
        if (checkGPS()) return;

        Intent intent = ParkActivity.newIntent(MainActivity.this);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private boolean checkGPS() {
        if (!GpsUtils.isGpsEnabled(MainActivity.this)) {
            MessageUtils.showAlertDialog(MainActivity.this, "UYARI", "Lütfen konumunuzun açık olduğundan emin olunuz...");
            return true;
        }
        return false;
    }

    @Override
    public void openGasStationActivity() {
        if (checkGPS()) return;

        Intent intent = GasStationActivity.newIntent(MainActivity.this);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openCarWashActivity() {
        if (checkGPS()) return;

        Intent intent = CarWashActivity.newIntent(MainActivity.this);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openCarServiceActivity() {
        if (checkGPS()) return;

        Intent intent = CarServiceActivity.newIntent(MainActivity.this);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    @Override
    public void openBankAtmActivity() {
        if (checkGPS()) return;

        Intent intent = BankAtmActivity.newIntent(MainActivity.this);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
}
