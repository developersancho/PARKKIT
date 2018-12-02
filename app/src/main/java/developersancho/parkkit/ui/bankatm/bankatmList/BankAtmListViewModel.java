package developersancho.parkkit.ui.bankatm.bankatmList;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import developersancho.parkkit.data.model.api.bankatm.BankAtm;
import developersancho.parkkit.data.model.db.BankAtmEntity;
import developersancho.parkkit.ui.base.BaseViewModel;
import developersancho.parkkit.utils.AppConstants;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BankAtmListViewModel extends BaseViewModel<IBankAtmListNavigator> {
    private MutableLiveData<List<BankAtm>> bankAtmList;

    public BankAtmListViewModel(@NonNull Application application) {
        super(application);
        bankAtmList = new MutableLiveData<>();
    }

    public MutableLiveData<List<BankAtm>> fetchBankAtmInsertRoom(String distance, String lat, String lng) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager().createBankAtmService().getBankAtmList(distance, lat, lng, AppConstants.API_KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response != null && response.getBankAtms() != null) {
                        bankAtmList.setValue(response.getBankAtms());
                        insertBankAtmEntity(response.getBankAtms());
                    } else {
                        getNavigator().showNotFoundMessage();
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
        return bankAtmList;
    }

    private void insertBankAtmEntity(List<BankAtm> bankAtms) {
        List<BankAtmEntity> bankAtmEntityList = new ArrayList<>();
        for (BankAtm bankAtm : bankAtms) {
            bankAtmEntityList.add(new BankAtmEntity(bankAtm.getAddress(), bankAtm.getBrand(), bankAtm.getCity(),
                    bankAtm.getCode(), bankAtm.getName(), bankAtm.getNeighborhood(), bankAtm.getPostcode(), bankAtm.getTown(),
                    bankAtm.getType(), bankAtm.getxCoor(), bankAtm.getyCoor(), bankAtm.getDistance()));
        }
        getDataManager().deleteBankAtmAll();
        getDataManager().insertBankAtmList(bankAtmEntityList);
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
        for (BankAtmEntity bankAtm : response) {
            bankAtms.add(new BankAtm(bankAtm.getAddress(), bankAtm.getBrand(), bankAtm.getCity(), bankAtm.getCode(),
                    bankAtm.getName(), bankAtm.getNeighborhood(), bankAtm.getPostcode(), bankAtm.getTown(), bankAtm.getType(),
                    bankAtm.getXcoor(), bankAtm.getYcoor(), bankAtm.getDistance()));
        }
        bankAtmList.setValue(bankAtms);
    }
}
