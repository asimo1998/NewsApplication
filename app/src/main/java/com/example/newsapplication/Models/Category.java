package com.example.newsapplication.Models;

public class Category {
    String tileCategory;
    String urlImageCategory;

    public Category(String tileCategory, String urlImageCategory) {
        this.tileCategory = tileCategory;
        this.urlImageCategory = urlImageCategory;
    }

    public String getTileCategory() {
        return tileCategory;
    }

    public void setTileCategory(String tileCategory) {
        this.tileCategory = tileCategory;
    }

    public String getUrlImageCategory() {
        return urlImageCategory;
    }

    public void setUrlImageCategory(String urlImageCategory) {
        this.urlImageCategory = urlImageCategory;
    }
}
