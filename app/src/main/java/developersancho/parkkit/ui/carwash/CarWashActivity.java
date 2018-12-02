package developersancho.parkkit.ui.carwash;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import developersancho.parkkit.R;
import developersancho.parkkit.ui.base.BaseSwitchActivity;
import developersancho.parkkit.ui.carwash.carwashList.CarWashListFragment;
import developersancho.parkkit.ui.carwash.carwashMap.CarWashMapFragment;

import android.content.Context;
import android.content.Intent;

import com.polyak.iconswitch.IconSwitch;

public class CarWashActivity extends BaseSwitchActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_car_wash;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbarCarWash);
    }

    @Override
    public IconSwitch getIconSwitch() {
        return findViewById(R.id.iconSwitchCarWash);
    }

    @Override
    public int getContainerViewId() {
        return R.id.containerCarWash;
    }

    @Override
    public Fragment getListFragment() {
        return new CarWashListFragment();
    }

    @Override
    public Fragment getMapFragment() {
        return new CarWashMapFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, CarWashActivity.class);
        return intent;
    }
}
