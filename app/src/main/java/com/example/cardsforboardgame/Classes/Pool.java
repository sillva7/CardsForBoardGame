package com.example.cardsforboardgame.Classes;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.cardsforboardgame.Utils.BitmapConverter;
import com.example.cardsforboardgame.Utils.CardConverter;
import com.example.cardsforboardgame.Utils.UriConverter;

import java.util.ArrayList;

@Entity(tableName = "pools")

public class Pool {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String title;
    String description;

    @TypeConverters({CardConverter.class})
    ArrayList<String> cards;

    public String pathToFile;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getCards() {//Будем брать тайтлы карточек из массива и по ним уже брать сами объекты из бд

        return cards;

    }

    public void setCards(ArrayList<String> cards) {
        this.cards = cards;

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public Pool(int id, String title, String description, ArrayList<String> cards, String pathToFile) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cards = cards;
        this.pathToFile = pathToFile;
    }

    public Pool() {
    }

    @Ignore
    public Pool(String title, String description, String pathToFile) {
        this.title = title;
        this.description = description;
        this.pathToFile = pathToFile;
    }

    @Ignore
    public Pool(String title, String description, ArrayList<String> cards) {
        this.title = title;
        this.description = description;
        this.cards = cards;
    }

    @Ignore
    public Pool(String title, String description, ArrayList<String> cards, String pathToFile) {
        this.title = title;
        this.description = description;
        this.cards = cards;
        this.pathToFile = pathToFile;
    }

    @Ignore
    public Pool(String title, ArrayList<String> cards) {
        this.title = title;
        this.cards = cards;
    }

    public String arrayToString(ArrayList<String> cards) {
        StringBuilder s = new StringBuilder("" + cards.get(0));
        Log.d("898989", "arrayToString: " + s.toString());
        if (cards.size() > 1) {
            for (int i = 1; i < cards.size(); i++) {
                s.append("; ").append(cards.get(i));
            }
        }

        return s.toString();
    }
}
