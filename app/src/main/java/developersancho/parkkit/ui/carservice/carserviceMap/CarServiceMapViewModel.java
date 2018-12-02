package developersancho.parkkit.ui.carservice.carserviceMap;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.carservice.CarService;
import developersancho.parkkit.data.model.db.CarServiceEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CarServiceMapViewModel extends BaseViewModel<ICarServiceMapNavigator> {
    private MutableLiveData<List<CarService>> carServiceList;

    public CarServiceMapViewModel(@NonNull Application application) {
        super(application);
        carServiceList = new MutableLiveData<>();
    }

    public MutableLiveData<List<CarService>> fetchCarServiceFromRoom() {
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
        for (CarServiceEntity entity : response) {
            carServices.add(new CarService(entity.getAddress(), entity.getBrand(), entity.getCity(), entity.getCode(),
                    entity.getName(), entity.getNeighborhood(), entity.getPostcode(), entity.getTown(), entity.getType(),
                    entity.getXcoor(), entity.getYcoor(), entity.getDistance()));
        }
        carServiceList.setValue(carServices);
    }
}
