package developersancho.parkkit.ui.bankatm.bankatmMap;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.bankatm.BankAtm;
import developersancho.parkkit.data.model.db.BankAtmEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BankAtmMapViewModel extends BaseViewModel<IBankAtmMapNavigator> {
    private MutableLiveData<List<BankAtm>> bankAtmList;

    public BankAtmMapViewModel(@NonNull Application application) {
        super(application);
        bankAtmList = new MutableLiveData<>();
    }

    public MutableLiveData<List<BankAtm>> fetchBankAtmFromRoom() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().getAllBankAtm()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null) {
                        convertBankAtmEntity(response);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return bankAtmList;
    }

    private void convertBankAtmEntity(List<BankAtmEntity> response) {
        List<BankAtm> bankAtms = new ArrayList<>();
        for (BankAtmEntity entity : response) {
            bankAtms.add(new BankAtm(entity.getAddress(), entity.getBrand(), entity.getCity(), entity.getCode(),
                    entity.getName(), entity.getNeighborhood(), entity.getPostcode(), entity.getTown(), entity.getType(),
                    entity.getXcoor(), entity.getYcoor(), entity.getDistance()));
        }
        bankAtmList.setValue(bankAtms);
    }
}
