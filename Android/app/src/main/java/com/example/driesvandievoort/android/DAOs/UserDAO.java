package com.example.driesvandievoort.android.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.driesvandievoort.android.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT phoneNumber FROM user WHERE phoneNumber = :phone_number")
    String checkIfUserExists(String phone_number);

    @Insert
    void insertUser(User... users);

    @Delete
    void deleteUser(User user);

}
