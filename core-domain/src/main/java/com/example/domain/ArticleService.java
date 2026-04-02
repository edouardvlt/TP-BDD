package com.example.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArticleService {

    @Autowired
    IDAOArticle daoArticle;

    public Article showArticle(String id) {
        return daoArticle.getId(id);
    }

    public List<Article> showAllArticles() {
        return daoArticle.getAll();
    }

    public boolean showBoolean(String id) {
        return daoArticle.deleteArticle(id);
    }

    public Article showArticleUpdated(Article article) {
        return daoArticle.saveArticle(article);
    }
}
