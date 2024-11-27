package com.sdi.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
//import com.example.monitor.Earthquake;
import com.sdi.Earthquake;

import java.util.List;

@Dao
public interface EqDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Earthquake> eqList);
    @Query("SELECT * FROM earthquakes")
    LiveData<List<Earthquake>> getEarthquakes();
}
