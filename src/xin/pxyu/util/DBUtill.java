package xin.pxyu.util;
import java.sql.*;
/*
获取数据库连接
 */
public class DBUtill {
    //建立连接
   public static Connection getConn() {
       Connection conn= null;
       try {
           Class.forName(LoadDBconfig.getDBValue("driverclass"));
           String url = LoadDBconfig.getDBValue("url");
           String user = LoadDBconfig.getDBValue("username");
           String password = LoadDBconfig.getDBValue("password");
           conn = DriverManager.getConnection(url, user, password);
       } catch (Exception e) {
           System.out.println("database connection start error!");
           e.printStackTrace();
       }
       return conn;
   }
   //关闭连接
    public static void closeConn(ResultSet rs, Statement st,Connection conn,PreparedStatement ps){
        if(rs!=null)
            try{
           //防止引用资源 不为null
           //为每个资源使用单独try catch块关闭资源，保证关闭资源时引发的一场不会影响其他的资源的关闭
           //使用finally块来关闭资源，保证程序打开的物理资源总是被关闭
           rs.close();
       }catch(SQLException e){
           System.out.println("rs 程序打开的物理资源close error!");
           e.printStackTrace();
       }
        if(st!=null)
       try{
            st.close();
       }catch(SQLException s){
           System.out.println("st 程序打开的物理资源close error!");
           s.printStackTrace();
       }
        if(conn!=null)
        try{
            conn.close();
        }catch(SQLException s){
            System.out.println("conn 序打开的物理资源close error!!");
            s.printStackTrace();
        }
        if(ps!=null)
            try{
            ps.close();
        }catch(SQLException s){
            System.out.println("ps 程序打开的物理资源close error!");
            s.printStackTrace();
        }
    }
}
