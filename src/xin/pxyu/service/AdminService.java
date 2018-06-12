package xin.pxyu.service;

import xin.pxyu.dao.AdminDao;
import xin.pxyu.dao.RoleDao;
import xin.pxyu.model.Admin;
import xin.pxyu.model.Role;

import java.util.List;

public class AdminService {
    //查询所有管理员
    public static List<Admin> getAllAdmin(){
        return AdminDao.getAllAdmin();
    }
    //根据id查询管理员
    public  static Admin getAdminById(String admin_id){
        return  AdminDao.getAdminById(admin_id);
    }
    //插入管理员
    public static boolean addAdmin(Admin admin){
        return  AdminDao.addAdmin(admin);
    }
    //编辑管理员
    public  static boolean updateAdmin(Admin admin){
        return AdminDao.updateAdmin(admin);
    }
    //删除管理员,通过id删除管理员
    public boolean deleteAdmin(String admin_id){
        return  AdminDao.deleteAdmin(admin_id);
    }
    //logIn管理员登陆接口
    public  static boolean logIn(String c_username,String c_password){
        return AdminDao.logIn(c_username,c_password);
    }
    //signIn管理员注册接口
    public static boolean signIn(String username,String password,String tel){
        return  AdminDao.signIn(username,password,tel);
    }
    //checkUsername
    public static boolean checkUsername(String name){
        return AdminDao.checkUsername(name);
    }
}
