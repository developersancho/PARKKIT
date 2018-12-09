package developersancho.parkkit.utils;

import android.content.Context;

import developersancho.parkkit.ui.menu.MenuActivity;

public class NavigationUtils {
    public static Context context;
    public static MenuActivity menuActivity;

    public static void initialize(Context ctx, MenuActivity activity) {
        context = ctx;
        menuActivity = activity;
    }

    public static void setFree() {
        context = null;
        menuActivity = null;
    }
}
