package developersancho.parkkit.ui.carwash.carwashMap;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.carwash.CarWash;
import developersancho.parkkit.data.model.db.CarWashEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CarWashMapViewModel extends BaseViewModel<ICarWashMapNavigator> {

    private MutableLiveData<List<CarWash>> carWashList;

    public CarWashMapViewModel(@NonNull Application application) {
        super(application);
        carWashList = new MutableLiveData<>();
    }

    public MutableLiveData<List<CarWash>> fetchCarWashFromRoom() {
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
        for (CarWashEntity entity : response) {
            carWashes.add(new CarWash(entity.getAddress(), entity.getBrand(), entity.getCity(), entity.getCode(),
                    entity.getName(), entity.getNeighborhood(), entity.getPostcode(), entity.getTown(), entity.getType(),
                    entity.getXcoor(), entity.getYcoor(), entity.getDistance()));
        }
        carWashList.setValue(carWashes);
    }
}
