package xin.pxyu.servlet;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.SingleObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.Task;
import xin.pxyu.service.TaskService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/*
taskByTLocationId
*/
public class taskByTLocationId extends HttpServlet {
    public taskByTLocationId(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"taskByTLocationId接口收到"+request.getRemoteAddr()+"的请求");
        try{
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/json; charset=utf-8");
            String tlocation_id=request.getParameter("tlocation_id");
            Task list= TaskService.getTaskByTLocationId(tlocation_id);
            SingleObject singleObject=new SingleObject();
            singleObject.setObject(list);
            if(list!=null){
                singleObject.setStatusObject(new StatusObject("200","通过tlocation_id查询任务成功"));
            }
            else{
                singleObject.setStatusObject(new StatusObject("400","任务表里没有该tlocation_id"));
            }
            String responseText= JackJsonUtils.toJson(singleObject);
            ResponseUtils.renderJson(response,responseText);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
