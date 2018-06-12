package xin.pxyu.dao;

import xin.pxyu.model.Suggestion;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static xin.pxyu.util.DBUtill.getConn;
import static xin.pxyu.util.ID.createId;

public class SuggestionDao {
    public SuggestionDao(){}
    public synchronized static Suggestion getSuggestionById(String s_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn = getConn();
        String sql = "select * from suggestion where s_id=?";
       Suggestion suggestion=new Suggestion();
        try {
            ps = conn.prepareStatement(sql);
        ps.setString(1,s_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String suggest_id = rs.getString("suggest_id");
                String topic= rs.getString("topic");
                String create_time=rs.getString("create_time");
                suggestion.setS_id(s_id);
                suggestion.setSuggest_id(suggest_id);
                suggestion.setTopic(topic);
                suggestion.setCreate_time(create_time);
            }
        } catch (SQLException s) {
            System.out.println("SQLException");
            s.printStackTrace();
        }
        return suggestion;
    }

    public synchronized static String getSuggestId(String s_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn = getConn();
        String suggest_id=" ";
        String sql = "select suggest_id from suggestion where s_id=?";
        try {
            ps = conn.prepareStatement(sql);
           ps.setString(1,s_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                 suggest_id = rs.getString("suggest_id");
            }
        } catch (SQLException s) {
            System.out.println("SQLException");
            s.printStackTrace();
        }
        return suggest_id;
    }
    public synchronized static boolean updateSuggestion(Suggestion suggestion) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn = getConn();
        if(getSuggestId(suggestion.getS_id()).equals(suggestion.getSuggest_id())){
            String sql = "update suggestion set topic=? where s_id=? and suggest_id=? ";
            try {
                ps = conn.prepareStatement(sql);
                ps.setString(1,suggestion.getTopic());
                ps.setString(2,suggestion.getS_id());
                ps.setString(3,suggestion.getSuggest_id());
                ps.executeUpdate();
                return true;
            } catch (SQLException e) {
                System.out.println("SQLException");
                e.printStackTrace();
            }
        }
        return false;
    }
    public synchronized static boolean addSuggestion(Suggestion suggestion){
        Connection conn=null;
        Statement st=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        conn=getConn();
        String create_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        suggestion.setCreate_time(create_time);
        String s_id=createId();
        suggestion.setS_id(s_id);
        System.out.println("s_id!!!!!!"+s_id);
        String sql="insert into suggestion(s_id,suggest_id,topic,create_time) value(?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,suggestion.getS_id());
            ps.setString(2,suggestion.getSuggest_id());
            ps.setString(3,suggestion.getTopic());
            ps.setString(4,suggestion.getCreate_time());
            ps.executeUpdate();
            return  true;
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return  false;
    }
}
