package xin.pxyu.dao;
import xin.pxyu.model.Admin;
import xin.pxyu.model.Role;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static xin.pxyu.util.DBUtill.closeConn;
import static xin.pxyu.util.DBUtill.getConn;
import static xin.pxyu.util.ID.createId;

public class AdminDao {
    //查询所有系统管理员
    //查询所有角色
    public synchronized static List<Admin> getAllAdmin(){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn= getConn();
        List<Admin> list=new ArrayList<Admin>();
        String sql="select * from admin";
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Admin admin=new Admin();
                String admin_id=rs.getString("admin_id");
                String name=rs.getString("name");
                String psw=rs.getString("psw");
                String email=rs.getString("email");
                int creator=rs.getInt("creator");
                int flag=rs.getInt("flag");
                String last_login_time=rs.getString("last_login_time");
                int update_user=rs.getInt("update_user");
                String update_time=rs.getString("update_time");
                admin.setAdmin_id(admin_id);
                admin.setName(name);
                admin.setPsw(psw);
                admin.setEmail(email);
                admin.setCreator(creator);
                admin.setFlag(flag);
                admin.setLast_login_time(last_login_time);
                admin.setUpdate_user(update_user);
                admin.setUpdate_time(update_time);
                list.add(admin);
            }
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return list;
    }

    //根据id查ADMIN
    public synchronized static Admin getAdminById(String admin_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn= getConn();
       Admin admin=new Admin();
        String sql="select * from admin where admin_id=?";
        try{
            ps=conn.prepareStatement(sql);
             ps.setString(1,admin_id);
            rs=ps.executeQuery();
            while(rs.next()){
                String name=rs.getString("name");
                String psw=rs.getString("psw");
                String email=rs.getString("email");
                int creator=rs.getInt("creator");
                int flag=rs.getInt("flag");
                String last_login_time=rs.getString("last_login_time");
                int update_user=rs.getInt("update_user");
                String update_time=rs.getString("update_time");
                admin.setAdmin_id(admin_id);
                admin.setName(name);
                admin.setPsw(psw);
                admin.setEmail(email);
                admin.setCreator(creator);
                admin.setFlag(flag);
                admin.setLast_login_time(last_login_time);
                admin.setUpdate_user(update_user);
                admin.setUpdate_time(update_time);
            }
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return admin;
    }
    //插入admin
    public synchronized static boolean addAdmin(Admin admin){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String admin_id=createId();
        admin.setAdmin_id(admin_id);
        String sql="insert into admin(admin_id,name,psw,email,creator,flag) value(?,?,?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,admin.getAdmin_id());
            ps.setString(2,admin.getName());
            ps.setString(3,admin.getPsw());
            ps.setString(4,admin.getEmail());
            ps.setInt(5,admin.getCreator());
            ps.setInt(6,admin.getFlag());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
    //编辑管理员
    public synchronized static boolean updateAdmin(Admin admin){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String sql="update admin set name=?,email=?,flag=?,update_user=?,update_time=? where admin_id=?&&creator=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,admin.getName());
            ps.setString(2,admin.getPsw());
            ps.setString(3,admin.getEmail());
            ps.setInt(4,admin.getFlag());
            ps.setInt(5,admin.getUpdate_user());
            ps.setString(6,admin.getUpdate_time());
            ps.setString(7,admin.getAdmin_id());
            ps.setInt(8,admin.getCreator());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
    //删除,通过id删除角色admin
    public synchronized static boolean deleteAdmin(String  admin_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String sql="delete from admin where admin_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,admin_id);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
    //logIn管理员登陆接口
    public synchronized static boolean logIn(String c_name,String c_psw){
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            conn=getConn();
            String sql="select name,psw from admin";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                String name=rs.getString("name");
                String psw=rs.getString("psw");
                if(name.equals(c_name)&&psw.equals(c_psw)){
                    return true;
                }
            }
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }finally{
            closeConn(rs,st,conn,ps);
        }
        return false;
    }
    //signIn管理员注册接口
    public  synchronized static boolean signIn(String name,String psw,String email){
        Connection conn=null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet rs=null;
        String admin_id=createId();
        try{
            conn=getConn();
            String sql="insert into admin(admin_id,name,psw,email) value(?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,admin_id);
            ps.setString(2,name);
            ps.setString(3,psw);
            ps.setString(4,email);
            ps.executeUpdate();
            return true;
        }catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }finally{
            closeConn(rs,st,conn,ps);
        }
        return false;
    }
    //checkUsername
    public synchronized  static boolean checkUsername(String c_name){
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        try{
            conn=getConn();
            String sql="select name from admin";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                String name=rs.getString("name");
                if(name.equals(c_name)){
                    return true;
                }
            }
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }finally{
            closeConn(rs,st,conn,ps);
        }
        return false;
    }
}
