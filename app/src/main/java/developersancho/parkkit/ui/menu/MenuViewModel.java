package developersancho.parkkit.ui.menu;

import android.app.Application;

import androidx.annotation.NonNull;
import developersancho.parkkit.ui.base.BaseViewModel;

public class MenuViewModel extends BaseViewModel<IMenuNavigator> {

    public MenuViewModel(@NonNull Application application) {
        super(application);
    }

    public void onGotoParkActivity() {
        getNavigator().openParkActivity();
    }

    public void onGotoGasStationActivity() {
        getNavigator().openGasStationActivity();
    }

    public void onGotoCarWashActivity() {
        getNavigator().openCarWashActivity();
    }

    public void onGotoCarServiceActivity() {
        getNavigator().openCarServiceActivity();
    }

    public void onGotoBankAtmActivity() {
        getNavigator().openBankAtmActivity();
    }

}
