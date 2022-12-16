package com.example.tirefinder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;

import java.util.List;

public class TireView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tire_view);


        TireInfoDatabase db = Room.databaseBuilder(getApplicationContext(), TireInfoDatabase.class, "TireInfoDatabase").build();
        TireInfoDAO tireDAO = db.TireDAO();
        List<TireInfo> Tires = tireDAO.getAllTires();

    }
}