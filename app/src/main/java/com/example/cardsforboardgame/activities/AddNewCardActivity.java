package com.example.cardsforboardgame.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.example.cardsforboardgame.Testt;
import com.example.cardsforboardgame.Utils.BitmapConverter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AddNewCardActivity extends AppCompatActivity {

    private Button setImgBtn;
    private ImageView imageViewOfCard;
    private final int Pick_image = 1;
    private byte[] byteForImg;
    private MainViewModel viewModel;
    EditText titleET, descriptionET;
    ArrayList<Card> cardList;
    Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_card);

        setImgBtn = findViewById(R.id.button);
        imageViewOfCard = findViewById(R.id.imageView);

        bitmap = null;
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        titleET = findViewById(R.id.titleET);
        descriptionET = findViewById(R.id.descriptionET);

        cardList = new ArrayList();
        LiveData<List<Card>> cardsFromListData = viewModel.getCards();
        cardsFromListData.observe(this, new Observer<List<Card>>() {//для вытаскивания списка из LiveData
            @Override
            public void onChanged(List<Card> cards) {
                cardList.addAll(cards);
            }
        });

        Intent intent = getIntent();
        int cardId = intent.getIntExtra("id",0);
        if(cardId==0){
            Toast.makeText(this, R.string.CreateNewCard, Toast.LENGTH_SHORT).show();
            setImgBtn.setVisibility(View.VISIBLE);
        }else{
            titleET.setText(viewModel.getCardById(cardId).getTitle());
            descriptionET.setText(viewModel.getCardById(cardId).getDescrption());
            imageViewOfCard.setImageBitmap(viewModel.getCardById(cardId).getBitmap());
            setImgBtn.setVisibility(View.GONE);
        }
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

//    private boolean hasImage(@NonNull ImageView view) {
//        Drawable drawable = view.getDrawable();
//        boolean hasImage = (drawable != null);
//
//        if (hasImage && (drawable instanceof BitmapDrawable)) {
//            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
//        }
//
//        return hasImage;
//    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;

        return true;
    }

    public void save(View view) {


        if (isEmpty(titleET)) {
            titleET.setError(getString(R.string.NeedTitle));
            Toast.makeText(this, getString(R.string.NeedTitle), Toast.LENGTH_SHORT).show();
        } else if (isEmpty(descriptionET)) {
            descriptionET.setError(getString(R.string.NeedDescription));
            Toast.makeText(this, getString(R.string.NeedDescription), Toast.LENGTH_SHORT).show();
        } else if (bitmap == null) {
            Toast.makeText(this, R.string.ImageIsEmpty, Toast.LENGTH_SHORT).show();
        } else {
            Card card = new Card(titleET.getText().toString(), descriptionET.getText().toString(), bitmap);
            viewModel.insertCard(card);
            Intent intent = new Intent(AddNewCardActivity.this, Testt.class);
            intent.putExtra("id", viewModel.getCardByTitle(card.getTitle()).getId());//сюда надо добавлять ID взятый из БД, а не из поля только что созданного объекта!!!!
            startActivity(intent);
        }


    }


    public void close(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
