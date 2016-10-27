package xyz.chaisong.newsexp.model;

/**
 * Created by song on 16/10/27.
 */

public class AppEnum {

    public enum ActivityAnim {
        SlideFromRight,
        SlideFromBottom,
        Fade
    }

    public enum ADConfigTitleReveal {
        //title_reveal字段1不显示标题2显示
        DISPLAYABLE(2),

        NON_DISPLAYABLE(1);

        public int value;

        ADConfigTitleReveal(int value) {
            this.value = value;
        }
    }
}
