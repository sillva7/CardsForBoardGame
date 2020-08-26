package com.example.cardsforboardgame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.Utils.BitmapConverter;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button setImgBtn;
    private ImageView imageViewOfCard;
    private final int Pick_image = 1;
    private byte[] byteForImg;
    private MainViewModel viewModel;
    EditText titleET, descriptionET;
    ArrayList<Card> cardList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setImgBtn = findViewById(R.id.button);
        imageViewOfCard = findViewById(R.id.imageView);

        byteForImg = null;
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        titleET = findViewById(R.id.titleET);
        descriptionET = findViewById(R.id.descriptionET);

        LiveData<List<Card>> cardsFromListData = viewModel.getCards();
        cardList = new ArrayList();
        cardsFromListData.observe(this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {

                // Log.d("494949", "onChanged: "+cards.get(0));
                cardList.addAll(cards);
                Log.d("565656", "onChanged: " + cards.toString());
                Log.d("565656", "onChanged: " + cards.size());
                if (cards.size() > 0) {
                    for (int i = 0; i < cards.size(); i++) {
                        Log.d("565656", "onChanged: from list: " + cards.get(i).getTitle());
                        Log.d("565656", "onChanged: from viewModel: " +
                                viewModel.getCardByTitle(cards.get(i).getTitle()).getTitle() +
                                " viewModel " + viewModel.getCardByTitle(cards.get(i).getTitle()).getId());
                    }

                }


            }
        });


    }


    public void setImg(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);////Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK
        photoPickerIntent.setType("image/*");//Тип получаемых объектов - image
        startActivityForResult(photoPickerIntent, Pick_image);//Запускаем переход с ожиданием обратного результата в виде информации об изображении
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//для загрузки с галереи изображения
        super.onActivityResult(requestCode, resultCode, data);

        //Получаем URI изображения, преобразуем его в Bitmap
        //объект и отображаем в элементе ImageView нашего интерфейса:
        switch (requestCode) {
            case Pick_image:
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        String s = getRealPathFromURI(imageUri);//для настоящего названия пути картинки
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        Log.d("666", "onActivityResult: " + selectedImage.toString());
                        byteForImg = BitmapConverter.getBytes(selectedImage);
                        Log.d("666", "onActivityResult: " + BitmapConverter.getImage(byteForImg).toString());
                        imageViewOfCard.setImageBitmap(selectedImage);

                        setImgBtn.setText(s);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }


    public String getRealPathFromURI(Uri uri) {//для настоящего названия пути картинки
        String[] projection = {MediaStore.Images.Media.DATA};
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void save(View view) {
        Card card = new Card(titleET.getText().toString(), descriptionET.getText().toString(), byteForImg);
        viewModel.insertCard(card);
        Intent intent = new Intent(MainActivity.this, Testt.class);
        intent.putExtra("id", viewModel.getCardByTitle(card.getTitle()).getId());//сюда надо добавлять ID взятый из БД, а не из поля только что созданного объекта!!!!
        startActivity(intent);

    }

    public void reveal(View view) {
        viewModel.deleteAllCards();
    }
}
