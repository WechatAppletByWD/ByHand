package xin.pxyu.servlet;

import xin.pxyu.json.ListObject;
import xin.pxyu.json.SingleObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.News;
import xin.pxyu.model.Suggestion;
import xin.pxyu.model.SystemMessage;
import xin.pxyu.service.NewsService;
import xin.pxyu.service.SuggestionService;
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

public class suggestionById extends HttpServlet {
    public suggestionById(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"suggestionById接口收到"+request.getRemoteAddr()+"的请求");
        try{
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
           String s_id=request.getParameter("s_id");
            Suggestion list= SuggestionService.getSuggestionById(s_id);
            SingleObject singleObject=new SingleObject();
            singleObject.setObject(list);
            if(list!=null){
                singleObject.setStatusObject(new StatusObject("200","通过id查询建议成功"));
            }
            else{
                singleObject.setStatusObject(new StatusObject("400","建议表里没有该id"));
            }
            String responseText= JackJsonUtils.toJson(singleObject);
            ResponseUtils.renderJson(response,responseText);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
