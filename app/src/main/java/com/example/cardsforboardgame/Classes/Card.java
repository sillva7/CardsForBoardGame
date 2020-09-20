package com.example.cardsforboardgame.Classes;

import android.net.Uri;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.cardsforboardgame.Utils.UriConverter;

@Entity(tableName = "cards")
public class Card {

    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String descrption;

    String pathToFile;
    @Ignore
    boolean isChecked = false;//переменная для мониторинга состояния чекбокса


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

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public Card(int id, String title, String descrption, String fileToPath) {
        this.id = id;
        this.title = title;
        this.descrption = descrption;
        this.pathToFile = fileToPath;
    }

    @Ignore
    public Card(String title, String descrption, String fileToPath) {
        this.title = title;
        this.descrption = descrption;
        this.pathToFile = fileToPath;
    }

    @Ignore
    public Card(String title, String descrption) {
        this.title = title;
        this.descrption = descrption;
    }

    public Card() {
    }
}
