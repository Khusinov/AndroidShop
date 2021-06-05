package com.example.shop.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.shop.db.beans.STovar;

import java.util.List;

@Dao
public interface STovarDao {

    @Query("SELECT * FROM s_tovar")
    List<STovar> getAll();

    @Query("SELECT * FROM s_tovar")
    LiveData<List<STovar>> getAllLive();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<STovar> tovar);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(STovar tovar);

    @Update
    void update(STovar tovar);

    @Delete
    void delete(STovar tovar);

    @Query("DELETE FROM s_tovar")
    void deleteAll();


}
