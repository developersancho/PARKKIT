package developersancho.parkkit.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import developersancho.parkkit.ui.base.BaseViewModel;

public class MainViewModel extends BaseViewModel<IMainNavigator> {

    public MainViewModel(@NonNull Application application) {
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
