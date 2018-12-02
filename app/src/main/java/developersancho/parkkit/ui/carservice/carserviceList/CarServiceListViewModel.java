package developersancho.parkkit.ui.carservice.carserviceList;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.carservice.CarService;
import developersancho.parkkit.data.model.db.CarServiceEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import developersancho.parkkit.utils.AppConstants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CarServiceListViewModel extends BaseViewModel<ICarServiceListNavigator> {
    private MutableLiveData<List<CarService>> carServiceList;

    public CarServiceListViewModel(@NonNull Application application) {
        super(application);
        carServiceList = new MutableLiveData<>();
    }

    public MutableLiveData<List<CarService>> fetchCarServicesInsertRoom(String distance, String lat, String lng) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().createCarService().getCarServiceList(distance, lat, lng, AppConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getCarServices() != null) {
                        carServiceList.setValue(response.getCarServices());
                        insertCarServiceEntity(response.getCarServices());
                    } else {
                        getNavigator().showNotFoundMessage();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return carServiceList;
    }

    private void insertCarServiceEntity(List<CarService> carServices) {
        List<CarServiceEntity> carServiceEntityList = new ArrayList<>();
        for (CarService carService : carServices) {
            carServiceEntityList.add(new CarServiceEntity(carService.getAddress(), carService.getBrand(), carService.getCity(),
                    carService.getCode(), carService.getName(), carService.getNeighborhood(), carService.getPostcode(), carService.getTown(),
                    carService.getType(), carService.getxCoor(), carService.getyCoor(), carService.getDistance()));
        }
        getDataManager().deleteCarServiceAll();
        getDataManager().insertCarServiceList(carServiceEntityList);
    }

    public MutableLiveData<List<CarService>> fetchCarServicesFromRoom() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getAllCarService()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        convertCarServiceEntity(response);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return carServiceList;
    }

    private void convertCarServiceEntity(List<CarServiceEntity> response) {
        List<CarService> carServices = new ArrayList<>();
        for (CarServiceEntity carService : response) {
            carServices.add(new CarService(carService.getAddress(), carService.getBrand(), carService.getCity(), carService.getCode(),
                    carService.getName(), carService.getNeighborhood(), carService.getPostcode(), carService.getTown(), carService.getType(),
                    carService.getXcoor(), carService.getYcoor(), carService.getDistance()));
        }
        carServiceList.setValue(carServices);
    }
}
