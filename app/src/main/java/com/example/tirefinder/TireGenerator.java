package com.example.tirefinder;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.tirefinder.databinding.ActivityTireGeneratorBinding;

public class TireGenerator extends AppCompatActivity {

    ActivityTireGeneratorBinding binding;
    TireModel tireModel;
    TireInfoDatabase db;
    TireInfoDAO tireInfoDAO;
    boolean updatingTire = false;
    int updatingTireID;
    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    ActivityResultLauncher<Intent> cameraResult;
    static final int CAMERA_PERM_CODE = 123;
    static final int CAMERA_RESULT_CODE = 122;
    ActivityResultLauncher<Intent> cameraResultLauncher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tire_generator);

        initialProcess();

        checkIfUpdating();

        initiateResultLauncher();

        createListeners();







    }

    private void createListeners() {

        binding.imageView.setOnClickListener(click ->{
            Intent intent = new Intent(this, PictureViewingActivity.class);
            intent.putExtra("id", updatingTireID);
            startActivity(intent);
        });

        binding.pictureButton.setOnClickListener(click ->{
            startCamera();
        });


        binding.deleteButton.setOnClickListener(click ->{

            AlertDialog.Builder builder = new AlertDialog.Builder( this );


            if (updatingTire){
                builder.setTitle("Question:")
                        .setMessage("Do you really want to delete " + binding.nameEditText.getText() + " from the database?")
                        .setNegativeButton("No", (dialog, which) -> {})
                        .setPositiveButton("Yes", (dialog, which) -> {

                            tireInfoDAO.DeleteTire(new TireInfo(updatingTireID));

                            tireModel.updatingTire.setValue(false);
                            Intent intent = new Intent(this, TireSearch.class);
                            startActivity(intent);

                        }).create().show();

            }else{
                tireModel.updatingTire.setValue(false);
                Intent intent = new Intent(this, TireSearch.class);
                startActivity(intent);
            }


        });

        binding.doneButton.setOnClickListener(click -> {
            TireInfo.TireBuilder builder = new TireInfo.TireBuilder();
            Bitmap picture = ((BitmapDrawable)binding.imageView.getDrawable()).getBitmap();
            if (updatingTire){
                TireInfo tire = builder
                        .setID(updatingTireID)
                        .setName(binding.nameEditText.getText().toString())
                        .setVehicle(binding.vehicleEditText.getText().toString())
                        .setTread(binding.treadEditText.getText().toString())
                        .setPicture(picture)
                        .build();
                tireInfoDAO.UpdateTire(tire);
            }else{
                TireInfo tire = builder
                        .setID(tireInfoDAO.getAllTires().size())
                        .setName(binding.nameEditText.getText().toString())
                        .setVehicle(binding.vehicleEditText.getText().toString())
                        .setTread(binding.treadEditText.getText().toString())
                        .setPicture(picture)
                        .build();

                tireInfoDAO.InsertTire(tire);
            }
            tireModel.updatingTire.setValue(false);
            Intent intent = new Intent(this, TireSearch.class);
            startActivity(intent);
        });
    }

    private void checkIfUpdating() {
        if (updatingTire){
            TireInfo tireToUpdate = tireInfoDAO.getTireByID(updatingTireID);
            binding.nameEditText.setText(tireToUpdate.name);
            binding.vehicleEditText.setText(tireToUpdate.vehicle);
            binding.treadEditText.setText(tireToUpdate.treadType);
            binding.imageView.setImageBitmap(tireToUpdate.picture);
        }
    }

    private void initialProcess() {
        tireModel = new ViewModelProvider(this).get(TireModel.class);

        binding = ActivityTireGeneratorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = Room
                .databaseBuilder(getApplicationContext(), TireInfoDatabase.class, "TireInfoDatabase")
                .allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build();
        tireInfoDAO = db.TireDAO();

        Bundle extras = getIntent().getExtras();
        updatingTire = getIntent().getExtras().getBoolean("updatingTire");
        updatingTireID = extras.getInt("id");
    }


    private void startCamera() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){ // Don't have permission to use camera
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE); //Request permission to use camera
        }else{
            openCamera();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERM_CODE){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(this, "Camera permission is required to use the camera", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void openCamera() {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraResultLauncher.launch(cameraIntent);

    }

    private void initiateResultLauncher(){
        cameraResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            // There are no request codes
                            Bitmap image = (Bitmap) result.getData().getExtras().get("data");
                            binding.imageView.setImageBitmap(image);
                        }
                    }
                });
    }
}