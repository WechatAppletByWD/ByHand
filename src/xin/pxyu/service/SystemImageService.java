package xin.pxyu.service;

import org.apache.commons.fileupload.FileItemIterator;
import xin.pxyu.dao.SystemImageDao;
import xin.pxyu.dao.TaskImageDao;
import xin.pxyu.model.SystemImage;
import xin.pxyu.model.TaskImage;

public class SystemImageService {
    //文件上传处理;
    public static SystemImage upload(String tpath, FileItemIterator fii){
        return SystemImageDao.upload(tpath,fii);
    }
    //获取头像图片
    public static boolean updateSystemImage(SystemImage systemImage) {
        return SystemImageDao.updateSystemImage(systemImage);
    }
}
