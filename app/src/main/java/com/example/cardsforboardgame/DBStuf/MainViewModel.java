package com.example.cardsforboardgame.DBStuf;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cardsforboardgame.Classes.Card;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static CardDB database;
    private LiveData<List<Card>> cards;

    public MainViewModel(@NonNull Application application) {
        super(application);

        database = CardDB.getInstance(getApplication());
        cards = database.cardDao().getAllCards();

    }

    public LiveData<List<Card>> getCards() {
        return cards;
    }

    private static class GetCardTask extends AsyncTask<Integer, Void, Card> {
        @Override
        protected Card doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return database.cardDao().getCardById(integers[0]);
            }
            return null;
        }
    }

    public Card getCardById(int id) {
        try {
            return new GetCardTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class DeleteAllCardsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            database.cardDao().deleteAllMCards();
            return null;
        }
    }

    public void deleteAllCards() {
        new DeleteAllCardsTask().execute();
    }

    private static class DeleteCardByIdTask extends AsyncTask<Card, Void, Void> {

        @Override
        protected Void doInBackground(Card... cards) {
            if (cards != null && cards.length > 0) {
                database.cardDao().deleteCard(cards[0]);
            }
            return null;
        }
    }

    public void deleteCardById(Card card) {
        new DeleteCardByIdTask().execute(card);
    }

    private static class InsertCardTask extends AsyncTask<Card, Void, Void> {

        @Override
        protected Void doInBackground(Card... cards) {
            if (cards != null && cards.length > 0) {
                database.cardDao().insertCard(cards[0]);
            }
            return null;
        }
    }
    public void insertCard(Card card){
        new InsertCardTask().execute(card);
    }


}
