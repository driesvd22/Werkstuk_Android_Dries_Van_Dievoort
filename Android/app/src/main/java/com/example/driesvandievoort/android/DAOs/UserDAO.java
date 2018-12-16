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

    @Query("SELECT username FROM user WHERE username = :username AND password = :password")
    String checkIfUserExists(String username, String password);

    @Query("SELECT username FROM user WHERE username = :username OR phoneNumber = :phoneNumber")
    String checkIfUserExistsSignUp(String username, String phoneNumber);

    @Insert
    void insertUser(User... users);

    @Delete
    void deleteUser(User user);

}
