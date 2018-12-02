package developersancho.parkkit.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import developersancho.parkkit.data.model.db.ParkEntity;

@Dao
public interface ParkDao {

    @Insert
    void insert(ParkEntity parkEntity);

    @Insert
    void insertAll(List<ParkEntity> parkEntityList);

    @Delete
    void delete(ParkEntity parkEntity);

    @Query("DELETE FROM park_table")
    void deleteAll();

    @Query("SELECT * FROM park_table ORDER BY distance ASC")
    List<ParkEntity> getAllParks();
}
