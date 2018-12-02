package developersancho.parkkit.ui.gasstation;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import developersancho.parkkit.R;
import developersancho.parkkit.ui.base.BaseSwitchActivity;
import developersancho.parkkit.ui.gasstation.gasList.GasListFragment;
import developersancho.parkkit.ui.gasstation.gasMap.GasMapFragment;

import android.content.Context;
import android.content.Intent;

import com.polyak.iconswitch.IconSwitch;

public class GasStationActivity extends BaseSwitchActivity {


    @Override
    public int getLayoutId() {
        return R.layout.activity_gas_station;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbarGas);
    }

    @Override
    public IconSwitch getIconSwitch() {
        return findViewById(R.id.iconSwitchGas);
    }

    @Override
    public int getContainerViewId() {
        return R.id.containerGas;
    }

    @Override
    public Fragment getListFragment() {
        return new GasListFragment();
    }

    @Override
    public Fragment getMapFragment() {
        return new GasMapFragment();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, GasStationActivity.class);
        return intent;
    }

}
