package com.example.driesvandievoort.android.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.driesvandievoort.android.Entities.Category;
import com.example.driesvandievoort.android.Entities.Favorite;
import com.example.driesvandievoort.android.Entities.User;

import java.util.List;

@Dao
public interface FavoriteDAO {
    @Query("SELECT * FROM favorite where username = :username")
    List<Favorite> getAllWhereUserIs(String username);

    @Query("SELECT * FROM favorite where username = :username AND categoryName = :categoryName")
    Favorite getFavorite(String username, String categoryName);

    @Insert
    void insertFavorite(Favorite... favorites);

    @Delete
    void deleteFavorite(Favorite favorite);

}
