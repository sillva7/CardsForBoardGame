package com.example.cardsforboardgame.Utils;

import android.net.Uri;

import androidx.room.TypeConverter;

public class UriConverter {

    @TypeConverter
    public String uriToString(Uri uri){
        String s = uri.toString();
        return s;
    }

    @TypeConverter
    public Uri stringToUri(String s){
        Uri u = Uri.parse(s);
        return u;
    }
}
