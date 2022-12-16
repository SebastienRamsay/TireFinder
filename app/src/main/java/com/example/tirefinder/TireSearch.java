package com.example.tirefinder;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Editable;
import android.text.NoCopySpan;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.example.tirefinder.databinding.ActivityTireSearchBinding;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class TireSearch extends AppCompatActivity {

    ActivityTireSearchBinding binding;
    TireModel tireModel;
    Adapter adapter;
    TireInfoDatabase db;
    TireInfoDAO tireInfoDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tire_search);

        binding = ActivityTireSearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room
                .databaseBuilder(getApplicationContext(), TireInfoDatabase.class, "TireInfoDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        tireInfoDAO = db.TireDAO();

        Log.v("test", "Testing verbose level logcat");
        initializeModel();
        initializeRecyclerView();
        setTextWatcher();


        binding.addTireButton.setOnClickListener(click ->{
            goToUpdateActivity();
        });

    }



    private void setTextWatcher() {

        binding.searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tireModel.tiresToDisplay.setValue(tireInfoDAO.getTiresWithName(String.valueOf(s)));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initializeModel() {
        tireModel = new ViewModelProvider(this).get(TireModel.class);
        tireModel.tiresToDisplay.setValue(tireInfoDAO.getAllTires());
    }

    private void initializeRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.tireView.setLayoutManager(layoutManager);

        adapter = new Adapter(tireModel, this);
        binding.tireView.setAdapter(adapter);
    }

    /**
     * hides the keyboard.
     * @param activity current activity
     * @param view current view
     */
    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }


    public void goToUpdateActivity() {
        Intent intent = new Intent(this, TireGenerator.class);
        intent.putExtra("updatingTire", false);
        startActivity(intent);
    }
    public void updateTire(int id) {
        Intent intent = new Intent(this, TireGenerator.class);
        intent.putExtra("updatingTire", true);
        intent.putExtra("id", id);
        startActivity(intent);
    }
}