package com.example.cardsforboardgame.Classes;

import android.graphics.Bitmap;
import android.util.Log;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.cardsforboardgame.Utils.BitmapConverter;
import com.example.cardsforboardgame.Utils.CardConverter;

import java.util.ArrayList;

@Entity(tableName = "pools")

public class Pool {
    @PrimaryKey(autoGenerate = true)
    public int id;
    String title;
    String description;

    @TypeConverters({CardConverter.class})
    ArrayList<String> cards;

    @TypeConverters({BitmapConverter.class})
    Bitmap bitmap;//for img

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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pool(int id, String title, String description, ArrayList<String> cards, Bitmap bitmap) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cards = cards;
        this.bitmap = bitmap;
    }

    @Ignore
    public Pool(String title, String description, ArrayList<String> cards, Bitmap bitmap) {
        this.title = title;
        this.description = description;
        this.cards = cards;
        this.bitmap = bitmap;
    }

    @Ignore//опционально можно будет добавлять или нет картинку к пулу
    public Pool(String title, String description, ArrayList<String> cards) {
        this.title = title;
        this.description = description;
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
