package developersancho.parkkit.data.model.api.parking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ParkResponse {

    @SerializedName("Parkkits")
    @Expose
    private List<Park> parks = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public ParkResponse() {
    }

    public ParkResponse(List<Park> parks, String status) {
        this.parks = parks;
        this.status = status;
    }

    public List<Park> getParks() {
        return parks;
    }

    public void setParks(List<Park> parks) {
        this.parks = parks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
