package com.onlylady.beautyapp.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/21.
 */
public class VideoListBean {


    /**
     * rd : 1004
     * errcode : 0
     * errmsg :
     * data : {"live":[{"lid":"34","laid":"A2016012700302","tt":"视频直播","iu":"http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a8681a323d2_532.jpg","stat":"1453866005","stu":3}],"articles":[{"aid":"3790980","iu":"http://new-img1.ol-img.com/moudlepic/1969_module_images/201602/56b05c3e44d1a_266.jpg","type":"va","tt":"Instagram创始人大婚！\u201c滤镜时代\u201d最受关注的一场爱情童话","cl":"0","usr":"郑守银","up":null}]}
     */

    private int rd;
    private int errcode;
    private String errmsg;
    private DataEntity data;

    public void setRd(int rd) {
        this.rd = rd;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getRd() {
        return rd;
    }

    public int getErrcode() {
        return errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable {
        /**
         * lid : 34
         * laid : A2016012700302
         * tt : 视频直播
         * iu : http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a8681a323d2_532.jpg
         * stat : 1453866005
         * stu : 3
         */

        private List<LiveEntity> live;
        /**
         * aid : 3790980
         * iu : http://new-img1.ol-img.com/moudlepic/1969_module_images/201602/56b05c3e44d1a_266.jpg
         * type : va
         * tt : Instagram创始人大婚！“滤镜时代”最受关注的一场爱情童话
         * cl : 0
         * usr : 郑守银
         * up : null
         */

        private List<ArticlesEntity> articles;

        public void setLive(List<LiveEntity> live) {
            this.live = live;
        }

        public void setArticles(List<ArticlesEntity> articles) {
            this.articles = articles;
        }

        public List<LiveEntity> getLive() {
            return live;
        }

        public List<ArticlesEntity> getArticles() {
            return articles;
        }

        public static class LiveEntity implements Serializable {
            private String lid;
            private String laid;
            private String tt;
            private String iu;
            private String stat;
            private int stu;
            private String vl;

            public String getVl() {
                return vl;
            }

            public void setVl(String vl) {
                this.vl = vl;
            }

            public void setLid(String lid) {
                this.lid = lid;
            }

            public void setLaid(String laid) {
                this.laid = laid;
            }

            public void setTt(String tt) {
                this.tt = tt;
            }

            public void setIu(String iu) {
                this.iu = iu;
            }

            public void setStat(String stat) {
                this.stat = stat;
            }

            public void setStu(int stu) {
                this.stu = stu;
            }

            public String getLid() {
                return lid;
            }

            public String getLaid() {
                return laid;
            }

            public String getTt() {
                return tt;
            }

            public String getIu() {
                return iu;
            }

            public String getStat() {
                return stat;
            }

            public int getStu() {
                return stu;
            }
        }

        public static class ArticlesEntity {
            private String aid;
            private String iu;
            private String type;
            private String tt;
            private String cl;
            private String usr;
            private String up;
            private String vl;
            private String pt;
            private String chne;

            public String getChne() {
                return chne;
            }

            public void setChne(String chne) {
                this.chne = chne;
            }

            public String getPt() {
                return pt;
            }

            public void setPt(String pt) {
                this.pt = pt;
            }

            public String getVl() {
                return vl;
            }

            public void setVl(String vl) {
                this.vl = vl;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public void setIu(String iu) {
                this.iu = iu;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setTt(String tt) {
                this.tt = tt;
            }

            public void setCl(String cl) {
                this.cl = cl;
            }

            public void setUsr(String usr) {
                this.usr = usr;
            }

            public void setUp(String up) {
                this.up = up;
            }

            public String getAid() {
                return aid;
            }

            public String getIu() {
                return iu;
            }

            public String getType() {
                return type;
            }

            public String getTt() {
                return tt;
            }

            public String getCl() {
                return cl;
            }

            public String getUsr() {
                return usr;
            }

            public String getUp() {
                return up;
            }
        }
    }
}
