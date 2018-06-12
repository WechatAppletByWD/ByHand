package xin.pxyu.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import static org.apache.commons.dbcp.BasicDataSourceFactory.createDataSource;

public class DBCP {
    private static Properties pro = null;//获取键值对
    private static Connection conn;//连接器
    private static javax.sql.DataSource ds ;//保存数据源
    static{
        pro = new Properties();
        //加载配置文件为输入流的形式
        InputStream is = DBCP.class.getClassLoader().getResourceAsStream("D://180404web_bg/web/WEB-INF/config/dbcp.ini");
        try{
            //获取流中的信息;
            pro.load(is);
            //创建数据源
            ds = createDataSource(pro);
        }catch(IOException e){
            System.out.println("IOException");
            e.printStackTrace();
        }catch (Exception f){
            System.out.println("Exception");
            f.printStackTrace();
        }
    }
    public static Connection getConn(){
        try{
            conn = ds.getConnection();
        }catch (Exception e){

        }
        return conn;
    }
    //测试连接成功;
    public static void main(String[] args) {
        System.out.println(DBCP.getConn());
    }
}
