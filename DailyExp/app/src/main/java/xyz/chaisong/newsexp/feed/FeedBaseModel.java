package xyz.chaisong.newsexp.feed;

import xyz.chaisong.newsexp.network.API;

/**
 * Created by song on 16/10/28.
 */

public class FeedBaseModel {
    private API.genre apiGenre;
    private String url; //以上两个负责点击跳转
    private String title;
    private String image;
    private String description;

    public API.genre getApiGenre() {
        return apiGenre;
    }

    public void setApiGenre(API.genre apiGenre) {
        this.apiGenre = apiGenre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
