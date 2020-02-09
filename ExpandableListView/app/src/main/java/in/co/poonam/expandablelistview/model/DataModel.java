package in.co.poonam.expandablelistview.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import static in.co.poonam.expandablelistview.util.Constants.date;
import static in.co.poonam.expandablelistview.util.Constants.deviceType;
import static in.co.poonam.expandablelistview.util.Constants.imagePath;
import static in.co.poonam.expandablelistview.util.Constants.quantity;
import static in.co.poonam.expandablelistview.util.Constants.rate;
import static in.co.poonam.expandablelistview.util.Constants.tableName;
import static in.co.poonam.expandablelistview.util.Constants.title;

@Entity(tableName = tableName)
public class DataModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = deviceType)
    private String device_type;

    @ColumnInfo(name = title)
    private String deviceTitle;

    @ColumnInfo(name = date)
    private long inventoryDate;

    @ColumnInfo(name = rate)
    private float rating;

    @ColumnInfo(name = quantity)
    private int deviceQuantity;

    @ColumnInfo(name = imagePath)
    private String image_path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDevice_type() {
        return device_type;
    }

    public void setDevice_type(String device_type) {
        this.device_type = device_type;
    }

    public String getDeviceTitle() {
        return deviceTitle;
    }

    public void setDeviceTitle(String deviceTitle) {
        this.deviceTitle = deviceTitle;
    }

    public long getInventoryDate() {
        return inventoryDate;
    }

    public void setInventoryDate(long inventoryDate) {
        this.inventoryDate = inventoryDate;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getDeviceQuantity() {
        return deviceQuantity;
    }

    public void setDeviceQuantity(int deviceQuantity) {
        this.deviceQuantity = deviceQuantity;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }
}
