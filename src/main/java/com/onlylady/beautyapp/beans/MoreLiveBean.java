package com.onlylady.beautyapp.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caining on 16/2/16.
 */
public class MoreLiveBean {

    /**
     * rd : 1018
     * errcode : 0
     * errmsg :
     * data : {"live":[{"lid":"54","laid":"A2016021500167","tt":"光棍节是单身狗永远的痛","iu":"2016/02/56c137947e4ae_344.jpg","stat":"1455416819","stu":3,"vl":140,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"49","laid":"A2016020400673","tt":"让你三天不洗头照样美美哒","iu":"2016/02/56b30482b1de2_476.jpg","stat":"1454745438","stu":3,"vl":224,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"43","laid":"A2016020201310","tt":"减龄妆教程，打造适合少女心","iu":"2016/02/56b07022ae036_610.jpg","stat":"1454661418","stu":3,"vl":170,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"47","laid":"A2016020400558","tt":"打发斯蒂芬","iu":"2016/02/56b2ff653bc1c_746.jpg","stat":"1454657745","stu":3,"vl":170,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"}]}
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
         * lid : 54
         * laid : A2016021500167
         * tt : 光棍节是单身狗永远的痛
         * iu : 2016/02/56c137947e4ae_344.jpg
         * stat : 1455416819
         * stu : 3
         * vl : 140
         * usr : 陈辉
         * up : http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg
         */

        private List<LiveEntity> live;

        public void setLive(List<LiveEntity> live) {
            this.live = live;
        }

        public List<LiveEntity> getLive() {
            return live;
        }

        public static class LiveEntity implements Serializable {
            private String lid;
            private String laid;
            private String tt;
            private String iu;
            private String stat;
            private int stu;
            private String vl;
            private String usr;
            private String up;

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

            public void setVl(String vl) {
                this.vl = vl;
            }

            public void setUsr(String usr) {
                this.usr = usr;
            }

            public void setUp(String up) {
                this.up = up;
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
