package com.xinxin.everyxday.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mengxiao on 15/8/11.
 */
public class ShowOrderDetialBean {

    /**
     * tags : ["摇臂个性阅读壁灯"]
     * buyurl : http://redirect.simba.taobao.com/rd?w=unionnojs&f=http%3A%2F%2Fai.taobao.com%2Fauction%2Fedetail.htm%3Fe%3DxqQ8m0AZHwojmraEDZVrLnfX0%252BF9G6aBwTF0nIgV33SLltG5xFicOdXrTUTgh9sMDPIwxrc30rhSWkknj6ZtyQfsbHUwDHbP%252B6dg5B%252FjUf9rjFmkjfrXJG3abJM7sDg2Le08917aNmWWdRmT3b5ysw%253D%253D%26ptype%3D100010%26from%3Dbasic&k=5ccfdb950740ca16&c=un&b=alimm_0&p=mm_28320650_8772147_29480766
     * id : 23
     * title : 今日最佳：笨蛋造灯，黑夜温暖
     * update_time : 2015-07-17 11:46:51
     * status : 1
     * contents : [{"description":"南灯记：笨蛋造灯，黑夜温暖。今日最佳：南灯记摇臂个性阅读壁灯。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/488303a88d611814867bc0cc1b83f367"},{"description":"工业感强烈，同时具备强烈的带入感。带入感，你懂的。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/c529867f0a27866834379bccf4c50d0f"},{"description":"美国黑胡桃木，木头中间为金属复古开关。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/10ce5fd75bfac6e75793bbf3f4b98b20"},{"description":"缅甸桃花芯木，给有强迫症的你。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/4f8412140c9f486ef0a6c20e5a6f625f"},{"description":"美国白蜡木，这一款显的更干净。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/1110f34fc932a2b7024c62dae4368eee"},{"description":"网状灯罩，更具设计感。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/b777fc177d5262d6aa244de1e5588900"},{"description":"灯罩上下角度调节，可以左右自由旋转，光照方位绝对方便。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/c938eb5ada7454c0fe80fa49a76902f9"},{"description":"复古的电线插头，配合整体设计，更具文艺气质。","img":"http://order-show-image.b0.upaiyun.com/excellent/show/1edf700a149ef7c19c7eba31cbd1a56b"}]
     * imgcover : 0
     * create_time : 2015-07-17 11:25:37
     * avatar : http://order-show-image.b0.upaiyun.comhttp://order-show-image.b0.upaiyun.com/excellent/show/5dd3b75d0a00038f58c15e1536827aa9
     */
    private List<String> tags;
    private String buyurl;
    private String id;
    private String title;
    private String update_time;
    private String status;
    private ArrayList<ContentsEntity> contents;
    private String imgcover;
    private String create_time;
    private String avatar;

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public void setBuyurl(String buyurl) {
        this.buyurl = buyurl;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setContents(ArrayList<ContentsEntity> contents) {
        this.contents = contents;
    }

    public void setImgcover(String imgcover) {
        this.imgcover = imgcover;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public List<String> getTags() {
        return tags;
    }

    public String getBuyurl() {
        return buyurl;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<ContentsEntity> getContents() {
        return contents;
    }

    public String getImgcover() {
        return imgcover;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getAvatar() {
        return avatar;
    }

    public static class ContentsEntity {
        /**
         * description : 南灯记：笨蛋造灯，黑夜温暖。今日最佳：南灯记摇臂个性阅读壁灯。
         * img : http://order-show-image.b0.upaiyun.com/excellent/show/488303a88d611814867bc0cc1b83f367
         */
        private String description;
        private String img;

        public void setDescription(String description) {
            this.description = description;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getDescription() {
            return description;
        }

        public String getImg() {
            return img;
        }
    }
}
