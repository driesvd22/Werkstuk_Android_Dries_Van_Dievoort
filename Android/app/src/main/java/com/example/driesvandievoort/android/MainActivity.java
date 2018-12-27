package com.example.driesvandievoort.android;

import android.app.AlertDialog;
import android.arch.persistence.room.Room;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.driesvandievoort.android.Database.AppDatabase;
import com.example.driesvandievoort.android.Entities.Category;
import com.example.driesvandievoort.android.Entities.User;
import com.example.driesvandievoort.android.Varia.CurrentUser;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private EditText editUsername;
    private EditText editPassword;
    private Button btnlogin;
    private Button btnsignUp;
    private static final String DATABASE_NAME = "app_db";
    public AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editUsername = (EditText)findViewById(R.id.inputUsername);
        editPassword = (EditText)findViewById(R.id.inputPassword);
        btnlogin = (Button)findViewById(R.id.btnLogin);
        btnsignUp = (Button)findViewById(R.id.btnSignupScreen);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new validateUserAsync().execute(editUsername.getText().toString(), editPassword.getText().toString());
            }
        });

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),getString(R.string.Redirect),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.ReallyExit))
                .setMessage(getString(R.string.ExitSure))
                .setNegativeButton(getString(R.string.NoExit), null)
                .setPositiveButton(getString(R.string.YesExit), new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }).create().show();
    }

    private class validateUserAsync extends AsyncTask<String,Integer,Integer>
    {
        User user;
        @Override
        protected Integer doInBackground(String... strings) {
            /*List<Category> list = new ArrayList<>();
            list.add(new Category(getString(R.string.FingerFoods), R.drawable.fingerfoods));
            list.add(new Category(getString(R.string.Traditional), R.drawable.traditional));
            list.add(new Category(getString(R.string.Chinese), R.drawable.chinese));
            list.add(new Category(getString(R.string.Fish), R.drawable.fish));
            list.add(new Category(getString(R.string.Pizza), R.drawable.pizza));
            list.add(new Category(getString(R.string.Vegetarian), R.drawable.vegetarian));
            list.add(new Category(getString(R.string.Pasta), R.drawable.pasta));
            appDatabase.categoryDAO().insertCategory(list.get(0),list.get(1),list.get(2),list.get(3),list.get(4),list.get(5),list.get(6));*/
            String ik = appDatabase.userDao().checkIfUserExists(strings[0], strings[1]);
            if (ik == null)
            {
                ik = "";
            }
            if (strings[0] == null || strings[0].isEmpty())
            {
                strings[0] = " ";
            }
            if (ik.equals(strings[0]))
            {
                user = appDatabase.userDao().getUser(strings[0], strings[1]);
                return 1;
            }
            else
            {
                return 0;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            if (integer == 1)
            {
                Toast.makeText(getApplicationContext(), getString(R.string.LoggedIn), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                CurrentUser.currentUser = user;
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(getApplicationContext(), getString(R.string.WrongCredentials), Toast.LENGTH_SHORT).show();

            }
        }
    }

}
