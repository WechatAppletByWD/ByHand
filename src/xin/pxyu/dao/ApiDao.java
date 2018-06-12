package xin.pxyu.dao;

import xin.pxyu.model.Api;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static xin.pxyu.util.DBUtill.getConn;

public class ApiDao {
    private static Map<String, String> api= new HashMap<String, String>();
    public ApiDao() {
      api.put("adminLogIn","/adminLogIn");
        api.put("adminSignIn","/adminSignIn");
        api.put("fileUpload","/fileUpload");
        api.put("fileUploads","/fileUploads");
        api.put("getAllApi","/getAllApi");
        api.put("getAllTasks","/getAllTasks");
        api.put("getAllUsers","/getAllUsers");
        api.put("getAPI","/getAPI");
        api.put("getConditionalTasks","/getConditionalTasks");
        api.put("getSystemMessage","/getSystemMessage");
        api.put("getTaskByLocationId","/getTaskByLocationId");
        api.put("newNews","/newNews");
        api.put("newsBySchool","/newsBySchool");
        api.put("newsByUploadTime","/newsByUploadTime");
        api.put("newSuggestion","/newSuggestion");
        api.put("newsUpdate","/newsUpdate");
        api.put("newSystemMessage","/newSystemMessage");
        api.put("newTask","/newTask");
        api.put("newUser","/newUser");
        api.put("suggestionById","/suggestionById");
        api.put("suggestionUpdate","/suggestionUpdate");
        api.put("systemMessageById","/systemMessageById");
        api.put("systemMessageUpdate","/systemMessageUpdate");
        api.put("taskAchieve","/taskAchieve");
        api.put("taskByStatus","/taskByStatus");
        api.put("taskByTCreateId","/taskByTCreateId");
        api.put("taskByTCreateTime","/taskByTCreateTime");
        api.put("taskByTEndTime","/taskByTEndTime");
        api.put("taskByTInceptId","/taskByTInceptId");
        api.put("taskByTLocationId","/taskByTLocationId");
        api.put("taskByType","/taskByType");
        api.put("taskCancel","/taskCancel");
        api.put("taskIncept","/taskIncept");
        api.put("taskInceptCancel","/taskInceptCancel");
        api.put("taskUpdate","/taskUpdate");
        api.put("Test","/Test");
        api.put("userByLocationId","/userByLocationId");
        api.put("userExits","/userExits");
        api.put("userUpdate","/userUpdate");
        api.put("userUpdateAddress","/userUpdateAddress");
    }
    public String toString(){
        StringBuilder s=new StringBuilder("{");
        String str;
        for(String key:api.keySet()){
            str="'"+key+"':"+"'"+api.get(key)+"'"+",";
            s.append(str);
        }
        s.append("}");
        return s.toString();
    }
   /* public static void main(String[] args){
        ApiDao apiDao=new ApiDao();
        System.out.println(apiDao.toString());
    }*/
    public synchronized static List<Api> getAllApi(){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        conn= getConn();
        List<Api> list=new ArrayList<Api>();
        String sql="select * from api";
        try{
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                Api api=new Api();
                int id=rs.getInt("id");
                String name=rs.getString("name");
                String url=rs.getString("url");
                String create_time=rs.getString("create_time");
                String update_time=rs.getString("update_time");
                api.setId(id);
                api.setName(name);
                api.setUrl(url);
                api.setCreate_time(create_time);
                api.setUpdate_time(update_time);
                list.add(api);
            }
        }catch (SQLException e){
            System.out.println("SQLException");
            e.printStackTrace();
        }
        return list;
    }
}
