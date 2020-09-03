package com.example.cardsforboardgame.Utils;

import androidx.room.TypeConverter;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;

import java.util.ArrayList;

public class CardConverter {
    public static MainViewModel viewModel;

    @TypeConverter
    public static int[] getIds(ArrayList<Card> cards) {
        int[] ids = new int[cards.size()];
        for (int i = 0; i < cards.size(); i++) {
            ids[i] =  (viewModel.getCardById(cards.get(i).getId())).getId();
        }
        return ids;
    }

    @TypeConverter
    public static ArrayList<Card> getList(int[] ids){
        ArrayList<Card> cards = new ArrayList<>();
        for(int i =0; i<ids.length; i++){
            cards.add(viewModel.getCardById(ids[i]));
        }
        return cards;
    }

}
