package xin.pxyu.model;

public class Admin {
    private String   admin_id;
    private String name;//用户名
    private String psw;//用户密码mds加密
    private String email;
    private int creator;//创建人 0为初始化
    private Integer flag;//用户状态 0禁用 1启用
    private String last_login_time;//上次登录时间
    private int update_user;//更新者id
    private String update_time;//更新时间

    public String getAdmin_id() {
        return admin_id;
    }

    public void setAdmin_id(String admin_id) {
        this.admin_id = admin_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public int getUpdate_user() {
        return update_user;
    }

    public void setUpdate_user(int update_user) {
        this.update_user = update_user;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }
}
