package xin.pxyu.servlet;
import net.sf.json.JSONObject;
import xin.pxyu.json.ListObject;
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
/*
newTask 生成任务接口
 */
public class newTask extends HttpServlet {
    public newTask(){}
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"newTask接口收到"+request.getRemoteAddr()+"的请求");
        try{
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            BufferedReader br=new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
            String s=null;
            StringBuilder sb=new StringBuilder();
            while((s=br.readLine())!=null){
                sb.append(s);
            }
            JSONObject json=JSONObject.fromObject(sb.toString());
            String title=json.getString("title");
            String content=json.getString("content");
            String tcreate_id=json.getString("tcreate_id");
            Integer type=Integer.parseInt(json.getString("type"));
            String tavatarUrl=json.getString("tavatarUrl");
            double reward=Double.parseDouble(json.getString("reward"));
            String timeLimit=json.getString("timeLimit");
            String taddress=json.getString("taddress");
            double longitude=Double.parseDouble(json.getString("longitude"));
            double latitude=Double.parseDouble(json.getString("latitude"));
            Task task=new Task();
            task.setTitle(title);
            task.setContent(content);
            task.setTcreate_id(tcreate_id);
            task.setType(type);
            task.setTavatarUrl(tavatarUrl);
            task.setReward(reward);
            task.setTimeLimit(timeLimit);
            task.setLongitude(longitude);
            task.setTaddress(taddress);
            task.setLatitude(latitude);
            boolean result=TaskService.newTask(task);
            ListObject listObject=new ListObject();
            if(result){
                listObject.setStatusObject(new StatusObject("200","生成任务成功"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }else{
                listObject.setStatusObject(new StatusObject("400","生成任务失败"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }
        }catch(UnsupportedEncodingException u){
           System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }catch(IOException e){
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}

