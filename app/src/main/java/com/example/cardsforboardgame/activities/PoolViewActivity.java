package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.adapters.CardAdapter;

import java.util.ArrayList;
import java.util.Random;

public class PoolViewActivity extends AppCompatActivity {

    private TextView title, description;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private CardAdapter cardAdapter;
    private ImageView imagePoolView;
    Pool pool;
    ArrayList<Card> cards;
    View customLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pool_view);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        Toast.makeText(this, "id is: " + id, Toast.LENGTH_SHORT).show();
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        pool = viewModel.getPoolById(id);
        title = findViewById(R.id.titlePoolView);
        description = findViewById(R.id.descriptionPoolView);
        ArrayList<String> cardsTitles = pool.getCards();

        Log.d("lololo1", "onCreateBeforeSOrt: " + cardsTitles.size());
        cards = new ArrayList<>();
        for (int i = 0; i < cardsTitles.size(); i++) {
            if (viewModel.getCardByTitle(cardsTitles.get(i)) == null) {
                Log.d("858585", "onCreate: выскочил нул");
            } else {
                cards.add(viewModel.getCardByTitle(cardsTitles.get(i)));
            }
        }
        Log.d("lololo1", "onCreate: "+cards.size());


        imagePoolView = findViewById(R.id.imageViewOfPoolView);
        Bitmap bitmap = pool.getBitmap();
        imagePoolView.setImageBitmap(bitmap);
        recyclerView = findViewById(R.id.recyclerViewInPoolViewActivity);
        Log.d("858585", "onCreate: " + cards.size());
        for (int i = 0; i < cards.size(); i++) {
            Log.d("858585", "onCreate: " + cards.get(i));
        }

        cardAdapter = new CardAdapter(cards);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(cardAdapter);

        title.setText(pool.getTitle());
        description.setText(pool.getDescription());

        cardAdapter.setOnCardClickListener(new CardAdapter.OnCardClickListener() {
            @Override
            public void onCardClick(int position) {
                Card card = cardAdapter.getCards().get(position);


                Intent intentForCardEdit = new Intent(PoolViewActivity.this, CardViewActivity.class);
                intentForCardEdit.putExtra("id", viewModel.getCardByTitle(card.getTitle()).getId());
                startActivity(intentForCardEdit);
            }
        });


    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteCard:
                deletePool();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void deletePool() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(PoolViewActivity.this);
        builder.setTitle(R.string.delete);
        builder.setMessage(R.string.rusure);
        builder.setNegativeButton("NO",//негативный ответ на диалоговое окно и там ниже позитивный
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(PoolViewActivity.this, R.string.no, Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                viewModel.deletePool(pool);
                PoolViewActivity.this.finish();
                Toast.makeText(PoolViewActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
            }});
        builder.show();
    }

    public void randomize(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(PoolViewActivity.this);
        customLayout = getLayoutInflater().inflate(R.layout.randomize_alert_dialog, null);//TYT
        builder.setView(customLayout);//********************************************************************и тут подключаем кастомынй лайаут
        builder.setTitle("Randomize");

        randomizer(customLayout);

        Button btn = customLayout.findViewById(R.id.alertRandomizeBTN);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomizer(customLayout);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void randomizer(View view){
        Card card;//блок с генерацией рандомной карты
        Random rand = new Random();//
        int randomGet = rand.nextInt(cards.size());//
        card = cards.get(randomGet);//

        TextView titleTV = customLayout.findViewById(R.id.alertRandomizeTitleTV);//привязка всех итемов
        TextView descriptionTV = customLayout.findViewById(R.id.alertRandomizeDescriptionTV);
        ImageView iv = customLayout.findViewById(R.id.alertRandomizeIV);

        titleTV.setText(card.getTitle());//ставим значения
        descriptionTV.setText(card.getDescrption());
        iv.setImageBitmap(card.getBitmap());
    }




}