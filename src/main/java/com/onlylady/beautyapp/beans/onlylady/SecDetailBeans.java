package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caining on 16/2/15.
 */
public class SecDetailBeans implements Serializable {

    /**
     * rd : 1003
     * errcode : 0
     * errmsg :
     * data : {"articles":[{"aid":"3786839","tt":"RADO瑞士雷达表推出DiaMaster钻霸系列大秒针腕表恭贺新禧","iu":"http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a7701784b98_998.png","chid":"2016","type":"ar","cl":"0","vl":"0","usr":"王皓然","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"aid":"3786833","tt":"卡地亚匠心献礼猴年祥瑞","iu":"","chid":"2013","type":"ar","cl":"0","vl":"0","usr":"宋梓兮","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"}]}
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

    public static class DataEntity {
        /**
         * aid : 3786839
         * tt : RADO瑞士雷达表推出DiaMaster钻霸系列大秒针腕表恭贺新禧
         * iu : http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a7701784b98_998.png
         * chid : 2016
         * type : ar
         * cl : 0
         * vl : 0
         * usr : 王皓然
         * up : http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg
         */

        private List<ArticlesEntity> articles;

        public void setArticles(List<ArticlesEntity> articles) {
            this.articles = articles;
        }

        public List<ArticlesEntity> getArticles() {
            return articles;
        }

        public static class ArticlesEntity {
            private String aid;
            private String tt;
            private String iu;
            private String chid;
            private String type;
            private String cl;
            private String vl;
            private String usr;
            private String up;

            public void setAid(String aid) {
                this.aid = aid;
            }

            public void setTt(String tt) {
                this.tt = tt;
            }

            public void setIu(String iu) {
                this.iu = iu;
            }

            public void setChid(String chid) {
                this.chid = chid;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setCl(String cl) {
                this.cl = cl;
            }

            public void setVl(String vl) {
                this.vl = vl;
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

            public String getTt() {
                return tt;
            }

            public String getIu() {
                return iu;
            }

            public String getChid() {
                return chid;
            }

            public String getType() {
                return type;
            }

            public String getCl() {
                return cl;
            }

            public String getVl() {
                return vl;
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
