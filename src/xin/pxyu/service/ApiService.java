package xin.pxyu.service;

import xin.pxyu.dao.ApiDao;
import xin.pxyu.model.Api;

import java.util.List;

public class ApiService {
    //获取所有的api
    public  static List<Api> getAllApi(){
        return ApiDao.getAllApi();
    }
}
