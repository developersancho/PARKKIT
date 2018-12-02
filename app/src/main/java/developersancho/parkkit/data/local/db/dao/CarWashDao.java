package developersancho.parkkit.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import developersancho.parkkit.data.model.db.CarWashEntity;

@Dao
public interface CarWashDao {

    @Insert
    void insert(CarWashEntity carWashEntity);

    @Insert
    void insertAll(List<CarWashEntity> carWashEntityList);

    @Delete
    void delete(CarWashEntity carWashEntity);

    @Query("DELETE FROM carwash_table")
    void deleteAll();

    @Query("SELECT * FROM carwash_table ORDER BY distance ASC")
    List<CarWashEntity> getAllCarWash();
}
