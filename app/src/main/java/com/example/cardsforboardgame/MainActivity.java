package com.example.cardsforboardgame;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

public class MainActivity extends AppCompatActivity {

    private Button setImgBtn;
    private ImageView imageViewOfCard;
    private final int Pick_image = 1;
    private byte[] byteForImg;
    private MainViewModel viewModel;
    EditText titleET, descriptionET;

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
                        byteForImg = BitmapConverter.getBytes(selectedImage);
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
        Log.d("494949", "save: " +viewModel.getCardById(1).getTitle());
        Intent intent = new Intent(MainActivity.this, Test.class);
        intent.putExtra("id", card.getId());
        //startActivity(intent);
    }
}
