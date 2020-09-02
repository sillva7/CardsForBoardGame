package com.example.cardsforboardgame.Classes;

import android.graphics.Bitmap;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.cardsforboardgame.Utils.BitmapConverter;

@Entity(tableName = "cards")
public class Card {

    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String descrption;
    @TypeConverters({BitmapConverter.class})
    Bitmap bitmap;
    @Ignore
    boolean isChecked = false;//переменная для мониторинга состояния чекбокса


    public Card(int id, String title, String descrption, Bitmap bitmap) {
        this.id = id;
        this.title = title;
        this.descrption = descrption;
        this.bitmap = bitmap;
    }

    @Ignore
    public Card(String title, String descrption) {
        this.title = title;
        this.descrption = descrption;
    }

    @Ignore
    public Card(String title, String descrption, Bitmap bitmap) {
        this.title = title;
        this.descrption = descrption;
        this.bitmap = bitmap;
    }

    @Ignore
    public boolean isChecked() {
        return isChecked;
    }
    @Ignore
    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap = bitmap;
    }
}
