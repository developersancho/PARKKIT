package developersancho.parkkit.data.model.api.gasstation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GasResponse {
    @SerializedName("Parkkits")
    @Expose
    private List<Gas> gases = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public GasResponse() {
    }

    public GasResponse(List<Gas> gases, String status) {
        this.gases = gases;
        this.status = status;
    }

    public List<Gas> getGases() {
        return gases;
    }

    public void setGases(List<Gas> gases) {
        this.gases = gases;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
