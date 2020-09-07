package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.adapters.CardAdapter;

public class MainActivity extends AppCompatActivity {

    Button addNewCard, addNewPool, toAllCards, toAllPools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addNewCard=findViewById(R.id.addNewCard);

        toAllCards = findViewById(R.id.allCardsBtn);
        toAllCards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AllCardsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void toAddNewCard(View view) {
        Intent intent = new Intent(MainActivity.this, AddNewCardActivity.class);
        startActivity(intent);
    }

    public void toAddNewPool(View view) {
        Intent intent = new Intent(MainActivity.this, AddNewPool.class);
        startActivity(intent);
    }

    public void toAllPools(View view) {
        Intent intent = new Intent(MainActivity.this, AllPoolsActivity.class);
        startActivity(intent);
    }
}