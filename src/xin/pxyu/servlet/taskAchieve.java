package xin.pxyu.servlet;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.service.UserService;
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
taskAchieve 表示该任务确定已经成功结束，被接收人完成该任务，且任务创建人创建的任务被成功完成
 */
public class taskAchieve extends HttpServlet {
    public taskAchieve(){}
    public void doGet(HttpServletRequest request,HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"taskAchieve接口收到"+request.getRemoteAddr()+"的请求");
        try{
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            String tlocation_id=request.getParameter("tlocation_id");
            String location_id=request.getParameter("location_id");
            String tincept_id=TaskService.getTInceptId(tlocation_id);
            //任务每成功一单，任务创建人和最后的接收人都会增加积分，且同时更新级别
            UserService.userAddCredit(location_id);
            UserService.userAddCredit(tincept_id);
            UserService.updateRank(location_id);
            UserService.updateRank(tincept_id);
            boolean result= TaskService.taskAchieve(tlocation_id,location_id);
            ListObject listObject=new ListObject();
            if(result){
                listObject.setStatusObject(new StatusObject("200","该任务成功完成"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }else{
                listObject.setStatusObject(new StatusObject("400","该任务完成失败，因为任务表里没有tlocation_id与tincept_id对应不上,,可能因为接单人id传错"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }
        }catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }
    }
}
