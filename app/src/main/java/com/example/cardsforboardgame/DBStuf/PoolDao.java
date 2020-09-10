package com.example.cardsforboardgame.DBStuf;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cardsforboardgame.Classes.Pool;

import java.util.List;

@Dao
public interface PoolDao {

    @Query("SELECT * FROM pools")
    LiveData<List<Pool>> getAllPools();

    @Query("SELECT * FROM pools WHERE title == :title")
    Pool getPoolByTitle(String title);

    @Query("SELECT * FROM pools WHERE id ==:poolId" )
    Pool getPoolById(int poolId);

    @Query("DELETE FROM pools")
    void deleteAllPools();

    @Insert
    void insertPool(Pool pool);

    @Delete
    void deletePool(Pool pool);

    @Update
    void updatePool(Pool pool);

}
