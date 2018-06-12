package xin.pxyu.servlet;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.User;
import xin.pxyu.service.UserService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/*
getAllUsers 查询所有用户的信息接口
 */
public class getAllUsers extends HttpServlet {
    public getAllUsers(){}
    public void doGet(HttpServletRequest request,HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"getAllUsers接口收到"+request.getRemoteAddr()+"的请求");
try{
    request.setCharacterEncoding("UTF-8");
    response.setContentType("text/json; charset=utf-8");
    List<User> list= UserService.getAllUsers();
    ListObject listObject=new ListObject();
    listObject.setData(list);
    if(list!=null){
        listObject.setStatusObject(new StatusObject("200","查询所有用户信息成功"));
    }else{
        listObject.setStatusObject(new StatusObject("400","用户表里用户信息为空"));
    }
    String responseText= JackJsonUtils.toJson(listObject);
    ResponseUtils.renderJson(response,responseText);
}catch(UnsupportedEncodingException e){
    System.out.println("UnsupportedEncodingException");
    e.printStackTrace();
}
    }
}
