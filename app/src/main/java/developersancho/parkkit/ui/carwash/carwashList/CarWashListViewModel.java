package developersancho.parkkit.ui.carwash.carwashList;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.carwash.CarWash;
import developersancho.parkkit.data.model.db.CarWashEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import developersancho.parkkit.utils.AppConstants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CarWashListViewModel extends BaseViewModel<ICarWashListNavigator> {

    private MutableLiveData<List<CarWash>> carWashList;

    public CarWashListViewModel(@NonNull Application application) {
        super(application);
        carWashList = new MutableLiveData<>();
    }

    public MutableLiveData<List<CarWash>> fetchCarWashesInsertRoom(String distance, String lat, String lng) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().createCarWashService().getCarWashList(distance, lat, lng, AppConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getCarWashes() != null) {
                        carWashList.setValue(response.getCarWashes());
                        insertCarWashEntity(response.getCarWashes());
                    } else {
                        getNavigator().showNotFoundMessage();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return carWashList;
    }

    private void insertCarWashEntity(List<CarWash> carWashes) {
        List<CarWashEntity> carWashEntityList = new ArrayList<>();
        for (CarWash carWash : carWashes) {
            carWashEntityList.add(new CarWashEntity(carWash.getAddress(), carWash.getBrand(), carWash.getCity(), carWash.getCode(),
                    carWash.getName(), carWash.getNeighborhood(), carWash.getPostcode(), carWash.getTown(), carWash.getType(),
                    carWash.getxCoor(), carWash.getyCoor(), carWash.getDistance()));
        }
        getDataManager().deleteCarWashAll();
        getDataManager().insertCarWashList(carWashEntityList);
    }

    public MutableLiveData<List<CarWash>> fetchCarWashesFromRoom() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getAllCarWash()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        convertCarWashEntity(response);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return carWashList;
    }

    private void convertCarWashEntity(List<CarWashEntity> response) {
        List<CarWash> carWashes = new ArrayList<>();
        for (CarWashEntity carWash : response) {
            carWashes.add(new CarWash(carWash.getAddress(), carWash.getBrand(), carWash.getCity(), carWash.getCode(),
                    carWash.getName(), carWash.getNeighborhood(), carWash.getPostcode(), carWash.getTown(), carWash.getType(),
                    carWash.getXcoor(), carWash.getYcoor(), carWash.getDistance()));
        }
        carWashList.setValue(carWashes);
    }
}
