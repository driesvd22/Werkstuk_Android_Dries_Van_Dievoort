package com.example.driesvandievoort.android.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.driesvandievoort.android.Entities.Category;

import java.util.List;

@Dao
public interface CategoryDAO {

    @Query("SELECT * FROM category")
    List<Category> getAll();

    @Query("SELECT * FROM category where name = :categoryName")
    Category getCategoryByName(String categoryName);

    @Insert
    void insertCategory(Category... categories);

    @Delete
    void deleteCategory(Category category);

}