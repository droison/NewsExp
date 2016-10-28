package xyz.chaisong.newsexp.feed.entity;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 16/10/28.
 */

public class JMFeedItemEntity {

    private String id;
    private String title;
    private String summary;
    private String publishtime;
    @SerializedName(value = "img", alternate = {"o_image"})
    private String img;
    /**
            "id": "926872",
            "title": "地方AMC松绑后江苏率先“生二胎” 多方位围剿不良资产",
             "summary": "不良抬头较早的江苏，不仅利用四大AMC和地方AMC处理不良资产，同时组建债权人委员会，督促银行加大核销力度，以及坚决压缩过剩资产和僵尸企业授信。",
            "publishtime": "1477626190",
            "img": "http://img3.jiemian.com/101/original/20161028/147762585996899900_a280x210.jpg",
     **/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(String publishtime) {
        this.publishtime = publishtime;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static class AnlistTypeAdapter extends com.google.gson.TypeAdapter<List<JMFeedItemEntity>> {
        @Override
        public void write(JsonWriter out, List<JMFeedItemEntity> value) throws IOException {
            Gson gson = new Gson();
            out.beginArray();
            for (JMFeedItemEntity entity: value ) {
                out.value(gson.toJson(value));
            }
            out.endArray();
        }

        @Override
        public List<JMFeedItemEntity> read(JsonReader in) throws IOException {
            List<JMFeedItemEntity> feeds = new ArrayList<>();
            Gson gson = new Gson();
            in.beginArray();
            while (in.hasNext()) {
                in.beginObject();
                while (in.hasNext()) {
                    String name = in.nextName();
                    if (name.equals("ar")) {
                        JMFeedItemEntity model = gson.fromJson(in, JMFeedItemEntity.class);
                        feeds.add(model);
                    } else if (name.equals("al")) {
                        in.beginArray();
                        if (in.hasNext()) {
                            JMFeedItemEntity model = gson.fromJson(in, JMFeedItemEntity.class);
                            feeds.add(model);
                        }
                        in.endArray();
                    }
                }
                in.endObject();
            }
            return feeds;
        }
    }
}
