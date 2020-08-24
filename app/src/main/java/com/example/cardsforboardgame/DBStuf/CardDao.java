package com.example.cardsforboardgame.DBStuf;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cardsforboardgame.Classes.Card;

import java.util.List;

@Dao
public interface CardDao {

    @Query("SELECT * FROM cards")
    LiveData<List<Card>> getAllCards();


    @Query("SELECT * FROM cards WHERE id == :cardId")
    Card getCardById(int cardId);


    @Query("DELETE FROM cards")
    void deleteAllMCards();

    @Insert
    void insertCard(Card card);

    @Delete
    void deleteCard(Card card);



}
