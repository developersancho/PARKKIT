package developersancho.parkkit.ui.park;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import developersancho.parkkit.R;
import developersancho.parkkit.ui.base.BaseSwitchActivity;
import developersancho.parkkit.ui.park.parkList.ParkListFragment;
import developersancho.parkkit.ui.park.parkMap.ParkMapFragment;

import android.content.Context;
import android.content.Intent;

import com.polyak.iconswitch.IconSwitch;

public class ParkActivity extends BaseSwitchActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_park;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbarPark);
    }

    @Override
    public IconSwitch getIconSwitch() {
        return findViewById(R.id.iconSwitchPark);
    }

    @Override
    public int getContainerViewId() {
        return R.id.containerPark;
    }

    @Override
    public Fragment getListFragment() {
        return new ParkListFragment();
    }

    @Override
    public Fragment getMapFragment() {
        return new ParkMapFragment();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ParkActivity.class);
        return intent;
    }

}
