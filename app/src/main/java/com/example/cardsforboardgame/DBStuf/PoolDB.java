package com.example.cardsforboardgame.DBStuf;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cardsforboardgame.Classes.Pool;

@Database(entities = {Pool.class}, version = 8, exportSchema = false)
public abstract class PoolDB extends RoomDatabase {

    private static final String DB_NAME = "pools.db";
    private static PoolDB database;
    private static final Object LOCK = new Object();

    public static PoolDB getInstance(Context context){
        synchronized (LOCK){
            if(database==null){
                database = Room.databaseBuilder(context, PoolDB.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
        }
        return database;
    }
    public abstract PoolDao poolDao();

}
