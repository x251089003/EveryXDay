package com.xinxin.everyxday.dao.newdao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.xinxin.everyxday.dao.model.Like;

import com.xinxin.everyxday.dao.newdao.LikeDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig likeDaoConfig;

    private final LikeDao likeDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        likeDaoConfig = daoConfigMap.get(LikeDao.class).clone();
        likeDaoConfig.initIdentityScope(type);

        likeDao = new LikeDao(likeDaoConfig, this);

        registerDao(Like.class, likeDao);
    }
    
    public void clear() {
        likeDaoConfig.getIdentityScope().clear();
    }

    public LikeDao getLikeDao() {
        return likeDao;
    }

}
