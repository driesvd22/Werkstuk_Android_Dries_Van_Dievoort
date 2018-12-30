package com.example.driesvandievoort.android.Entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "favorite")
public class Favorite {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "username")
    private String username;
    @ColumnInfo(name = "categoryName")
    private String categoryName;

    public Favorite(String name, String category) {
        username = name;
        categoryName = category;
    }

    public Favorite() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
