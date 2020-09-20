package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.Testt;
import com.example.cardsforboardgame.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.content.Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;

public class MainActivity extends AppCompatActivity {

    Button addNewCard, addNewPool, toAllCards, toAllPools;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewCard=findViewById(R.id.addNewCard);

        toAllCards = findViewById(R.id.allCardsBtn);
        toAllCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//для практики. тут клик лисенер, а там чз онклик
                Intent intent = new Intent(MainActivity.this, AllCardsActivity.class);
                startActivity(intent);
            }
        });

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);


        Card card = new Card("Example", "Example");

        final ArrayList<Card> cardList = new ArrayList();
        LiveData<List<Card>> cardsFromListData = viewModel.getCards();
        cardsFromListData.observe(this, new Observer<List<Card>>() {//для вытаскивания списка из LiveData
            @Override
            public void onChanged(List<Card> cards) {
                cardList.addAll(cards);
            }
        });
        if(!cardList.contains(card)){
            //viewModel.insertCard(card);
        }
        for (Card cardd:cardList){
            if(cardd.getTitle()=="Example")
            {
                viewModel.deleteCard(cardd);
            }
        }



    }



    public void toAllPools(View view) {
        Intent intent = new Intent(MainActivity.this, AllPoolsActivity.class);

        startActivity(intent);
    }

    
}