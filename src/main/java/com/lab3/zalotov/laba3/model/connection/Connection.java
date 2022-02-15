package com.lab3.zalotov.laba3.model.connection;

import com.lab3.zalotov.laba3.model.Article;

import java.util.List;

public interface Connection {
    List<Article> getNewsByCountry(String country);

    List<Article> getNewsByCategory(String category);
}
