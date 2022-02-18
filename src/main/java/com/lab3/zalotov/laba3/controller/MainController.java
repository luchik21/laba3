package com.lab3.zalotov.laba3.controller;

import com.lab3.zalotov.laba3.model.Article;
import com.lab3.zalotov.laba3.service.GetNewsService;
import com.lab3.zalotov.laba3.service.SaveNewsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class MainController {

    private final static Logger logger = Logger.getLogger(MainController.class);

    private GetNewsService getNewsService;

    private SaveNewsService saveNewsService;

    private String categoryHelp;

    @Autowired
    public void setCategoryHelp(@Value("${category.list}") String categoryHelp) {
        this.categoryHelp = categoryHelp;
    }

    @Autowired
    public void setGetNewsService(GetNewsService getNewsService) {
        this.getNewsService = getNewsService;
    }

    @Autowired
    public void setSaveNewsService(SaveNewsService saveNewsService) {
        this.saveNewsService = saveNewsService;
    }

    @RequestMapping(path = "/")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Provided options:" +
                "/findByCountry/{country} and /findByCountry/{country}/save" +
                "/findByCategory/{category} and /findByCategory/{category}/save" +
                "/findByCountryAndCategory/{country}/{category} and /findByCountryAndCategory/{country}/{category}/save");
    }

    @RequestMapping("/findByCountry/{country}")
    public ResponseEntity<?> findNewsByCountry(@PathVariable String country) throws ExecutionException, InterruptedException {
        logger.info("calling find by country:" + country);
        CompletableFuture<List<Article>> article = getNewsService.findByCountry(country);
        if (!article.get().isEmpty())
            return ResponseEntity.ok(article.get());
        return ResponseEntity.ok("Cant find news with parameter country: " + country);
    }

    @RequestMapping("/findByCategory/{category}")
    public ResponseEntity<?> findNewsByCategory(@PathVariable String category) throws ExecutionException, InterruptedException {
        logger.info("calling find by country:" + category);
        CompletableFuture<List<Article>> article = getNewsService.findByCategory(category);
        if (!article.get().isEmpty())
            return ResponseEntity.ok(article.get());
        return ResponseEntity.ok("Cant find news with parameter category: " + category + " Look news with category:" + categoryHelp);
    }

    @RequestMapping("/findByCountry/{country}/save")
    public ResponseEntity<?> saveNewsByCountry(@PathVariable String country) throws ExecutionException, InterruptedException {
        logger.info("calling save by country:" + country);
        CompletableFuture<List<Article>> article = getNewsService.findByCountry(country);
        saveNewsService.POIDocumentCreate(article.get(), country);
        if (!article.get().isEmpty())
            return ResponseEntity.ok("Successfully create document for request " + country.toUpperCase());
        return ResponseEntity.ok("Can't find news with parameter request: " + country.toUpperCase());
    }

    @RequestMapping("/findByCategory/{category}/save")
    public ResponseEntity<?> saveNewsByCategory(@PathVariable String category) throws ExecutionException, InterruptedException {
        logger.info("calling save by category:" + category);
        CompletableFuture<List<Article>> article = getNewsService.findByCategory(category);
        saveNewsService.POIDocumentCreate(article.get(), category);
        if (!article.get().isEmpty())
            return ResponseEntity.ok("Successfully create document for request " + category.toUpperCase());
        return ResponseEntity.ok("Can't find news with parameter request: " + category.toUpperCase() + " Look news with category:" + categoryHelp);
    }

    @RequestMapping("/findByCountryAndCategory/{country}/{category}")
    public ResponseEntity<?> findNewsByCountryAndCategory(@PathVariable String country, @PathVariable String category) throws ExecutionException, InterruptedException {
        logger.info("calling find by country and category:" + country + "/" + category);
        CompletableFuture<List<Article>> article = getNewsService.findByCountryAndCategory(country, category);
        if (!article.get().isEmpty())
            return ResponseEntity.ok(article.get());
        return ResponseEntity.ok("Can't find news with parameters: " + country.toUpperCase() + "/" + category.toUpperCase() + " Look news with category:" + categoryHelp);
    }

    @RequestMapping("/findByCountryAndCategory/{country}/{category}/save")
    public ResponseEntity<?> saveNewsByCountryAndCategory(@PathVariable String country, @PathVariable String category) throws ExecutionException, InterruptedException {
        logger.info("calling save by country and category:" + country + "/" + category);
        CompletableFuture<List<Article>> article = getNewsService.findByCountryAndCategory(country, category);
        saveNewsService.POIDocumentCreate(article.get(), country + category);
        if (!article.get().isEmpty())
            return ResponseEntity.ok("Successfully create document for request " + country.toUpperCase() + "/" + category.toUpperCase());
        return ResponseEntity.ok("Can't find news with parameters: " + country.toUpperCase() + "/" + category.toUpperCase() + " Look news with category:" + categoryHelp);
    }
}
