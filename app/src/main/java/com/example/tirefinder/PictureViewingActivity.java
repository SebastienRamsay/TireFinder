package com.example.tirefinder;

import android.os.Bundle;

import com.example.tirefinder.databinding.ActivityTireGeneratorBinding;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.room.Room;

import com.example.tirefinder.databinding.ActivityPictureViewingBinding;

public class PictureViewingActivity extends AppCompatActivity {

    private ActivityPictureViewingBinding binding;
    TireModel tireModel;
    TireInfoDatabase db;
    TireInfoDAO tireInfoDAO;
    int tireID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initialProcess();

        populateImageView();

    }

    private void populateImageView() {

        binding.imageView2.setImageBitmap(tireInfoDAO.getTireByID(tireID).picture);

    }

    private void initialProcess() {
        tireModel = new ViewModelProvider(this).get(TireModel.class);

        binding = ActivityPictureViewingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room
                .databaseBuilder(getApplicationContext(), TireInfoDatabase.class, "TireInfoDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        tireInfoDAO = db.TireDAO();

        Bundle extras = getIntent().getExtras();
        tireID = extras.getInt("id");
    }
}