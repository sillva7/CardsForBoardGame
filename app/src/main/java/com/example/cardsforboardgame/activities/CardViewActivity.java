package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;


public class CardViewActivity extends AppCompatActivity {
    TextView description;
    ImageView imageViewInCardView;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_view);
        description = findViewById(R.id.descriptionCardTV);
        imageViewInCardView = findViewById(R.id.imageViewInCardView);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);
        Log.d("494949", "onCreate: " + id);
        Card card = viewModel.getCardById(id);
        Log.d("494949", "onCreate: "+card.getTitle());
        imageViewInCardView.setImageBitmap(card.getBitmap());
    }

    public void extend(View view) {
        description.setMaxLines(Integer.MAX_VALUE);//убираем ограничение по строкам, чтобы раскрыть описание полностью
        description.setEllipsize(null);//также необходимо как и строчка выше. идут в наборе

    }
}