package com.example.cardsforboardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.Utils.BitmapConverter;

public class Testt extends AppCompatActivity {
    TextView tvt, tvd;
    ImageView iv;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testt);
        tvt = findViewById(R.id.textViewT);
        tvd = findViewById(R.id.textViewD);
        iv = findViewById(R.id.imageView2);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 1);

        tvt.setText(viewModel.getCardById(id).getTitle());
        tvd.setText(viewModel.getCardById(id).getDescrption());
    }
}
