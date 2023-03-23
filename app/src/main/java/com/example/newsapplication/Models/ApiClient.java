package com.example.newsapplication.Models;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://newsapi.org/v2/";
    private static final String API_KEY = "f87051c4c4df485ab61f3a34fe9708f3";

    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static NewsService getNewsService() {
        return getClient().create(NewsService.class);
    }

    public static String getApiKey() {
        return API_KEY;
    }
}
