package xin.pxyu.servlet;
import net.sf.json.JSONObject;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.service.TaskService;
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

/*
taskCancel 取消任务接口
 */
public class taskCancel extends HttpServlet {
    public taskCancel(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"taskCancel接口收到"+request.getRemoteAddr()+"的请求");
        try{
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            String tlocation_id=request.getParameter("tlocation_id");
            String location_id=request.getParameter("location_id");
            boolean result= TaskService.taskCancel(tlocation_id,location_id);
            ListObject listObject=new ListObject();
            if(result){
                listObject.setStatusObject(new StatusObject("200","取消任务成功"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }else{
                listObject.setStatusObject(new StatusObject("400","撤销任务失败，因为任务表里没有该tlocation_id"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
