package com.example.tirefinder;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class TireInfo {

    @PrimaryKey
    protected int id;

    @ColumnInfo(name = "name")
    protected String name;

    @ColumnInfo(name = "vehicle")
    protected String vehicle;

    @ColumnInfo(name = "treadType")
    protected String treadType;

    @ColumnInfo(name = "picture")
    protected Bitmap picture;

    public TireInfo(int id, String name, String vehicle, String treadType, Bitmap picture) {
        this.id = id;
        this.name = name;
        this.vehicle = vehicle;
        this.treadType = treadType;
        this.picture = picture;
    }

    public TireInfo(int id) {
        this.id = id;
    }

    public TireInfo() {



    }

    private TireInfo(TireBuilder builtTire){
        id = builtTire.id;
        name = builtTire.name;
        vehicle = builtTire.vehicle;
        treadType = builtTire.treadType;
        picture = builtTire.picture;
    }
    public static class TireBuilder{
        private int id;
        private String name;
        private String vehicle;
        private String treadType;
        private Bitmap picture;

        public TireBuilder setID(int id){
            this.id = id;
            return this;
        }

        public TireBuilder setName(String name){
            this.name = name;
            return this;
        }

        public TireBuilder setVehicle(String vehicle){
            this.vehicle = vehicle;
            return this;
        }

        public TireBuilder setTread(String treadType){
            this.treadType = treadType;
            return this;
        }

        public TireBuilder setPicture(Bitmap picture){
            this.picture = picture;
            return this;
        }

        public TireInfo build(){
            return new TireInfo(this);
        }
    }
}


