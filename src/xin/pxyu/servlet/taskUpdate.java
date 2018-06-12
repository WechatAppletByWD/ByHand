package xin.pxyu.servlet;
import net.sf.json.JSONObject;
import xin.pxyu.dao.TaskDao;
import xin.pxyu.dao.UserDao;
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
taskUpdate重新编辑任务接口
 */
public class taskUpdate extends HttpServlet {
    public taskUpdate(){}
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"taskUpdate接口收到"+request.getRemoteAddr()+"的请求");
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
    Integer type=Integer.parseInt(json.getString("type"));
    String tlocation_id=json.getString("tlocation_id");
    String tavatarUrl=json.getString("tavatarUrl");
    double reward=Double.parseDouble(json.getString("reward"));
    String timeLimit=json.getString("timeLimit");
    String taddress=json.getString("taddress");
    String tcreate_id=json.getString("tcreate_id");
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
    task.setTlocation_id(tlocation_id);
    ListObject listObject=new ListObject();
    boolean result= TaskService.taskUpdate(task);
    boolean r=UserDao.updateLastAddress(tlocation_id,tcreate_id);
    if(result&&r){
        listObject.setStatusObject(new StatusObject("200","重新编辑任务成功"));
        String responseText= JackJsonUtils.toJson(listObject);
        ResponseUtils.renderJson(response,responseText);
    }else{
        listObject.setStatusObject(new StatusObject("400","重新编辑任务失败"));
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
