package xin.pxyu.servlet;

import xin.pxyu.json.ListObject;
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

public class getSystemMessage extends HttpServlet {
    public getSystemMessage(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"systemMessageByCreateTime接口收到"+request.getRemoteAddr()+"的请求");
        try{
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            int start=Integer.parseInt(request.getParameter("start"));
            int length=Integer.parseInt(request.getParameter("length"));
            List<SystemMessage> list= SystemMessageService.getSystemMessageByCreateTime(start,length);
            ListObject listObject=new ListObject();
            listObject.setData(list);
            if(list!=null){
                listObject.setStatusObject(new StatusObject("200","查询系统公告成功"));
            }
            else{
                listObject.setStatusObject(new StatusObject("400","任务表没有系统公告记录"));
            }
            String responseText= JackJsonUtils.toJson(listObject);
            ResponseUtils.renderJson(response,responseText);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
