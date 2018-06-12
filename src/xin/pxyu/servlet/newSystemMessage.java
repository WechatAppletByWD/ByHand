package xin.pxyu.servlet;

import net.sf.json.JSONObject;
import xin.pxyu.dao.SystemMessageDao;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.News;
import xin.pxyu.model.SystemMessage;
import xin.pxyu.service.NewsService;
import xin.pxyu.service.SystemMessageService;
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

public class newSystemMessage  extends HttpServlet{
    public newSystemMessage(){}
    public void doPost(HttpServletRequest request, HttpServletResponse response){
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"newSystemMessage接口收到"+request.getRemoteAddr()+"的请求");
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
            String topic=json.getString("topic");
            //String content=json.getString("content");
            String receive_id=json.getString("receive_id");
            Integer toAll=json.getInt("toAll");
            SystemMessage systemMessage=new SystemMessage();
            systemMessage.setTopic(topic);
           // systemMessage.setContent(content);
            systemMessage.setToAll(toAll);
            systemMessage.setReceive_id(receive_id);
            boolean result= SystemMessageService.addSystemMessage(systemMessage);
            ListObject listObject=new ListObject();
            if(result){
                listObject.setStatusObject(new StatusObject("200","生成系统公告成功"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }else{
                listObject.setStatusObject(new StatusObject("400","生成系统公告失败"));
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
