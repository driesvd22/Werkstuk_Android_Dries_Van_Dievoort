package com.example.driesvandievoort.android.Functions;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.driesvandievoort.android.DAOs.UserDAO;
import com.example.driesvandievoort.android.Database.AppDatabase;
import com.example.driesvandievoort.android.Entities.User;

import java.util.List;

public class DatabaseInitializer extends AndroidViewModel {

    private static final String TAG = DatabaseInitializer.class.getSimpleName();
    private UserDAO userDAO;
    private AppDatabase userDb;

    public DatabaseInitializer(Application application){
        super(application);
        userDb = AppDatabase.getAppDatabase(application);
        userDAO = userDb.userDao();
    }

    public void addUser(User user) {
        new InsertAsyncTask(userDAO).execute(user);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG,"Viewmodel Destroyed");
    }

    private static class InsertAsyncTask extends AsyncTask<User,Void,Void>{

        UserDAO mUserDao;

        public InsertAsyncTask(UserDAO mUserDao){
            this.mUserDao = mUserDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            mUserDao.insertUser(users[0]);
            return null;
        }
    }

}
