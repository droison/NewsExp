package xyz.chaisong.newsexp.root.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import xyz.chaisong.lib.network.ICallBack;
import xyz.chaisong.lib.network.NetUtil;
import xyz.chaisong.lib.network.request.ReqEntity;
import xyz.chaisong.lib.network.response.RespEntity;
import xyz.chaisong.lib.network.response.RespError;

/**
 * Created by song on 16/11/9.
 */

public class CategoryLoader {
    private static final String URL = "https://appapi.jiemian.com/cate/all.json?version=3.5.0";

    public void load(final CallBack callBack){
        NetUtil.getInstance().get(new ReqEntity<CategoryResponse>() {
            @Override
            public Map<String, Object> getParams() {
                return null;
            }

            @Override
            public String getUrl() {
                return URL;
            }
        }.setResponseClass(CategoryResponse.class), new ICallBack<CategoryResponse>() {
            @Override
            public void onSuccess(ReqEntity<CategoryResponse> netParams, RespEntity<CategoryResponse> data) {
                callBack.onGetSuccess(convert(data.getResponseMeta()));
            }

            @Override
            public void onFail(ReqEntity<CategoryResponse> netParams, RespError failData) {
                callBack.onGetFail(failData);
            }
        });
    }

    private List<CategoryEntity> convert(CategoryResponse response) {
        List<CategoryEntity> result = new ArrayList<>();
        for (List<CategoryMeta> listMetas: response.result) {
            for (CategoryMeta meta: listMetas) {
                CategoryEntity entity = new CategoryEntity();
                entity.id = meta.id;
                entity.name = meta.name;
                entity.url = meta.url + "?version=3.5.0";
                result.add(entity);
            }
        }
        return result;
    }

    public interface CallBack {
        void onGetSuccess(@NonNull List<CategoryEntity> response);

        void onGetFail(RespError failData);
    }
}
