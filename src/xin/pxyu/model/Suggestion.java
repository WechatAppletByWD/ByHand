package xin.pxyu.model;

public class Suggestion {
    private String s_id;
    private String suggest_id;//建议人id
    private String topic;//建议内容
    private String create_time;//建议创建时间

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public String getSuggest_id() {
        return suggest_id;
    }

    public void setSuggest_id(String suggest_id) {
        this.suggest_id = suggest_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }
}
