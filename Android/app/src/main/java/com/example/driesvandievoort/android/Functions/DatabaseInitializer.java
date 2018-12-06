package com.example.driesvandievoort.android.Functions;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.driesvandievoort.android.Database.AppDatabase;
import com.example.driesvandievoort.android.Entities.User;

import java.util.List;

public class DatabaseInitializer {

    private static final String TAG = DatabaseInitializer.class.getName();

    public static void populateAsync(@NonNull final AppDatabase db) {
        PopulateDbAsync task = new PopulateDbAsync(db);
        task.execute();
    }

    public static void populateSync(@NonNull final AppDatabase db) {
        populateWithTestData(db);
    }

    private static User addUser(final AppDatabase db, User user) {
        db.userDao().insertUser(user);
        return user;
    }


    private static void populateWithTestData(AppDatabase db) {
        User user = new User();
        user.setUsername("driesvd");
        user.setPassword("1234");
        user.setPhoneNumber("0412345678");
        addUser(db, user);

        List<User> userList = db.userDao().getAll();
        Log.d(DatabaseInitializer.TAG, "Rows Count: " + ((List) userList).size());
    }

    private static class PopulateDbAsync extends AsyncTask<Void,Void,Void>{

        private final AppDatabase mDb;

        PopulateDbAsync(AppDatabase db){
            mDb = db;
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            populateWithTestData(mDb);
            return null;
        }
    }

}
