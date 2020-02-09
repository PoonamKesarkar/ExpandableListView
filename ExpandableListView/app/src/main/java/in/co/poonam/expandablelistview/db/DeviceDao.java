package in.co.poonam.expandablelistview.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import in.co.poonam.expandablelistview.model.DataModel;

import java.util.List;

import static in.co.poonam.expandablelistview.util.Constants.date;
import static in.co.poonam.expandablelistview.util.Constants.deviceType;
import static in.co.poonam.expandablelistview.util.Constants.quantity;
import static in.co.poonam.expandablelistview.util.Constants.rate;
import static in.co.poonam.expandablelistview.util.Constants.tableName;

@Dao
public interface DeviceDao {

    @Query("SELECT * FROM " + tableName)
    List<DataModel> getAll();

    @Query("SELECT * FROM " + tableName + " where " + deviceType + " = :device_type " + " order by " + rate + " DESC ")
    List<DataModel> findByDevice(String device_type);

    @Insert
    void insertAll(DataModel... deviceInfo);

    @Delete
    void delete(DataModel deviceInfo);

    @Query("DELETE FROM "+ tableName + " where " + deviceType + " = :device_type ")
    public void clearTable(String device_type);

    @Query("SELECT * FROM " + tableName + " where " + deviceType + " = :device_type " + " order by " + quantity + " ASC ")
    List<DataModel> sortByQuantityIncreasing(String device_type);

    @Query("SELECT * FROM " + tableName + " where " + deviceType + " = :device_type " + " order by " + quantity + " DESC ")
    List<DataModel> sortByQuantityDecreasing(String device_type);

    @Query("SELECT * FROM " + tableName + " where " + deviceType + " = :device_type " + " order by " + date + " ASC ")
    List<DataModel> sortByDateIncreasing(String device_type);

    @Query("SELECT * FROM " + tableName + " where " + deviceType + " = :device_type " + " order by " + date + " DESC ")
    List<DataModel> sortByDateDecreasing(String device_type);

    @Query("SELECT * FROM " + tableName + " where " + deviceType + " = :device_type " + " order by " + rate + " ASC ")
    List<DataModel> sortByPopularityIncreasing(String device_type);

    @Query("SELECT * FROM " + tableName + " where " + deviceType + " = :device_type " + " order by " + rate + " DESC ")
    List<DataModel> sortByPopularityDecreasing(String device_type);
}
