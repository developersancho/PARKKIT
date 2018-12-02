package developersancho.parkkit.ui.gasstation.gasMap;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.gasstation.Gas;
import developersancho.parkkit.data.model.db.GasEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GasMapViewModel extends BaseViewModel<IGasMapNavigator> {
    private MutableLiveData<List<Gas>> gasList;

    public GasMapViewModel(@NonNull Application application) {
        super(application);
        gasList = new MutableLiveData<>();
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
