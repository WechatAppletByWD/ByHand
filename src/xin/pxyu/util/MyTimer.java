package xin.pxyu.util;
import xin.pxyu.dao.TaskDao;
import xin.pxyu.dao.TaskImageDao;

import java.io.File;
import java.util.Date;
import java.util.TimerTask;


public class MyTimer extends TimerTask {
    public static final long TOKEN_CHECKED_TIME=24*60*60*1000;

    //改变任务状态，每24小时检查一次，任务上传超过24小时没人接单，任务status改变
    public void changeStatus(){
       TaskDao.getTLocationId();
    }

    //每24小时检查一次，删除文件夹下的指定文件（上传图片时间超过1天的,非长期存储的）
    public void deleteFolder(File file){
        File[] files=file.listFiles();
        for(File f:files){
            if(new Date().getTime()-f.lastModified()>=TOKEN_CHECKED_TIME&&TaskImageDao.getPriorityByFileName(f.getName())!=1){
                //从数据库中删除指定的文件，根据文件名
                System.out.println("删除指定的文件哈哈哈"+TaskImageDao.delete(f.getName()));
                //从服务器中删除指定的文件
                f.delete();
            }
        }
    }

    public void run(){
        //指定文件夹的路径
        File file=new File("/root/apache-tomcat-8.0.52/webapps/upload");
        deleteFolder(file);
        changeStatus();
    }
    /*public static void main(String[] args){
        Timer t=new Timer();
        t.schedule(new MyTimer(),0,24*60*60*1000);
    }*/

}
