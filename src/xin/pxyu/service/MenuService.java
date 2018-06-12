package xin.pxyu.service;

import xin.pxyu.dao.MenuDao;
import xin.pxyu.dao.RoleDao;
import xin.pxyu.model.Menu;
import xin.pxyu.model.Role;

import java.util.List;

public class MenuService {
    //查询所有菜单
    public  static List<Menu> getAllMenu(){
        return MenuDao.getAllMenu();
    }
    //根据id查menu
    public synchronized static Menu getMenuById(String menu_id){
        return MenuDao.getMenuById(menu_id);
    }
    //插入菜单
    public  static boolean addMenu(Menu menu){
        return  MenuDao.addMenu(menu);
    }
    //编辑菜单
    public  static boolean updateMenu(Menu menu){
        return MenuDao.updateMenu(menu);
    }
    //删除菜单,通过id删除菜单
    public synchronized boolean deleteMenu(String menu_id){
        return MenuDao.deleteMenu(menu_id);
    }
}
