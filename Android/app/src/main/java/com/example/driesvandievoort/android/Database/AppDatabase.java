package com.example.driesvandievoort.android.Database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.driesvandievoort.android.DAOs.CategoryDAO;
import com.example.driesvandievoort.android.DAOs.FavoriteDAO;
import com.example.driesvandievoort.android.DAOs.UserDAO;
import com.example.driesvandievoort.android.Entities.Category;
import com.example.driesvandievoort.android.Entities.Favorite;
import com.example.driesvandievoort.android.Entities.User;

@Database(entities = {User.class, Category.class, Favorite.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDAO userDao();
    public abstract CategoryDAO categoryDAO();
    public abstract FavoriteDAO favoriteDAO();

    public static AppDatabase getAppDatabase(Context context){
        if (INSTANCE == null){
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class, "app_db").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE = null;
    }
}
