package com.example.driesvandievoort.android.Varia;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.driesvandievoort.android.CategoryActivity;
import com.example.driesvandievoort.android.Entities.Category;
import com.example.driesvandievoort.android.R;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.myViewHolder> {


    Context context;
    List<Category> categories;


    public Adapter(Context context, List<Category> categories) {
        this.context = context;
        this.categories = categories;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.content_home,viewGroup,false);
        return new myViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, final int i) {

        myViewHolder.imageView.setImageResource(categories.get(i).getImage());
        myViewHolder.textView.setText(categories.get(i).getName());
        myViewHolder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,CategoryActivity.class);

                // passing data to the book activity
                intent.putExtra("Title",categories.get(i).getName());
                intent.putExtra("Image",categories.get(i).getImage());
                //Extra toevoegen om mlee te sturen naar category activity
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;
        Button button;

        public myViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_image);
            textView = itemView.findViewById(R.id.textNameCategory);
            button = itemView.findViewById(R.id.button_select);
        }
    }
}
