package com.example.driesvandievoort.android;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.driesvandievoort.android.Database.AppDatabase;
import com.example.driesvandievoort.android.Entities.Category;
import com.example.driesvandievoort.android.Entities.Favorite;
import com.example.driesvandievoort.android.Entities.User;
import com.example.driesvandievoort.android.Varia.Adapter;
import com.example.driesvandievoort.android.Varia.CurrentUser;

import java.util.ArrayList;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    List<Favorite> favorites = new ArrayList<>();
    List<Category> categories = new ArrayList<>();
    private static final String DATABASE_NAME = "app_db";
    public AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
        new getCategoryList().execute();
        setContentView(R.layout.activity_favorite);
        RecyclerView recyclerView = findViewById(R.id.recyclerviewFavorite);
        Adapter adapter = new Adapter(this, categories);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private class getCategoryList extends AsyncTask<String,Integer,Integer>
    {
        User user;
        @Override
        protected Integer doInBackground(String... strings) {
            Category category;
            favorites = appDatabase.favoriteDAO().getAllWhereUserIs(CurrentUser.currentUser.getUsername());
            for (int i=0; i < favorites.size(); i++)
            {
                category = appDatabase.categoryDAO().getCategoryByName(favorites.get(i).getCategoryName());
                categories.add(category);
            }
            return 1;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {

        }
    }

}
