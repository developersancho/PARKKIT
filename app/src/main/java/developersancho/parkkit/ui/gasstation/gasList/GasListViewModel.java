package developersancho.parkkit.ui.gasstation.gasList;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.gasstation.Gas;
import developersancho.parkkit.data.model.db.GasEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import developersancho.parkkit.utils.AppConstants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GasListViewModel extends BaseViewModel<IGasListNavigator> {

    private MutableLiveData<List<Gas>> gasList;

    public GasListViewModel(@NonNull Application application) {
        super(application);
        gasList = new MutableLiveData<>();
    }

    public MutableLiveData<List<Gas>> fetchGasesInsertRoom(String distance, String lat, String lng) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().createGasService().getGasList(distance, lat, lng, AppConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getGases() != null) {
                        gasList.setValue(response.getGases());
                        insertGasEntity(response.getGases());
                    } else {
                        getNavigator().showNotFoundMessage();
                    }

                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return gasList;
    }

    private void insertGasEntity(List<Gas> gases) {
        List<GasEntity> gasEntityList = new ArrayList<>();
        for (Gas gas : gases) {
            gasEntityList.add(new GasEntity(gas.getAddress(), gas.getBrand(), gas.getCity(), gas.getCode(),
                    gas.getName(), gas.getNeighborhood(), gas.getPostcode(), gas.getTown(), gas.getType(),
                    gas.getxCoor(), gas.getyCoor(), gas.getDistance()));
        }
        getDataManager().deleteGasAll();
        getDataManager().insertGasList(gasEntityList);
    }

    public MutableLiveData<List<Gas>> fetchGasesFromRoom() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getAllGases()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        convertGasEntity(response);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return gasList;
    }

    private void convertGasEntity(List<GasEntity> response) {
        List<Gas> gases = new ArrayList<>();
        for (GasEntity gas : response) {
            gases.add(new Gas(gas.getAddress(), gas.getBrand(), gas.getCity(), gas.getCode(),
                    gas.getName(), gas.getNeighborhood(), gas.getPostcode(), gas.getTown(), gas.getType(),
                    gas.getXcoor(), gas.getYcoor(), gas.getDistance()));
        }
        gasList.setValue(gases);
    }
}
