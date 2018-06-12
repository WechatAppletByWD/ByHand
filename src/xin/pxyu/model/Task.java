package xin.pxyu.model;
public class Task {
    private String tlocation_id;
    private String title;
    private String content;
    private Integer status;//0创建或者取消接收 1接收 2撤销 3完成
    private String tcreate_time;//任务创建时间
    private String tend_time;//任务截止时间
    private String tcancel_time;//任务撤销时间
    private String tcreate_id;
    private String tincept_id;
    private Integer type;
    private String tavatarUrl;
    private String avatarUrl;
    private double reward;
    private String tincept_time;//任务接收时间
    private String tachieve_time;//任务完成时间
    private String timeLimit;//任务限时。从接单开始倒计时
    private int msg;
    private String taddress;
    private double longitude;//经度
    private double latitude;//纬度
    private double distance;//距离
    private long create_time;//创建时间的时间戳
    public Task(){}

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getTlocation_id() {
        return tlocation_id;
    }

    public void setTlocation_id(String tlocation_id) {
        this.tlocation_id = tlocation_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTcreate_time() {
        return tcreate_time;
    }

    public void setTcreate_time(String tcreate_time) {
        this.tcreate_time = tcreate_time;
    }

    public String getTend_time() {
        return tend_time;
    }

    public void setTend_time(String tend_time) {
        this.tend_time = tend_time;
    }

    public String getTcancel_time() {
        return tcancel_time;
    }

    public void setTcancel_time(String tcancel_time) {
        this.tcancel_time = tcancel_time;
    }

    public String getTcreate_id() {
        return tcreate_id;
    }

    public void setTcreate_id(String tcreate_id) {
        this.tcreate_id = tcreate_id;
    }

    public String getTincept_id() {
        return tincept_id;
    }

    public void setTincept_id(String tincept_id) {
        this.tincept_id = tincept_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTavatarUrl() {
        return tavatarUrl;
    }

    public void setTavatarUrl(String tavatarUrl) {
        this.tavatarUrl = tavatarUrl;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }

    public String getTaddress() {
        return taddress;
    }

    public void setTaddress(String taddress) {
        this.taddress = taddress;
    }

    public String getTincept_time() {
        return tincept_time;
    }

    public void setTincept_time(String tincept_time) {
        this.tincept_time = tincept_time;
    }

    public String getTachieve_time() {
        return tachieve_time;
    }

    public void setTachieve_time(String tachieve_time) {
        this.tachieve_time = tachieve_time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String toString(){
        return String.format("入参为:[tlocation_id=%s,title=%s,content=%s,status=%d,tcreate_time=%s,tend_time=%s,tcancel_time=%s,tcreate_id=%s,tincept_id=%s,type=%d,tavatarUrl=%s,msg=%d,taddress=%s,avatarUrl=%s,tincept_time=%s,tachieve_time=%d,longitude=%d,latitude=%s,distance=%]",this.tlocation_id,this.title,this.content,this.status,this.tcreate_time,this.tend_time,this.tcancel_time,this.tcreate_id,this.tincept_id,this.type,this.tavatarUrl,this.msg,this.taddress,this.avatarUrl,this.tincept_time,this.tachieve_time,this.longitude,this.latitude,this.distance);
    }
}
