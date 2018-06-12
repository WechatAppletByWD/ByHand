package xin.pxyu.dao;
import xin.pxyu.model.SystemMessage;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static xin.pxyu.util.DBUtill.closeConn;
import static xin.pxyu.util.DBUtill.getConn;
import static xin.pxyu.util.ID.createId;

public class SystemMessageDao {
    public SystemMessageDao(){}
    public synchronized static SystemMessage getSystemMessageById(String sm_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn = getConn();
        String sql = "select * from systemmessage where sm_id=?";
        SystemMessage systemMessage = new SystemMessage();
        try {
            ps = conn.prepareStatement(sql);
         ps.setString(1,sm_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String receive_id = rs.getString("receive_id");
                String content = rs.getString("content");
                String topic= rs.getString("topic");
                Integer toAll= rs.getInt("toAll");
                String create_time=rs.getString("create_time");
              systemMessage.setContent(content);
              systemMessage.setSm_id(sm_id);
              systemMessage.setReceive_id(receive_id);
              systemMessage.setToAll(toAll);
              systemMessage.setTopic(topic);
              systemMessage.setCreate_time(create_time);
            }
        } catch (SQLException s) {
            System.out.println("SQLException");
            s.printStackTrace();
        }
        return systemMessage;
    }
    public synchronized static List<SystemMessage> getSystemMessage(int start,int length) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CallableStatement cs=null;
        conn = getConn();
        List<SystemMessage> list=new ArrayList<SystemMessage>();
        int end=start+length;
        try {
            String sql="call getSystemMessage(?,?)";
            cs = conn.prepareCall(sql);
            cs.setInt(1,start);
            cs.setInt(2,end);
            cs.execute();
            rs =cs.getResultSet();
            while (rs.next()) {
                SystemMessage systemMessage = new SystemMessage();
                String sm_id=rs.getString("sm_id");
                String receive_id = rs.getString("receive_id");
                String content = rs.getString("content");
                String topic= rs.getString("topic");
                Integer toAll= rs.getInt("toAll");
                String create_time=rs.getString("create_time");
                systemMessage.setContent(content);
                systemMessage.setSm_id(sm_id);
                systemMessage.setReceive_id(receive_id);
                systemMessage.setToAll(toAll);
                systemMessage.setTopic(topic);
                systemMessage.setCreate_time(create_time);
                list.add(systemMessage);
            }
        } catch (SQLException s) {
            System.out.println("SQLException");
            s.printStackTrace();
        }finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }
    public synchronized static boolean updateSystemMessage(SystemMessage systemMessage) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn = getConn();
        String sql = "update systemmessage set receive_id=?,topic=?,toAll=? where sm_id=? ";
        try {
            ps = conn.prepareStatement(sql);
            ps.setString(1,systemMessage.getReceive_id());
            ps.setString(2,systemMessage.getTopic());
            ps.setInt(3,systemMessage.getToAll());
            ps.setString(4,systemMessage.getSm_id());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return false;
    }
    public synchronized static boolean addSystemMessage(SystemMessage systemMessage){
        Connection conn=null;
        Statement st=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        conn=getConn();
        String create_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        systemMessage.setCreate_time(create_time);
        String sm_id=createId();
        systemMessage.setSm_id(sm_id);
        String sql="insert into systemmessage(sm_id,receive_id,topic,toAll,create_time) value(?,?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,systemMessage.getSm_id());
           ps.setString(2,systemMessage.getReceive_id());
           ps.setString(3,systemMessage.getTopic());
      //     ps.setString(3,systemMessage.getContent());
           ps.setInt(4,systemMessage.getToAll());
           ps.setString(5,systemMessage.getCreate_time());
            ps.executeUpdate();
            return  true;
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return  false;
    }
}