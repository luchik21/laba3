package com.lab3.zalotov.laba3.model.connection;

import com.lab3.zalotov.laba3.model.Article;

import java.io.IOException;
import java.util.List;

public interface Connection {
    List<Article> getNewsByCountry(String country);

    List<Article> getNewsByCountryAndCategory(String country, String category);

    List<Article> getNewsByCategory(String category);

    String getNewsByCountryRest(String country) throws IOException;

    String getNewsByCategoryRest(String country) throws IOException;

    String getNewsByCountryAndCategoryRest(String country, String category) throws IOException;
}
