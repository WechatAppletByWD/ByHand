package xin.pxyu.model;
/*
model层用来封装数据实体
 */
public class User {
    private String location_id;
    private String username;
    private Integer sex;//0不明 1男 2女
    private int credit;
    private String wechat;
    private String qq;
    private String tel;
    private String address;
    private String ip;
    private String device;
    private Integer rank;//默认等级1，注册用户等级1
    private String avatarUrl;
    private String last_address;
    private String create_time;

    public String getCreate_time() {
        return create_time;
    }

    public User(){}
    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }


    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getLast_address() {
        return last_address;
    }

    public void setLast_address(String last_address) {
        this.last_address = last_address;
    }

    public String toString(){
        return String.format("入参为:[location_id=%s,username=%s,sex=%d,credit=%d,wechat=%s,qq=%s,tel=%s,address=%s,ip=%s,device=%s,rank=%d,create_time=%s,avatarUrl=%s,last_address=%s]",this.location_id,this.username,this.sex,this.credit,this.wechat,this.qq,this.tel,this.address,this.ip,this.device,this.rank,this.create_time,this.avatarUrl,this.last_address);
    }
}
