package xin.pxyu.servlet;

import xin.pxyu.json.ListObject;
import xin.pxyu.json.SingleObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.Api;
import xin.pxyu.service.ApiService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class getAllApi extends HttpServlet {

    public getAllApi(){}
    public void doGet(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"getAllApi接口收到"+request.getRemoteAddr()+"的请求");
     /*   ListObject listObject=new ListObject();

        listObject.setStatusObject(new StatusObject("200","获取所有api成功"));*/
        SingleObject singleObject=new SingleObject();
        singleObject.setObject("{'getAllTasks':'/getAllTasks','newsBySchool':'/newsBySchool','newSystemMessage':'/newSystemMessage','fileUpload':'/fileUpload','adminLogIn':'/adminLogIn','suggestionUpdate':'/suggestionUpdate','taskByTEndTime':'/taskByTEndTime','taskUpdate':'/taskUpdate','getTaskByLocationId':'/getTaskByLocationId','taskIncept':'/taskIncept','Test':'/Test','taskByTCreateTime':'/taskByTCreateTime','userExits':'/userExits','getSystemMessage':'/getSystemMessage','fileUploads':'/fileUploads','systemMessageUpdate':'/systemMessageUpdate','taskByType':'/taskByType','taskByStatus':'/taskByStatus','taskInceptCancel':'/taskInceptCancel','userByLocationId':'/userByLocationId','getAllUsers':'/getAllUsers','getConditionalTasks':'/getConditionalTasks','taskByTCreateId':'/taskByTCreateId','newSuggestion':'/newSuggestion','userUpdate':'/userUpdate','taskCancel':'/taskCancel','userUpdateAddress':'/userUpdateAddress','newNews':'/newNews','taskByTLocationId':'/taskByTLocationId','getAPI':'/getAPI','taskAchieve':'/taskAchieve','getAllApi':'/getAllApi','adminSignIn':'/adminSignIn','newsUpdate':'/newsUpdate','suggestionById':'/suggestionById','newsByUploadTime':'/newsByUploadTime','systemMessageById':'/systemMessageById','newUser':'/newUser','taskByTInceptId':'/taskByTInceptId','newTask':'/newTask'}");
        singleObject.setStatusObject(new StatusObject("200","获取所有api成功"));
        String responseText= JackJsonUtils.toJson(singleObject);
        ResponseUtils.renderJson(response,responseText);
    }
}
