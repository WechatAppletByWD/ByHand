package xin.pxyu.dao;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;
import xin.pxyu.model.Task;
import xin.pxyu.util.ID;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static xin.pxyu.util.DBUtill.closeConn;
import static xin.pxyu.util.DBUtill.getConn;
/*
dao层用于操作数据库
 */
public class TaskDao {
    public static final long TOKEN_CHECKED_TIME=24*60*60*1000;
    // getTLocationId---》触发事件---changeStatus
    public synchronized  static void getTLocationId() {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String tlocation_id="";
        try {
            conn = getConn();
            String sql="select tlocation_id from taskinfo where status=0";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {
               tlocation_id = rs.getString("tlocation_id");
                taskAutoChangeStatus(tlocation_id);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
    }
    //获取任务创建时间的时间戳
    public synchronized  static long  getCreateTime(String tlocation_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long create_time=0;
        try {
            conn = getConn();
            String sql="select create_time from taskinfo where tlocation_id=?";
            ps=conn.prepareStatement(sql);
            ps.setString(1,tlocation_id);
            rs=ps.executeQuery();
            while (rs.next()) {
                create_time=rs.getLong("create_time");
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return create_time;
    }
    //taskChangeStatus当任务的截止时间到了，但是却没有人接收任务时，自动调整任务的状态，任务结束
    public synchronized   static boolean taskAutoChangeStatus(String tlocation_id){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer status=-1;
        long create_time=getCreateTime(tlocation_id);
        String tend_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            if(new Date().getTime()-create_time>=TOKEN_CHECKED_TIME){
                String sql = "update taskinfo set status=?,tend_time=? where tlocation_id=?";
                conn = getConn();
                ps = conn.prepareStatement(sql);
                ps.setInt(1,status);
                ps.setString(2,tend_time);
                ps.setString(3, tlocation_id);
                ps.executeUpdate();
                return true;
            }
            else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }
    //newTask生成任务接口
    public synchronized  static boolean newTask(Task task) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long create_time=new Date().getTime();
        task.setCreate_time(create_time);
        //创建任务的状态，默认为发布0
        Integer status=0;
        task.setStatus(status);
        String tlocation_id= ID.createId();
        task.setTlocation_id(tlocation_id);
        String tcreate_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        task.setTcreate_time(tcreate_time);
        String avatarurl=UserDao.getAvatarUrlByLocationId(task.getTcreate_id());
        task.setAvatarUrl(avatarurl);
        try {
            String sql = "insert into taskinfo(tlocation_id,title,content,status,tcreate_time,tcreate_id,type,tavatarUrl,reward,timeLimit,taddress,avatarUrl,longitude,latitude,create_time) value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,task.getTlocation_id());
            ps.setString(2, task.getTitle());
            ps.setString(3, task.getContent());
            ps.setInt(4, task.getStatus());
            ps.setString(5,task.getTcreate_time());
            ps.setString(6, task.getTcreate_id());
            ps.setInt(7,task.getType());
            ps.setString(8,task.getTavatarUrl());
            ps.setDouble(9,task.getReward());
            ps.setString(10,task.getTimeLimit());
            ps.setString(11,task.getTaddress());
            ps.setString(12,task.getAvatarUrl());
            ps.setDouble(13,task.getLongitude());
            ps.setDouble(14,task.getLatitude());
            ps.setLong(15,task.getCreate_time());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            UserDao.updateLastAddress(task.getTlocation_id(),task.getTcreate_id());
            closeConn(rs, st, conn, ps);
        }
        return false;
    }
    //taskUpdate重新编辑任务接口
    public synchronized  static boolean taskUpdate(Task task) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConn();
            if(task.getTcreate_id().equals(TaskDao.getTCreateId(task.getTlocation_id()))){
                //只有任务的创建人才有权限修改任务的信息
                String sql = "update taskinfo set title=?,content=?,type=?,tavatarUrl=?,reward=?,timeLimit=?,taddress=?,longitude=?,latitude=? where tlocation_id=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1,task.getTitle());
                ps.setString(2,task.getContent());
                ps.setInt(3,task.getType());
                ps.setString(4,task.getTavatarUrl());
                ps.setDouble(5,task.getReward());
                ps.setString(6,task.getTimeLimit());
                ps.setString(7,task.getTaddress());
                ps.setDouble(8,task.getLongitude());
                ps.setDouble(9,task.getLatitude());
                ps.setString(10,task.getTlocation_id());
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }
    //getTaskStatus接口,通过任务id获取到任务的状态的接口
    public synchronized  static int getTaskStatus(String tlocation_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int status=0;
        try {
            conn = getConn();
            String sql = "select status from taskinfo where tlocation_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,tlocation_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                status=rs.getInt("status");
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return status;
    }
    //getTaskInceptId接口，通过任务id获取任务接单者的id
    public synchronized  static String getTInceptId(String tlocation_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String incept_id="";
        try {
            conn = getConn();
            String sql = "select tincept_id from taskinfo where tlocation_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tlocation_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                incept_id=rs.getString("tincept_id");
            }
            return incept_id;
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return incept_id;
    }
    //getTaskEndtime接口，通过任务id获取任务的截止时间接口
    public synchronized  static String getTaskEndTime(String tlocation_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String tend_time="";
        try {
            conn = getConn();
            String sql = "select tend_time from taskinfo where tlocation_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tlocation_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                tend_time=rs.getString("tend_time");
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return tend_time;
    }

//getTCreateId接口，通过任务id获取任务的创建者id
public synchronized  static String getTCreateId(String tlocation_id) {
    Connection conn = null;
    Statement st = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String tcreate_id="";
    try {
        conn = getConn();
        String sql = "select tcreate_id from taskinfo where tlocation_id=? ";
        ps = conn.prepareStatement(sql);
        ps.setString(1, tlocation_id);
        rs = ps.executeQuery();
        while (rs.next()) {
            tcreate_id=rs.getString("tcreate_id");
        }
    } catch (SQLException e) {
        System.out.println("SQLException");
        e.printStackTrace();
    } finally {
        closeConn(rs, st, conn, ps);
    }
    return tcreate_id;
}
    //taskByTLocationId 接口,通过tlocation_id获得任务的所有信息
    public synchronized static Task getTaskByTLocationId(String tlocation_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Task task = new Task();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where tlocation_id=? ";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tlocation_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                Integer status = rs.getInt("status");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id=rs.getString("tcreate_id");
                String tincept_id=rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return task;
    }

    //taskByTCreateTime接口，通过任务创建时间查询任务列表
    public synchronized  static List<Task> getTaskByCreateTime(String tcreate_time) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
       List<Task> list=new ArrayList<Task>();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where tcreate_time=? order by tcreate_time desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tcreate_time);
            rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                String tlocation_id=rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Integer status = rs.getInt("status");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id=rs.getString("tcreate_id");
                String tincept_id=rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
                list.add(task);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }
    //taskByEndTime接口,通过任务截止时间查询任务列表
    public synchronized  static List<Task> getTaskByEndTime(String tend_time) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list=new ArrayList<Task>();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where tend_time=? order by tend_time desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tend_time);
            rs = ps.executeQuery();
            while (rs.next()) {
                Task task= new Task();
                String tlocation_id=rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Integer status = rs.getInt("status");
                String tcreate_time = rs.getString("tcreate_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id=rs.getString("tcreate_id");
                String tincept_id=rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
               list.add(task);
            }
        } catch (SQLException e) {
            System.out.println("select data error");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }

    //taskByType 接口,,通过查询任务类型，获得任务
    public synchronized  static List<Task> getTaskByType(Integer type) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list = new ArrayList<Task>();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where type=? order by tcreate_time desc ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, type);
            rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                String tlocation_id=rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Integer status = rs.getInt("status");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id=rs.getString("tcreate_id");
                String tincept_id=rs.getString("tincept_id");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
                list.add(task);
            }
        } catch (SQLException e) {
            System.out.println("select data error");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }
    //taskByStatus 接口,,通过任务状态查询任务
    public synchronized  static List<Task> getTaskByStatus(Integer status) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list = new ArrayList<Task>();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where status=? order by tcreate_time desc ";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,status);
            rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                String tlocation_id=rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id=rs.getString("tcreate_id");
                String tincept_id=rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
                list.add(task);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }


    //taskIncept以接单任务接口  接单-改变任务状态)
    public synchronized  static boolean taskIncept(String tlocation_id,String tincept_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String tincept_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try{
            String tcreate_id=TaskDao.getTCreateId(tlocation_id);
            if(tcreate_id.equals(tincept_id)||getTaskStatus(tlocation_id)!=0)
            {
                //自己不能接自己得到单
                //只能接处于发布状态的单，，其他状态的单不能接
                return false;
            }
        }catch(Exception e){
            System.out.println("任务的创建者不能接单");
            e.printStackTrace();
        }
        try {
            String sql = "update taskinfo set status=1,tincept_time=?,tincept_id=? where tlocation_id=?";
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,tincept_time);
            ps.setString(2,tincept_id);
            ps.setString(3,tlocation_id);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("SQLException");
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }
    //taskAchieve 创建人创建的任务已经成功被接收人完成
    public synchronized  static boolean taskAchieve(String tlocation_id,String location_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String tachieve_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            String tincept_id=TaskDao.getTInceptId(tlocation_id);
            if(tincept_id.equals(location_id)){
                String sql = "update taskinfo set status=3,tachieve_time=? where tlocation_id=? and tincept_id=?";
                conn = getConn();
                ps = conn.prepareStatement(sql);
                ps.setString(1,tachieve_time);
                ps.setString(2, tlocation_id);
                ps.setString(3,location_id);
                ps.executeUpdate();
                return true;
            }
           else{
                return false;
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }

    //taskInceptCancel接单的用户想要放弃该任务的接单，该任务重新处于被发布状态,,任务取消
public synchronized  static boolean taskInceptCancel(String tlocation_id,String location_id) {
    Connection conn = null;
    Statement st = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String tincept_id=TaskDao.getTInceptId(tlocation_id);
    String tincept_time=null;
    System.out.println(tincept_id);
    try {
        if(location_id.equals(tincept_id)){
            tincept_id="";
            String sql = "update taskinfo set status=0,tincept_time=?,tincept_id=? where tlocation_id=?";
            conn = getConn();
            ps = conn.prepareStatement(sql);
            ps.setString(1,tincept_time);
            ps.setString(2,tincept_id);
            ps.setString(3, tlocation_id);
            ps.executeUpdate();
            return true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        System.out.println("SQLException");
    } finally {
        closeConn(rs, st, conn, ps);
    }
    return false;
}
    //taskCancel 任务撤销接口
    public synchronized  static boolean taskCancel(String tlocation_id,String location_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int status=TaskDao.getTaskStatus(tlocation_id);
        String tcancel_time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        try {
            String tcreate_id=TaskDao.getTCreateId(tlocation_id);
            if(tcreate_id.equals(location_id)&&status==0){
                //只有任务的创建人有资个取消任务，，当且仅当该任务处于发布状态是，，否则无法取消任务
                String sql = "update taskinfo set status=2,tcancel_time=? where tlocation_id=?";
                conn = getConn();
                ps = conn.prepareStatement(sql);
                ps.setString(1,tcancel_time);
                ps.setString(2, tlocation_id);
                ps.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("sql error");
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return false;
    }

    //taskByTCreateId 接口,通过任务创建者查询任务
    public synchronized  static List<Task> getTaskByTCreateId(String tcreate_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list = new ArrayList<Task>();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where tcreate_id=? order by tcreate_time desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1,tcreate_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                String tlocation_id=rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Integer status = rs.getInt("status");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tincept_id=rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
                list.add(task);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }
    //getTaddress接口,通过任务id查询任务地址
    public synchronized  static String getTaskAddress(String tlocation_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String taddress="";
        try {
            conn = getConn();
            String sql = "select taddress from taskinfo where tlocation_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,tlocation_id);
            rs = ps.executeQuery();
            while (rs.next()) {
               taddress=rs.getString("taddress");
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return taddress;
    }
//getTaskByLocationId,,通过用户id查询这个人创建的任务,接收的任务
public synchronized  static List<Task> getTaskByLocationId(String location_id) {
    Connection conn = null;
    Statement st = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Task> list = new ArrayList<Task>();
    try {
        conn = getConn();
        String sql = "select * from taskinfo where tcreate_id=? order by tcreate_time desc";
        ps = conn.prepareStatement(sql);
        ps.setString(1,location_id);
        rs = ps.executeQuery();
        while (rs.next()) {
            Task task = new Task();
            String tlocation_id=rs.getString("tlocation_id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            Integer status = rs.getInt("status");
            String tcreate_time = rs.getString("tcreate_time");
            String tend_time = rs.getString("tend_time");
            String tcancel_time = rs.getString("tcancel_time");
            String tcreate_id=rs.getString("tcreate_id");
            String tincept_id=rs.getString("tincept_id");
            Integer type = rs.getInt("type");
            String tavatarUrl=rs.getString("tavatarUrl");
            String avatarUrl=rs.getString("avatarUrl");
            double reward=rs.getDouble("reward");
            String timeLimit=rs.getString("timeLimit");
            String taddress=rs.getString("taddress");
            String tincept_time=rs.getString("tincept_time");
            String tachieve_time=rs.getString("tachieve_time");
            double longitude=rs.getDouble("longitude");
            double latitude=rs.getDouble("latitude");
            double distance=rs.getDouble("distance");
            int msg=0;
            task.setTlocation_id(tlocation_id);
            task.setTitle(title);
            task.setContent(content);
            task.setStatus(status);
            task.setTcreate_time(tcreate_time);
            task.setTend_time(tend_time);
            task.setTcancel_time(tcancel_time);
            task.setTcreate_id(tcreate_id);
            task.setTincept_id(tincept_id);
            task.setType(type);
            task.setTavatarUrl(tavatarUrl);
            task.setAvatarUrl(avatarUrl);
            task.setReward(reward);
            task.setTimeLimit(timeLimit);
            task.setMsg(msg);
            task.setTaddress(taddress);
            task.setTincept_time(tincept_time);
            task.setTachieve_time(tachieve_time);
            task.setLongitude(longitude);
            task.setLatitude(latitude);
            task.setDistance(distance);
            list.add(task);
        }
    } catch (SQLException e) {
        System.out.println("SQLException");
        e.printStackTrace();
    } finally {
        closeConn(rs, st, conn, ps);
    }
    try {
        conn = getConn();
        String sql = "select * from taskinfo where tincept_id=? order by tcreate_time desc";
        ps = conn.prepareStatement(sql);
        ps.setString(1,location_id);
        rs = ps.executeQuery();
        while (rs.next()) {
            Task task = new Task();
            String tlocation_id=rs.getString("tlocation_id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            Integer status = rs.getInt("status");
            String tcreate_time = rs.getString("tcreate_time");
            String tend_time = rs.getString("tend_time");
            String tcancel_time = rs.getString("tcancel_time");
            String tcreate_id=rs.getString("tcreate_id");
            String tincept_id=rs.getString("tincept_id");
            Integer type = rs.getInt("type");
            String tavatarUrl=rs.getString("tavatarUrl");
            String avatarUrl=rs.getString("avatarUrl");
            double reward=rs.getDouble("reward");
            String timeLimit=rs.getString("timeLimit");
            String taddress=rs.getString("taddress");
            String tincept_time=rs.getString("tincept_time");
            String tachieve_time=rs.getString("tachieve_time");
            double longitude=rs.getDouble("longitude");
            double latitude=rs.getDouble("latitude");
            double distance=rs.getDouble("distance");
            int msg=1;
            task.setTlocation_id(tlocation_id);
            task.setTitle(title);
            task.setContent(content);
            task.setStatus(status);
            task.setTcreate_time(tcreate_time);
            task.setTend_time(tend_time);
            task.setTcancel_time(tcancel_time);
            task.setTcreate_id(tcreate_id);
            task.setTincept_id(tincept_id);
            task.setType(type);
            task.setTavatarUrl(tavatarUrl);
            task.setAvatarUrl(avatarUrl);
            task.setReward(reward);
            task.setTimeLimit(timeLimit);
            task.setMsg(msg);
            task.setTaddress(taddress);
            task.setTincept_time(tincept_time);
            task.setTachieve_time(tachieve_time);
            task.setLongitude(longitude);
            task.setLatitude(latitude);
            task.setDistance(distance);
            list.add(task);
        }

    } catch (SQLException e) {
        System.out.println("SQLException");
        e.printStackTrace();
    } finally {
        closeConn(rs, st, conn, ps);
    }
    return list;
}
    //taskByTInceptId接口,通过任务接收人查询任务
    public synchronized  static List<Task> getTaskByTInceptId(String tincept_id) {
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list = new ArrayList<Task>();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where tincept_id=? order by tcreate_time desc ";
            ps = conn.prepareStatement(sql);
            ps.setString(1,tincept_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                String tlocation_id=rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                Integer status = rs.getInt("status");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id=rs.getString("tcreate_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
                list.add(task);
            }

        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }
    //getDistance,
    public synchronized  static boolean getDistance(double u_longitude,double u_latitude){
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql="select longitude,latitude,tlocation_id from taskinfo";
        conn=getConn();
        try{
          ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                String tlocation_id=rs.getString("tlocation_id");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                //比较用户距离和任务距离，计算出二者的经纬度差距
                double distance=Math.pow(u_latitude-latitude,2)+Math.pow(u_longitude-longitude,2);
             setDistance(distance,tlocation_id);
            }
            return true;
            }
        catch(SQLException e){
            System.out.println("SQLException异常");
            e.printStackTrace();
        }
return false;
    }
     //setDistance
     public synchronized  static boolean setDistance(double distance,String tlocation_id){
         Connection conn = null;
         Statement st = null;
         PreparedStatement ps = null;
         ResultSet rs = null;
         String sql="update taskinfo set distance=? where tlocation_id=?";
         try{
             conn=getConn();
             ps=conn.prepareStatement(sql);
                 ps.setDouble(1,distance);
                 ps.setString(2,tlocation_id);
                 ps.executeUpdate();
                 return true;
             }
         catch(SQLException e){
             System.out.println("SQLException");
             e.printStackTrace();
         }
         return false;
     }
//多条件筛选任务，找出最优任务，为用户显示
public synchronized  static List<Task> getConditionalTasks(int start,int length,Integer status,double u_longitude,double u_latitude) {
    CallableStatement cs=null;
    Connection conn = null;
    Statement st = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Task> list = new ArrayList<Task>();
    int end=start+length;
    try {
        conn = getConn();
    /*    String sql = "select * from taskinfo where status=? order by tcreate_time desc,distance asc limit 2";
        st=conn.createStatement();
        rs=st.executeQuery(sql);*/
    String sql="call getTask(?,?,?)";
        cs = conn.prepareCall(sql);
       cs.setInt(1,start);
       cs.setInt(2,end);
       cs.setInt(3,status);
      cs.execute();
        rs =cs.getResultSet();
        while (rs.next()) {
            Task task = new Task();
            String tlocation_id = rs.getString("tlocation_id");
            String title = rs.getString("title");
            String content = rs.getString("content");
            String tcreate_time = rs.getString("tcreate_time");
            String tend_time = rs.getString("tend_time");
            String tcancel_time = rs.getString("tcancel_time");
            String tcreate_id = rs.getString("tcreate_id");
            String tincept_id = rs.getString("tincept_id");
            Integer type = rs.getInt("type");
            String tavatarUrl=rs.getString("tavatarUrl");
            String avatarUrl=rs.getString("avatarUrl");
            double reward=rs.getDouble("reward");
            String timeLimit=rs.getString("timeLimit");
            String taddress=rs.getString("taddress");
            String tincept_time=rs.getString("tincept_time");
            String tachieve_time=rs.getString("tachieve_time");
            double longitude=rs.getDouble("longitude");
            double latitude=rs.getDouble("latitude");
            double distance=rs.getDouble("distance");
            task.setTlocation_id(tlocation_id);
            task.setTitle(title);
            task.setContent(content);
            task.setStatus(status);
            task.setTcreate_time(tcreate_time);
            task.setTend_time(tend_time);
            task.setTcancel_time(tcancel_time);
            task.setTcreate_id(tcreate_id);
            task.setTincept_id(tincept_id);
            task.setType(type);
            task.setTavatarUrl(tavatarUrl);
            task.setAvatarUrl(avatarUrl);
            task.setReward(reward);
            task.setTimeLimit(timeLimit);
            task.setTaddress(taddress);
            task.setTincept_time(tincept_time);
            task.setTachieve_time(tachieve_time);
            task.setLongitude(longitude);
            task.setLatitude(latitude);
            task.setDistance(distance);
            list.add(task);
        }
    } catch (SQLException e) {
        System.out.println("SQLException");
        e.printStackTrace();
    } finally {
        closeConn(rs, st, conn, ps);
    }
    return list;
}
    //taskByLidUid 接口,,通过任务id用户id查询任务
    public synchronized  static Task getTaskByTidUid(String tlocation_id,String tcreate_id) { ;
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list = new ArrayList<Task>();
        Task task = new Task();
        try {
            conn = getConn();
            String sql = "select * from taskinfo where tlocation_id=? and tcreate_id=? order by tcreate_time desc";
            ps = conn.prepareStatement(sql);
            ps.setString(1, tlocation_id);
            ps.setString(2, tcreate_id);
            rs = ps.executeQuery();
            while (rs.next()) {
                String title = rs.getString("title");
                String content = rs.getString("content");
                Integer status = rs.getInt("status");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tincept_id=rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
            }
        } catch (SQLException e) {
            System.out.println("select data error");
            e.printStackTrace();
            //打印异常的跟踪站信息
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return task;
    }

    //显示所有的任务 getAllTasks
    public synchronized  static List<Task> getAllTasks() {
        CallableStatement cs=null;
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list = new ArrayList<Task>();
        try {
            conn = getConn();
    /*    String sql = "select * from taskinfo where status=? order by tcreate_time desc,distance asc limit 2";
        st=conn.createStatement();
        rs=st.executeQuery(sql);*/
            String sql="select * from taskinfo order by tcreate_time desc";
            ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            while (rs.next()) {
                Task task = new Task();
                String tlocation_id = rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id = rs.getString("tcreate_id");
                String tincept_id = rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                Integer status=rs.getInt("status");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
                list.add(task);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }
    //多条件筛选任务，
    public synchronized  static List<Task> getAllTask(int start,int length) {
        CallableStatement cs=null;
        Connection conn = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Task> list = new ArrayList<Task>();
        int end=start+length;
        try {
            conn = getConn();
    /*    String sql = "select * from taskinfo where status=? order by tcreate_time desc,distance asc limit 2";
        st=conn.createStatement();
        rs=st.executeQuery(sql);*/
            String sql="call getTasks(?,?)";
            cs = conn.prepareCall(sql);
            cs.setInt(1,start);
            cs.setInt(2,end);
            cs.execute();
            rs =cs.getResultSet();
            while (rs.next()) {
                Task task = new Task();
                String tlocation_id = rs.getString("tlocation_id");
                String title = rs.getString("title");
                String content = rs.getString("content");
                String tcreate_time = rs.getString("tcreate_time");
                String tend_time = rs.getString("tend_time");
                String tcancel_time = rs.getString("tcancel_time");
                String tcreate_id = rs.getString("tcreate_id");
                String tincept_id = rs.getString("tincept_id");
                Integer type = rs.getInt("type");
                String tavatarUrl=rs.getString("tavatarUrl");
                String avatarUrl=rs.getString("avatarUrl");
                double reward=rs.getDouble("reward");
                String timeLimit=rs.getString("timeLimit");
                String taddress=rs.getString("taddress");
                String tincept_time=rs.getString("tincept_time");
                String tachieve_time=rs.getString("tachieve_time");
                double longitude=rs.getDouble("longitude");
                double latitude=rs.getDouble("latitude");
                double distance=rs.getDouble("distance");
                Integer status=rs.getInt("status");
                task.setTlocation_id(tlocation_id);
                task.setTitle(title);
                task.setContent(content);
                task.setStatus(status);
                task.setTcreate_time(tcreate_time);
                task.setTend_time(tend_time);
                task.setTcancel_time(tcancel_time);
                task.setTcreate_id(tcreate_id);
                task.setTincept_id(tincept_id);
                task.setType(type);
                task.setTavatarUrl(tavatarUrl);
                task.setAvatarUrl(avatarUrl);
                task.setReward(reward);
                task.setTimeLimit(timeLimit);
                task.setTaddress(taddress);
                task.setTincept_time(tincept_time);
                task.setTachieve_time(tachieve_time);
                task.setLongitude(longitude);
                task.setLatitude(latitude);
                task.setDistance(distance);
                list.add(task);
            }
        } catch (SQLException e) {
            System.out.println("SQLException");
            e.printStackTrace();
        } finally {
            closeConn(rs, st, conn, ps);
        }
        return list;
    }

}
