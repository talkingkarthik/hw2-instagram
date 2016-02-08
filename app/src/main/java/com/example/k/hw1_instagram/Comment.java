package com.example.k.hw1_instagram;

/**
 * Created by Komal on 2/7/2016.
 */
public class Comment {

    private String picUrl;
    private String comment;

    public Comment (String picUrl, String comment) {
        this.picUrl = picUrl;
        this.comment = comment;
    }


    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
