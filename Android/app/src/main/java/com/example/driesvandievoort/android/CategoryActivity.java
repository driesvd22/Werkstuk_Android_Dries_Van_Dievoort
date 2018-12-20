package com.example.driesvandievoort.android;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class CategoryActivity extends AppCompatActivity {

    private TextView categoryTitle;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        categoryTitle = (TextView)findViewById(R.id.textTitleCategory);
        imageView = (ImageView)findViewById(R.id.categoryThumbnail);

        Intent intent = getIntent();
        String Title = intent.getExtras().getString("Title");
        int Image = intent.getExtras().getInt("Image");

        categoryTitle.setText(Title);
        imageView.setImageResource(Image);
    }
}
