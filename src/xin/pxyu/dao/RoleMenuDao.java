package xin.pxyu.dao;

import xin.pxyu.json.ListObject;
import xin.pxyu.model.Menu;

import java.sql.*;
import java.util.List;

import static xin.pxyu.util.DBUtill.getConn;

public class RoleMenuDao {
    //查询角色的相关资源权限（菜单权限）
    public synchronized Menu getMenuByRoleId(String  roleId){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        Menu menu=null;
        String sql="select menuid from role_menu where roleid=?";
        try{
            ps=conn.prepareStatement(sql);
           ps.setString(1,roleId);
            rs=ps.executeQuery();
            while(rs.next()){
                String  menuId=rs.getString("menuid");
                menu=MenuDao.getMenuById(menuId);
            }
        }catch (SQLException s){
            System.out.println("SQLException");
            s.printStackTrace();
        }
        return menu;
    }
    //更新角色的权限
   // public synchronized  boolean updateRoleMenu(int )
}
