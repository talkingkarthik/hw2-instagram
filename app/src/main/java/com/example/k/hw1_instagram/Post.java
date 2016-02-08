package com.example.k.hw1_instagram;

import java.util.ArrayList;

/**
 * Created by Komal on 2/5/2016.
 */
public class Post {
    private String imgUrl;

    private Integer likes;
    private String lastLiked;

    private String user;
    private String userImgUrl;

    private ArrayList<Comment> comments = null;

    public Post(String imgUrl, String user, String userImgUrl, Integer likes, String lastLiked, ArrayList<Comment> comments) {
        this.imgUrl = imgUrl;
        this.user = user;
        this.userImgUrl = userImgUrl;
        this.likes = likes;
        this.lastLiked = lastLiked;
        this.comments = comments;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUserImgUrl() {
        return userImgUrl;
    }

    public void setUserImgUrl(String userImgUrl) {
        this.userImgUrl = userImgUrl;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public String getLastLiked() {
        return lastLiked;
    }

    public void setLastLiked(String lastLiked) {
        this.lastLiked = lastLiked;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
