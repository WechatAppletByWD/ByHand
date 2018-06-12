package xin.pxyu.util;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
/*
加载配置
 */
public class LoadDBconfig {
//存储配置文件中的值
    private static HashMap<String ,String > dbMap=new HashMap<String,String>();
    public static  String getDBValue(String name){
        return dbMap.get(name);
    }
    public static void load(String path){
        try{
            //加载dbconfig.properties
            Properties properties=new Properties();
            //加载文件
            properties.load(new FileInputStream(path));
            //  取值
            String driverclass=properties.getProperty("driverclass");
            dbMap.put("driverclass",driverclass);
            String url=properties.getProperty("url");
            dbMap.put("url",url);
            String username=properties.getProperty("username");
            dbMap.put("username",username);
            String password=properties.getProperty("password");
            dbMap.put("password",password);
        }catch(FileNotFoundException e){
            System.out.println("FileNotFoundException");
            e.printStackTrace();
        }
        catch(IOException e){
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}
