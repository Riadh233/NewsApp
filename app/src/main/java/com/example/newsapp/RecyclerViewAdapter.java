package com.example.newsapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private Context context;
    public ArrayList<Article> articles;

    public RecyclerViewAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View itemView) {
            super(itemView);
        }

        TextView articleTitle = itemView.findViewById(R.id.tv_news_title);
        TextView articleWriter = itemView.findViewById(R.id.tv_writer_name);
        TextView articleDate = itemView.findViewById(R.id.tv_date);
        ImageView articleImage = itemView.findViewById(R.id.img_news);
        ImageView imageWriter = itemView.findViewById(R.id.img_writer);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.articleTitle.setText(article.getArticleTitle());
        holder.articleWriter.setText(article.getArticleWriter());
        Glide.with(context)
                .load(article.getArticleImage())
                .into(holder.articleImage);
        Glide.with(context)
                .load(article.getImageWriter())
                .into(holder.imageWriter);

        if (article.getTimeInMillis().contains("T")) {
            String[] location = article.getTimeInMillis().split("T");
            holder.articleDate.setText(location[0]);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
}

