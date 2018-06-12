package xin.pxyu.servlet;
import net.sf.json.JSONObject;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/*
 taskByTCreateTime通过任务创建时间查询任务信息接口
 */
public class taskByTCreateTime extends HttpServlet {
    public taskByTCreateTime(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"taskByTCreateTime接口收到"+request.getRemoteAddr()+"的请求");
        try{
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/json; charset=utf-8");
            String tcreate_time=request.getParameter("tcreate_time");
            List<Task> list= TaskService.getTaskByTCreateTime(tcreate_time);
            ListObject listObject=new ListObject();
            listObject.setData(list);
            if(list!=null){
                listObject.setStatusObject(new StatusObject("200","通过tcreate_time查询任务成功"));
            }
            else{
                listObject.setStatusObject(new StatusObject("400","任务表里没有该创建时间(tcreate_time)的记录"));
            }
            String responseText= JackJsonUtils.toJson(listObject);
            ResponseUtils.renderJson(response,responseText);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
