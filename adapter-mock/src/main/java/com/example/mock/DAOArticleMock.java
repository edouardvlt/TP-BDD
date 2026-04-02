package com.example.mock;

import com.example.domain.IDAOArticle;
import com.example.domain.Article;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DAOArticleMock implements IDAOArticle {

    private final List<Article> articles = new ArrayList<>();

    public DAOArticleMock() {
        Article a1 = new Article();
        a1.id = "1";
        a1.title = "Premier Article";
        a1.description = "Ceci est le contenu du premier article mocké.";

        Article a2 = new Article();
        a2.id = "2";
        a2.title = "Second Article";
        a2.description = "Un autre article pour tester l'affichage de la liste.";

        articles.add(a1);
        articles.add(a2);
    }

    @Override
    public Article getId(String id) {
        return articles.stream()
                .filter(a -> a.id.equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Article> getAll() {
        return new ArrayList<>(articles);
    }

    @Override
    public boolean deleteArticle(String id) {
        return articles.removeIf(a -> a.id.equals(id));
    }

    @Override
    public Article saveArticle(Article article) {
        if (article.id == null) {
            boolean titleExists = articles.stream()
                    .anyMatch(a -> a.title.equals(article.title));
            if (titleExists) return null;

            article.id = UUID.randomUUID().toString();
            articles.add(article);
            return article;
        } else {
            boolean titleExists = articles.stream()
                    .anyMatch(a -> a.title.equals(article.title) && !a.id.equals(article.id));
            if (titleExists) return null;

            Article existing = getId(article.id);
            if (existing == null) return null;

            existing.title = article.title;
            existing.description = article.description;
            return existing;
        }
    }
}
