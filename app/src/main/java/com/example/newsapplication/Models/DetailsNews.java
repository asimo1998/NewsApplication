package com.example.newsapplication.Models;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.security.identity.CipherSuiteNotSupportedException;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import com.example.newsapplication.DetailsArticle;
import com.example.newsapplication.R;
import com.squareup.picasso.Picasso;


import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DetailsNews extends AppCompatActivity {
    private TextView tvTitle, tvAuthorName, tvPublishedAt, tvDescription, tvNameDetails, tvContent;
    private ImageView imageViewDetails;
    private Button buttonDetails;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        Article article = (Article) bundle.get("data");

        //tvNameDetails = findViewById(R.id.name_details);
        tvTitle = findViewById(R.id.title_details);
        tvAuthorName = findViewById(R.id.author_details);
        tvPublishedAt = findViewById(R.id.publishedAt_details);
        tvDescription = findViewById(R.id.description_details);
        imageViewDetails = findViewById(R.id.image_details);
        tvContent = findViewById(R.id.content_details);
        buttonDetails = findViewById(R.id.button_details);

        tvTitle.setText(article.getTitle());
        tvAuthorName.setText("Author: " + article.getAuthor());
        tvPublishedAt.setText(article.getPublishedAt());
        Picasso.get().load(article.getUrlToImage()).into(imageViewDetails);
        try {
            tvPublishedAt.setText("Time: " + simpleDateFormat.parse(article.getPublishedAt()).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        tvDescription.setText(article.getDescription());
        tvContent.setText(article.getContent());
        buttonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = article.getUrl();
                Intent intent = new Intent(DetailsNews.this, DetailsArticle.class);
                intent.putExtra("urlData", url);
                startActivity(intent);
            }
        });
    }

}