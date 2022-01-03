package com.photos.servicepublication.repo.model;

import javax.persistence.*;

@Entity
@Table(name="publications")
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    private String imagepath;
    private String description;
    private long author_id;

    public Publication(String name, String imagepath, String description, long author_id) {
        this.name = name;
        this.imagepath = imagepath;
        this.description = description;
        this.author_id = author_id;
    }

    public Publication() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getDescription() {
        return description;
    }

    public long getAuthor_id() {
        return author_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAuthor_id(long author_id) {
        this.author_id = author_id;
    }
}
