package com.example.tirefinder;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TireInfo.class}, version = 2)
@TypeConverters(Converters.class)
public abstract class TireInfoDatabase extends RoomDatabase {

    public abstract TireInfoDAO TireDAO();
}
