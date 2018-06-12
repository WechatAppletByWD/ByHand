package xin.pxyu.servlet;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.Task;
import xin.pxyu.model.User;
import xin.pxyu.service.TaskService;
import xin.pxyu.service.UserService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/*
getAllTasks 用于获取所有任务的接口
 */
public class getConditionalTasks extends HttpServlet {
    public getConditionalTasks(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"getConditionalTasks接口收到"+request.getRemoteAddr()+"的请求");
        int start=Integer.parseInt(request.getParameter("start"));
        int length=Integer.parseInt(request.getParameter("length"));
        Integer c_status=Integer.parseInt(request.getParameter("status"));
        double longitude=Double.parseDouble(request.getParameter("longitude"));
        double latitude=Double.parseDouble(request.getParameter("latitude"));
        TaskService.getDistance(longitude,latitude);
        List<Task> list= TaskService.getConditionalTasks(start,length,c_status,longitude,latitude);
        ListObject listObject=new ListObject();
        listObject.setData(list);
        if(list!=null){
            listObject.setStatusObject(new StatusObject("200","获取任务表里的任务成功"));
        }
        else{
            listObject.setStatusObject(new StatusObject("400","任务表里没有任务记录"));
        }
        String responseText= JackJsonUtils.toJson(listObject);
        ResponseUtils.renderJson(response,responseText);
    }
}
