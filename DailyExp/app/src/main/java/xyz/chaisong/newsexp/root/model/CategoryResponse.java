package xyz.chaisong.newsexp.root.model;

import java.util.List;

import xyz.chaisong.lib.network.response.RespBaseMeta;

/**
 * Created by song on 16/11/9.
 */

public class CategoryResponse extends RespBaseMeta {
    public String code;
    public String message;
    public List<List<CategoryMeta>> result;
}
