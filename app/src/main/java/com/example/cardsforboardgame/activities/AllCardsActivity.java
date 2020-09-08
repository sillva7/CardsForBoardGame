package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.Utils.BitmapConverter;
import com.example.cardsforboardgame.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllCardsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardAdapter cardAdapter;
    MainViewModel viewModel;
    ArrayList<Card> cards;
    Button saveToPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);
        saveToPool = findViewById(R.id.saveToPool);
       // final StringBuilder titleOfBtn = new StringBuilder();
        final Intent intent = getIntent();
        final int checkBoxAndBtn = intent.getIntExtra("checkbox", 0);


        if (checkBoxAndBtn == 0) {//для отображения чекбокса и на видимость кнопки для сохранения карточек в пул
            CardAdapter.checkbox = 0;
            saveToPool.setVisibility(View.GONE);

        } else {
            CardAdapter.checkbox = 1;
            saveToPool.setVisibility(View.VISIBLE);
            AddNewPool.cards.clear();

        }
        saveToPool.setOnClickListener(new View.OnClickListener() {//сохранение в пул по нажатию этой кнопки
            @Override
            public void onClick(View view) {
                String titleOfBtn = "";
                Log.d("747474", "onClick: "+titleOfBtn);

                for (int i = 0; i < AddNewPool.cards.size(); i++) {
                    titleOfBtn = titleOfBtn + AddNewPool.cards.get(i) + " ";
                }
                AddNewPool.addNewBtn.setText(titleOfBtn);
                Log.d("747474", "onClick1: "+titleOfBtn);


                for (int i = 0; i < AddNewPool.cards.size(); i++) {//проверка
                    Log.d("777", "onClick: " + AddNewPool.cards.get(i));
                }
                CardAdapter.checkbox = 0;//отключаем чекбоксы при выходе

                AllCardsActivity.this.finish();

            }
        });


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
                Card card = cardAdapter.getCards().get(position);


                Intent intentForCardEdit = new Intent(AllCardsActivity.this, CardViewActivity.class);
                intentForCardEdit.putExtra("id", viewModel.getCardByTitle(card.getTitle()).getId());
                Log.d("494949", "onCardClick: "+viewModel.getCardByTitle(card.getTitle()).getId());
                startActivity(intentForCardEdit);


            }
        });
    }
}