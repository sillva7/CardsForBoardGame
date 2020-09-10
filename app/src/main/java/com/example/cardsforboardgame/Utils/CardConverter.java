package com.example.cardsforboardgame.Utils;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.TypeConverter;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;

import java.util.ArrayList;

public class CardConverter extends AppCompatActivity {


    @TypeConverter
    public String toStrings(ArrayList<String> cards1) {
        String s ="";
        for (int i = 0; i < cards1.size(); i++) {
            s +=  cards1.get(i)+"_ _";
        }

        return s;
    }

    @TypeConverter
    public ArrayList<String> toArrayList(String s) {
        Log.d("989898", "toStrings: "+ s);

        String[] a = s.split("_ _");

        ArrayList<String> cards = new ArrayList<>();
        for (int i = 0; i < a.length; i++) {
            cards.add(a[i]);
        }
        Log.d("989898", "toArrayList111: " + cards.toString());
        return cards;

    }

//    @TypeConverter
//    public static int[] getIds(ArrayList<Card> cards) {
//        int[] ids = new int[cards.size() - 1];
//        for (int i = 0; i < cards.size(); i++) {
//            ids[i] = viewModel.getCardByTitle(cards.get(i).getTitle()).getId();
//        }
//        return ids;
//    }
//
//    @TypeConverter
//    public static ArrayList<Card> getList(int[] ids) {
//        ArrayList<Card> cards = new ArrayList<>();
//        for (int i = 0; i < ids.length; i++) {
//            cards.add(viewModel.getCardById(ids[i]));
//        }
//        return cards;
//    }

}
