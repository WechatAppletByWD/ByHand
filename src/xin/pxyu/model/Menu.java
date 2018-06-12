package xin.pxyu.model;
//菜单
public class Menu {
    private String menu_id;
    private String title;//菜单名称
    private String url;//网址
    private String icon;//显示的图标
    private Integer menu_type;//类型 0 菜单 1链接网址 2隐藏菜单
    private int display;//显示排序
    private int parent_id;//父级id
    private int creator;//创建者id 0为超级管理员
    private String create_time;//创建时间
    private int update_user;//更新者id
    private String update_time;//更新时间
    private Integer status;//是否启用 0 禁用 1 启用

    public String getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(String menu_id) {
        this.menu_id = menu_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Integer getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(Integer menu_type) {
        this.menu_type = menu_type;
    }

    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    public int getParent_id() {
        return parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
