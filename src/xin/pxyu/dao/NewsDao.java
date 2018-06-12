package xin.pxyu.dao;

import xin.pxyu.model.News;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static xin.pxyu.util.DBUtill.getConn;
import static xin.pxyu.util.ID.createId;

public class NewsDao {
    //addNews
    public synchronized static boolean addNews(News news){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String news_id=createId();
        news.setNews_id(news_id);
        String sql="insert into news(news_id,title,content,upload_time,school) value(?,?,?,?,?)";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,news.getNews_id());
            ps.setString(2,news.getTitle());
            ps.setString(3,news.getContent());
            ps.setString(4,news.getUpload_time());
            ps.setString(5,news.getSchool());
            ps.executeUpdate();
            return true;
        }catch(SQLException s){
            System.out.println("SQLException ");
            s.printStackTrace();
        }
return false;
    }
    //updateNews
    public synchronized static boolean updateNews(News news){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        String sql="update news set title=?,content=?,upload_time=?,school=? where news_id=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,news.getTitle());
            ps.setString(2,news.getContent());
            ps.setString(3,news.getUpload_time());
            ps.setString(4,news.getSchool());
            ps.setString(5,news.getNews_id());
            ps.executeUpdate();
            return true;
        }catch(SQLException s){
            System.out.println("SQLException ");
            s.printStackTrace();
        }
        return false;
    }
    //getNewsBySchool
    public synchronized static List<News> getNewsBySchool(String school){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        List<News> list=new ArrayList<News>();
        String sql="select * from news where school=? order by upload_time desc";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,school);
            rs=ps.executeQuery();
            while(rs.next()){
News news=new News();
String news_id=rs.getString("news_id");
String title=rs.getString("title");
String content=rs.getString("content");
String upload_time=rs.getString("upload_time");
news.setNews_id(news_id);
news.setTitle(title);
news.setContent(content);
news.setUpload_time(upload_time);
news.setSchool(school);
list.add(news);
            }
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return list;
    }
 //   getNewsByUploadTime
    public synchronized  static List<News> getNewsByUploadTime(String upload_time){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn=getConn();
        List<News> list=new ArrayList<News>();
        String sql="select * from news where upload_time=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,upload_time);
            rs=ps.executeQuery();
            while(rs.next()){
                News news=new News();
               String news_id=rs.getString("news_id");
                String title=rs.getString("title");
                String content=rs.getString("content");
                String school=rs.getString("school");
                news.setNews_id(news_id);
                news.setTitle(title);
                news.setContent(content);
                news.setUpload_time(upload_time);
                news.setSchool(school);
                list.add(news);
            }
        }catch(SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return list;
    }
}
