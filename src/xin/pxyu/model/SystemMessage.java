package xin.pxyu.model;
/*
系统通知信息
 */
public class SystemMessage {
    private String sm_id;
    private String receive_id;//接收用户id
    private Integer toAll;//0 发送所有用户 1 不采用
    private String topic;//系统信息标题
    private String content;//系统发信息内容
    private String create_time;//系统消息发出时间

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public Integer getToAll() {
        return toAll;
    }

    public void setToAll(Integer toAll) {
        this.toAll = toAll;
    }

    public String getSm_id() {
        return sm_id;
    }

    public void setSm_id(String sm_id) {
        this.sm_id = sm_id;
    }

    public String getReceive_id() {
        return receive_id;
    }

    public void setReceive_id(String receive_id) {
        this.receive_id = receive_id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
