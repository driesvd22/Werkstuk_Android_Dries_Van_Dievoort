package com.example.driesvandievoort.android.DAOs;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.example.driesvandievoort.android.Entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT phoneNumber FROM user WHERE phoneNumber = :phone_number")
    int checkIfUserExists(int phone_number);



}
