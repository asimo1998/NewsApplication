package com.example.newsapplication.Models;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.security.identity.CipherSuiteNotSupportedException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplication.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> articles;
    private Context context;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private OnItemClickListener onItemClickListener;


    public interface OnItemClickListener {
        void OnItemClick(View view, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public NewsAdapter(List<Article> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.itemview_headlines, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Article article = articles.get(position);

        holder.titleTextView.setText(article.getTitle());
        holder.descriptionTextView.setText(article.getDescription());
        try {
            holder.publishedAtTextView.setText(simpleDateFormat.parse(article.getPublishedAt()).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.get().load(article.getUrlToImage()).into(holder.imageView);
        holder.recyclerViewItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItem(article);
            }
        });
    }
    private void onClickItem(Article article){
        Intent intent = new Intent(context, DetailsNews.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", article);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleTextView, descriptionTextView, publishedAtTextView;
        RelativeLayout recyclerViewItem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerViewItem = itemView.findViewById(R.id.container_item);
            imageView = itemView.findViewById(R.id.image_view);
            titleTextView = itemView.findViewById(R.id.title_text_view);
            descriptionTextView = itemView.findViewById(R.id.description_text_view);
            publishedAtTextView = itemView.findViewById(R.id.publishedAt_text_view);
        }
    }
    public void release(){
        context = null;
    }
}
