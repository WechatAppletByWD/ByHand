package xin.pxyu.service;

import xin.pxyu.dao.TaskDao;
import xin.pxyu.dao.UserDao;
import xin.pxyu.model.Task;
import xin.pxyu.model.User;

import java.util.List;

public class TaskService {
    //getTaskByType
    public static List<Task> getTaskByType(Integer type){
        return TaskDao.getTaskByType(type);
    }
    //getTaskByStatus
    public static List<Task> getTaskByStatus(Integer status){
        return TaskDao.getTaskByStatus(status);
    }
    //getTaskByTCreateTime
    public static List<Task> getTaskByTCreateTime(String tcreate_time){
        return TaskDao.getTaskByCreateTime(tcreate_time);
    }
    //taskByTEndTime
    public static List<Task> getTaskByTEndTime(String tend_time){
        return TaskDao.getTaskByEndTime(tend_time);
    }
    //newTask
    public static boolean newTask(Task task) {
        return TaskDao.newTask(task);
    }
    //taskIncept
    public static boolean taskIncept(String tlocation_id,String tincept_id){
        return TaskDao.taskIncept(tlocation_id,tincept_id);
    }
    //taskCancel
    public static boolean taskCancel(String tlocation_id,String location_id){
        return TaskDao.taskCancel(tlocation_id,location_id);
    }
    //taskUpdate
    public static boolean taskUpdate(Task task){
        return TaskDao.taskUpdate(task);
    }
    //taskByTCreateId
    public static List<Task> getTaskByTCreateId(String tcreate_id){ return TaskDao.getTaskByTCreateId(tcreate_id); }
    //taskByTInceptId
    public static List<Task> getTaskByTInceptId(String tincept_id){ return TaskDao.getTaskByTInceptId(tincept_id); }
    //getConditionalTasks
    public static List<Task> getConditionalTasks(int start,int length,Integer status,double u_longitude,double u_latitude){
        return TaskDao.getConditionalTasks(start,length,status,u_longitude,u_latitude);
    }
    //taskByTLocationId
    public static  Task getTaskByTLocationId(String tlocation_id){ return TaskDao.getTaskByTLocationId(tlocation_id); }
    //taskByTLidUid
    public static Task getTaskByTLidUid(String tlocation_id,String tcreate_id){ return TaskDao.getTaskByTidUid(tlocation_id,tcreate_id); }
   //taskInceptCancel
    public static boolean taskInceptCancel(String tlocation_id,String location_id){
        return TaskDao.taskInceptCancel(tlocation_id,location_id);
    }
    //taskAchieve
    public static boolean taskAchieve(String tlocation_id,String location_id){
        return TaskDao.taskAchieve(tlocation_id,location_id);
    }
    //getTInceptId
    public static String getTInceptId(String tlocation_id) {
        return TaskDao.getTInceptId(tlocation_id);
    }
    //getTaskByLocationId
    public static List<Task> getTaskByLocationId(String location_id) {
        return TaskDao.getTaskByLocationId(location_id);
    }
    //getTaskEndTime
    public static String getTaskEndTime(String tlocation_id){
        return TaskDao.getTaskEndTime(tlocation_id);
    }
    //taskAutoChangeStatus
    public  static  boolean taskAutoChangeStatus(String tlocation_id){
        return TaskDao.taskAutoChangeStatus(tlocation_id);
    }
    //获得经纬度，计算距离
    public static boolean getDistance(double u_longitude,double u_latitude){
        return TaskDao.getDistance(u_longitude,u_latitude);
    }
    //设置距离
    public static boolean setDistance(double distance,String tlocation_id){
        return TaskDao.setDistance(distance,tlocation_id);
    }
    //获取所有的任务
    public   static List<Task> getAllTasks(){
        return  TaskDao.getAllTasks();
    }
    ////有条件获取所有的任务
    public synchronized  static List<Task> getAllTask(int start,int length){
        return TaskDao.getAllTask(start,length);
    }

    }
