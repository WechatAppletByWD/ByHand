package xin.pxyu.util;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Timer;

/*
监听上下文，加载配置
实现ServletContextListener，实现相应方法
 */
public class ListenerLoadConfig implements ServletContextListener{
public static final long TOKEN_CHECKED_TIME=24*60*60*1000;
@Override
    public void contextDestroyed(ServletContextEvent sce){
}
@Override
    public void contextInitialized(ServletContextEvent event){
        //加载数据库配置文件
        String path=event.getServletContext().getRealPath("/WEB-INF/config/dbconfig.properties");
        LoadDBconfig.load(path);
        System.out.println(DBUtill.getConn());
        //定时器
       Timer t=new Timer();
        //每隔24hour执行一次任务，没有延迟，立即执行
        t.schedule(new MyTimer(),0,TOKEN_CHECKED_TIME);
}
}
