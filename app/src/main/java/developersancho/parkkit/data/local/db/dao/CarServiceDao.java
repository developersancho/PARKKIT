package developersancho.parkkit.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import developersancho.parkkit.data.model.db.CarServiceEntity;

@Dao
public interface CarServiceDao {
    @Insert
    void insert(CarServiceEntity carServiceEntity);

    @Insert
    void insertAll(List<CarServiceEntity> carServiceEntityList);

    @Delete
    void delete(CarServiceEntity carServiceEntity);

    @Query("DELETE FROM carservice_table")
    void deleteAll();

    @Query("SELECT * FROM carservice_table ORDER BY distance ASC")
    List<CarServiceEntity> getAllCarService();
}
