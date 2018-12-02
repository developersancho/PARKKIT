package developersancho.parkkit;

import android.app.Application;

import developersancho.parkkit.utils.AppLogger;

public class ParkKitApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.init();
    }
}
