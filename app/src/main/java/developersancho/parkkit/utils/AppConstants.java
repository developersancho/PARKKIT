package developersancho.parkkit.utils;

import developersancho.parkkit.BuildConfig;

public class AppConstants {
    public static final String TIMESTAMP_FORMAT = "yyyyMMdd_HHmmss";
    public static final String DB_NAME = "parkkit.db";
    public static final String PREF_NAME = "parkkit_pref";
    public static final int PRIVATE_MODE = 0;
    public static final String LOCATION = "USER_LOCATION";
    public static final String BASE_URL = BuildConfig.BASE_URL;
    public static final String API_KEY = BuildConfig.API_KEY;
    public static final int cacheSize = 10 * 1024 * 1024; // 10 MiB
    public static final int DELAY_TIME = 2500;
    public static final String DISTANCE = "1000"; // 1 km
    public static final String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";
    public static final String GPS_ACTION = "android.location.PROVIDERS_CHANGED";//"android.location.GPS_ENABLED_CHANGE";
    public static final int DURATION_COLOR_CHANGE_MS = 300;
}
