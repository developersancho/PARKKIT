package developersancho.parkkit.data.local.db;

import android.app.Application;

import java.util.List;
import java.util.concurrent.Callable;

import developersancho.parkkit.data.local.db.dao.BankAtmDao;
import developersancho.parkkit.data.local.db.dao.CarServiceDao;
import developersancho.parkkit.data.local.db.dao.CarWashDao;
import developersancho.parkkit.data.local.db.dao.GasDao;
import developersancho.parkkit.data.local.db.dao.ParkDao;
import developersancho.parkkit.data.local.db.database.ParkKitDatabase;
import developersancho.parkkit.data.model.db.BankAtmEntity;
import developersancho.parkkit.data.model.db.CarServiceEntity;
import developersancho.parkkit.data.model.db.CarWashEntity;
import developersancho.parkkit.data.model.db.GasEntity;
import developersancho.parkkit.data.model.db.ParkEntity;
import io.reactivex.Observable;

public class DbHelper implements IDbHelper {
    private ParkDao parkDao;
    private GasDao gasDao;
    private BankAtmDao bankAtmDao;
    private CarWashDao carWashDao;
    private CarServiceDao carServiceDao;

    public DbHelper(Application application) {
        ParkKitDatabase database = ParkKitDatabase.getInstance(application);
        parkDao = database.parkDao();
        gasDao = database.gasDao();
        bankAtmDao = database.bankAtmDao();
        carWashDao = database.carWashDao();
        carServiceDao = database.carServiceDao();
    }


    @Override
    public void insertPark(ParkEntity parkEntity) {
        parkDao.insert(parkEntity);
    }

    @Override
    public void insertParkList(List<ParkEntity> parkEntityList) {
        parkDao.insertAll(parkEntityList);
    }

    @Override
    public void deletePark(ParkEntity parkEntity) {
        parkDao.delete(parkEntity);
    }

    @Override
    public void deleteParkAll() {
        parkDao.deleteAll();
    }

    @Override
    public Observable<List<ParkEntity>> getAllParks() {
        return Observable.fromCallable(() -> parkDao.getAllParks());
    }

    @Override
    public void insertGas(GasEntity gasEntity) {
        gasDao.insert(gasEntity);
    }

    @Override
    public void insertGasList(List<GasEntity> gasEntityList) {
        gasDao.insertAll(gasEntityList);
    }

    @Override
    public void deleteGas(GasEntity gasEntity) {
        gasDao.delete(gasEntity);
    }

    @Override
    public void deleteGasAll() {
        gasDao.deleteAll();
    }

    @Override
    public Observable<List<GasEntity>> getAllGases() {
        return Observable.fromCallable(new Callable<List<GasEntity>>() {
            @Override
            public List<GasEntity> call() {
                return gasDao.getAllGases();
            }
        });
    }

    @Override
    public void insertBankAtm(BankAtmEntity entity) {
        bankAtmDao.insert(entity);
    }

    @Override
    public void insertBankAtmList(List<BankAtmEntity> entityList) {
        bankAtmDao.insertAll(entityList);
    }

    @Override
    public void deleteBankAtm(BankAtmEntity entity) {
        bankAtmDao.delete(entity);
    }

    @Override
    public void deleteBankAtmAll() {
        bankAtmDao.deleteAll();
    }

    @Override
    public Observable<List<BankAtmEntity>> getAllBankAtm() {
        return Observable.fromCallable(new Callable<List<BankAtmEntity>>() {
            @Override
            public List<BankAtmEntity> call() {
                return bankAtmDao.getAllBankAtm();
            }
        });
    }

    @Override
    public void insertCarService(CarServiceEntity entity) {
        carServiceDao.insert(entity);
    }

    @Override
    public void insertCarServiceList(List<CarServiceEntity> entityList) {
        carServiceDao.insertAll(entityList);
    }

    @Override
    public void deleteCarService(CarServiceEntity entity) {
        carServiceDao.delete(entity);
    }

    @Override
    public void deleteCarServiceAll() {
        carServiceDao.deleteAll();
    }

    @Override
    public Observable<List<CarServiceEntity>> getAllCarService() {
        return Observable.fromCallable(new Callable<List<CarServiceEntity>>() {
            @Override
            public List<CarServiceEntity> call() {
                return carServiceDao.getAllCarService();
            }
        });
    }

    @Override
    public void insertCarWash(CarWashEntity entity) {
        carWashDao.insert(entity);
    }

    @Override
    public void insertCarWashList(List<CarWashEntity> entityList) {
        carWashDao.insertAll(entityList);
    }

    @Override
    public void deleteCarWash(CarWashEntity entity) {
        carWashDao.delete(entity);
    }

    @Override
    public void deleteCarWashAll() {
        carWashDao.deleteAll();
    }

    @Override
    public Observable<List<CarWashEntity>> getAllCarWash() {
        return Observable.fromCallable(new Callable<List<CarWashEntity>>() {
            @Override
            public List<CarWashEntity> call() {
                return carWashDao.getAllCarWash();
            }
        });
    }
}
