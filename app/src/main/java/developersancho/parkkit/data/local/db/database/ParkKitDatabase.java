package developersancho.parkkit.data.local.db.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import developersancho.parkkit.data.local.db.dao.BankAtmDao;
import developersancho.parkkit.data.local.db.dao.CarServiceDao;
import developersancho.parkkit.data.local.db.dao.CarWashDao;
import developersancho.parkkit.data.local.db.dao.GasDao;
import developersancho.parkkit.data.local.db.dao.ParkDao;
import developersancho.parkkit.data.model.db.BankAtmEntity;
import developersancho.parkkit.data.model.db.CarServiceEntity;
import developersancho.parkkit.data.model.db.CarWashEntity;
import developersancho.parkkit.data.model.db.GasEntity;
import developersancho.parkkit.data.model.db.ParkEntity;
import developersancho.parkkit.utils.AppConstants;

@Database(entities = {ParkEntity.class, GasEntity.class, BankAtmEntity.class, CarServiceEntity.class, CarWashEntity.class},
        version = 3, exportSchema = false)
public abstract class ParkKitDatabase extends RoomDatabase {
    public abstract ParkDao parkDao();

    public abstract GasDao gasDao();

    public abstract BankAtmDao bankAtmDao();

    public abstract CarServiceDao carServiceDao();

    public abstract CarWashDao carWashDao();

    private static ParkKitDatabase instance;

    public static synchronized ParkKitDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), ParkKitDatabase.class, AppConstants.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }
}
