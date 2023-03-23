package com.example.newsapplication.Models;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.zip.Inflater;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private Context context;
    private ArrayList<Category> categoryArrayList;
    private onClickItemCategoryInterface onClickItemCategoryInterface;

    public CategoryAdapter(Context context, ArrayList<Category> categoryArrayList,
                           onClickItemCategoryInterface onClickItemCategoryInterface) {
        this.context = context;
        this.categoryArrayList = categoryArrayList;
        this.onClickItemCategoryInterface = onClickItemCategoryInterface;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_category_news, parent, false);
        return new CategoryAdapter.CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.CategoryViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Category category = categoryArrayList.get(position);

        holder.tvTitleCategory.setText(category.getTileCategory());
        Picasso.get().load(category.getUrlImageCategory()).into(holder.imageCategory);
        holder.itemCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickItemCategoryInterface.onClickCategory(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView imageCategory;
        TextView tvTitleCategory;
        RelativeLayout itemCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imageCategory = itemView.findViewById(R.id.img_category);
            tvTitleCategory = itemView.findViewById(R.id.title_category);
            itemCategory = itemView.findViewById(R.id.item_category);
        }
    }

    public interface onClickItemCategoryInterface {
        void onClickCategory(int position);
    }

    public void releaseContext(){
        context = null;
    }
}
