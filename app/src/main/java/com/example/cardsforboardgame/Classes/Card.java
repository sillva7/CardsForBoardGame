package com.example.cardsforboardgame.Classes;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards")
public class Card {

    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String descrption;
    byte[] bytesForImg;

    public Card(int id, String title, String descrption, byte[] bytesForImg) {
        this.id = id;
        this.title = title;
        this.descrption = descrption;
        this.bytesForImg = bytesForImg;
    }

    @Ignore
    public Card(String title, String descrption) {
        this.title = title;
        this.descrption = descrption;
    }

    @Ignore

    public Card(String title, String descrption, byte[] bytesForImg) {
        this.title = title;
        this.descrption = descrption;
        this.bytesForImg = bytesForImg;
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

    public byte[] getBytesForImg() {
        return bytesForImg;
    }

    public void setBytesForImg(byte[] bytesForImg) {
        this.bytesForImg = bytesForImg;
    }
}
