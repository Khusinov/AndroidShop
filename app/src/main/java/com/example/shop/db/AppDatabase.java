package com.example.shop.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.shop.db.beans.STovar;
import com.example.shop.db.dao.STovarDao;


@Database(entities = {STovar.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "teda").build();
                }
            }
        }
        return instance;
    }

    public abstract STovarDao sTovarDao();


}
