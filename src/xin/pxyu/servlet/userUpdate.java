package xin.pxyu.servlet;
import net.sf.json.JSONObject;
import xin.pxyu.json.ListObject;
import xin.pxyu.json.StatusObject;
import xin.pxyu.model.User;
import xin.pxyu.service.UserService;
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
    userUpdate 更新user信息的接口
 */
public class userUpdate extends HttpServlet {
    public userUpdate() {
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+"userUpdate接口收到"+request.getRemoteAddr()+"的请求");
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
            String location_id=json.getString("location_id");
            String wechat = json.getString("wechat");
            String qq = json.getString("qq");
            String tel = json.getString("tel");
            String ip = json.getString("ip");
            String device = json.getString("device");
            User user=new User();
            user.setLocation_id(location_id);
            user.setWechat(wechat);
            user.setQq(qq);
            user.setTel(tel);
            user.setIp(ip);
            user.setDevice(device);
            boolean result = UserService.userUpdate(user);
            ListObject listObject=new ListObject();
            if(result){
                UserService.userAddCredit(location_id);
                UserService.updateRank(location_id);
                listObject.setStatusObject(new StatusObject("200","完善个人信息成功"));
                String responseText= JackJsonUtils.toJson(listObject);
                ResponseUtils.renderJson(response,responseText);
            }else{
                listObject.setStatusObject(new StatusObject("400","完善个人信息失败"));
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
