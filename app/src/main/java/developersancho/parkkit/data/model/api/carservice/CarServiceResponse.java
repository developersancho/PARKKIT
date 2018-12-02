package developersancho.parkkit.data.model.api.carservice;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarServiceResponse {
    @SerializedName("AutoServices")
    @Expose
    private List<CarService> carServices = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<CarService> getCarServices() {
        return carServices;
    }

    public void setCarServices(List<CarService> carServices) {
        this.carServices = carServices;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
