package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_cards);


        Intent intent = getIntent();
        if (intent.getIntExtra("checkbox", 0) == 0) {//для отображения чекбокса
            CardAdapter.checkbox = 0;
        } else {
            CardAdapter.checkbox = 1;
        }


        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        cards = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerView);
        cardAdapter = new CardAdapter(cards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        LiveData<List<Card>> cardsFromListData = viewModel.getCards();
        cardsFromListData.observe(this, new Observer<List<Card>>() {//для вытаскивания списка из LiveData
            @Override
            public void onChanged(List<Card> cardss) {
                cardAdapter.setCards(cardss);

            }
        });
        recyclerView.setAdapter(cardAdapter);


        cardAdapter.setOnCardClickListener(new CardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                Card card = cardAdapter.getCards().get(position);
                Intent intentForCardEdit = new Intent(AllCardsActivity.this, AddNewCardActivity.class);
                intentForCardEdit.putExtra("id", viewModel.getCardByTitle(card.getTitle()).getId());
                startActivity(intentForCardEdit);
            }
        });
    }
}