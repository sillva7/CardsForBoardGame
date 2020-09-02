package com.example.cardsforboardgame.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.TextView;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.adapters.CardAdapter;
import com.nex3z.flowlayout.FlowLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

public class AddNewPool extends AppCompatActivity {
    EditText titleET, descriptionET;
    FlowLayout flowLayoutBtns;
    public static Button addNewBtn;//не забыть исправить с помощью того метода что и при галереи использовался мб?
    public static ArrayList<Card> cards = new ArrayList<>();//будем помещать карточки сюда и вставлять весь список в объект Pool
    private final int Pick_image = 1;
    Bitmap bitmap;
    ImageView imageViewOfCard;
    Button setImg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pool);
        flowLayoutBtns = findViewById(R.id.flowLayoutBtns);
        addNewBtn = findViewById(R.id.addNewCard);
        imageViewOfCard = findViewById(R.id.imageViewOfCard);
        setImg = findViewById(R.id.setIMG);
        addNewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBtn();
            }
        });
    }


    public void addBtn() {

        Intent intent = new Intent(AddNewPool.this, AllCardsActivity.class);//call another activity
        intent.putExtra("checkbox", 1);
        startActivity(intent);

        //Button myButton = new Button(this);//создаём кнопу. Надо будет поставить какую-нибудь проверку на то, что если карточка не была добавлена, кнопа новая не создавалась
        //myButton.setText(R.string.add_new_card_to_pool_btn);//устанавливаем ей нужные параметры
        //myButton.setMinWidth(0);
//        myButton.setOnClickListener(new View.OnClickListener() {//ставим тригер нажатия на эту кнопу
//
//            @Override
//            public void onClick(View view) {
//                addBtn();//рекурсия для кнопы
//
//            }
//        });


        //flowLayoutBtns.addView(myButton);//добавляем кнопку в наш лайаут

    }
    public void setImg(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);////Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK
        photoPickerIntent.setType("image/*");//Тип получаемых объектов - image
        startActivityForResult(photoPickerIntent, Pick_image);//Запускаем переход с ожиданием обратного результата в виде информации об изображении
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//для загрузки с галереи изображения
        super.onActivityResult(requestCode, resultCode, data);//хм...а этот же метод можно было использовать и для выбора Карточек в пул, не так ли?...

        //Получаем URI изображения, преобразуем его в Bitmap
        //объект и отображаем в элементе ImageView нашего интерфейса:
        switch (requestCode) {
            case Pick_image://в принципе, если будет другой кейс, то можно сделать сюда и добавление карточек
                if (resultCode == RESULT_OK) {
                    try {
                        final Uri imageUri = data.getData();
                        String s = getRealPathFromURI(imageUri);//для настоящего названия пути картинки
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);

                        bitmap = selectedImage;

                        imageViewOfCard.setImageBitmap(selectedImage);//сохраняется изображение прям в приложение
                        //imageViewOfCard.setImageURI(imageUri);//изображение загружается из памяти телефона
                        setImg.setText(s);
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

    public void savePool(View view) {
    }
}