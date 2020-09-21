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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // do your stuff
                } else {
                    Toast.makeText(this, "GET_ACCOUNTS Denied",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions,
                        grantResults);
        }
    }

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    1);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                1);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pools);
        recyclerView = findViewById(R.id.recyclerView);
        pools = new ArrayList<>();
        poolAdapter = new PoolAdapter(pools);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        if (checkPermissionREAD_EXTERNAL_STORAGE(this)) {
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
    }

    public void toAddNewPoolFromList(View view) {
        Intent intent = new Intent(AllPoolsActivity.this, AddNewPool.class);
        startActivity(intent);

    }
}