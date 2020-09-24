package com.example.cardsforboardgame.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cardsforboardgame.Classes.Card;
import com.example.cardsforboardgame.Classes.Pool;
import com.example.cardsforboardgame.DBStuf.MainViewModel;
import com.example.cardsforboardgame.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.app.PendingIntent.getActivity;

public class AddNewCardActivity extends AppCompatActivity {

    private Button setImgBtn, save, close;
    private ImageView imageViewOfCard, testIV;
    private final int Pick_image = 1111;
    private final int Pick_image_from_camera = 2;
    private byte[] byteForImg;
    private MainViewModel viewModel;
    private EditText titleET, descriptionET;
    private ArrayList<Card> cardList;
    private Bitmap bitmap;
    private int cardId;//важная часть, т.к. от неё зависит состояние кнопок по загрузки изображения и кнопки сохранить/обновить
    private int fromPoolId;
    private String pathToImage;
    private View customLayout;
    private int flag = 0;
    private Uri uriFilePath;
    private static final String IMAGE_DIRECTORY = "/14052020";
   // static final int REQUEST_TAKE_PHOTO = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {//для камеры. НАЧАЛО
            if (uriFilePath == null && savedInstanceState.getString("uri_file_path") != null) {
                uriFilePath = Uri.parse(savedInstanceState.getString("uri_file_path"));
            }
        }//для камеры.КОНЕЦ

        setContentView(R.layout.activity_add_new_card);

        setImgBtn = findViewById(R.id.button);
        save = findViewById(R.id.save);
        imageViewOfCard = findViewById(R.id.imageView);
        testIV = findViewById(R.id.imageView2);
        close = findViewById(R.id.close);

        fromPoolId = getIntent().getIntExtra("toPoolId", 0);


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
        cardId = intent.getIntExtra("id", 0);
        if (cardId == 0) {
            Toast.makeText(this, R.string.CreateNewCard, Toast.LENGTH_SHORT).show();
            setImgBtn.setVisibility(View.VISIBLE);
        } else {
            save.setVisibility(View.GONE);
            close.setVisibility(View.GONE);
            setImgBtn.setVisibility(View.GONE);

            imageViewOfCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AddNewCardActivity.this);
                    customLayout = getLayoutInflater().inflate(R.layout.img_of_card, null);//TYT
                    builder.setView(customLayout);//*******************************************************и тут подключаем кастомынй лайаут

                    ImageView iv = customLayout.findViewById(R.id.bigImg);
                    iv.setImageURI(Uri.parse(viewModel.getCardById(cardId).getPathToImage()));
                    iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            save.setText(R.string.Update);
            titleET.setText(viewModel.getCardById(cardId).getTitle());
            descriptionET.setText(viewModel.getCardById(cardId).getDescrption());
            imageViewOfCard.setImageURI(Uri.parse(viewModel.getCardById(cardId).getPathToImage()));
        }

    }


    public void setImg(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewCardActivity.this);
        customLayout = getLayoutInflater().inflate(R.layout.galery_or_camera, null);//TYT
        builder.setView(customLayout);//*******************************************************и тут подключаем кастомынй лайаут

        Button galery = customLayout.findViewById(R.id.galery);
        Button camera = customLayout.findViewById(R.id.camera);

        galery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_OPEN_DOCUMENT);////Вызываем стандартную галерею для выбора изображения с помощью Intent.ACTION_PICK
                photoPickerIntent.setType("image/*");//Тип получаемых объектов - image
                startActivityForResult(photoPickerIntent, Pick_image);//Запускаем переход с ожиданием обратного результата в виде информации об изображении
            }
        });
//        camera.setOnClickListener(new View.OnClickListener() {//для камеры. НАчало
//            @Override
//            public void onClick(View v) {
//                dispatchTakePictureIntent();
//
//            }
//        });//для камер. КонЕЦ

        AlertDialog dialog = builder.create();
        dialog.show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {//для загрузки с галереи изображения
        super.onActivityResult(requestCode, resultCode, data);//хм...а этот же метод можно было использовать и для выбора Карточек в пул, не так ли?...

        //Получаем URI изображения, преобразуем его в Bitmap
        //объект и отображаем в элементе ImageView нашего интерфейса:
        if (resultCode == RESULT_OK) {
            if (requestCode == Pick_image) {
                try {
                    final Uri imageUri = data.getData();
                    pathToImage = imageUri.toString();//для настоящего названия пути картинки. Если оставить так, то не будут работать API22
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        getContentResolver().takePersistableUriPermission(imageUri, Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    }
                    //Ниже представлен код, которым пользовался изначально. Ничгео плохого, но я понимаю его хуже чем часть снизу. Но оставлю на всякий
                    final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    bitmap = selectedImage;
                    //imageViewOfCard.setImageBitmap(selectedImage);
                    //ниже будем загружать картинку во второй ИВ просто для теста, чтобы найти
                    //как загрузить карт имея только путь до неё:
                    //УПД: оставлю этот способ
//                        File imgFile = new  File(pathToImage);
//                        if(imgFile.exists()){
//                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageViewOfCard.setImageURI(imageUri);
                    setImgBtn.setText(pathToImage);
                } catch (FileNotFoundException e) {
                }
//            } else if (requestCode == Pick_image_from_camera) {
////                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
////                imageViewOfCard.setImageBitmap(thumbnail);
////                saveImage(thumbnail);
//                Bundle extras = data.getExtras();
//                Bitmap imageBitmap = (Bitmap) extras.get("data");
//                imageViewOfCard.setImageBitmap(imageBitmap);
//                //pathToImage = getImageUri(this, thumbnail).toString();
//                Log.d("787878", "onActivityResult: " + pathToImage);
//                setImgBtn.setText(pathToImage);
//            }

        }
    }

//    String currentPhotoPath;
//
//    private File createImageFile() throws IOException {//для хорошего качества картинки
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//    private void galleryAddPic() {//добавление фото с камеры в галлерею
//        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        File f = new File(currentPhotoPath);
//        Uri contentUri = Uri.fromFile(f);
//        mediaScanIntent.setData(contentUri);
//        this.sendBroadcast(mediaScanIntent);
//    }
//
//    private void setPic() {//установка на imageViewOfCard
//        // Get the dimensions of the View
//        int targetW = imageViewOfCard.getWidth();
//        int targetH = imageViewOfCard.getHeight();
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//
//        BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.max(1, Math.min(photoW/targetW, photoH/targetH));
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);
//        imageViewOfCard.setImageBitmap(bitmap);
    }

//    private void dispatchTakePictureIntent() { //также для картинки с камеры, вызываем и создаём интент. ФайлПровайдер надо настроить в манифесте
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.android.fileprovider",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, Pick_image_from_camera);
//            }
//        }
//    }



//    public String saveImage(Bitmap myBitmap) {//сохранение картинки из камеры в память
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
//        File wallpaperDirectory = new File(Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
//        if (!wallpaperDirectory.exists()) {  // have the object build the directory structure, if needed.
//            wallpaperDirectory.mkdirs();
//        }
//
//        try {
//            File f = new File(wallpaperDirectory, Calendar.getInstance().getTimeInMillis() + ".jpg");
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//            MediaScannerConnection.scanFile(this,
//                    new String[]{f.getPath()},
//                    new String[]{"image/jpeg"}, null);
//            fo.close();
//            Log.d("TAG", "File Saved::---&gt;" + f.getAbsolutePath());
//
//            return f.getAbsolutePath();
//        } catch (IOException e1) {
//            e1.printStackTrace();
//        }
//        return "";
//    }
//
//    public Uri getImageUri(Context inContext, Bitmap inImage) {//для Битмапа в Юри
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
//        return Uri.parse(path);
//    }
//
//
//    public String getRealPathFromURI(Uri uri) {//для понятного названия пути картинки
//        String[] projection = {MediaStore.Images.Media.DATA};
//        @SuppressWarnings("deprecation")
//        Cursor cursor = managedQuery(uri, projection, null, null, null);
//        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//        cursor.moveToFirst();
//        return cursor.getString(column_index);
//    }
////    private boolean hasImage(@NonNull ImageView view) {
////        Drawable drawable = view.getDrawable();
////        boolean hasImage = (drawable != null);
////
////        if (hasImage && (drawable instanceof BitmapDrawable)) {
////            hasImage = ((BitmapDrawable)drawable).getBitmap() != null;
////        }
////
////        return hasImage;
////    }

    private boolean isEmpty(EditText etText) {
        if (etText.getText().toString().trim().length() > 0)
            return false;
        return true;
    }

    public void save(View view) {
        //нужна проверка на уникальность имени
        LiveData<List<Card>> cardsFromLiveData = viewModel.getCards();
        final ArrayList<String> titlesOfCards = new ArrayList<>();
        cardsFromLiveData.observe(AddNewCardActivity.this, new Observer<List<Card>>() {
            @Override
            public void onChanged(List<Card> cards) {
                for (Card card : cards) {
                    titlesOfCards.add(card.getTitle());
                }
            }
        });





        if (isEmpty(titleET)) {
            titleET.setError(getString(R.string.NeedTitle));
            Toast.makeText(this, getString(R.string.NeedTitle), Toast.LENGTH_SHORT).show();
        } else if (titlesOfCards.contains(titleET.getText().toString())) {//проверка на уникальность тайтла
            titleET.setError(getString(R.string.NeedUniqName));
            Toast.makeText(this, R.string.NeedUniqName, Toast.LENGTH_SHORT).show();
            //проверка дискрпшна:
        /*} else if (isEmpty(descriptionET)) {
            descriptionET.setError(getString(R.string.NeedDescription));
            Toast.makeText(this, getString(R.string.NeedDescription), Toast.LENGTH_SHORT).show();*/
        } else if (pathToImage == null) {

            Toast.makeText(this, R.string.ImageIsEmpty, Toast.LENGTH_SHORT).show();
        } else {
            if (cardId == 0) {
                Card card = new Card(titleET.getText().toString(), descriptionET.getText().toString(), pathToImage);
                viewModel.insertCard(card);
                if (fromPoolId != 0) {
                    Pool pool = viewModel.getPoolById(fromPoolId);
                    ArrayList<String> titles = pool.getCards();
                    titles.add(titleET.getText().toString());
                    pool.setCards(titles);
                    viewModel.updatePool(pool);
                    Intent intent = new Intent(AddNewCardActivity.this, PoolViewActivity.class);
                    intent.putExtra("id", fromPoolId);
                    startActivity(intent);
                }
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                AddNewCardActivity.this.finish();

            } else {//при апдейте карты
                Card card = new Card(cardId, titleET.getText().toString(), descriptionET.getText().toString(), pathToImage);
                viewModel.updateCard(card);
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                AddNewCardActivity.this.finish();
            }
        }
    }

    public void close(View view) {
        AddNewCardActivity.this.finish();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.deleteCard);
        MenuItem menuItem1 = menu.findItem(R.id.edit);
        if (cardId != 0) {
            menuItem.setVisible(true);
            menuItem1.setVisible(true);

        } else {
            menuItem.setVisible(false);
            menuItem1.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.deleteCard:
                deleteCard();
                //AddNewCardActivity.this.finish();
                return true;
            case R.id.edit:

                if (flag == 0) {
                    setImgBtn.setVisibility(View.VISIBLE);
                    close.setVisibility(View.VISIBLE);
                    save.setVisibility(View.VISIBLE);
                    flag = 1;
                } else {
                    setImgBtn.setVisibility(View.GONE);
                    close.setVisibility(View.GONE);
                    save.setVisibility(View.GONE);
                    flag = 0;
                }
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteCard() {//перед удалением будет появляться окно запрашивающее точно ли хочу удалить?
        final AlertDialog.Builder builder = new AlertDialog.Builder(AddNewCardActivity.this);
        builder.setTitle(R.string.delete);
        builder.setMessage(R.string.rusure);
        builder.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(AddNewCardActivity.this, R.string.no, Toast.LENGTH_SHORT).show();
                    }
                });
        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        LiveData<List<Pool>> poolsFromListData = viewModel.getPools();//ниже блок для того чтобы удалять карточку из Пулов
                        poolsFromListData.observe(AddNewCardActivity.this, new Observer<List<Pool>>() {//для вытаскивания списка из LiveData
                            @Override
                            public void onChanged(List<Pool> poolss) {
                                poolss = new ArrayList<>();
                                for (int i = 0; i < poolss.size(); i++) {
                                    ArrayList<String> cards = poolss.get(i).getCards();
                                    ArrayList<String> cardsForRemove = new ArrayList<>();
                                    for (int j = 0; j < cards.size(); j++) {
                                        if (cards.get(j) == viewModel.getCardById(cardId).getTitle()) {
                                            cardsForRemove.add(cards.get(j));
                                        }
                                        //cards.removeAll(cardsForRemove);
                                        if (cardsForRemove.size() > 0) {
                                            cards.removeAll(cardsForRemove);
                                            Pool pool = poolss.get(i);
                                            pool.setCards(cards);
                                            viewModel.updatePool(pool);
                                        }
                                    }
                                }
                            }
                        });
                        viewModel.deleteCard(viewModel.getCardById(cardId));
                        Toast.makeText(AddNewCardActivity.this, R.string.deleted, Toast.LENGTH_SHORT).show();
                        AddNewCardActivity.this.finish();
                    }
                });
        builder.show();
    }
}
