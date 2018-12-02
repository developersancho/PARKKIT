package developersancho.parkkit.data.local.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.GsonBuilder;

import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.utils.AppConstants;

public class SessionManager implements ISessionManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context context;

    public SessionManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(AppConstants.PREF_NAME, AppConstants.PRIVATE_MODE);
        editor = pref.edit();
    }


    @Override
    public void saveUserLocation(String json) {
        editor.putString(AppConstants.LOCATION, json);
        editor.commit();
    }

    @Override
    public UserLocation getUserLocation() {
        UserLocation userLocation = new GsonBuilder().create()
                .fromJson(pref.getString(AppConstants.LOCATION, null), UserLocation.class);
        return userLocation;
    }


}
