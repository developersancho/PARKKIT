package developersancho.parkkit.data.model.api.carwash;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarWashResponse {
    @SerializedName("Parkkits")
    @Expose
    private List<CarWash> carWashes = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<CarWash> getCarWashes() {
        return carWashes;
    }

    public void setCarWashes(List<CarWash> carWashes) {
        this.carWashes = carWashes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
