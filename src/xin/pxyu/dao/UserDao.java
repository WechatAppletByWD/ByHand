package xin.pxyu.dao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xin.pxyu.model.Task;
import xin.pxyu.model.User;
import xin.pxyu.util.DBUtill;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/*
dao层用来操作数据库
 */
public class UserDao extends DBUtill{
    //newUser
    public synchronized static boolean newUser(User user){
        Connection conn=null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet rs=null;
        //授权登录时间
        String create_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        user.setCreate_time(create_time);
        //初始积分100 允许微信登陆便可获得积分100,每次接单需要10分，或者支付0.1元
        int credit=100;
        user.setCredit(credit);
        //初始的等级1
        Integer rank=1;
        user.setRank(rank);
        try{
            conn=getConn();
            String sql="insert into userinfo(location_id,username,sex,credit,address,ip,device,rank,create_time,avatarUrl) value(?,?,?,?,?,?,?,?,?,?)";
            ps=conn.prepareStatement(sql);
            ps.setString(1,user.getLocation_id());
            ps.setString(2,user.getUsername());
            ps.setInt(3,user.getSex());
            ps.setInt(4,user.getCredit());
            ps.setString(5,user.getAddress());
            ps.setString(6,user.getIp());
            ps.setString(7,user.getDevice());
            ps.setInt(8,user.getRank());
            ps.setString(9,user.getCreate_time());
            ps.setString(10,user.getAvatarUrl());
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
    //userUpdate
    public synchronized static boolean userUpdate(User user){
        Connection conn=null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet rs=null;
        conn=getConn();
        try{
            if(isLocationId(user.getLocation_id())){
                //只有用户存在时才可以更改个人信息
                String sql = "update userinfo set wechat=?,qq=?,tel=?,ip=?,device=? where location_id=?";
                ps=conn.prepareStatement(sql);
                ps.setString(1,user.getWechat());
                ps.setString(2,user.getQq());
                ps.setString(3,user.getTel());
                ps.setString(4,user.getIp());
                ps.setString(5,user.getDevice());
                ps.setString(6,user.getLocation_id());
                ps.executeUpdate();
                return true;
            }
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }finally{
            closeConn(rs,st,conn,ps);
        }
        return false;
    }
    //检查location_id是否存在的接口（用户是否存在的接口）
    public synchronized  static boolean isLocationId(String location_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            String sql = "select location_id from userinfo";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                if(location_id.equals(rs.getString("location_id"))){
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }

    //updateLastAddress
    public synchronized static boolean updateLastAddress(String tlocation_id,String location_id){
        Connection conn=null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet rs=null;
        String last_address=TaskDao.getTaskAddress(tlocation_id);
        try{
            conn=getConn();
            String sql = "update userinfo set last_address=? where location_id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,last_address);
            ps.setString(2,location_id);
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
    //updateAddress
    public synchronized static boolean updateAddress(String location_id,String address){
        Connection conn=null;
        PreparedStatement ps=null;
        Statement st=null;
        ResultSet rs=null;
        try{
            conn=getConn();
            String sql = "update userinfo set address=? where location_id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,address);
            ps.setString(2,location_id);
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

    //userByLocationId
    public synchronized static User getUserByLocationId(String location_id){
        Connection conn=null;
        Statement st=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        User user=new User();
        try{
            conn=getConn();
            String sql="select * from userinfo where location_id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,location_id);
            rs=ps.executeQuery();
            while(rs.next()){
                String username=rs.getString("username");
                String password=rs.getString("password");
                Integer sex=rs.getInt("sex");
                int credit=rs.getInt("credit");
                String wechat=rs.getString("wechat");
                String qq=rs.getString("qq");
                String tel=rs.getString("tel");
                String address=rs.getString("address");
                String ip=rs.getString("ip");
                String device=rs.getString("device");
                int rank=rs.getInt("rank");
                String address_id=rs.getString("address_id");
                String create_time=rs.getString("create_time");
                String avatarUrl=rs.getString("avatarUrl");
                String last_address=rs.getString("last_address");
                user.setLocation_id(location_id);
                user.setUsername(username);
                user.setSex(sex);
                user.setCredit(credit);
                user.setWechat(wechat);
                user.setQq(qq);
                user.setTel(tel);
                user.setAddress(address);
                user.setIp(ip);
                user.setDevice(device);
                user.setRank(rank);
                user.setCreate_time(create_time);
                user.setAvatarUrl(avatarUrl);
                user.setLast_address(last_address);
            }
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }finally{
            closeConn(rs,st,conn,ps);
        }
        return user;
    }
    //getAvatarUrlByLocationId
    public synchronized static String getAvatarUrlByLocationId(String location_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String avatarUrl="";
        try {
            conn = getConn();
            String sql = "select avatarUrl from userinfo where location_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,location_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                avatarUrl=rs.getString("avatarUrl");
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
       return avatarUrl;
    }
    //getAllUsers
    public synchronized  static List<User> getAllUsers(){
        Connection conn=null;
        Statement st=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        List<User> list=new ArrayList<User>();
        try{
            conn=getConn();
            String sql="select * from userinfo order by create_time desc";
            st=conn.createStatement();
            rs=st.executeQuery(sql);
            while(rs.next()){
                User user=new User();
                String location_id=rs.getString("location_id");
                String username=rs.getString("username");
                String password=rs.getString("password");
                Integer sex=rs.getInt("sex");
                int credit=rs.getInt("credit");
                String wechat=rs.getString("wechat");
                String qq=rs.getString("qq");
                String tel=rs.getString("tel");
                String address=rs.getString("address");
                String ip=rs.getString("ip");
                String device=rs.getString("device");
                int rank=rs.getInt("rank");
                String address_id=rs.getString("address_id");
                String create_time=rs.getString("create_time");
                String avatarUrl=rs.getString("avatarUrl");
                String last_address=rs.getString("last_address");
                user.setLocation_id(location_id);
                user.setUsername(username);
                user.setSex(sex);
                user.setCredit(credit);
                user.setWechat(wechat);
                user.setQq(qq);
                user.setTel(tel);
                user.setAddress(address);
                user.setIp(ip);
                user.setDevice(device);
                user.setRank(rank);
                user.setCreate_time(create_time);
                user.setAvatarUrl(avatarUrl);
                user.setLast_address(last_address);
                list.add(user);
            }
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return list;
    }
    //updateRank
    public synchronized  static boolean updateRank(String location_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int rank=0;
        int credit=getUserCredit(location_id);
        if(credit<500){
            rank=0;//普通会员
        }else if(credit>=500&&credit<1000){
            rank=1;//白金会员
        }else if(credit>=1000&&credit<5000){
            rank=2;//黄金会员
        }else if(credit>=5000){
            rank=3;//钻石会员
        }
        try {
            String sql = "update userinfo set rank=? where location_id=?";
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,rank);
            ps.setString(2, location_id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }

    //userSubCredit
    public synchronized  static boolean userSubCredit(String location_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int credit=getUserCredit(location_id)-50;
        try {
            String sql = "update userinfo set credit=?  where location_id=?";
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,credit);
            ps.setString(2, location_id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }
    //userAddCredit  当用户接单成功或发布任务成功时，均会给用户的信用积分加分
    public synchronized static boolean userAddCredit(String location_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int credit=getUserCredit(location_id)+50;
        try {
            String sql = "update userinfo set credit=?  where location_id=?";
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.setInt(1,credit);
            ps.setString(2,location_id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }

    //getUserCredit 获取用户积分
    public synchronized  static int getUserCredit(String location_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        //登录即可获得150积分
        int credit=150;
        try {
            String sql="select credit from userinfo where location_id=?";
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1, location_id);
            rs=ps.executeQuery();
            while(rs.next()){
                credit=rs.getInt("credit");
                return  credit;
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return credit;
    }
}
