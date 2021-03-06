package com.xinxin.everyxday.dao.model;

// THIS CODE IS GENERATED BY greenDAO, EDIT ONLY INSIDE THE "KEEP"-SECTIONS

// KEEP INCLUDES - put your custom includes here
// KEEP INCLUDES END
/**
 * Entity mapped to table "LIKE".
 */
public class Like {

    private Long id;
    private String newid;
    private String avatar;
    private String title;
    private String cover;
    private String detailNew;
    private String category;
    private java.util.Date createTime;

    // KEEP FIELDS - put your custom fields here
    // KEEP FIELDS END

    public Like() {
    }

    public Like(Long id) {
        this.id = id;
    }

    public Like(Long id, String newid, String avatar, String title, String cover, String detailNew, String category, java.util.Date createTime) {
        this.id = id;
        this.newid = newid;
        this.avatar = avatar;
        this.title = title;
        this.cover = cover;
        this.detailNew = detailNew;
        this.category = category;
        this.createTime = createTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNewid() {
        return newid;
    }

    public void setNewid(String newid) {
        this.newid = newid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getDetailNew() {
        return detailNew;
    }

    public void setDetailNew(String detailNew) {
        this.detailNew = detailNew;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public java.util.Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(java.util.Date createTime) {
        this.createTime = createTime;
    }

    // KEEP METHODS - put your custom methods here
    // KEEP METHODS END

}
