package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.cardsforboardgame.Classes.Card;
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
    //    FloatingActionButton saveToPool;
//    FloatingActionButton toAddNewCard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);
        //saveToPool = findViewById(R.id.saveToPool);
        //toAddNewCard = findViewById(R.id.addNewCard);
        // final StringBuilder titleOfBtn = new StringBuilder();

        Intent intent = getIntent();
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
        LiveData<List<Card>> cardsFromListData = viewModel.getCards();
        cardsFromListData.observe(this, new Observer<List<Card>>() {//для вытаскивания списка из LiveData
            @Override
            public void onChanged(List<Card> cardss) {

                cardAdapter.setCards(cardss);

            }
        });


        recyclerView.setAdapter(cardAdapter);


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
                String titleOfBtn = "";
                for (int i = 0; i < AddNewPool.cards.size(); i++) {
                    titleOfBtn = titleOfBtn + AddNewPool.cards.get(i) + " ";
                }
                AddNewPool.addNewBtn.setText(titleOfBtn);
                CardAdapter.checkbox = 0;//отключаем чекбоксы при выходе
                AllCardsActivity.this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem add = menu.findItem(R.id.add);
        MenuItem done = menu.findItem(R.id.done);c
        if (CardAdapter.checkbox ==0){
            done.setVisible(false);
        }else{
            done.setVisible(true);
        }
            return super.onPrepareOptionsMenu(menu);
    }
}