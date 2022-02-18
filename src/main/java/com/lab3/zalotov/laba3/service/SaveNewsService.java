package com.lab3.zalotov.laba3.service;

import com.lab3.zalotov.laba3.model.Article;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface SaveNewsService {

    void POIDocumentCreate(List<Article> articleList, String filename);

    InputStream image(String url) throws IOException;
}
