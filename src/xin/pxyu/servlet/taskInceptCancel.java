package xin.pxyu.servlet;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
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

/*
taskInceptCancel  用户接单之后想放弃该任务的接收，该任务应修改状态，切可以被其他用户接收
 */
public class taskInceptCancel extends HttpServlet {
    public taskInceptCancel(){}
public void doGet(HttpServletRequest request, HttpServletResponse response){
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"taskInceptCancel接口收到"+request.getRemoteAddr()+"的请求");
    try{
        response.setContentType("text/json; charset=utf-8");
        request.setCharacterEncoding("UTF-8");
        String tlocation_id=request.getParameter("tlocation_id");
        String location_id=request.getParameter("location_id");
        boolean result= TaskService.taskInceptCancel(tlocation_id,location_id);
        ListObject listObject=new ListObject();
        if(result){
            listObject.setStatusObject(new StatusObject("200","该任务接单取消成功"));
            String responseText= JackJsonUtils.toJson(listObject);
            ResponseUtils.renderJson(response,responseText);
        }else{
            listObject.setStatusObject(new StatusObject("400","该任务接单取消失败，因为任务表里没有该tlocation_id,,,或者这个人不是任务的接单人"));
            String responseText= JackJsonUtils.toJson(listObject);
            ResponseUtils.renderJson(response,responseText);
        }
    }catch(UnsupportedEncodingException u){
        System.out.println("UnsupportedEncodingException");
        u.printStackTrace();
    }
}
}
