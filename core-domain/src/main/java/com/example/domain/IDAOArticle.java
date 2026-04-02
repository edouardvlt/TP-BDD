package com.example.domain;

import java.util.List;

public interface IDAOArticle {
    public Article getId(String id);
    public List<Article> getAll();
    public boolean deleteArticle(String id);
    public Article saveArticle(Article article);
}
