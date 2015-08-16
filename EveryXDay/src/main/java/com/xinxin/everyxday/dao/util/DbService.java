package com.xinxin.everyxday.dao.util;

import android.content.Context;
import android.util.Log;

import com.xinxin.everyxday.EveryXDayApplication;
import com.xinxin.everyxday.dao.model.Like;
import com.xinxin.everyxday.dao.newdao.DaoSession;
import com.xinxin.everyxday.dao.newdao.LikeDao;

import java.util.List;

/**
 * Created by mengxiao on 15/8/13.
 */
public class DbService {

    private static final String TAG = DbService.class.getSimpleName();
    private static DbService instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private LikeDao likeDao;

    private DbService() {
    }

    public static DbService getInstance(Context context) {
        if (instance == null) {
            instance = new DbService();
            if (appContext == null){
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = EveryXDayApplication.getDaoSession(context);
            instance.likeDao = instance.mDaoSession.getLikeDao();
        }
        return instance;
    }


    public Like loadLike(long id) {
        return likeDao.load(id);
    }

    public List<Like> loadAllLike(){
        return likeDao.queryRaw("WHERE _ID > 0 ORDER BY _ID DESC");
    }

    /**
     * query list with where clause
     * ex: begin_date_time >= ? AND end_date_time <= ?
     * @param where where clause, include 'where' word
     * @param params query parameters
     * @return
     */

    public List<Like> queryLike(String where, String... params){
        return likeDao.queryRaw(where, params);
    }


    /**
     * insert or update like
     * @param like
     * @return insert or update like id
     */
    public long saveLike(Like like){
        return likeDao.insertOrReplace(like);
    }


    /**
     * insert or update likeList use transaction
     * @param list
     */
    public void saveLikeLists(final List<Like> list){
        if(list == null || list.isEmpty()){
            return;
        }
        likeDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for(int i=0; i<list.size(); i++){
                    Like like = list.get(i);
                    likeDao.insertOrReplace(like);
                }
            }
        });

    }

    /**
     * delete all like
     */
    public void deleteAllLike(){
        likeDao.deleteAll();
    }

    /**
     * delete like by id
     * @param id
     */
    public void deleteLike(long id){
        likeDao.deleteByKey(id);
        Log.i(TAG, "delete");
    }

    public void deleteLike(Like like){
        likeDao.delete(like);
    }
}
