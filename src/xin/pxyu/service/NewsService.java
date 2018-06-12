package xin.pxyu.service;

import xin.pxyu.dao.NewsDao;
import xin.pxyu.model.News;

import java.util.List;

public class NewsService {
    //addNews
    public  static boolean addNews(News news){
        return NewsDao.addNews(news);
    }
    //updateNews
    public static boolean updateNews(News news){
        return  NewsDao.updateNews(news);
    }
    //getNewsBySchool
    public synchronized static List<News> getNewsBySchool(String school){
        return NewsDao.getNewsBySchool(school);
    }
    //   getNewsByUploadTime
    public synchronized  static List<News> getNewsByUploadTime(String upload_time){
        return NewsDao.getNewsByUploadTime(upload_time);
    }
}
