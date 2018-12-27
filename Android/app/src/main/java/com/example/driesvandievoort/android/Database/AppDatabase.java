package com.example.driesvandievoort.android.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.driesvandievoort.android.DAOs.CategoryDAO;
import com.example.driesvandievoort.android.DAOs.UserDAO;
import com.example.driesvandievoort.android.Entities.Category;
import com.example.driesvandievoort.android.Entities.User;

@Database(entities = {User.class, Category.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDAO userDao();
    public abstract CategoryDAO categoryDAO();

    public static AppDatabase getAppDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "app_database").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
