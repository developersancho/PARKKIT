package developersancho.parkkit.data.repository;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import developersancho.parkkit.data.local.db.DbHelper;
import developersancho.parkkit.data.local.pref.SessionManager;
import developersancho.parkkit.data.model.db.BankAtmEntity;
import developersancho.parkkit.data.model.db.CarServiceEntity;
import developersancho.parkkit.data.model.db.CarWashEntity;
import developersancho.parkkit.data.model.db.GasEntity;
import developersancho.parkkit.data.model.db.ParkEntity;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.data.remote.api.ApiHelper;
import developersancho.parkkit.data.remote.api.retrofit.IBankAtmService;
import developersancho.parkkit.data.remote.api.retrofit.ICarService;
import developersancho.parkkit.data.remote.api.retrofit.ICarWashService;
import developersancho.parkkit.data.remote.api.retrofit.IGasService;
import developersancho.parkkit.data.remote.api.retrofit.IParkService;
import io.reactivex.Observable;

public class DataManager implements IDataManager {
    private ApiHelper apiHelper;
    private SessionManager sessionManager;
    private DbHelper dbHelper;

    public DataManager(Application application) {
        apiHelper = new ApiHelper();
        sessionManager = new SessionManager(application);
        dbHelper = new DbHelper(application);
    }

    @Override
    public IParkService createParkService() {
        return apiHelper.createParkService();
    }

    @Override
    public IGasService createGasService() {
        return apiHelper.createGasService();
    }

    @Override
    public IBankAtmService createBankAtmService() {
        return apiHelper.createBankAtmService();
    }

    @Override
    public ICarService createCarService() {
        return apiHelper.createCarService();
    }

    @Override
    public ICarWashService createCarWashService() {
        return apiHelper.createCarWashService();
    }

    @Override
    public void saveUserLocation(String json) {
        sessionManager.saveUserLocation(json);
    }

    @Override
    public UserLocation getUserLocation() {
        return sessionManager.getUserLocation();
    }


    @Override
    public void insertPark(ParkEntity parkEntity) {
        new InsertParkAsyncTask(dbHelper).execute(parkEntity);
    }

    @Override
    public void insertParkList(List<ParkEntity> parkEntityList) {
        new InsertParkListAsyncTask(dbHelper).execute(parkEntityList);
    }

    @Override
    public void deletePark(ParkEntity parkEntity) {
        new DeleteParkAsyncTask(dbHelper).execute(parkEntity);
    }

    @Override
    public void deleteParkAll() {
        new DeleteAllParkAsyncTask(dbHelper).execute();
    }

    @Override
    public Observable<List<ParkEntity>> getAllParks() {
        return dbHelper.getAllParks();
    }

    @Override
    public void insertGas(GasEntity gasEntity) {
        new InsertGasAsyncTask(dbHelper).execute(gasEntity);
    }

    @Override
    public void insertGasList(List<GasEntity> gasEntityList) {
        new InsertGasListAsyncTask(dbHelper).execute(gasEntityList);
    }

    @Override
    public void deleteGas(GasEntity gasEntity) {
        new DeleteGasAsyncTask(dbHelper).execute(gasEntity);
    }

    @Override
    public void deleteGasAll() {
        new DeleteAllGasAsyncTask(dbHelper).execute();
    }

    @Override
    public Observable<List<GasEntity>> getAllGases() {
        return dbHelper.getAllGases();
    }

    @Override
    public void insertBankAtm(BankAtmEntity entity) {
        new InsertBankAtmAsyncTask(dbHelper).execute(entity);
    }

    @Override
    public void insertBankAtmList(List<BankAtmEntity> entityList) {
        new InsertBankAtmListAsyncTask(dbHelper).execute(entityList);
    }

    @Override
    public void deleteBankAtm(BankAtmEntity entity) {
        new DeleteBankAtmAsyncTask(dbHelper).execute(entity);
    }

    @Override
    public void deleteBankAtmAll() {
        new DeleteAllBankAtmAsyncTask(dbHelper).execute();
    }

    @Override
    public Observable<List<BankAtmEntity>> getAllBankAtm() {
        return dbHelper.getAllBankAtm();
    }

    @Override
    public void insertCarService(CarServiceEntity entity) {
        new InsertCarServiceAsyncTask(dbHelper).execute(entity);
    }

    @Override
    public void insertCarServiceList(List<CarServiceEntity> entityList) {
        new InsertCarServiceListAsyncTask(dbHelper).execute(entityList);
    }

    @Override
    public void deleteCarService(CarServiceEntity entity) {
        new DeleteCarServiceAsyncTask(dbHelper).execute(entity);
    }

    @Override
    public void deleteCarServiceAll() {
        new DeleteAllCarServiceAsyncTask(dbHelper).execute();
    }

    @Override
    public Observable<List<CarServiceEntity>> getAllCarService() {
        return dbHelper.getAllCarService();
    }

    @Override
    public void insertCarWash(CarWashEntity entity) {
        new InsertCarWashAsyncTask(dbHelper).execute(entity);
    }

    @Override
    public void insertCarWashList(List<CarWashEntity> entityList) {
        new InsertCarWashListAsyncTask(dbHelper).execute(entityList);
    }

    @Override
    public void deleteCarWash(CarWashEntity entity) {
        new DeleteCarWashAsyncTask(dbHelper).execute(entity);
    }

    @Override
    public void deleteCarWashAll() {
        new DeleteAllCarWashAsyncTask(dbHelper).execute();
    }

    @Override
    public Observable<List<CarWashEntity>> getAllCarWash() {
        return dbHelper.getAllCarWash();
    }


    private static class InsertParkAsyncTask extends AsyncTask<ParkEntity, Void, Void> {

        private DbHelper dbHelper;

        public InsertParkAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(ParkEntity... parkEntities) {
            dbHelper.insertPark(parkEntities[0]);
            return null;
        }
    }

    private static class DeleteParkAsyncTask extends AsyncTask<ParkEntity, Void, Void> {

        private DbHelper dbHelper;

        public DeleteParkAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(ParkEntity... parkEntities) {
            dbHelper.deletePark(parkEntities[0]);
            return null;
        }
    }

    private static class DeleteAllParkAsyncTask extends AsyncTask<Void, Void, Void> {

        private DbHelper dbHelper;

        public DeleteAllParkAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.deleteParkAll();
            return null;
        }
    }

    private static class InsertParkListAsyncTask extends AsyncTask<List<ParkEntity>, Void, Void> {
        private DbHelper dbHelper;

        public InsertParkListAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(List<ParkEntity>... lists) {
            dbHelper.insertParkList(lists[0]);
            return null;
        }
    }


    private static class InsertGasAsyncTask extends AsyncTask<GasEntity, Void, Void> {

        private DbHelper dbHelper;

        public InsertGasAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(GasEntity... gasEntities) {
            dbHelper.insertGas(gasEntities[0]);
            return null;
        }
    }

    private static class DeleteGasAsyncTask extends AsyncTask<GasEntity, Void, Void> {

        private DbHelper dbHelper;

        public DeleteGasAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(GasEntity... gasEntities) {
            dbHelper.deleteGas(gasEntities[0]);
            return null;
        }
    }

    private static class DeleteAllGasAsyncTask extends AsyncTask<Void, Void, Void> {

        private DbHelper dbHelper;

        public DeleteAllGasAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.deleteGasAll();
            return null;
        }
    }

    private static class InsertGasListAsyncTask extends AsyncTask<List<GasEntity>, Void, Void> {
        private DbHelper dbHelper;

        public InsertGasListAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(List<GasEntity>... lists) {
            dbHelper.insertGasList(lists[0]);
            return null;
        }
    }


    private static class InsertBankAtmAsyncTask extends AsyncTask<BankAtmEntity, Void, Void> {

        private DbHelper dbHelper;

        public InsertBankAtmAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(BankAtmEntity... bankAtmEntities) {
            dbHelper.insertBankAtm(bankAtmEntities[0]);
            return null;
        }
    }

    private static class DeleteBankAtmAsyncTask extends AsyncTask<BankAtmEntity, Void, Void> {

        private DbHelper dbHelper;

        public DeleteBankAtmAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(BankAtmEntity... bankAtmEntities) {
            dbHelper.deleteBankAtm(bankAtmEntities[0]);
            return null;
        }
    }

    private static class DeleteAllBankAtmAsyncTask extends AsyncTask<Void, Void, Void> {

        private DbHelper dbHelper;

        public DeleteAllBankAtmAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.deleteBankAtmAll();
            return null;
        }
    }

    private static class InsertBankAtmListAsyncTask extends AsyncTask<List<BankAtmEntity>, Void, Void> {
        private DbHelper dbHelper;

        public InsertBankAtmListAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(List<BankAtmEntity>... lists) {
            dbHelper.insertBankAtmList(lists[0]);
            return null;
        }
    }


    private static class InsertCarServiceAsyncTask extends AsyncTask<CarServiceEntity, Void, Void> {

        private DbHelper dbHelper;

        public InsertCarServiceAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(CarServiceEntity... carServiceEntities) {
            dbHelper.insertCarService(carServiceEntities[0]);
            return null;
        }
    }

    private static class DeleteCarServiceAsyncTask extends AsyncTask<CarServiceEntity, Void, Void> {

        private DbHelper dbHelper;

        public DeleteCarServiceAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(CarServiceEntity... carServiceEntities) {
            dbHelper.deleteCarService(carServiceEntities[0]);
            return null;
        }
    }

    private static class DeleteAllCarServiceAsyncTask extends AsyncTask<Void, Void, Void> {

        private DbHelper dbHelper;

        public DeleteAllCarServiceAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.deleteCarServiceAll();
            return null;
        }
    }

    private static class InsertCarServiceListAsyncTask extends AsyncTask<List<CarServiceEntity>, Void, Void> {
        private DbHelper dbHelper;

        public InsertCarServiceListAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(List<CarServiceEntity>... lists) {
            dbHelper.insertCarServiceList(lists[0]);
            return null;
        }
    }


    private static class InsertCarWashAsyncTask extends AsyncTask<CarWashEntity, Void, Void> {

        private DbHelper dbHelper;

        public InsertCarWashAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(CarWashEntity... carWashEntities) {
            dbHelper.insertCarWash(carWashEntities[0]);
            return null;
        }
    }

    private static class DeleteCarWashAsyncTask extends AsyncTask<CarWashEntity, Void, Void> {

        private DbHelper dbHelper;

        public DeleteCarWashAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(CarWashEntity... carWashEntities) {
            dbHelper.deleteCarWash(carWashEntities[0]);
            return null;
        }
    }

    private static class DeleteAllCarWashAsyncTask extends AsyncTask<Void, Void, Void> {

        private DbHelper dbHelper;

        public DeleteAllCarWashAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dbHelper.deleteCarWashAll();
            return null;
        }
    }

    private static class InsertCarWashListAsyncTask extends AsyncTask<List<CarWashEntity>, Void, Void> {
        private DbHelper dbHelper;

        public InsertCarWashListAsyncTask(DbHelper dbHelper) {
            this.dbHelper = dbHelper;
        }

        @Override
        protected Void doInBackground(List<CarWashEntity>... lists) {
            dbHelper.insertCarWashList(lists[0]);
            return null;
        }
    }
}
