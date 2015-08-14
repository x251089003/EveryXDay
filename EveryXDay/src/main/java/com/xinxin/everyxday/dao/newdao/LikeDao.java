package com.xinxin.everyxday.dao.newdao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.Property;
import de.greenrobot.dao.internal.DaoConfig;

import com.xinxin.everyxday.dao.model.Like;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LIKE".
*/
public class LikeDao extends AbstractDao<Like, Long> {

    public static final String TABLENAME = "LIKE";

    /**
     * Properties of entity Like.<br/>
     * Can be used for QueryBuilder and for referencing column names.
    */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Newid = new Property(1, String.class, "newid", false, "NEWID");
        public final static Property Avatar = new Property(2, String.class, "avatar", false, "AVATAR");
        public final static Property Title = new Property(3, String.class, "title", false, "TITLE");
        public final static Property Cover = new Property(4, String.class, "cover", false, "COVER");
        public final static Property DetailNew = new Property(5, String.class, "detailNew", false, "DETAIL_NEW");
        public final static Property CreateTime = new Property(6, java.util.Date.class, "createTime", false, "CREATE_TIME");
    };


    public LikeDao(DaoConfig config) {
        super(config);
    }
    
    public LikeDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(SQLiteDatabase db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LIKE\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"NEWID\" TEXT UNIQUE ," + // 1: newid
                "\"AVATAR\" TEXT," + // 2: avatar
                "\"TITLE\" TEXT," + // 3: title
                "\"COVER\" TEXT," + // 4: cover
                "\"DETAIL_NEW\" TEXT," + // 5: detailNew
                "\"CREATE_TIME\" INTEGER);"); // 6: createTime
    }

    /** Drops the underlying database table. */
    public static void dropTable(SQLiteDatabase db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LIKE\"";
        db.execSQL(sql);
    }

    /** @inheritdoc */
    @Override
    protected void bindValues(SQLiteStatement stmt, Like entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String newid = entity.getNewid();
        if (newid != null) {
            stmt.bindString(2, newid);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(3, avatar);
        }
 
        String title = entity.getTitle();
        if (title != null) {
            stmt.bindString(4, title);
        }
 
        String cover = entity.getCover();
        if (cover != null) {
            stmt.bindString(5, cover);
        }
 
        String detailNew = entity.getDetailNew();
        if (detailNew != null) {
            stmt.bindString(6, detailNew);
        }
 
        java.util.Date createTime = entity.getCreateTime();
        if (createTime != null) {
            stmt.bindLong(7, createTime.getTime());
        }
    }

    /** @inheritdoc */
    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    /** @inheritdoc */
    @Override
    public Like readEntity(Cursor cursor, int offset) {
        Like entity = new Like( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // newid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // avatar
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // title
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // cover
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // detailNew
            cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)) // createTime
        );
        return entity;
    }
     
    /** @inheritdoc */
    @Override
    public void readEntity(Cursor cursor, Like entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setNewid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAvatar(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setTitle(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCover(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setDetailNew(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCreateTime(cursor.isNull(offset + 6) ? null : new java.util.Date(cursor.getLong(offset + 6)));
     }
    
    /** @inheritdoc */
    @Override
    protected Long updateKeyAfterInsert(Like entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    /** @inheritdoc */
    @Override
    public Long getKey(Like entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    /** @inheritdoc */
    @Override    
    protected boolean isEntityUpdateable() {
        return true;
    }
    
}
