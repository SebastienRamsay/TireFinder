package com.example.tirefinder;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Converters {
    @TypeConverter
    public byte[] fromBitmap(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try{
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        }catch (Exception e){}
        return outputStream.toByteArray();
    }
    @TypeConverter
    public Bitmap toBitmap(byte[] byteArray){
        try {
            return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }catch(Exception e){
            return null;
        }
    }

}
