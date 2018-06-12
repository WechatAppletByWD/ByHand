package xin.pxyu.servlet;
import net.sf.json.JSONObject;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.News;
import xin.pxyu.service.NewsService;
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

public class newsUpdate extends HttpServlet {
public newsUpdate(){}
public void doPost(HttpServletRequest request,HttpServletResponse response){
    System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"newsUpdate接口收到"+request.getRemoteAddr()+"的请求");
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
        String upload_time=json.getString("upload_time");
        String school=json.getString("school");
        String news_id=json.getString("news_id");
        News news=new News();
        news.setTitle(title);
        news.setContent(content);
        news.setUpload_time(upload_time);
        news.setSchool(school);
        news.setNews_id(news_id);
        ListObject listObject=new ListObject();
        boolean result= NewsService.updateNews(news);
        if(result){
            listObject.setStatusObject(new StatusObject("200","重新编辑新闻成功"));
            String responseText= JackJsonUtils.toJson(listObject);
            ResponseUtils.renderJson(response,responseText);
        }else{
            listObject.setStatusObject(new StatusObject("400","重新编辑新闻失败"));
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
