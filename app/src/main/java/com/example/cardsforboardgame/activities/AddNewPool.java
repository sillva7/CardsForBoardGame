package com.example.cardsforboardgame.activities;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.Testt;
import com.example.cardsforboardgame.Utils.BitmapConverter;
import com.example.cardsforboardgame.adapters.CardAdapter;
import com.nex3z.flowlayout.FlowLayout;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.example.cardsforboardgame.R.drawable;


public class AddNewPool extends AppCompatActivity {
    EditText titleET, descriptionET;
    FlowLayout flowLayoutBtns;
    public static Button addNewBtn;//не забыть исправить с помощью того метода что и при галереи использовался мб?
    public static ArrayList<String> cards;//будем помещать карточки сюда и вставлять весь список в объект Pool
    private final int Pick_image = 1;
    Bitmap bitmap;
    ImageView imageViewOfCard;
    Button setImg;
    MainViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_pool);
        titleET = findViewById(R.id.etPoolTitle);
        descriptionET = findViewById(R.id.etPoolDescription);
        flowLayoutBtns = findViewById(R.id.flowLayoutBtns);
        addNewBtn = findViewById(R.id.addNewCard);
        imageViewOfCard = findViewById(R.id.imageViewOfCard);
        setImg = findViewById(R.id.setIMG);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        cards = new ArrayList<>();

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

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0) {
            return false;
        }

        return true;
    }

    public void savePool(View view) {
        LiveData<List<Pool>> poolsFromLiveData = viewModel.getPools();
        final ArrayList<String> titlesOfPool = new ArrayList<>();
        poolsFromLiveData.observe(AddNewPool.this, new Observer<List<Pool>>() {
            @Override
            public void onChanged(List<Pool> pools) {
                for (Pool pool : pools) {
                    titlesOfPool.add(pool.getTitle());
                }
            }
        });

        if (isEmpty(titleET)) {
            titleET.setError(getString(R.string.NeedTitle));
            Toast.makeText(this, getString(R.string.NeedTitle), Toast.LENGTH_SHORT).show();
        } else if (titlesOfPool.contains(titleET.getText().toString())) {
            titleET.setError(getString(R.string.NeedUniqName));
            Toast.makeText(this, "Need unique title", Toast.LENGTH_SHORT).show();
        } else if (isEmpty(descriptionET)) {
            descriptionET.setError(getString(R.string.NeedDescription));
            Toast.makeText(this, getString(R.string.NeedDescription), Toast.LENGTH_SHORT).show();
        } else if (cards.isEmpty()) {
            Toast.makeText(this, R.string.EmptyListOfCard, Toast.LENGTH_SHORT).show();
            addNewBtn.setError(getString(R.string.needCards));
        } else if (bitmap == null) {
            Toast.makeText(this, R.string.choose_picture, Toast.LENGTH_SHORT).show();
        } else {
            Pool pool;

            //pool = new Pool(titleET.getText().toString(), descriptionET.getText().toString(), cards);//добавим в будущем

            pool = new Pool(titleET.getText().toString(), descriptionET.getText().toString(), cards, bitmap);


            viewModel.insertPool(pool);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            AddNewPool.this.finish();

        }


    }

    public void close(View view) {

        AddNewPool.this.finish();

    }
}