package developersancho.parkkit.ui.base;

import android.app.Application;

import java.lang.ref.WeakReference;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;
import developersancho.parkkit.data.model.others.UserLocation;
import developersancho.parkkit.data.repository.DataManager;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel<N> extends AndroidViewModel {
    private DataManager dataManager;
    private ObservableBoolean isLoading = new ObservableBoolean(false);
    private CompositeDisposable compositeDisposable;
    private WeakReference<N> navigator;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        dataManager = new DataManager(application);
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public N getNavigator() {
        return navigator.get();
    }

    public void setNavigator(N navigator) {
        this.navigator = new WeakReference<>(navigator);
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading.set(isLoading);
    }

    public void setUserLocation(String userLocation) {
        dataManager.saveUserLocation(userLocation);
    }

    public UserLocation getUserLocation() {
        return dataManager.getUserLocation();
    }


}
