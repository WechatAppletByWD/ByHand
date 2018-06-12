package xin.pxyu.servlet;

import xin.pxyu.json.ListObject;
import xin.pxyu.json.SingleObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.News;
import xin.pxyu.model.SystemMessage;
import xin.pxyu.service.NewsService;
import xin.pxyu.service.SystemMessageService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class systemMessageById extends HttpServlet {
    public systemMessageById(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"systemMessageById接口收到"+request.getRemoteAddr()+"的请求");
        try{
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            String sm_id=request.getParameter("sm_id");
            SystemMessage list= SystemMessageService.getSystemMessageById(sm_id);
            SingleObject singleObject=new SingleObject();
            singleObject.setObject(list);
            if(list!=null){
                singleObject.setStatusObject(new StatusObject("200","通过id查询系统公告成功"));
            }
            else{
                singleObject.setStatusObject(new StatusObject("400","系统公告表里没有该id"));
            }
            String responseText= JackJsonUtils.toJson(singleObject);
            ResponseUtils.renderJson(response,responseText);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
