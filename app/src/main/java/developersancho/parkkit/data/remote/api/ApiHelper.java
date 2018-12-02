package developersancho.parkkit.data.remote.api;

import developersancho.parkkit.data.remote.api.retrofit.IBankAtmService;
import developersancho.parkkit.data.remote.api.retrofit.ICarService;
import developersancho.parkkit.data.remote.api.retrofit.ICarWashService;
import developersancho.parkkit.data.remote.api.retrofit.IGasService;
import developersancho.parkkit.data.remote.api.retrofit.IParkService;
import developersancho.parkkit.data.remote.api.retrofit.NetworkService;
import developersancho.parkkit.utils.AppConstants;

public class ApiHelper implements IApiHelper {

    @Override
    public IParkService createParkService() {
        return NetworkService.getClient(AppConstants.BASE_URL).create(IParkService.class);
    }

    @Override
    public IGasService createGasService() {
        return NetworkService.getClient(AppConstants.BASE_URL).create(IGasService.class);
    }

    @Override
    public IBankAtmService createBankAtmService() {
        return NetworkService.getClient(AppConstants.BASE_URL).create(IBankAtmService.class);
    }

    @Override
    public ICarService createCarService() {
        return NetworkService.getClient(AppConstants.BASE_URL).create(ICarService.class);
    }

    @Override
    public ICarWashService createCarWashService() {
        return NetworkService.getClient(AppConstants.BASE_URL).create(ICarWashService.class);
    }
}
