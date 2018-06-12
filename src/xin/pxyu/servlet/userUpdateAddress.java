package xin.pxyu.servlet;

import xin.pxyu.json.SingleObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.User;
import xin.pxyu.service.UserService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
更改用户地址接口
 */
public class userUpdateAddress extends HttpServlet{
    public userUpdateAddress(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"userUpdateAddress接口收到"+request.getRemoteAddr()+"的请求");
        try{
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/json; charset=utf-8");
            String location_id=request.getParameter("location_id");
            String address=request.getParameter("address");
            boolean result=UserService.updateAddress(location_id,address);
            SingleObject singleObject=new SingleObject();
            if(result){
                singleObject.setStatusObject(new StatusObject("200","更改用户地址成功"));
            }
            else{
                singleObject.setStatusObject(new StatusObject("400","更改用户地址存在，可能是location——id传错了，或者是没有该用户"));
            }
            String responseText= JackJsonUtils.toJson(singleObject);
            ResponseUtils.renderJson(response,responseText);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }

}
