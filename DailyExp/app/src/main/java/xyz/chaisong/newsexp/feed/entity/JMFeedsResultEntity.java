package xyz.chaisong.newsexp.feed.entity;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by song on 16/10/28.
 */

public class JMFeedsResultEntity {
    private String pageCount;
    private String page;
    @SerializedName(value = "carousel", alternate = {"carousel"})
    private List<JMFeedItemEntity> banners;
    @JsonAdapter(value = JMFeedItemEntity.AnlistTypeAdapter.class)
    private List<JMFeedItemEntity> feeds;

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<JMFeedItemEntity> getBanners() {
        return banners;
    }

    public void setBanners(List<JMFeedItemEntity> banners) {
        this.banners = banners;
    }

    public List<JMFeedItemEntity> getFeeds() {
        return feeds;
    }

    public void setFeeds(List<JMFeedItemEntity> feeds) {
        this.feeds = feeds;
    }
}
