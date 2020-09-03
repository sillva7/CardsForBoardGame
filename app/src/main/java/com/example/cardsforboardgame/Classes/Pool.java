package com.example.cardsforboardgame.Classes;

import android.graphics.Bitmap;

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
    int id;
    String title;
    String description;
    @TypeConverters({CardConverter.class})
    ArrayList<Card> cards;
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

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Pool(int id, String title, String description, ArrayList<Card> cards, Bitmap bitmap) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cards = cards;
        this.bitmap = bitmap;
    }

    @Ignore
    public Pool(String title, String description, ArrayList<Card> cards, Bitmap bitmap) {
        this.title = title;
        this.description = description;
        this.cards = cards;
        this.bitmap = bitmap;
    }

    @Ignore//опционально можно будет добавлять или нет картинку к пулу
    public Pool(String title, String description, ArrayList<Card> cards) {
        this.title = title;
        this.description = description;
        this.cards = cards;
    }
}
