package developersancho.parkkit.data.local.pref;

import developersancho.parkkit.data.model.others.UserLocation;

public interface ISessionManager {
    void saveUserLocation(String json);

    UserLocation getUserLocation();
}
