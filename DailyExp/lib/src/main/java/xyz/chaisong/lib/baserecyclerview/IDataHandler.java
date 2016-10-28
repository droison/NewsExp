package xyz.chaisong.lib.baserecyclerview;

import java.util.List;

/**
 * Created by song on 16/8/16.
 */
public interface IDataHandler<T extends Model.ItemData> {
    void add(T model);
    void add(T model, int index);
    void add(List<T> models);
    void add(List<T> models, int index);

    void remove(T model);
    void remove(int index, int count);
    void remove(int index);

    void setDatas(List<T> models);
    void clear();
}
