package com.onlylady.beautyapp.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class LiveBean implements Serializable {

    /**
     * rd : 1006
     * errcode : 0
     * errmsg :
     * data : {"today":[{"lid":"45","laid":"A2016020400540","tt":"情侣互化","iu":"http://new-img1.ol-img.com/letv/2016/02/56b2f94070de2_330.jpg","stat":"1454587767","stu":2,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"46","laid":"A2016020400547","tt":"少女妆","iu":"http://new-img1.ol-img.com/letv/2016/02/56b2ff419c886_395.jpg","stat":"1454596702","stu":2,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"44","laid":"A2016020400513","tt":"2月4日测试","iu":"http://new-img1.ol-img.com/letv/2016/02/56b2f90489776_160.jpg","stat":"1454569856","stu":3,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"}],"yesterday":[{"lid":"40","laid":"A2016020200508","tt":"教你如何打造眼泪装","iu":"http://new-img1.ol-img.com/letv/2016/02/56b0438977522_149.jpg","stat":"1454479200","stu":3,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"42","laid":"A2016020201146","tt":"陈辉测试","iu":"http://new-img1.ol-img.com/letv/2016/02/56b06247867fb_457.jpg","stat":"1454486118","stu":3,"usr":"郑守银","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"41","laid":"A2016020200669","tt":"胭脂发彩打造一个全新的自己","iu":"http://new-img1.ol-img.com/letv/2016/02/56b048d8ab04c_696.jpg","stat":"1454490000","stu":3,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"}],"tomorrow":[{"lid":"48","laid":"A2016020400574","tt":"网传各种妙招究竟靠不靠谱？","iu":"http://new-img1.ol-img.com/letv/2016/02/56b304412faef_104.jpg","stat":"1454640176","stu":2,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"47","laid":"A2016020400558","tt":"打发斯蒂芬","iu":"http://new-img1.ol-img.com/letv/2016/02/56b2ff6808f5f_300.jpg","stat":"1454657745","stu":2,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"43","laid":"A2016020201310","tt":"减龄妆教程，打造适合少女心","iu":"http://new-img1.ol-img.com/letv/2016/02/56b0702b17301_860.jpg","stat":"1454661418","stu":2,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"},{"lid":"49","laid":"A2016020400673","tt":"让你三天不洗头照样美美哒","iu":"http://new-img1.ol-img.com/letv/2016/02/56b3048539fa4_877.jpg","stat":"1454745438","stu":2,"usr":"陈辉","up":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg"}]}
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

    public static class DataEntity implements Serializable{
        /**
         * lid : 45
         * laid : A2016020400540
         * tt : 情侣互化
         * iu : http://new-img1.ol-img.com/letv/2016/02/56b2f94070de2_330.jpg
         * stat : 1454587767
         * stu : 2
         * usr : 陈辉
         * up : http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg
         */

        private List<TodayEntity> today;
        /**
         * lid : 40
         * laid : A2016020200508
         * tt : 教你如何打造眼泪装
         * iu : http://new-img1.ol-img.com/letv/2016/02/56b0438977522_149.jpg
         * stat : 1454479200
         * stu : 3
         * usr : 陈辉
         * up : http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg
         */

        private List<YesterdayEntity> yesterday;

        public List<YesterdayEntity> getYesterday() {
            return yesterday;
        }

        public void setYesterday(List<YesterdayEntity> yesterday) {
            this.yesterday = yesterday;
        }

        /**

         * lid : 48
         * laid : A2016020400574
         * tt : 网传各种妙招究竟靠不靠谱？
         * iu : http://new-img1.ol-img.com/letv/2016/02/56b304412faef_104.jpg
         * stat : 1454640176
         * stu : 2
         * usr : 陈辉
         * up : http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg
         */

        private List<TomorrowEntity> tomorrow;

        public void setToday(List<TodayEntity> today) {
            this.today = today;
        }



        public void setTomorrow(List<TomorrowEntity> tomorrow) {
            this.tomorrow = tomorrow;
        }

        public List<TodayEntity> getToday() {
            return today;
        }



        public List<TomorrowEntity> getTomorrow() {
            return tomorrow;
        }

        public static class TodayEntity implements Serializable {
            private String lid;
            private String laid;
            private String tt;
            private String iu;
            private String stat;
            private int stu;
            private String usr;
            private String up;
            private String type ;
            private String vl;
            private int pm;//0 屏占比 8：5 1 16：9
            private int irse;//预约状态

            public int getPm() {
                return pm;
            }

            public void setPm(int pm) {
                this.pm = pm;
            }

            public int getIrse() {
                return irse;
            }

            public void setIrse(int irse) {
                this.irse = irse;
            }

            public String getVl() {
                return vl;
            }

            public void setVl(String vl) {
                this.vl = vl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getUsr() {
                return usr;
            }

            public String getUp() {
                return up;
            }
        }

        public static class YesterdayEntity implements Serializable{
            private String lid;
            private String laid;
            private String tt;
            private String iu;
            private String stat;
            private int stu;
            private String usr;
            private String vl;
            private String up;
            private String type;
            private int pm;//0 屏占比 8：5 1 16：9
            private int irse;//预约状态

            public int getPm() {
                return pm;
            }

            public void setPm(int pm) {
                this.pm = pm;
            }

            public int getIrse() {
                return irse;
            }

            public void setIrse(int irse) {
                this.irse = irse;
            }

            public String getVl() {
                return vl;
            }

            public void setVl(String vl) {
                this.vl = vl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getUsr() {
                return usr;
            }

            public String getUp() {
                return up;
            }
        }

        public static class TomorrowEntity implements Serializable {
            private String lid;
            private String laid;
            private String tt;
            private String iu;
            private String stat;
            private int stu;
            private String usr;
            private String up;
            private String type;
            private String vl;
            private int pm;//0 屏占比 8：5 1 16：9
            private int irse;//预约状态

            public int getPm() {
                return pm;
            }

            public void setPm(int pm) {
                this.pm = pm;
            }

            public int getIrse() {
                return irse;
            }

            public void setIrse(int irse) {
                this.irse = irse;
            }

            public String getVl() {
                return vl;
            }

            public void setVl(String vl) {
                this.vl = vl;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
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

            public String getUsr() {
                return usr;
            }

            public String getUp() {
                return up;
            }
        }
    }
}
