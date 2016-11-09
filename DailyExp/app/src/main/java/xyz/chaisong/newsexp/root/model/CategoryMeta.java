package xyz.chaisong.newsexp.root.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by song on 16/11/9.
 */

public class CategoryMeta {
    public String id;
    public String name;
    @SerializedName(value = "app_en_name")
    public String nameEN;
    public String url;
}
