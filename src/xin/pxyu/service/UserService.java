package xin.pxyu.service;
import xin.pxyu.dao.UserDao;
import xin.pxyu.model.User;
import java.util.List;
/*
用于对外提供数据操作，调用dao层
 */
public class UserService {
     //newUser
     public static boolean newUser(User user) {
         return UserDao.newUser(user);
     }
     //userUpdate
     public static boolean userUpdate(User user) {
         return UserDao.userUpdate(user);
     }
    //getAvatarUrlByLocationId
     public static String getAvatarUrlByLocationId(String location_id){
         return UserDao.getAvatarUrlByLocationId(location_id);
     }
    //userLocation_id
    public static User getUserByLocationId(String location_id){
        return UserDao.getUserByLocationId(location_id);
    }
    //getAllUsers
    public static List<User> getAllUsers(){
        return UserDao.getAllUsers();
    }
    //UpdateLastAddress
    public static boolean updateLastAddress(String tlocation_id,String location_id){
        return UserDao.updateLastAddress(tlocation_id,location_id);
    }
    //UpdateAddress
    public static boolean updateAddress(String location_id,String address){
        return UserDao.updateAddress(location_id,address);
    }
    //检查用户是否存在接口
    public static boolean isLocationId(String location_id){
        return UserDao.isLocationId(location_id);
    }
    //userAddCredit
    public static boolean userAddCredit(String location_id) {
        return UserDao.userAddCredit(location_id);
    }
    //userSubCredit
    public static boolean userSubCredit(String location_id) {
        return UserDao.userSubCredit(location_id);
    }
    //getUserCredit
    public static int getUserCredit(String location_id) {
        return UserDao.getUserCredit(location_id);
    }
    // updateRank
    public static boolean updateRank(String location_id){
        return UserDao.updateRank(location_id);
    }
}

