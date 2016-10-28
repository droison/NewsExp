package xyz.chaisong.newsexp.feed.entity;

/**
 * Created by song on 16/10/28.
 */

public class JMFeedsEntity {
    private String code;
    private String message;
    private JMFeedsResultEntity result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JMFeedsResultEntity getResult() {
        return result;
    }

    public void setResult(JMFeedsResultEntity result) {
        this.result = result;
    }
}
