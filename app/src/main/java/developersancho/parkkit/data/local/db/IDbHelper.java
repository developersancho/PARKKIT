package developersancho.parkkit.data.local.db;


import java.util.List;

import developersancho.parkkit.data.model.db.BankAtmEntity;
import developersancho.parkkit.data.model.db.CarServiceEntity;
import developersancho.parkkit.data.model.db.CarWashEntity;
import developersancho.parkkit.data.model.db.GasEntity;
import developersancho.parkkit.data.model.db.ParkEntity;
import io.reactivex.Observable;

public interface IDbHelper {
    void insertPark(ParkEntity parkEntity);

    void insertParkList(List<ParkEntity> parkEntityList);

    void deletePark(ParkEntity parkEntity);

    void deleteParkAll();

    Observable<List<ParkEntity>> getAllParks();


    void insertGas(GasEntity gasEntity);

    void insertGasList(List<GasEntity> gasEntityList);

    void deleteGas(GasEntity gasEntity);

    void deleteGasAll();

    Observable<List<GasEntity>> getAllGases();


    void insertBankAtm(BankAtmEntity entity);

    void insertBankAtmList(List<BankAtmEntity> entityList);

    void deleteBankAtm(BankAtmEntity entity);

    void deleteBankAtmAll();

    Observable<List<BankAtmEntity>> getAllBankAtm();


    void insertCarService(CarServiceEntity entity);

    void insertCarServiceList(List<CarServiceEntity> entityList);

    void deleteCarService(CarServiceEntity entity);

    void deleteCarServiceAll();

    Observable<List<CarServiceEntity>> getAllCarService();


    void insertCarWash(CarWashEntity entity);

    void insertCarWashList(List<CarWashEntity> entityList);

    void deleteCarWash(CarWashEntity entity);

    void deleteCarWashAll();

    Observable<List<CarWashEntity>> getAllCarWash();
}
