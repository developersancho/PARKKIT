package developersancho.parkkit.data.remote.api;

import developersancho.parkkit.data.remote.api.retrofit.IBankAtmService;
import developersancho.parkkit.data.remote.api.retrofit.ICarService;
import developersancho.parkkit.data.remote.api.retrofit.ICarWashService;
import developersancho.parkkit.data.remote.api.retrofit.IGasService;
import developersancho.parkkit.data.remote.api.retrofit.IParkService;

public interface IApiHelper {
    IParkService createParkService();

    IGasService createGasService();

    IBankAtmService createBankAtmService();

    ICarService createCarService();

    ICarWashService createCarWashService();
}
