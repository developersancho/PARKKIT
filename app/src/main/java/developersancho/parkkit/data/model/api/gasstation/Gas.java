package developersancho.parkkit.data.model.api.gasstation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Gas {
    @SerializedName("Id")
    @Expose
    private int id;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Brand")
    @Expose
    private String brand;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Neighborhood")
    @Expose
    private String neighborhood;
    @SerializedName("Postcode")
    @Expose
    private String postcode;
    @SerializedName("Town")
    @Expose
    private String town;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("XCoor")
    @Expose
    private double xCoor;
    @SerializedName("YCoor")
    @Expose
    private double yCoor;
    @SerializedName("Distance")
    @Expose
    private double distance;

    public Gas() {
    }

    public Gas(String address, String brand, String city, String code, String name, String neighborhood, String postcode, String town, String type, double xCoor, double yCoor, double distance) {
        this.address = address;
        this.brand = brand;
        this.city = city;
        this.code = code;
        this.name = name;
        this.neighborhood = neighborhood;
        this.postcode = postcode;
        this.town = town;
        this.type = type;
        this.xCoor = xCoor;
        this.yCoor = yCoor;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getxCoor() {
        return xCoor;
    }

    public void setxCoor(double xCoor) {
        this.xCoor = xCoor;
    }

    public double getyCoor() {
        return yCoor;
    }

    public void setyCoor(double yCoor) {
        this.yCoor = yCoor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
