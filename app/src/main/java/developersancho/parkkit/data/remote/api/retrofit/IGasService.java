package developersancho.parkkit.data.remote.api.retrofit;

import developersancho.parkkit.data.model.api.gasstation.GasResponse;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IGasService {
    @GET("parkkit/distgasstation?")
    Single<GasResponse> getGasList(@Query("distance") String distance,
                                   @Query("lat") String lat,
                                   @Query("lng") String lng,
                                   @Query("api_key") String apikey);
}
