package com.example.newsapplication.Models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsService {
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey,
            @Query("category") String category,
            @Query("q") String q
    );

    @GET("everything")
    Call<NewsResponse> getEverything(
            @Query("q") String q,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey
    );
}
