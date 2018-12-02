package developersancho.parkkit.ui.bankatm;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import developersancho.parkkit.R;
import developersancho.parkkit.ui.bankatm.bankatmList.BankAtmListFragment;
import developersancho.parkkit.ui.bankatm.bankatmMap.BankAtmMapFragment;
import developersancho.parkkit.ui.base.BaseSwitchActivity;

import android.content.Context;
import android.content.Intent;

import com.polyak.iconswitch.IconSwitch;

public class BankAtmActivity extends BaseSwitchActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_bank_atm;
    }

    @Override
    public Toolbar getToolbar() {
        return findViewById(R.id.toolbarBankAtm);
    }

    @Override
    public IconSwitch getIconSwitch() {
        return findViewById(R.id.iconSwitchBankAtm);
    }

    @Override
    public int getContainerViewId() {
        return R.id.containerBankAtm;
    }

    @Override
    public Fragment getListFragment() {
        return new BankAtmListFragment();
    }

    @Override
    public Fragment getMapFragment() {
        return new BankAtmMapFragment();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, BankAtmActivity.class);
        return intent;
    }
}
