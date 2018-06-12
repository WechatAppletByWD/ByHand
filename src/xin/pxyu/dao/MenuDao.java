package xin.pxyu.dao;

import xin.pxyu.model.Admin;
import xin.pxyu.model.Menu;
import xin.pxyu.model.Role;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static xin.pxyu.util.DBUtill.getConn;
import static xin.pxyu.util.ID.createId;


public class MenuDao {
    //查询所有菜单
    public synchronized static List<Menu> getAllMenu(){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        List<Menu> list=new ArrayList<Menu>();
        String sql="select * from menu";
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Menu menu=new Menu();
               String menu_id=rs.getString("menu_id");
                String title=rs.getString("title");
                String url=rs.getString("url");
                String icon=rs.getString("icon");
                Integer menu_type=rs.getInt("menu_type");
                int display=rs.getInt("display");
                int parent_id=rs.getInt("parent_id");
                int creator=rs.getInt("creator");
                String create_time=rs.getString("create_time");
                int update_user=rs.getInt("update_user");
                String update_time=rs.getString("update_time");
                Integer status=rs.getInt("status");
                menu.setMenu_id(menu_id);
                menu.setTitle(title);
                menu.setUrl(url);
                menu.setIcon(icon);
                menu.setMenu_type(menu_type);
                menu.setDisplay(display);
                menu.setParent_id(parent_id);
                menu.setCreator(creator);
                menu.setCreate_time(create_time);
                menu.setUpdate_user(update_user);
                menu.setUpdate_time(update_time);
                menu.setStatus(status);
                list.add(menu);
            }
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return list;
    }
    //根据id查menu
    public synchronized static Menu getMenuById(String menu_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn= getConn();
        Menu menu=new Menu();
        String sql="select * from menu where menu_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,menu_id);
            rs=ps.executeQuery();
            while(rs.next()){
                String title=rs.getString("title");
                String url=rs.getString("url");
                String icon=rs.getString("icon");
                Integer menu_type=rs.getInt("menu_type");
                int display=rs.getInt("display");
                int parent_id=rs.getInt("parent_id");
                int creator=rs.getInt("creator");
                String create_time=rs.getString("create_time");
                int update_user=rs.getInt("update_user");
                String update_time=rs.getString("update_time");
                Integer status=rs.getInt("status");
                menu.setMenu_id(menu_id);
                menu.setTitle(title);
                menu.setUrl(url);
                menu.setIcon(icon);
                menu.setMenu_type(menu_type);
                menu.setDisplay(display);
                menu.setParent_id(parent_id);
                menu.setCreator(creator);
                menu.setCreate_time(create_time);
                menu.setUpdate_user(update_user);
                menu.setUpdate_time(update_time);
                menu.setStatus(status);
            }
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return menu;
    }
    //插入菜单
    public synchronized static boolean addMenu(Menu menu){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        menu.setMenu_id(createId());
        menu.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        String sql="insert into menu(menu_id,title,url,icon,menu_type,display,parent_id,creator,create_time,status) value(?,?,?,?,?,?,?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,menu.getMenu_id());
            ps.setString(2,menu.getTitle());
            ps.setString(3,menu.getUrl());
            ps.setString(4,menu.getIcon());
            ps.setInt(5,menu.getMenu_type());
            ps.setInt(6,menu.getDisplay());
            ps.setInt(7,menu.getParent_id());
            ps.setInt(8,menu.getCreator());
            ps.setString(9,menu.getCreate_time());
            ps.setInt(10,menu.getStatus());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
    //编辑菜单
    public synchronized static boolean updateMenu(Menu menu){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String sql="update menu set title=?,url=?,icon=?,menu_type=?,display=?,parent_id=?,status=?,update_user=?,update_time=? where menu_id=?&&creator=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,menu.getTitle());
            ps.setString(2,menu.getUrl());
            ps.setString(3,menu.getIcon());
            ps.setInt(4,menu.getMenu_type());
            ps.setInt(5,menu.getDisplay());
            ps.setInt(6,menu.getParent_id());
            ps.setInt(7,menu.getStatus());
            ps.setInt(8,menu.getUpdate_user());
            ps.setString(9,menu.getUpdate_time());
            ps.setString(10,menu.getMenu_id());
            ps.setInt(11,menu.getCreator());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
    //删除菜单,通过id删除菜单
    public synchronized static boolean deleteMenu(String menu_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String sql="delete from menu where menu_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,menu_id);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
}
