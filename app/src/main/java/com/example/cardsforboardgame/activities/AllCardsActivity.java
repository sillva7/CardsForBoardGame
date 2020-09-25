package com.example.cardsforboardgame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllCardsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardAdapter cardAdapter;
    MainViewModel viewModel;
    ArrayList<Card> cards;
    int poolId;
    //    FloatingActionButton saveToPool;
//    FloatingActionButton toAddNewCard;

    @Override//начало блока с доступом к файлам
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                                    1);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[]{permission},
                                1);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }//Конец блока с доступом к файлам

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_all_cards);
        //saveToPool = findViewById(R.id.saveToPool);
        //toAddNewCard = findViewById(R.id.addNewCard);
        // final StringBuilder titleOfBtn = new StringBuilder();

        Intent intent = getIntent();
        poolId = intent.getIntExtra("poolId", 0);
        final int checkBoxAndBtn = intent.getIntExtra("checkbox", 0);
        if (checkBoxAndBtn == 0) {//для отображения чекбокса и на видимость кнопки для сохранения карточек в пул
            CardAdapter.checkbox = 0;
            //saveToPool.hide();
//            toAddNewCard.show();
        } else {
            CardAdapter.checkbox = 1;
            //saveToPool.show();
            //toAddNewCard.hide();
            AddNewPool.cards.clear();
        }
        int forUpdatePool = intent.getIntExtra("forUpdatePool", 0);
        if (forUpdatePool == 0) {
            cardAdapter.forUpdatePool = 0;
        } else {
            cardAdapter.forUpdatePool = 1;
            PoolViewActivity.cardsForUpdate=new ArrayList<>();
        }

//        saveToPool.setOnClickListener(new View.OnClickListener() {//сохранение в пул по нажатию этой кнопки
//            @Override
//            public void onClick(View view) {
//
//
//            }
//        });


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        cards = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        cardAdapter = new CardAdapter(cards);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        //recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,true));
        LiveData<List<Card>> cardsFromListData = viewModel.getCards();
        cardsFromListData.observe(this, new Observer<List<Card>>() {//для вытаскивания списка из LiveData
            @Override
            public void onChanged(List<Card> cardss) {

                cardAdapter.setCards(cardss);

            }
        });


        recyclerView.setAdapter(cardAdapter);
        //recyclerView.setHasFixedSize(true);


        if (forUpdatePool == 1) {
            cardAdapter.setOnCardClickListener(new CardAdapter.OnCardClickListener() {
                @Override
                public void onCardClick(int position) {
                    if (PoolViewActivity.cardsForUpdate.contains(cardAdapter.getCards().get(position))) {
                        PoolViewActivity.cardsForUpdate.remove(cardAdapter.getCards().get(position));
                    } else {
                        PoolViewActivity.cardsForUpdate.add(cardAdapter.getCards().get(position));
                    }
                }
            });
        } else {
            cardAdapter.setOnCardClickListener(new CardAdapter.OnCardClickListener() {//по клику переходим на страницу с карточкой и информацией о ней
                @Override
                public void onCardClick(int position) {
                    if (position == cardAdapter.getCards().size()) {
                        Intent intentForAddNewCardToPool = new Intent(AllCardsActivity.this, AddNewCardActivity.class);
                        startActivity(intentForAddNewCardToPool);
                    } else {
                        Card card = cardAdapter.getCards().get(position);
                        Intent intentForCardEdit = new Intent(AllCardsActivity.this, AddNewCardActivity.class);
                        intentForCardEdit.putExtra("id", viewModel.getCardByTitle(card.getTitle()).getId());
                        startActivity(intentForCardEdit);
                    }


                }
            });
        }


    }

    public void toAddNewCardFromList(View view) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_menu_and_save, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent = new Intent(AllCardsActivity.this, AddNewCardActivity.class);
                startActivity(intent);
                return true;
            case R.id.done:
                if (CardAdapter.checkbox == 1 && CardAdapter.forUpdatePool == 0) {
                    String titleOfBtn = "";
                    for (int i = 0; i < AddNewPool.cards.size(); i++) {
                        titleOfBtn = titleOfBtn + AddNewPool.cards.get(i) + " ";
                    }
                    AddNewPool.addNewBtn.setText(titleOfBtn);
                    CardAdapter.checkbox = 0;//отключаем чекбоксы при выходе
                    AllCardsActivity.this.finish();
                    return true;
                } else if (CardAdapter.checkbox == 0 && CardAdapter.forUpdatePool == 1) {//для апдейта Пула
                    if(!PoolViewActivity.cardsForUpdate.isEmpty()){
                        ArrayList<String> titlesOfCards = new ArrayList<>();
                        for (Card card : PoolViewActivity.cardsForUpdate) {
                            titlesOfCards.add(card.getTitle());
                        }
                        Pool pool = viewModel.getPoolById(poolId);
                        pool.setCards(titlesOfCards);
                        viewModel.updatePool(pool);
                    }
                    AllCardsActivity.this.finish();
//                    Intent backToUpdatePool = new Intent(AllCardsActivity.this, PoolViewActivity.class);
//                    backToUpdatePool.putExtra("id", poolId);
//                    CardAdapter.forUpdatePool=0;
//                    startActivity(backToUpdatePool);
                    return true;
                }


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem add = menu.findItem(R.id.add);
        MenuItem done = menu.findItem(R.id.done);
        if (CardAdapter.checkbox == 0 && CardAdapter.forUpdatePool == 0) {
            done.setVisible(false);
        } else if(CardAdapter.checkbox == 0 && CardAdapter.forUpdatePool == 1) {
            done.setVisible(true);
        } else if(CardAdapter.checkbox == 1 && CardAdapter.forUpdatePool == 0){
            done.setVisible(true);
        } else if(CardAdapter.checkbox == 1 && CardAdapter.forUpdatePool == 1){
            done.setVisible(true);
        }else{
            done.setVisible(false);
        }

        return super.onPrepareOptionsMenu(menu);
    }
}