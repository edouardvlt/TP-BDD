package com.example.jpa;

import com.example.domain.Article;
import com.example.domain.IDAOArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class DAOArticleJpa implements IDAOArticle {

    @Autowired
    ArticleJpaRepository articleJpaRepository;

    @Override
    public Article getId(String id) {
        ArticleJpa articleJpa = articleJpaRepository.findById(id).orElse(null);
        if (articleJpa == null) return null;

        Article article = new Article();
        article.id = articleJpa.id;
        article.title = articleJpa.title;
        article.description = articleJpa.description;
        return article;
    }

    @Override
    public List<Article> getAll() {
        List<ArticleJpa> articlesJpa = articleJpaRepository.findAll();
        List<Article> articles = new ArrayList<>();
        for (ArticleJpa articleJpa : articlesJpa) {
            Article article = new Article();
            article.id = articleJpa.id;
            article.title = articleJpa.title;
            article.description = articleJpa.description;
            articles.add(article);
        }
        return articles;
    }

    @Override
    public boolean deleteArticle(String id) {
        ArticleJpa articleJpa = articleJpaRepository.findById(id).orElse(null);
        if (articleJpa == null) return false;
        articleJpaRepository.deleteById(id);
        return true;
    }

    @Override
    public Article saveArticle(Article article) {
        List<ArticleJpa> articlesJpa = articleJpaRepository.findAll();

        if (article.id == null) {
            // Création : vérifier unicité du titre
            for (ArticleJpa existing : articlesJpa) {
                if (Objects.equals(article.title, existing.title)) return null;
            }
            ArticleJpa newArticleJpa = new ArticleJpa();
            newArticleJpa.id = UUID.randomUUID().toString();
            newArticleJpa.title = article.title;
            newArticleJpa.description = article.description;
            articleJpaRepository.save(newArticleJpa);

            Article articleCreated = new Article();
            articleCreated.id = newArticleJpa.id;
            articleCreated.title = newArticleJpa.title;
            articleCreated.description = newArticleJpa.description;
            return articleCreated;

        } else {
            // Mise à jour : vérifier unicité du titre en excluant l'article courant
            for (ArticleJpa existing : articlesJpa) {
                if (Objects.equals(article.title, existing.title)
                        && !Objects.equals(article.id, existing.id)) return null;
            }
            ArticleJpa articleJpa = articleJpaRepository.findById(article.id).orElse(null);
            if (articleJpa == null) return null;

            articleJpa.title = article.title;
            articleJpa.description = article.description;
            articleJpaRepository.save(articleJpa);

            Article articleUpdated = new Article();
            articleUpdated.id = articleJpa.id;
            articleUpdated.title = articleJpa.title;
            articleUpdated.description = articleJpa.description;
            return articleUpdated;
        }
    }
}
