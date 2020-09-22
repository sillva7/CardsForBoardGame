package com.example.cardsforboardgame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.adapters.CardAdapter;
import com.example.cardsforboardgame.adapters.PoolAdapter;

import java.util.ArrayList;
import java.util.List;

public class AllPoolsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PoolAdapter poolAdapter;
    MainViewModel viewModel;
    ArrayList<Pool> pools;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pools);
        recyclerView = findViewById(R.id.recyclerView);
        pools = new ArrayList<>();
        poolAdapter = new PoolAdapter(pools);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            LiveData<List<Pool>> poolsFromListData = viewModel.getPools();
            poolsFromListData.observe(this, new Observer<List<Pool>>() {//для вытаскивания списка из LiveData
                @Override
                public void onChanged(List<Pool> poolss) {

                    poolAdapter.setPools(poolss);

                }
            });
            recyclerView.setAdapter(poolAdapter);


            poolAdapter.setOnPoolClickListener(new PoolAdapter.OnPoolClickListener() {
                @Override
                public void onPoolClick(int position) {
                    //Toast.makeText(AllPoolsActivity.this, "BOOOO" + position, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AllPoolsActivity.this, PoolViewActivity.class);
                    int id = poolAdapter.getPools().get(position).getId();
                    //Log.d("161616", "onPoolClick: "+id);
                    intent.putExtra("id", id);
                    startActivity(intent);
                }
            });

    }

    public void toAddNewPoolFromList(View view) {
        Intent intent = new Intent(AllPoolsActivity.this, AddNewPool.class);
        startActivity(intent);

    }
}