package developersancho.parkkit.data.repository;

import developersancho.parkkit.data.local.db.IDbHelper;
import developersancho.parkkit.data.local.pref.ISessionManager;
import developersancho.parkkit.data.remote.api.IApiHelper;

public interface IDataManager extends IApiHelper, ISessionManager, IDbHelper {
}
