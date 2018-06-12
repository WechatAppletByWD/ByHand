package xin.pxyu.service;

import xin.pxyu.dao.SuggestionDao;
import xin.pxyu.dao.SystemMessageDao;
import xin.pxyu.model.Suggestion;
import xin.pxyu.model.SystemMessage;

import java.util.List;

public class SuggestionService {
    public static Suggestion getSuggestionById( String s_id){
        return SuggestionDao.getSuggestionById(s_id);
    }
    public  static boolean updateSuggestion(Suggestion suggestion){
        return SuggestionDao.updateSuggestion(suggestion);
    }
    public static boolean addSuggestion(Suggestion suggestion){
        return SuggestionDao.addSuggestion(suggestion);
    }

}
