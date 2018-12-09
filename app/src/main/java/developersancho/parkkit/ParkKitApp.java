package developersancho.parkkit;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import developersancho.parkkit.utils.AppLogger;

public class ParkKitApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppLogger.init();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
