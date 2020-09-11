package com.example.cardsforboardgame.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.adapters.CardAdapter;

import java.util.ArrayList;

public class PoolViewActivity extends AppCompatActivity {

    private TextView title, description;
    private RecyclerView recyclerView;
    private MainViewModel viewModel;
    private CardAdapter cardAdapter;
    private ImageView imagePoolView;
    Pool pool;

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
        ArrayList<Card> cards = new ArrayList<>();
        for (int i = 0; i < cardsTitles.size(); i++) {
            if (viewModel.getCardByTitle(cardsTitles.get(i))==null){
                Log.d("858585", "onCreate: выскочил нул");
            }else{
                cards.add(viewModel.getCardByTitle(cardsTitles.get(i)));
            }

        }
        imagePoolView = findViewById(R.id.imageViewOfPoolView);
        Bitmap bitmap = pool.getBitmap();
        imagePoolView.setImageBitmap(bitmap);
        recyclerView = findViewById(R.id.recyclerViewInPoolViewActivity);
        Log.d("858585", "onCreate: "+cards.size());
        for(int i =0; i<cards.size();i++){
            Log.d("858585", "onCreate: "+cards.get(i));
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
        viewModel.deletePool(pool);
        PoolViewActivity.this.finish();
    }
}