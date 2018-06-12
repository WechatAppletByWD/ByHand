package xin.pxyu.service;

import xin.pxyu.dao.SystemMessageDao;
import xin.pxyu.model.SystemMessage;

import java.util.List;

public class SystemMessageService {
    public static SystemMessage getSystemMessageById(String sm_id){
        return SystemMessageDao.getSystemMessageById(sm_id);
    }
    public static List<SystemMessage> getSystemMessageByCreateTime(int start,int length){
        return SystemMessageDao.getSystemMessage(start,length);
    }
    public  static boolean updateSystemMessage(SystemMessage systemMessage){
        return SystemMessageDao.updateSystemMessage(systemMessage);
    }
    public static boolean addSystemMessage(SystemMessage systemMessage){
        return SystemMessageDao.addSystemMessage(systemMessage);
    }
}
