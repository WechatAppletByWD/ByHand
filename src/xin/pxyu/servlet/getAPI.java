package xin.pxyu.servlet;

import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.Api;
import xin.pxyu.model.Task;
import xin.pxyu.service.ApiService;
import xin.pxyu.service.TaskService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class getAPI extends HttpServlet {
  public getAPI(){}
  public void doGet(HttpServletRequest request, HttpServletResponse response){
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"getAPI接口收到"+request.getRemoteAddr()+"的请求");
    List<Api> list=null;
    list= ApiService.getAllApi();
    ListObject listObject=new ListObject();
    listObject.setData(list);
    if(list!=null){
      listObject.setStatusObject(new StatusObject("200","获取所有Api成功"));
    }
    else{
      listObject.setStatusObject(new StatusObject("400","获取所有Api失败"));
    }
    String responseText= JackJsonUtils.toJson(listObject);
    ResponseUtils.renderJson(response,responseText);
  }
}
