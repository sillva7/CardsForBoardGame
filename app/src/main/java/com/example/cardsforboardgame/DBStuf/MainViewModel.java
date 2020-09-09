package com.example.cardsforboardgame.DBStuf;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static CardDB cardDatabase;
    private static PoolDB poolDatabase;
    private LiveData<List<Card>> cards;
    private LiveData<List<Pool>> pools;

    public MainViewModel(@NonNull Application application) {
        super(application);

        cardDatabase = CardDB.getInstance(getApplication());
        poolDatabase = PoolDB.getInstance(getApplication());
        cards = cardDatabase.cardDao().getAllCards();
        pools = poolDatabase.poolDao().getAllPools();

    }

    public LiveData<List<Pool>> getPools() {
        return pools;
    }

    private static class GetPoolByTitleTask extends AsyncTask<String, Void, Pool> {

        @Override
        protected Pool doInBackground(String... strings) {

            if (strings != null && strings.length > 0) {
                return poolDatabase.poolDao().getPoolByTitle(strings[0]);
            }
            return null;
        }
    }

    public Pool getPoolByTitle(String title) {
        try {
            return new GetPoolByTitleTask().execute(title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetPoolByIdTask extends AsyncTask<Integer, Void, Pool> {

        @Override
        protected Pool doInBackground(Integer... integers) {
            if (integers.length > 0) {//здесь не буду дописывать integers!=null, посмотрим как будет работать
                return poolDatabase.poolDao().getPoolById(integers[0]);
            }
            return null;
        }
    }

    public Pool getPoolById(int i) {
        try {
            return new GetPoolByIdTask().execute(i).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class DeleteAllPoolsTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            poolDatabase.poolDao().deleteAllPools();
            return null;
        }
    }

    public void deleteAllPools() {
        new DeleteAllPoolsTask().execute();
    }

    private static class DeletePoolTask extends AsyncTask<Pool, Void, Void> {

        @Override
        protected Void doInBackground(Pool... pools) {
            if (pools.length > 0) {
                poolDatabase.poolDao().deletePool(pools[0]);
                return null;
            }
            return null;
        }
    }

    public void deletePool(Pool pool) {
        new DeletePoolTask().execute(pool);
    }

    private static class InsertPoolTask extends AsyncTask<Pool, Void, Void> {

        @Override
        protected Void doInBackground(Pool... pools) {

            if (pools != null && pools.length > 0) {
                poolDatabase.poolDao().insertPool(pools[0]);
            }
            return null;
        }
    }

    public void insertPool(Pool pool) {
        new InsertPoolTask().execute(pool);
    }


    public LiveData<List<Card>> getCards() {
        return cards;
    }

    private static class GetCardTask extends AsyncTask<String, Void, Card> {
        @Override
        protected Card doInBackground(String... string) {
            if (string != null && string.length > 0) {
                return cardDatabase.cardDao().getCardByTitle(string[0]);
            }
            return null;
        }
    }

    public Card getCardByTitle(String title) {
        try {
            return new GetCardTask().execute(title).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class GetCardTaskById extends AsyncTask<Integer, Void, Card> {
        @Override
        protected Card doInBackground(Integer... integers) {
            if (integers != null && integers.length > 0) {
                return cardDatabase.cardDao().getCardById(integers[0]);
            }
            return null;
        }
    }

    public Card getCardById(int id) {
        try {
            return new GetCardTaskById().execute(id).get();
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
            cardDatabase.cardDao().deleteAllMCards();
            return null;
        }
    }

    public void deleteAllCards() {
        new DeleteAllCardsTask().execute();
    }

    private static class DeleteCardByTask extends AsyncTask<Card, Void, Void> {

        @Override
        protected Void doInBackground(Card... cards) {
            if (cards != null && cards.length > 0) {
                cardDatabase.cardDao().deleteCard(cards[0]);
            }
            return null;
        }
    }

    public void deleteCard(Card card) {
        new DeleteCardByTask().execute(card);
    }

    private static class InsertCardTask extends AsyncTask<Card, Void, Void> {

        @Override
        protected Void doInBackground(Card... cards) {
            if (cards != null && cards.length > 0) {
                cardDatabase.cardDao().insertCard(cards[0]);
            }
            return null;
        }
    }

    public void insertCard(Card card) {
        new InsertCardTask().execute(card);
    }


    private static class UpdateCardTask extends AsyncTask<Card, Void, Void>{

        @Override
        protected Void doInBackground(Card... cards) {
            if (cards != null && cards.length > 0) {
                cardDatabase.cardDao().updateCard(cards[0]);
            }
            return null;
        }
    }
    public void updateCard(Card card){
        new UpdateCardTask().execute(card);
    }


}
