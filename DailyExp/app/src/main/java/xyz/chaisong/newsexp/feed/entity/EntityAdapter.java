package xyz.chaisong.newsexp.feed.entity;

import xyz.chaisong.newsexp.feed.FeedBaseModel;

/**
 * Created by song on 16/10/28.
 */

public class EntityAdapter {
    public static FeedBaseModel convertJMFeedItemEntity(JMFeedItemEntity entity) {
        FeedBaseModel model = new FeedBaseModel();
        model.setDescription(entity.getSummary());
        model.setTitle(entity.getTitle());
        model.setImage(entity.getImg());
        return model;
    }
}
