package xin.pxyu.dao;

import xin.pxyu.model.Role;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static xin.pxyu.util.DBUtill.getConn;
import static xin.pxyu.util.ID.createId;

public class RoleDao {
    //查询所有角色
    public synchronized static List<Role> getAllRole(){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        List<Role> list=new ArrayList<Role>();
        String sql="select * from role";
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Role role=new Role();
                String role_id=rs.getString("role_id");
                String name=rs.getString("name");
                String create_time=rs.getString("create_time");
                String creator=rs.getString("creator");
                String description=rs.getString("description");
                int update_user=rs.getInt("update_user");
                String update_time=rs.getString("update_time");
                role.setRole_id(role_id);
                role.setName(name);
                role.setCreate_time(create_time);
                role.setCreator(creator);
                role.setDescription(description);
                role.setUpdate_user(update_user);
                role.setUpdate_time(update_time);
                list.add(role);
            }
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return list;
    }
    //根据id查询角色
    public synchronized static Role getRoleById(String role_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        Role role=new Role();
        String sql="select * from role where role_id=?";
        try{
            ps=conn.prepareStatement(sql);
          ps.setString(1,role_id);
            rs=ps.executeQuery();
            while(rs.next()){
                String name=rs.getString("name");
                String create_time=rs.getString("create_time");
                String creator=rs.getString("creator");
                String description=rs.getString("description");
                int update_user=rs.getInt("update_user");
                String update_time=rs.getString("update_time");
              role.setRole_id(role_id);
                role.setName(name);
                role.setCreate_time(create_time);
                role.setCreator(creator);
                role.setDescription(description);
                role.setUpdate_user(update_user);
                role.setUpdate_time(update_time);
            }
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return role;
    }
    //插入角色
    public synchronized static boolean addRole(Role role){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        role.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
       role.setRole_id(createId());
        String sql="insert into role(role_id,name,create_time,creator,description) value(?,?,?,?,?)";
         try{
         ps=conn.prepareStatement(sql);
         ps.setString(1,role.getRole_id());
         ps.setString(2,role.getName());
         ps.setString(3,role.getCreate_time());
         ps.setString(4,role.getCreator());
         ps.setString(5,role.getDescription());
         ps.executeUpdate();
         return true;
         }catch(SQLException e){
             System.out.println("SQLException ");
             e.printStackTrace();
         }
         return false;
    }
    //编辑角色
    public synchronized static boolean updateRole(Role role){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String sql="update role set name=?,description=?,update_user=?,update_time=? where role_id=?&&creator=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,role.getName());
            ps.setString(2,role.getDescription());
            ps.setInt(3,role.getUpdate_user());
            ps.setString(4,role.getUpdate_time());
            ps.setString(5,role.getRole_id());
            ps.setString(6,role.getCreator());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
    //删除角色,通过id删除角色
    public synchronized static boolean deleteRole(String role_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String sql="delete from role where role_id=?";
        try{
            ps=conn.prepareStatement(sql);
           ps.setString(1,role_id);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
}
