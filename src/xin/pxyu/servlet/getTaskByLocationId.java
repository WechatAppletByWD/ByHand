package xin.pxyu.servlet;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.Task;
import xin.pxyu.service.TaskService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/*
getTaskByLocationId  通过location_id获得该用户接过的单，和发布的单
 */
public class getTaskByLocationId extends HttpServlet {
    public getTaskByLocationId(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"taskByLocationId接口收到"+request.getRemoteAddr()+"的请求");
        try{
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            String location_id=request.getParameter("location_id");
            List<Task> listC= TaskService.getTaskByLocationId(location_id);
            ListObject listObjectC=new ListObject();
            listObjectC.setData(listC); if(listC!=null){
                listObjectC.setStatusObject(new StatusObject("200","查询该用户创建接收的任务列表成功"));
            }
            else{
                listObjectC.setStatusObject(new StatusObject("400","该用户未创建或或接收任何任务，或者该用户不存在"));
            }
            String responseTextC= JackJsonUtils.toJson(listObjectC);
            ResponseUtils.renderJson(response,responseTextC);
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }


}
