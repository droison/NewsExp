package xyz.chaisong.newsexp.feed;

import android.text.TextUtils;

/**
 * Created by song on 16/10/28.
 */

public class FeedApi {
    public static Builder builder() {
        return new Builder();
    }

    private String baseUrl;

    public FeedApi(Builder builder) {
        this.baseUrl = builder.getUrl();
    }

    public String getUrl(int page) {
        return String.format(baseUrl, page);
    }

    public static final class Builder {

        private Builder() {}

        private String baseUrl;
        private String feedId;

        public FeedApi build() {
            if (baseUrl == null) {
                throw new IllegalStateException("baseUrl must be set");
            }
            return new FeedApi(this);
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setFeedId(String feedId) {
            this.feedId = feedId;
            return this;
        }

        public String getUrl(){
            if (TextUtils.isEmpty(this.feedId)) {
                return this.baseUrl;
            }
            return String.format(this.baseUrl, this.feedId);
        }
    }
}
