package com.example.cardsforboardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.Utils.BitmapConverter;
import com.example.cardsforboardgame.activities.CardViewActivity;

public class Testt extends AppCompatActivity {
    TextView tvt, tvd;
    ImageView iv;
    MainViewModel viewModel;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testt);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.activity_add_new_card);
            }
        });


//        tvt = findViewById(R.id.textViewT);
//        tvd = findViewById(R.id.textViewD);
//        iv = findViewById(R.id.imageView2);
//
//        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
//
//        Intent intent = getIntent();
//        int id = intent.getIntExtra("id", 1);
//
//        tvt.setText(viewModel.getCardById(id).getTitle());
//        tvd.setText(viewModel.getCardById(id).getDescrption());
//
//        iv.setImageBitmap(viewModel.getCardById(id).getBitmap());
//    }
    }


    private void editCard() {



    }
}
