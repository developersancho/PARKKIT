package developersancho.parkkit.data.model.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "carservice_table")
public class CarServiceEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int Id;
    private String address;
    private String brand;
    private String city;
    private String code;
    private String name;
    private String neighborhood;
    private String postcode;
    private String town;
    private String type;
    private double xcoor;
    private double ycoor;
    private double distance;

    public CarServiceEntity(String address, String brand, String city, String code, String name, String neighborhood, String postcode, String town, String type, double xcoor, double ycoor, double distance) {
        this.address = address;
        this.brand = brand;
        this.city = city;
        this.code = code;
        this.name = name;
        this.neighborhood = neighborhood;
        this.postcode = postcode;
        this.town = town;
        this.type = type;
        this.xcoor = xcoor;
        this.ycoor = ycoor;
        this.distance = distance;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
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

    public double getXcoor() {
        return xcoor;
    }

    public void setXcoor(double xcoor) {
        this.xcoor = xcoor;
    }

    public double getYcoor() {
        return ycoor;
    }

    public void setYcoor(double ycoor) {
        this.ycoor = ycoor;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
