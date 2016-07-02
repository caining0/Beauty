package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;
import java.util.List;

/**
 * Created by caining on 16/3/30.
 */
public class MineLiveBeans {

    /**
     * rd : 1105
     * errcode : 0
     * errmsg :
     * data : {"live":[{"lid":"180","tt":"测试","stat":"1459312380","stu":1,"iu":"http://new-img1.ol-img.com/lejpeg","vl":""}]}
     */

    private int rd;
    private int errcode;
    private String errmsg;
    private DataEntity data;

    public int getRd() {
        return rd;
    }

    public void setRd(int rd) {
        this.rd = rd;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public static class DataEntity implements Serializable {
        /**
         * lid : 180
         * tt : 测试
         * stat : 1459312380
         * stu : 1
         * iu : http://new-img1.ol-img.com/lejpeg
         * vl :
         */

        private List<LiveEntity> live;

        public List<LiveEntity> getLive() {
            return live;
        }

        public void setLive(List<LiveEntity> live) {
            this.live = live;
        }

        public static class LiveEntity implements Serializable {
            private String lid;
            private String tt;
            private String stat;
            private int stu;
            private String iu;
            private String vl;

            public String getLid() {
                return lid;
            }

            public void setLid(String lid) {
                this.lid = lid;
            }

            public String getTt() {
                return tt;
            }

            public void setTt(String tt) {
                this.tt = tt;
            }

            public String getStat() {
                return stat;
            }

            public void setStat(String stat) {
                this.stat = stat;
            }

            public int getStu() {
                return stu;
            }

            public void setStu(int stu) {
                this.stu = stu;
            }

            public String getIu() {
                return iu;
            }

            public void setIu(String iu) {
                this.iu = iu;
            }

            public String getVl() {
                return vl;
            }

            public void setVl(String vl) {
                this.vl = vl;
            }
        }
    }
}
