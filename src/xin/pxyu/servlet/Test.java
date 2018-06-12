package xin.pxyu.servlet;
import net.sf.json.JSONArray;
import xin.pxyu.dao.UserDao;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
此servlet用来匹配任意url地址的客户端发来的请求
 */
public class Test extends HttpServlet {
    public static final long serialVersionUID=1L;
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"Test接口收到"+request.getRemoteAddr()+"的请求");
     try{
         response.setContentType("text/json; charset=utf-8");
         request.setCharacterEncoding("UTF-8");
      PrintWriter pw=response.getWriter();
        pw.println("\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "  <meta charset=\"utf-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<h1>404!</h1>\n" +
                "    <h3>The page you are looking for has not been found!</h3>\n" +
                "    <h4>The page you are looking for might have been removed, had its name changed, or unavailable. <br /></h4>\n" +
                "\t</body>\n" +
                "\n" +
                "    </html>");
     }catch(UnsupportedEncodingException u){
         System.out.println("UnsupportedEncodingException");
         u.printStackTrace();
     }catch(IOException e){
         System.out.println("IOException");
         e.printStackTrace();
     }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response)
    {
        this.doGet(request,response);
    }
}

