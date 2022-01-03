package com.photos.servicemention.repo.model;

import javax.persistence.*;

@Entity
@Table(name="mentions")
public class Mention {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private long user_id;
    private long publication_id;
    private String content;
    private MentionStatus status;

    public Mention() {
    }

    public Mention(long user_id, long publication_id, String content, MentionStatus status) {
        this.user_id = user_id;
        this.publication_id = publication_id;
        this.content = content;
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public long getUser_id() {
        return user_id;
    }

    public long getPublication_id() {
        return publication_id;
    }

    public String getContent() {
        return content;
    }

    public MentionStatus getStatus() {
        return status;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public void setPublication_id(long publication_id) {
        this.publication_id = publication_id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setStatus(MentionStatus status) {
        this.status = status;
    }
}
