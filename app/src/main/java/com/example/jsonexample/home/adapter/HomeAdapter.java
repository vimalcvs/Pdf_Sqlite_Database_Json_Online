package com.example.jsonexample.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.cardview.widget.CardView;
import androidx.core.widget.ImageViewCompat;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jsonexample.PdfViewActivity;
import com.example.jsonexample.R;
import com.example.jsonexample.home.model.HomeModel;
import com.google.android.material.card.MaterialCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MovieViewHolder> {

    Context context;
    List<HomeModel> homeModels;

    public HomeAdapter(Context context, List<HomeModel> homeModels) {
        this.context = context;
        this.homeModels = homeModels;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.listview_rl, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        final String title = homeModels.get(position).getTitle();
        final String imageURL = homeModels.get(position).getImageURL();
        final String pdfURL = homeModels.get(position).getPdfURL();
        final int idHome = homeModels.get(position).getIdHome();
        final String time = homeModels.get(position).getTime();

        holder.title.setText(title);
        Picasso.get().load(imageURL).into(holder.image);

        holder.material_card.setOnClickListener(view -> {
            Intent movieDetailsIntent = new Intent(context, PdfViewActivity.class);
            movieDetailsIntent.putExtra("idHome", idHome);
            movieDetailsIntent.putExtra("title", title);
            movieDetailsIntent.putExtra("imageURL", imageURL);
            movieDetailsIntent.putExtra("pdfURL", pdfURL);
            movieDetailsIntent.putExtra("time", time);
            context.startActivity(movieDetailsIntent);
        });
    }

    @Override
    public int getItemCount() {
        return homeModels.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;
        MaterialCardView material_card;

        public MovieViewHolder(@NonNull View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            material_card = itemView.findViewById(R.id.material_card);

        }
    }
}
