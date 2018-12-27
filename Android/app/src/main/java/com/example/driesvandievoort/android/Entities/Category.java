package com.example.driesvandievoort.android.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "category")
public class Category {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String Name;
    @ColumnInfo(name = "image")
    private int Image;

    public Category(String name, int image) {
        Name = name;
        Image = image;
    }

    public Category() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
