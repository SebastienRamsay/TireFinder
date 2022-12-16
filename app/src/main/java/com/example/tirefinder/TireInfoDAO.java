package com.example.tirefinder;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.DeleteColumn;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TireInfoDAO {
    @Insert
    void InsertTire(TireInfo T);

    @Query("Select * from TireInfo")
    List<TireInfo> getAllTires();

    @Query("Select * from TireInfo where name like '%' || :name || '%'")
    List<TireInfo> getTiresWithName(String name);

    @Query("Select * from TireInfo where id = :id")
    TireInfo getTireByID(int id);

    @Update
    void UpdateTire(TireInfo TireToUpdate);

    @Delete
    void DeleteTire(TireInfo TireToDelete);





}
