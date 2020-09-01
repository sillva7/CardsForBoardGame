package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.adapters.CardAdapter;
import com.nex3z.flowlayout.FlowLayout;

import java.util.ArrayList;

public class AddNewPool extends AppCompatActivity {
    EditText titleET, descriptionET;
    FlowLayout flowLayoutBtns;
    Button addNewBtn;
    static ArrayList<Card> cards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pool);
        cards = new ArrayList<>();//будем помещать карточки сюда и вставлять весь список в объект Pool

        flowLayoutBtns = findViewById(R.id.flowLayoutBtns);
        addNewBtn = findViewById(R.id.addNewCard);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn();
            }
        });
    }


    public void addBtn(){

        Intent intent = new Intent(AddNewPool.this, AllCardsActivity.class);//call another activity
        intent.putExtra("checkbox", 1);
        intent.putExtra("button", 1);

        startActivity(intent);
        Button myButton = new Button(this);//создаём кнопу. Надо будет поставить какую-нибудь проверку на то, что если карточка не была добавлена, кнопа новая не создавалась
        myButton.setText(R.string.add_new_card_to_pool_btn);//устанавливаем ей нужные параметры
        myButton.setMinWidth(0);
        myButton.setOnClickListener(new View.OnClickListener() {//ставим тригер нажатия на эту кнопу

            @Override
            public void onClick(View view) {
                addBtn();//рекурсия для кнопы

            }
        });

        flowLayoutBtns.addView(myButton);//добавляем кнопку в наш лайаут

    }
}