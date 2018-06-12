package xin.pxyu.servlet;
import net.sf.json.JSONObject;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.User;
import xin.pxyu.service.TaskService;
import xin.pxyu.service.UserService;
import xin.pxyu.util.JackJsonUtils;
import xin.pxyu.util.ResponseUtils;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.fasterxml.jackson.databind.util.ISO8601Utils.format;

/*
newUser生成新用户接口
 */
public class newUser extends HttpServlet {
    public newUser() {}
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"newUser接口收到"+request.getRemoteAddr()+"的请求");
        try {
            response.setContentType("text/json; charset=utf-8");
            request.setCharacterEncoding("UTF-8");
            BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "utf-8"));
            String s = null;
            StringBuilder sb = new StringBuilder();
            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            JSONObject json = JSONObject.fromObject(sb.toString());
            String location_id = json.getString("location_id");
            String username = json.getString("username");
            Integer sex = Integer.parseInt(json.getString("sex"));
            String address = json.getString("address");
            String ip = json.getString("ip");
            String device = json.getString("device");
            String avatarUrl=json.getString("avatarUrl");
            User user=new User();
            user.setLocation_id(location_id);
            user.setUsername(username);
            user.setSex(sex);
            user.setAddress(address);
            user.setIp(ip);
            user.setDevice(device);
            user.setAvatarUrl(avatarUrl);
            boolean result = UserService.newUser(user);
            ListObject listObject=new ListObject();
            if(result){
                listObject.setStatusObject(new StatusObject("200","用户注册成功"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }else{
                listObject.setStatusObject(new StatusObject("400","用户注册失败"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }
        } catch(UnsupportedEncodingException u){
            System.out.println("UnsupportedEncodingException");
            u.printStackTrace();
        }catch(IOException e){
            System.out.println("IOException");
            e.printStackTrace();
        }
    }
}

