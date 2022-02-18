package com.lab3.zalotov.laba3.model.connection;

import com.lab3.zalotov.laba3.model.Article;
import com.lab3.zalotov.laba3.model.ArticleParser;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

@Component
public class ConnectionImpl implements Connection {

    private final Logger logger = Logger.getLogger(ConnectionImpl.class);

    private ArticleParser articleParser;

    private final UriComponentsBuilder urlSite;

    public ConnectionImpl(@Value("${news.api}") String apiKey) {
        this.urlSite = UriComponentsBuilder.fromHttpUrl("https://newsapi.org/v2/top-headlines").queryParam("apiKey", apiKey);
    }

    @Autowired
    public void setArticleParser(ArticleParser articleParser) {
        this.articleParser = articleParser;
    }

    public HttpURLConnection getConnection(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();//получаем соединение через юрл
            connection.setRequestMethod("GET");//с методом get
            connection.setDoInput(true);
        } catch (IOException e) {
            logger.error("Error in getConnection: " + e.getMessage());
        }
        return connection;
    }

    private String parseStream(InputStream stream) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();//получаем информацию с сайта в виде строки
        } catch (IOException e) {
            logger.error("Error in parseStream: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Article> getNewsByCountry(String country) {
        try {
            String urlString = urlSite.cloneBuilder().queryParam("country", country).build().toString();//задаем строку для получения новостей с сайта
            URL url = new URL(urlString);//вида ЮРЛ
            HttpURLConnection connection = getConnection(url);
            JSONObject jsonObject = new JSONObject(parseStream(connection.getInputStream()));//получаем json с даними после парсинга
            if (jsonObject.has("Error")) {
                return null;
            }
            return articleParser.articleParser(jsonObject);//вытягиваем новости еще одним парсером
        } catch (IOException e) {
            logger.error("Error in getNewsByCountry: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Article> getNewsByCategory(String category) {
        try {
            String urlString = urlSite.cloneBuilder().queryParam("category", category).build().toString();//задаем url с параметрами
            URL url = new URL(urlString);
            HttpURLConnection connection = getConnection(url);//подключение
            JSONObject jsonObject = new JSONObject(parseStream(connection.getInputStream()));//получение json объекта с информацией
            if (jsonObject.has("Error")) {
                return null;
            }
            return articleParser.articleParser(jsonObject);//вытягиваем новости в лист
        } catch (IOException e) {
            logger.error("Error in getNewsByCategory: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Article> getNewsByCountryAndCategory(String country, String category) {
        try {
            String urlString = urlSite.cloneBuilder().queryParam("country", country).queryParam("category", category).build().toString();//задаем url с параметрами
            URL url = new URL(urlString);
            HttpURLConnection connection = getConnection(url);//подключение
            JSONObject jsonObject = new JSONObject(parseStream(connection.getInputStream()));//получение json объекта с информацией
            if (jsonObject.has("Error")) {
                return null;
            }
            return articleParser.articleParser(jsonObject);//вытягиваем новости в лист
        } catch (IOException e) {
            logger.error("Error in getNewsByCountryAndCategory: " + e.getMessage());
        }
        return null;
    }
}
