package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/2/2.
 */
public class Comment {

    /**
     * rd : 1021
     * errcode : 0
     * errmsg :
     * data : {"review":[{"rid":"12222","con":"谢谢您的分享哦，不错哦","iu":"http://wwwcdn.kimiss.net/btn/201509/xxxxx.jpg","un":"小丸子","up":"http://bbs.onlylady.com/uc_server/data/avatar/012/30/74/52_avatar_middle.jpg","pt":"145211245"}]}
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
         * rid : 12222
         * con : 谢谢您的分享哦，不错哦
         * iu : http://wwwcdn.kimiss.net/btn/201509/xxxxx.jpg
         * un : 小丸子
         * up : http://bbs.onlylady.com/uc_server/data/avatar/012/30/74/52_avatar_middle.jpg
         * pt : 145211245
         */

        private List<ReviewEntity> review;

        public void setReview(List<ReviewEntity> review) {
            this.review = review;
        }

        public List<ReviewEntity> getReview() {
            return review;
        }

        public static class ReviewEntity implements Serializable {
            private String rid;
            private String con;
            private String iu;
            private String un;
            private String up;
            private String pt;

            public void setRid(String rid) {
                this.rid = rid;
            }

            public void setCon(String con) {
                this.con = con;
            }

            public void setIu(String iu) {
                this.iu = iu;
            }

            public void setUn(String un) {
                this.un = un;
            }

            public void setUp(String up) {
                this.up = up;
            }

            public void setPt(String pt) {
                this.pt = pt;
            }

            public String getRid() {
                return rid;
            }

            public String getCon() {
                return con;
            }

            public String getIu() {
                return iu;
            }

            public String getUn() {
                return un;
            }

            public String getUp() {
                return up;
            }

            public String getPt() {
                return pt;
            }
        }
    }
}
