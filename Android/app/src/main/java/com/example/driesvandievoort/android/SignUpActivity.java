package com.example.driesvandievoort.android;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.driesvandievoort.android.Database.AppDatabase;
import com.example.driesvandievoort.android.Entities.User;
import com.example.driesvandievoort.android.Varia.CurrentUser;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputPhoneNumber;
    private Button btnsignUp;
    private static final String DATABASE_NAME = "app_db";
    public AppDatabase appDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputUsername = (EditText) findViewById(R.id.inputUsername);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputPhoneNumber = (EditText) findViewById(R.id.inputPhoneNumber);
        btnsignUp = (Button) findViewById(R.id.btnSignUp);

        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new checkUserAsync().execute(inputUsername.getText().toString(), inputPhoneNumber.getText().toString(), inputPassword.getText().toString());
            }
        });
    }


        private class checkUserAsync extends AsyncTask<String, Integer, Integer> {
            final User user = new User();
            @Override
            protected Integer doInBackground(final String... strings) {
                user.setUsername(strings[0]);
                user.setPassword(strings[2]);
                user.setPhoneNumber(strings[1]);
                String ik = appDatabase.userDao().checkIfUserExistsSignUp(strings[0], strings[1]);
                if (ik == null)
                {
                    ik = "";
                }
                if (strings[0].isEmpty() || strings[1].isEmpty() || strings[2].isEmpty())
                {
                    ik = "ik";
                }
                if(ik.equals("ik"))
                {
                    return 2;
                }
                if (!ik.isEmpty()) {
                    return 0;
                } else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            appDatabase.userDao().insertUser(user);
                        }
                    }).start();
                    return 1;
                }
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values[0]);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                if (integer == 1) {
                    Toast.makeText(getApplicationContext(), getString(R.string.UserCreated), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                    CurrentUser.currentUser = user;
                    startActivity(intent);
                } else if (integer == 2)
                {
                    Toast.makeText(getApplicationContext(), getString(R.string.FiledsFilledIn), Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), getString(R.string.UserCredExist), Toast.LENGTH_SHORT).show();

                }
            }
        }
    }
