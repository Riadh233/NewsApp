package com.example.newsapp;

import java.util.Date;

public class Article {
    private String articleTitle;
    private String articleWriter;
    private String timeInMillis;
    private String articleImage;
    private String url;
    private String section;
    private String imageWriter;

    public Article(String section, String articleTitle, String articleWriter, String timeInMillis, String articleImage, String imageWriter, String url) {
        this.section = section;
        this.articleTitle = articleTitle;
        this.articleWriter = articleWriter;
        this.timeInMillis = timeInMillis;
        this.articleImage = articleImage;
        this.url = url;
        this.imageWriter = imageWriter;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public String getArticleWriter() {
        return articleWriter;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public String getUrl() {
        return url;
    }

    public String getSection() {
        return section;
    }

    public String getTimeInMillis() {
        return timeInMillis;
    }

    public String getImageWriter() {
        return imageWriter;
    }
}
