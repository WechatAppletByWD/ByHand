package xin.pxyu.dao;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.util.Streams;
import xin.pxyu.model.TaskImage;
import java.io.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import static xin.pxyu.util.DBUtill.closeConn;
import static xin.pxyu.util.DBUtill.getConn;
//处理图片上传的类
public class TaskImageDao {
    private static Map<String, String> types = new HashMap<String, String>();
    private static File saveDir;//创建保存目录
    public TaskImageDao() {
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/bmp", ".bmp");
        types.put("image/png", ".png");
        types.put("image/jpg", ".jpg");
    }

    public static TaskImage upload(String tpath, FileItemIterator fii) {
        TaskImageDao taskImageDao=new TaskImageDao();
       TaskImage taskImage=new TaskImage();
        try {
            //遍历容器中的文件元素;
            while (fii.hasNext()) {
                FileItemStream fs = fii.next();
                System.out.println("fs你穿的所有流"+fs);
                String name = fs.getFieldName();//获取字段名

                System.out.println("字段名"+name);
                InputStream is = fs.openStream();//打开流
                //是文件域而且已经选择了头像
                if (!fs.isFormField() && fs.getName().length() > 0){
                    String prev=fs.getName();
                    System.out.println("prevfs.getName"+prev);
                    String contype = fs.getContentType();//获取文件后缀类型;

                    System.out.println("文件后缀类型"+contype);
                    if (!types.containsKey(contype)) {
                        //判断文件类型是否符合;
                        System.out.println("文件类型不符合");
                        break;//如果不符合，则直接结束,无需上传
                    }
                    //利用缓冲流
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String upload ="upload";
                    UUID id = UUID.randomUUID();//生成UUID 防止文件名重复，发生冲突;
                    String filename = id.toString() + types.get(contype);//生成文件名字

                    System.out.println("filename文件名"+filename);
                    taskImage.setFilename(filename);
                    String url="http://pxyu.xin:8080/upload/"+filename;

                    System.out.println("url存的url地址"+url);
                    taskImage.setUrl(url);
                    //Tomcat路径+文件存储路径;
                    String allPath = tpath + upload + System.getProperty("file.separator") + filename;

                    System.out.println("文件存储路径"+allPath);
                    saveDir = new File(allPath);   //生成文件
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(allPath)));
                    Streams.copy(bis, bos, true);//上传;
                    taskImage.setPath(allPath);

                    System.out.println("文件的根路径"+taskImage.getPath());
                }
            }
        } catch (FileNotFoundException f) {
         System.out.println("异常异常FileNotFoundException");
         f.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("异常异常IOException");
        }catch (FileUploadException u){
            u.printStackTrace();
            System.out.println("异常异常FileUploadException");
        }
        return taskImage;
    }

    public static boolean  delete(String filename){
        ResultSet rs=null;
        Statement st=null;
        PreparedStatement ps=null;
        Connection conn=getConn();
        String sql="delete from taskimage where filename=?";
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,filename);
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return false;
    }
    public static boolean updateTaskImage(TaskImage taskImage) {
        ResultSet rs=null;
        Statement st=null;
        PreparedStatement ps=null;
      // String path=taskImage.getPath();
     //  System.out.println("taskImage.getPath()"+taskImage.getPath());
        String upload_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        taskImage.setUpload_time(upload_time);
        Integer priority=0;
        taskImage.setPriority(priority);
        String sql = "insert into taskimage(path,pic,upload_time,filename,url,priority) value(?,?,?,?,?,?)";
        Connection conn=getConn();
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,taskImage.getPath());

            System.out.println("update文件的存储路径"+taskImage.getPath());
            FileInputStream fis = new FileInputStream(taskImage.getPath());
           ps.setBinaryStream(2,fis,fis.available());
           ps.setString(3,taskImage.getUpload_time());

           System.out.println("uploadtime"+taskImage.getUpload_time());
           ps.setString(4,taskImage.getFilename());

           System.out.println("文件名"+taskImage.getFilename());
           ps.setString(5,taskImage.getUrl());

           System.out.println("文件优先级"+taskImage.getPriority());
           ps.setInt(6,taskImage.getPriority());
           ps.executeUpdate();
           return true;
        }catch (SQLException e){
           System.out.println("SQLException");
           e.printStackTrace();
        }catch(FileNotFoundException f) {
           System.out.println("FileNotFoundException");
           f.printStackTrace();
        }catch(IOException o){
            System.out.println("IOException");
            o.printStackTrace();
        } finally{
           closeConn(rs,st,conn,ps);
        }
        return false;
    }
    public static int getPriorityByFileName(String filename){
        ResultSet rs=null;
        Statement st=null;
        PreparedStatement ps=null;
        Connection conn=getConn();
        int priority=0;
        String sql="select priority from taskimage where filename=?";
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,filename);
            rs=ps.executeQuery();
            while(rs.next()){
                priority=rs.getInt("priority");
            }
        }catch(SQLException e){
            System.out.println("SQLException ");
            e.printStackTrace();
        }
        return priority;
    }
}
