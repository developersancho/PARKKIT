package developersancho.parkkit.data.model.api.bankatm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BankAtmResponse {
    @SerializedName("Parkkits")
    @Expose
    private List<BankAtm> bankAtms = null;
    @SerializedName("Status")
    @Expose
    private String status;

    public List<BankAtm> getBankAtms() {
        return bankAtms;
    }

    public void setBankAtms(List<BankAtm> bankAtms) {
        this.bankAtms = bankAtms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
