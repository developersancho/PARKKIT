package developersancho.parkkit.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import developersancho.parkkit.data.model.db.BankAtmEntity;

@Dao
public interface BankAtmDao {
    @Insert
    void insert(BankAtmEntity bankAtmEntity);

    @Insert
    void insertAll(List<BankAtmEntity> bankAtmEntityList);

    @Delete
    void delete(BankAtmEntity bankAtmEntity);

    @Query("DELETE FROM bankatm_table")
    void deleteAll();

    @Query("SELECT * FROM bankatm_table ORDER BY distance ASC")
    List<BankAtmEntity> getAllBankAtm();
}
