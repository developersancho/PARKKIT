package developersancho.parkkit.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import developersancho.parkkit.data.model.db.GasEntity;

@Dao
public interface GasDao {
    @Insert
    void insert(GasEntity gasEntity);

    @Insert
    void insertAll(List<GasEntity> gasEntityList);

    @Delete
    void delete(GasEntity gasEntity);

    @Query("DELETE FROM gas_table")
    void deleteAll();

    @Query("SELECT * FROM gas_table ORDER BY distance ASC")
    List<GasEntity> getAllGases();
}
