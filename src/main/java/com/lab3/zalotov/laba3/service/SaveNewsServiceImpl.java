package com.lab3.zalotov.laba3.service;

import com.lab3.zalotov.laba3.model.Article;
import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.util.List;

@Service
public class SaveNewsServiceImpl implements SaveNewsService {

    private final static Logger logger = Logger.getLogger(SaveNewsServiceImpl.class);

    @Async("taskExecutor")
    @Override
    public void POIDocumentCreate(List<Article> articleList, String requestName) {
        try {
            if (articleList.isEmpty()) {//если лист пустой - не создаем ничего
                return;
            } else {
                XWPFDocument doc = new XWPFDocument();// создание документа
                XWPFParagraph paragraph = doc.createParagraph();// добавление параграфа
                FileOutputStream out = new FileOutputStream(requestName.toUpperCase() + "NewsPOI.docx");//задаем имя файла
                for (Article article : articleList) {// перебираем лист задавая все параметры в док файл
                    XWPFRun run = paragraph.createRun();
                    run.addBreak();
                    if (article.getUrlToImage() == null) {
                        run.setText("No image");
                    } else {
                        try {
                            run.addPicture(image(article.getUrlToImage()), XWPFDocument.PICTURE_TYPE_JPEG, "pic", Units.toEMU(250), Units.toEMU(250));//добавляем картинку по ссылке
                            image(article.getUrlToImage()).close();
                        } catch (IOException | InvalidFormatException e) {
                            logger.error("Error in set image " + e.getMessage());
                            run.setText("No image");
                        }
                    }
                    run.addBreak();
                    run.setText(article.getTitle());
                    run.addBreak();
                    if (!article.getAuthor().isEmpty()) {
                        run.setText("Author: " + article.getAuthor());
                        run.addBreak();
                    }
                    run.setText("Source: " + article.getSource());
                    run.addBreak();
                    run.setText("Description: " + article.getDescription());
                    run.addBreak();
                    run.setText("Published at: " + article.getPublishedAt());
                    run.addBreak();
                    run = paragraph.createHyperlinkRun(article.getUrl());
                    run.setText("URL: " + article.getUrl());
                    run.addBreak();
                    run.addBreak();
                    run.addBreak();
                }
                doc.write(out);//запись всего в файл
                doc.close();//закрываем ресурсы
                out.close();
                logger.info("File successfully created: " + requestName.toUpperCase() + "NewsPOI.docx");
            }
        } catch (IOException e) {
            logger.error("Error in POICreate" + e.getMessage());
        }
    }

    @Override
    public InputStream image(String url) throws IOException {//получение потока картинки по юрл
        URL imageUrl = new URL(url);
        return imageUrl.openStream();
    }
}
