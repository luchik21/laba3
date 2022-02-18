package com.lab3.zalotov.laba3.service;

import com.lab3.zalotov.laba3.model.Article;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface GetNewsService {

    CompletableFuture<List<Article>> findByCategory(String category);

    CompletableFuture<List<Article>> findByCountry(String country);

    CompletableFuture<List<Article>> findByCountryAndCategory(String country, String category);
}
