package developersancho.parkkit.ui.splash;

import android.app.Application;

import androidx.annotation.NonNull;
import developersancho.parkkit.ui.base.BaseViewModel;

public class SplashViewModel extends BaseViewModel<ISplashNavigator> {

    public SplashViewModel(@NonNull Application application) {
        super(application);
    }
}
