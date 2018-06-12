package xin.pxyu.service;

import org.apache.commons.fileupload.FileItemIterator;
import xin.pxyu.dao.TaskImageDao;
import xin.pxyu.model.TaskImage;

public class TaskImageService {
    //文件上传处理;
    public static TaskImage upload(String tpath, FileItemIterator fii){
        return TaskImageDao.upload(tpath,fii);
    }
    //获取头像图片
    public static boolean updateTaskImage(TaskImage taskImage) {
        return TaskImageDao.updateTaskImage(taskImage);
    }
}
