package developersancho.parkkit.data.remote.api.retrofit;

import developersancho.parkkit.data.model.api.carwash.CarWashResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICarWashService {
    @GET("parkkit/distcarwash?")
    Single<CarWashResponse> getCarWashList(@Query("distance") String distance,
                                           @Query("lat") String lat,
                                           @Query("lng") String lng,
                                           @Query("api_key") String apikey);
}
