package developersancho.parkkit.ui.carservice;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import developersancho.parkkit.R;
import developersancho.parkkit.ui.base.BaseSwitchActivity;
import developersancho.parkkit.ui.carservice.carserviceList.CarServiceListFragment;
import developersancho.parkkit.ui.carservice.carserviceMap.CarServiceMapFragment;

import android.content.Context;
import android.content.Intent;

import com.polyak.iconswitch.IconSwitch;

public class CarServiceActivity extends BaseSwitchActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_car_service;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbarCarService);
    }

    @Override
    public IconSwitch getIconSwitch() {
        return findViewById(R.id.iconSwitchCarService);
    }

    @Override
    public int getContainerViewId() {
        return R.id.containerCarService;
    }

    @Override
    public Fragment getListFragment() {
        return new CarServiceListFragment();
    }

    @Override
    public Fragment getMapFragment() {
        return new CarServiceMapFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CarServiceActivity.class);
        return intent;
    }
}
