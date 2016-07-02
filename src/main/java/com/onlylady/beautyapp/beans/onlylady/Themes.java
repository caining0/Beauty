package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class Themes {

    /**
     * rd : 1002
     * errcode : 0
     * errmsg :
     * data : {"theme":[{"tid":"7939","tt":"新美妆","iu":"http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a7689ab4612_926.png"}]}
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
         * tid : 7939
         * tt : 新美妆
         * iu : http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a7689ab4612_926.png
         */

        private List<ThemeEntity> theme;

        public void setTheme(List<ThemeEntity> theme) {
            this.theme = theme;
        }

        public List<ThemeEntity> getTheme() {
            return theme;
        }

        public static class ThemeEntity implements Serializable{
            private String tid;
            private String tt;
            private String iu;

            public void setTid(String tid) {
                this.tid = tid;
            }

            public void setTt(String tt) {
                this.tt = tt;
            }

            public void setIu(String iu) {
                this.iu = iu;
            }

            public String getTid() {
                return tid;
            }

            public String getTt() {
                return tt;
            }

            public String getIu() {
                return iu;
            }
        }
    }
}
