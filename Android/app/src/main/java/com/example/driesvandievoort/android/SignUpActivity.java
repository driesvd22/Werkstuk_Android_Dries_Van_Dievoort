package com.example.driesvandievoort.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputUsername;
    private EditText inputPassword;
    private EditText inputPhoneNumber;
    private Button btnsignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        inputUsername = (EditText)findViewById(R.id.inputUsername);
        inputPassword = (EditText)findViewById(R.id.inputPassword);
        inputPhoneNumber = (EditText) findViewById(R.id.inputPhoneNumber);
        btnsignUp = (Button)findViewById(R.id.btnSignUp);

        btnsignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"Redirecting...",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SignUpActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }
}
