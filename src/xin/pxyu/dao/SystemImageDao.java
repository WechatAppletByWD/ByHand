package xin.pxyu.dao;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.util.Streams;
import xin.pxyu.model.SystemImage;
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

public class SystemImageDao {
    private static Map<String, String> types = new HashMap<String, String>();
    private static File saveDir;//创建保存目录
    public SystemImageDao() {
        types.put("image/jpeg", ".jpg");
        types.put("image/gif", ".gif");
        types.put("image/bmp", ".bmp");
        types.put("image/png", ".png");
    }
    public static SystemImage upload(String tpath, FileItemIterator fii) {
        SystemImageDao systemImageDao=new SystemImageDao();
       SystemImage systemImage=new SystemImage();
        try {
            //遍历容器中的文件元素;
            while (fii.hasNext()) {
                FileItemStream fs = fii.next();
                String name = fs.getFieldName();//获取字段名
                InputStream is = fs.openStream();//打开流
                //是文件域而且已经选择了头像
                if (!fs.isFormField() && fs.getName().length() > 0){
                    String contype = fs.getContentType();//获取文件后缀类型;
                    if (!types.containsKey(contype)) {
                        //判断文件类型是否符合;
                        //System.out.println("文件类型不符合");
                        break;//如果不符合，则直接结束,无需上传
                    }
                    //利用缓冲流
                    BufferedInputStream bis = new BufferedInputStream(is);
                    String system ="system";
                    UUID id = UUID.randomUUID();//生成UUID 防止文件名重复，发生冲突;
                    String filename = id.toString() + types.get(contype);//生成文件名字
                    System.out.println("filename"+filename);
                    systemImage.setFilename(filename);
                    String url="http://pxyu.xin:8080/system/"+filename;
                    System.out.println("url"+url);
                    systemImage.setUrl(url);
                    //Tomcat路径+文件存储路径;
                    String allPath = tpath + system + System.getProperty("file.separator") + filename;
                    System.out.println("allpath"+allPath);
                    saveDir = new File(allPath);   //生成文件
                    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(allPath)));
                    Streams.copy(bis, bos, true);//上传;
                   systemImage.setPath(allPath);
                    System.out.println("systemImage.setPath"+systemImage.getPath());
                }else{
                    System.out.println("未上传用户图像");
                }
            }
        } catch (FileNotFoundException f) {
            System.out.println("异常异常FileNotFoundException");
        }catch(IOException e){
            System.out.println("异常异常IOException");
        }catch (FileUploadException u){
            System.out.println("异常异常FileUploadException");
        }
        return systemImage;
    }
    public static boolean updateSystemImage(SystemImage systemImage) {
        ResultSet rs=null;
        Statement st=null;
        PreparedStatement ps=null;
        // String path=taskImage.getPath();
        //  System.out.println("taskImage.getPath()"+taskImage.getPath());
        String upload_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
       systemImage.setUpload_time(upload_time);
        String sql = "insert into systemimage(path,pic,upload_time,filename,url) value(?,?,?,?,?)";
        Connection conn=getConn();
        try{
            ps = conn.prepareStatement(sql);
            ps.setString(1,systemImage.getPath());
            FileInputStream fis = new FileInputStream(systemImage.getPath());
            ps.setBinaryStream(2,fis,fis.available());
            ps.setString(3,systemImage.getUpload_time());
            ps.setString(4,systemImage.getFilename());
            ps.setString(5,systemImage.getUrl());
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
}
