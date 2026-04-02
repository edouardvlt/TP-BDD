package com.example.mongo;

import com.example.domain.Article;
import com.example.domain.IDAOArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
public class DAOArticleMongo implements IDAOArticle {

    @Autowired
    ArticleMongoRepository articleMongoRepository;

    @Override
    public Article getId(String id) {
        ArticleMongo articleMongo = articleMongoRepository.findById(id).orElse(null);
        if (articleMongo == null) return null;

        Article article = new Article();
        article.id = articleMongo.id;
        article.title = articleMongo.title;
        article.description = articleMongo.description;
        return article;
    }

    @Override
    public List<Article> getAll() {
        List<ArticleMongo> articlesMongo = articleMongoRepository.findAll();
        List<Article> articles = new ArrayList<>();
        for (ArticleMongo articleMongo : articlesMongo) {
            Article article = new Article();
            article.id = articleMongo.id;
            article.title = articleMongo.title;
            article.description = articleMongo.description;
            articles.add(article);
        }
        return articles;
    }

    @Override
    public boolean deleteArticle(String id) {
        ArticleMongo articleMongo = articleMongoRepository.findById(id).orElse(null);
        if (articleMongo == null) return false;
        articleMongoRepository.deleteById(id);
        return true;
    }

    @Override
    public Article saveArticle(Article article) {
        List<ArticleMongo> articlesMongo = articleMongoRepository.findAll();

        if (article.id == null) {
            // Création : vérifier unicité du titre
            for (ArticleMongo existing : articlesMongo) {
                if (Objects.equals(article.title, existing.title)) return null;
            }
            ArticleMongo newArticleMongo = new ArticleMongo();
            newArticleMongo.id = UUID.randomUUID().toString();
            newArticleMongo.title = article.title;
            newArticleMongo.description = article.description;
            articleMongoRepository.save(newArticleMongo);

            Article articleCreated = new Article();
            articleCreated.id = newArticleMongo.id;
            articleCreated.title = newArticleMongo.title;
            articleCreated.description = newArticleMongo.description;
            return articleCreated;

        } else {
            // Mise à jour : vérifier unicité du titre en excluant l'article courant
            for (ArticleMongo existing : articlesMongo) {
                if (Objects.equals(article.title, existing.title)
                        && !Objects.equals(article.id, existing.id)) return null;
            }
            ArticleMongo articleMongo = articleMongoRepository.findById(article.id).orElse(null);
            if (articleMongo == null) return null;

            articleMongo.title = article.title;
            articleMongo.description = article.description;
            articleMongoRepository.save(articleMongo);

            Article articleUpdated = new Article();
            articleUpdated.id = articleMongo.id;
            articleUpdated.title = articleMongo.title;
            articleUpdated.description = articleMongo.description;
            return articleUpdated;
        }
    }
}
