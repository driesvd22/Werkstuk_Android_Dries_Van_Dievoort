package com.example.driesvandievoort.android.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.driesvandievoort.android.DAOs.UserDAO;
import com.example.driesvandievoort.android.Entities.User;

@Database(entities = {User.class}, version = 1)
public abstract  class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDAO userDao();

    public static AppDatabase getAppDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "user_database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
