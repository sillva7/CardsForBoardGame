package com.example.cardsforboardgame.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.TypeConverter;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;

import java.util.ArrayList;

public class CardConverter extends AppCompatActivity {
    public MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);


    @TypeConverter
    public String toStrings(ArrayList<Card> cards) {
        String s;
        s = cards.get(0).getTitle();
        for (int i = 1; i < cards.size(); i++) {
            s += " " + cards.get(i).getTitle();
        }
        return s;
    }

    @TypeConverter
    public ArrayList<Card> toArrayList(String s) {
        String[] a = s.split(" ");
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            cards.add(viewModel.getCardByTitle(a[i]));
        }
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
