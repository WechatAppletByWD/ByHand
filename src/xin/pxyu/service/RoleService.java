package xin.pxyu.service;

import xin.pxyu.dao.RoleDao;
import xin.pxyu.model.Role;

import java.util.List;

public class RoleService {
    //查询所有角色
    public static List<Role> getAllRole(){
        return RoleDao.getAllRole();
    }
    //根据id查询角色
    public  static Role getRoleById(String role_id){
        return  RoleDao.getRoleById(role_id);
    }
    //插入角色
    public static boolean addRole(Role role){
        return  RoleDao.addRole(role);
    }
    //编辑角色
    public  static boolean updateRole(Role role){
        return RoleDao.updateRole(role);
    }
    //删除角色,通过id删除角色
    public boolean deleteRole(String role_id){
        return  RoleDao.deleteRole(role_id);
    }
}
