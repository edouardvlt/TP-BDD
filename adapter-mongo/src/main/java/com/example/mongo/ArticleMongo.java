package com.example.mongo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "articles")
public class ArticleMongo {

    @Id
    public String id;
    public String title;
    public String description;
}
