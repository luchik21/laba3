package com.lab3.zalotov.laba3.controller;

import com.lab3.zalotov.laba3.model.Article;
import com.lab3.zalotov.laba3.service.GetNewsService;
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
@RequestMapping(path = "/main")
public class MainController {

    private final static Logger logger = Logger.getLogger(MainController.class);

    private GetNewsService getNewsService;

    private String categoryHelp;

    @Autowired
    public void setCategoryHelp(@Value("${category.list}") String categoryHelp) {
        this.categoryHelp = categoryHelp;
    }

    @Autowired
    public void setGetNewsService(GetNewsService getNewsService) {
        this.getNewsService = getNewsService;
    }

    @RequestMapping(path = "/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok("OK");
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
        return ResponseEntity.ok("Cant find news with parameter category: " + category +"; Look news with category like this:"+categoryHelp);
    }
}
