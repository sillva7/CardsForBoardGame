package com.example.cardsforboardgame.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;
import com.nex3z.flowlayout.FlowLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class AddNewPool extends AppCompatActivity {
    private EditText titleET, descriptionET;
    private FlowLayout flowLayoutBtns;
    public static Button addNewBtn;//не забыть исправить с помощью того метода что и при галереи использовался мб?
    public static ArrayList<String> cards;//будем помещать карточки сюда и вставлять весь список в объект Pool
    private final int Pick_image = 1;
    private Bitmap bitmap;
    private String pathToImage;
    private ImageView imageViewOfCard;
    private Button setImg;
    private MainViewModel viewModel;
    private Uri imageUri;
    private View customLayout;


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

        if (addNewBtn.getText().toString().length() == 0) {
            addNewBtn.setText(R.string.choose_cards);
        }

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
        //myButton.setText(R.string.add_new_card_to_pool_btn);//устанавливаем ей нужные параметрыca
        //myButton.setMinWidth(0);
//        myButton.setOnClickListener(new View.OnClickListener() {//ставим тригер нажатия на эту кнопу//
//            @Override
//            public void onClick(View view) {
//                addBtn();//рекурсия для кнопы//
//            }
//        });
        //flowLayoutBtns.addView(myButton);//добавляем кнопку в наш лайаут
    }

    public void setImg(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);//Вызываем стандартную галерею для выбора изображения с помощью
        // ACTION_OPEN_DOCUMENT(ИМенно ACTION_OPEN_DOCUMENT - решает проблемы с SecurityException)
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

                    imageUri = data.getData();
                    //pathToImage = getRealPathFromURI(imageUri);//для настоящего названия пути картинки
                    pathToImage = imageUri.toString();
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {//2 эти строчки чисто для SecurityException
                        getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);//SecurityException
                    }
//                    File imgFile = new File(pathToImage);
//                    if (imgFile.exists()) {
//                      Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath(),bmOptions);
                    imageViewOfCard.setImageURI(imageUri);
                    setImg.setText(pathToImage);
                    //}


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

        Log.d("456456", "savePool: " + titlesOfPool.toString());

        if (isEmpty(titleET)) {
            titleET.setError(getString(R.string.NeedTitle));
            Toast.makeText(this, getString(R.string.NeedTitle), Toast.LENGTH_SHORT).show();
        } else if (titlesOfPool.contains(titleET.getText().toString())) {
            titleET.setError(getString(R.string.NeedUniqName));
            Toast.makeText(this, "Need unique title", Toast.LENGTH_SHORT).show();
//        } else if (isEmpty(descriptionET)) {
//            descriptionET.setError(getString(R.string.NeedDescription));
//            Toast.makeText(this, getString(R.string.NeedDescription), Toast.LENGTH_SHORT).show();
        } else if (cards.isEmpty()) {
            Toast.makeText(this, R.string.EmptyListOfCard, Toast.LENGTH_SHORT).show();
            addNewBtn.setError(getString(R.string.needCards));
//        } else if (pathToImage == null) {
//            Toast.makeText(this, R.string.choose_picture, Toast.LENGTH_SHORT).show();
        } else {
            Pool pool;
            //pool = new Pool(titleET.getText().toString(), descriptionET.getText().toString(), cards);//добавим в будущем

            pool = new Pool(titleET.getText().toString(), descriptionET.getText().toString(), cards, pathToImage);

            viewModel.insertPool(pool);
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            AddNewPool.this.finish();

        }


    }

    public void close(View view) {

        AddNewPool.this.finish();

    }
}