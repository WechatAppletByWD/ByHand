package xin.pxyu.servlet;
import net.sf.json.JSONObject;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.SingleObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.User;
import xin.pxyu.service.UserService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
userByLocationId 通过Location_id 查询用户的个人的信息
 */
public class userByLocationId extends HttpServlet {
    public userByLocationId(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"userByLocationId接口收到"+request.getRemoteAddr()+"的请求");
        try{
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/json; charset=utf-8");
           String location_id=request.getParameter("location_id");
            User list= UserService.getUserByLocationId(location_id);
            SingleObject singleObject=new SingleObject();
            singleObject.setObject(list);
            if(list!=null){
                singleObject.setStatusObject(new StatusObject("200","返回个人信息成功"));
            }
            else{
                singleObject.setStatusObject(new StatusObject("400","任务表里没有该Location_id,返回个人信息失败"));
            }
            String responseText= JackJsonUtils.toJson(singleObject);
            ResponseUtils.renderJson(response,responseText);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
