package xin.pxyu.servlet;
/*
c/s模式  p2p模式
应用层协议 http ftp tftp telnet smtp pop3 imap dns ssh ssl
传输层协议 tcp udp rdt1.0 rdt2.0 rdt2.1 rdt2.2 rdt3.0  go-back-n select-repeat

 */
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import xin.pxyu.json.SingleObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.SystemImage;
import xin.pxyu.model.TaskImage;
import xin.pxyu.service.SystemImageService;
import xin.pxyu.service.TaskImageService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

//上传图片
public class fileUploads extends HttpServlet {
    /*
    http请求报文：
    1)请求行 ：请求方法，请求资源的url
    2）首部行header：
    3）尸体主题body：
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"fileUploads接口收到"+request.getRemoteAddr()+"的请求");
        if(ServletFileUpload.isMultipartContent(request)){//二进制上传; "multipart/form-data"
            //获取tomcat路径;
            // String path = request.getServletContext().getRealPath("/");
            String path="/root/apache-tomcat-8.0.52/webapps/";
            //获取系统的路径分隔符;
            String tmp = System.getProperty("file.separator")+"temp";
            //创建临时目录
            File tmpDir = new File(tmp);
            //创建磁盘文件工厂
            DiskFileItemFactory dff = new DiskFileItemFactory();
            dff.setRepository(tmpDir);//设置临时存储目录
            dff.setSizeThreshold(1024*1024*10);//设置临时文件缓存大//10M
            //创建
            ServletFileUpload sfu = new ServletFileUpload(dff);//servlet文件上传对象;
            sfu.setFileSizeMax(1024*1024*10);//设置单个文件最大空间
            sfu.setSizeMax(1024*1024*10*100);//设置所有文件最大空间大小；
            try{
                //获取所有上传组件的遍历器
                FileItemIterator fii = sfu.getItemIterator(request);
                SystemImage systemImage= SystemImageService.upload(path,fii);
                if(SystemImageService.updateSystemImage(systemImage)){
                    SystemImage list=systemImage;
                    SingleObject singleObject=new SingleObject();
                    singleObject.setObject(list);
                    if(list!=null) {
                        singleObject.setStatusObject(new StatusObject("200", "上传图片成功"));
                        String responseText= JackJsonUtils.toJson(singleObject);
                        ResponseUtils.renderJson(response,responseText);
                    }
                }else{
                    SystemImage list=systemImage;
                    SingleObject singleObject=new SingleObject();
                    singleObject.setObject(list);
                    String responseText= JackJsonUtils.toJson(singleObject);
                    ResponseUtils.renderJson(response,responseText);singleObject.setStatusObject(new StatusObject("400","上传图片失败"));
                }
            }catch(FileUploadException f){
                System.out.println("FileUploadException错误");
                f.printStackTrace();
            }catch (IOException e){
                System.out.println("IOException错误");
                e.printStackTrace();
            }
        }}
}
