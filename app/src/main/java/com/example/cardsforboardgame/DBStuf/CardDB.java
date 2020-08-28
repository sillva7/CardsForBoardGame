package com.example.cardsforboardgame.DBStuf;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cardsforboardgame.Classes.Card;


@Database(entities = {Card.class}, version = 2, exportSchema = false)
public abstract class CardDB extends RoomDatabase {

    private static final String DB_NAME = "movies.db";
    private static CardDB database;
    private static final Object LOCK = new Object();

    public static CardDB getInstance(Context context){
        synchronized (LOCK){
            if(database==null){
                database = Room.databaseBuilder(context, CardDB.class, DB_NAME)
                        .fallbackToDestructiveMigration().build();//для апдейта БД
            }

        }
        return database;
    }
    public abstract CardDao cardDao();
}
