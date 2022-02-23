package com.lab3.zalotov.laba3.service;

import com.lab3.zalotov.laba3.model.Article;
import com.lab3.zalotov.laba3.model.connection.ConnectionImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;

@Service
public class GetNewsServiceImpl implements GetNewsService {

    private final Logger logger = Logger.getLogger(GetNewsServiceImpl.class);

    private ConnectionImpl connection;

    @Autowired
    public void setConnection(ConnectionImpl connection) {
        this.connection = connection;
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<Article>> findByCategory(String category) {
        logger.info("Call find by: " + category);
        return CompletableFuture.completedFuture(connection.getNewsByCategory(category));
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<Article>> findByCountry(String country) {
        logger.info("Call find by: " + country);
        return CompletableFuture.completedFuture(connection.getNewsByCountry(country));
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<List<Article>> findByCountryAndCategory(String country, String category) {
        logger.info("Call find by: " + country + "/" + category);
        return CompletableFuture.completedFuture(connection.getNewsByCountryAndCategory(country, category));
    }

    @Override
    public CompletableFuture<String> findByCategoryRest(String category) throws IOException {
        logger.info("Call find by: " + category);
        return CompletableFuture.completedFuture(connection.getNewsByCategoryRest(category));
    }

    @Override
    @Async("taskExecutor")
    public CompletableFuture<String> findByCountryRest(String country) throws IOException {
        logger.info("Call find by: " + country);
        return CompletableFuture.completedFuture(connection.getNewsByCountryRest(country));
    }

    @Override
    public CompletableFuture<String> findByCountryAndCategoryRest(String country, String category) throws IOException {
        logger.info("Call find by: " + country + "/" + category);
        return CompletableFuture.completedFuture(connection.getNewsByCountryAndCategoryRest(country, category));
    }
}

