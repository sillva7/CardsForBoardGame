package com.example.cardsforboardgame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

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
//    @Override//начало блока с доступом к файлам. Абсолютно бесполезная вещь, но если оставить, я думаю никому не помешает. В противном случае, надо бы создать отдельный файлик под такие Опилки?
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//
//        switch (requestCode) {
//            case 1:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // do your stuff
//
//
//
//
//
//                } else {
//                    Toast.makeText(this, "GET_ACCOUNTS Denied",
//                            Toast.LENGTH_SHORT).show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions,
//                        grantResults);
//        }
//    }

//    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
//            final Context context) {
//        int currentAPIVersion = Build.VERSION.SDK_INT;
//        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
//            if (ContextCompat.checkSelfPermission(context,
//                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(
//                        (Activity) context,
//                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                    showDialog("External storage", context,
//                            Manifest.permission.READ_EXTERNAL_STORAGE);
//
//                } else {
//                    ActivityCompat
//                            .requestPermissions(
//                                    (Activity) context,
//                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
//                                    1);
//                }
//                return false;
//            } else {
//                return true;
//            }
//
//        } else {
//            return true;
//        }
//    }
//    public void showDialog(final String msg, final Context context,
//                           final String permission) {
//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
//        alertBuilder.setCancelable(true);
//        alertBuilder.setTitle("Permission necessary");
//        alertBuilder.setMessage(msg + " permission is necessary");
//        alertBuilder.setPositiveButton(android.R.string.yes,
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        ActivityCompat.requestPermissions((Activity) context,
//                                new String[] { permission },
//                                1);
//                    }
//                });
//        AlertDialog alert = alertBuilder.create();
//        alert.show();
//    }//Конец блока с доступом к файлам

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
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