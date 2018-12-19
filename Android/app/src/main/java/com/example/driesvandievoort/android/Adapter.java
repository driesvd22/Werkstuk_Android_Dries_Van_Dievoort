package com.example.driesvandievoort.android;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.driesvandievoort.android.Entities.Category;

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
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {

        myViewHolder.imageView.setImageResource(categories.get(i).getImage());
        myViewHolder.textView.setText(categories.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public myViewHolder(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.card_image);
            textView = itemView.findViewById(R.id.textNameCategory);
        }
    }
}
