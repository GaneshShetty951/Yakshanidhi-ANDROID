package com.example.ganeshshetty.yakshanidhi.model;

/**
 * Created by Ganesh Shetty on 14-05-2017.
 */

public class Comment {
    private int show_id,user_id;
    private String commentText,commentedAt,name;

    public Comment() {
    }

    public Comment(int show_id, int user_id, String commentText, String commentedAt, String name) {
        this.show_id = show_id;
        this.user_id = user_id;
        this.commentText = commentText;
        this.commentedAt = commentedAt;
        this.name = name;
    }

    public int getShow_id() {
        return show_id;
    }

    public void setShow_id(int show_id) {
        this.show_id = show_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getCommentedAt() {
        return commentedAt;
    }

    public void setCommentedAt(String commentedAt) {
        this.commentedAt = commentedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
