package xyz.chaisong.newsexp.network;

/**
 * Created by song on 16/10/27.
 */

public class API {
    public enum genre {

    }

    public static final String QDailyCategoryList = "http://app3.qdaily.com/app3/homes/left_sidebar.json";// 好奇心日报分类列表
    public static final String QDailyHome = "http://app3.qdaily.com/app3/homes/index/%s.json";   //好奇心日报首页
    public static final String QDailyCategory = "http://app3.qdaily.com/app3/categories/index/%s/%s.json";   //好奇心日报分类

    public static final String JiemianCategoryList = "https://appapi.jiemian.com/cate/all.json?version=3.5.0"; //界面分类列表
    public static final String JiemianHome = "https://appapi.jiemian.com/cate/main.json?page=%s&appid=AGcCMAhmB2YBOA==&version=3.5.0"; //界面首页
    public static final String JiemianCategory = "http://appapi.jiemian.com/article/cate/%s.json?page=%s"; //界面分类
    public static final String JiemianArticle = "https://appapi.jiemian.com/article/%s.json"; //界面详情    这里把详情和题图已经分开了


}
