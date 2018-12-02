package developersancho.parkkit.data.remote.api.retrofit;

import developersancho.parkkit.data.model.api.carservice.CarServiceResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICarService {
    @GET("parkkit/distautoservice?")
    Single<CarServiceResponse> getCarServiceList(@Query("distance") String distance,
                                                 @Query("lat") String lat,
                                                 @Query("lng") String lng,
                                                 @Query("api_key") String apikey);
}
