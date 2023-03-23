package com.example.newsapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import android.widget.Toast;

//import com.example.newsapplication.Models.OnFetchDataListener;
//import com.example.newsapplication.Models.RequestManager;

import com.example.newsapplication.Models.ApiClient;
import com.example.newsapplication.Models.Article;
import com.example.newsapplication.Models.Category;
import com.example.newsapplication.Models.CategoryAdapter;
import com.example.newsapplication.Models.DetailsNews;
import com.example.newsapplication.Models.NewsAdapter;
import com.example.newsapplication.Models.NewsResponse;
import com.example.newsapplication.Models.NewsService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.onClickItemCategoryInterface {

    private RecyclerView recyclerView, recyclerViewCategory;
    private NewsAdapter newsAdapter;
    private List<Article> articles = new ArrayList<>();
    private ArrayList<Category> categoryArrayList;
    private CategoryAdapter categoryAdapter;
    private SearchView searchView;
    private Dialog dialog;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait...");
        dialog.show();

        recyclerView = findViewById(R.id.recyclerview_news);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter(new ArrayList<>(), this);
        recyclerView.setAdapter(newsAdapter);

        recyclerViewCategory = findViewById(R.id.category_news);
        recyclerViewCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        categoryArrayList = new ArrayList<>();
        categoryAdapter = new CategoryAdapter(this, categoryArrayList, this::onClickCategory);
        recyclerViewCategory.setAdapter(categoryAdapter);

        searchView = findViewById(R.id.search_news);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                dialog.setTitle("Searching for " + query);
                dialog.show();
                NewsService newsService = ApiClient.getNewsService();
                Call<NewsResponse> call = newsService.getTopHeadlines("", ApiClient.getApiKey(), "", query);

                call.enqueue(new Callback<NewsResponse>() {
                    @Override
                    public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                        if (response.isSuccessful()) {
                            NewsResponse newsResponse = response.body();
                            newsAdapter.setArticles(newsResponse.getArticles());
                            dialog.dismiss();
                        } else {
                            Log.d("API Call", "Error: " + response.errorBody());
                        }
                    }

                    @Override
                    public void onFailure(Call<NewsResponse> call, Throwable t) {
                        Log.d("API Call", "Error: " + t.getMessage());
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return true;
            }
        });

        getCategory();
        callApi("");
        newsAdapter.notifyDataSetChanged();
//        authenticateUser();
    }

    private void callApi(String category) {
        NewsService newsService = ApiClient.getNewsService();
        Call<NewsResponse> call = newsService.getTopHeadlines("us", ApiClient.getApiKey(), category, "");

        call.enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                if (response.isSuccessful()) {
                    NewsResponse newsResponse = response.body();
                    newsAdapter.setArticles(newsResponse.getArticles());
                    dialog.dismiss();
                } else {
                    Log.d("API Call", "Error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                Log.d("API Call", "Error: " + t.getMessage());
            }
        });
    }

//    @RequiresApi(api = Build.VERSION_CODES.P)
//    private void authenticateUser() {
//        BiometricPrompt biometricPrompt = new BiometricPrompt.Builder(this)
//                .setTitle("Xác thực bằng vân tay")
//                .setSubtitle("Sử dụng vân tay để đăng nhập ứng dụng")
//                .setDescription("Chạm ngón tay lên cảm biến vân tay để xác thực")
//                .setNegativeButton("Thoát", Executors.newSingleThreadExecutor(), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                }).build();
//
//        biometricPrompt.authenticate(new CancellationSignal(), Executors.newSingleThreadExecutor(),
//                new BiometricPrompt.AuthenticationCallback() {
//                    @Override
//                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
//                        super.onAuthenticationSucceeded(result);
//
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(MainActivity.this, "Xác thực vân tay thành công", Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//                        // Thực hiện các hành động đăng nhập vào ứng dụng ở đây
//                    }

//                    @Override
//                    public void onAuthenticationError(int errorCode, CharSequence errString) {
//                        super.onAuthenticationError(errorCode, errString);
//
//                        Toast.makeText(MainActivity.this, "Xác thực bằng vân tay thất bại: " + errString, Toast.LENGTH_LONG).show();
//                    }
//                });
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (newsAdapter != null && categoryAdapter != null) {
            newsAdapter.release();
            categoryAdapter.releaseContext();
        }
    }

    @Override
    public void onClickCategory(int position) {
        String category = categoryArrayList.get(position).getTileCategory();
        dialog.setTitle("Loading " + category);
        dialog.show();
        callApi(category);
    }

    private void getCategory() {
        categoryArrayList.add(new Category("", "https://i.imgur.com/rT4UZCZ.png"));
        categoryArrayList.add(new Category("Business", "https://i.imgur.com/1vnNu4O.jpg"));
        categoryArrayList.add(new Category("Entertainment", "https://i.imgur.com/nS5PNfb.jpg"));
        categoryArrayList.add(new Category("General", "https://i.imgur.com/2RH7C0f.jpg"));
        categoryArrayList.add(new Category("Health", "https://i.imgur.com/bw5VqxV.jpg"));
        categoryArrayList.add(new Category("Science", "https://i.imgur.com/K03TQOt.jpg"));
        categoryArrayList.add(new Category("Technology", "https://i.imgur.com/8DGMlgZ.jpg"));
        categoryArrayList.add(new Category("Sports", "https://i.imgur.com/OpaUke7.jpg"));

        categoryAdapter.notifyDataSetChanged();
    }
}