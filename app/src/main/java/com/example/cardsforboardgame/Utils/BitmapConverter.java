package com.example.cardsforboardgame.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class BitmapConverter {
    // из bitmap в byte[]
    @TypeConverter
    public static byte[] getBytes(Bitmap bitmap) {
        Bitmap bmp = bitmap;
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        bmp.recycle();
        return byteArray;
    }

    // из byte[] в bitmap. Потому что просто битмап с картинки не достаётся
    @TypeConverter
    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
