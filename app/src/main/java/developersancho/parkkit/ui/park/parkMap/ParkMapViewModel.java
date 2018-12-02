package developersancho.parkkit.ui.park.parkMap;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.parking.Park;
import developersancho.parkkit.data.model.db.ParkEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import developersancho.parkkit.utils.AppConstants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ParkMapViewModel extends BaseViewModel<IParkMapNavigator> {
    private MutableLiveData<List<Park>> parkList;

    public ParkMapViewModel(@NonNull Application application) {
        super(application);
        parkList = new MutableLiveData<>();
    }


    public MutableLiveData<List<Park>> fetchParks(String distance, String lat, String lng) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().createParkService().getParkList(distance, lat, lng, AppConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getParks() != null)
                        parkList.setValue(response.getParks());
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return parkList;
    }

    public MutableLiveData<List<Park>> fetchParksFromRoom() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getAllParks()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        convertParkEntity(response);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return parkList;
    }

    private void convertParkEntity(List<ParkEntity> response) {
        List<Park> parks = new ArrayList<>();
        for (ParkEntity park : response) {
            parks.add(new Park(park.getAddress(), park.getBrand(), park.getCity(), park.getCode(),
                    park.getName(), park.getNeighborhood(), park.getPostcode(), park.getTown(), park.getType(),
                    park.getXcoor(), park.getYcoor(), park.getDistance()));
        }
        parkList.setValue(parks);
    }
}
