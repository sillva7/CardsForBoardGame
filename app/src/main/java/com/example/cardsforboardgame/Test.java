package com.example.cardsforboardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.Utils.BitmapConverter;

public class Test extends AppCompatActivity {
    ImageView imageView;
    MainViewModel viewModel;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = findViewById(R.id.imageView2);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        Intent intent = getIntent();
        i = intent.getIntExtra("id", 0);
        Toast.makeText(this, ""+viewModel.getCardById(i), Toast.LENGTH_SHORT).show();

    }

    public void get(View view) {

        Card card = viewModel.getCardById(i);
        imageView.setImageBitmap(BitmapConverter.getImage(card.getBytesForImg()));
    }
}
