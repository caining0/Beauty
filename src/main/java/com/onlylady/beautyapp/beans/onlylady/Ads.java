package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cbs on 2016/4/1.
 */
public class Ads {

    /**
     * rd : 1106
     * errcode : 0
     * errmsg :
     * data : {"ads":[{"aid":"19785","hpl":"http://btn.onlylady.com/201511/b7ff542d927ad246ee513962500deb14.jpg","tt":"","val":"http://e.cn.miaozhen.com/r/k=2013629&p=6wyhU&dx=0&rt=2&ns=__IP__&ni=__IESID__&v=__LOC__&nd=__DRA__&np=__POS__&nn=__APP__&vo=33ffb20ef&vr=2&o=http://jump.ztcadx.com/rest?shop_id=106897045&target=https%3A%2F%2Fmaxamhzp.m.tmall.com%2F&mvtype=134&mvsid=[M_CAID]&mvcid=[M_SPID]&mvkeywords=[M_KWID]&mvid=[M_MZID]&mvip=[M_IP]"}]}
     */

    private int rd;
    private int errcode;
    private String errmsg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean  implements Serializable{
        /**
         * aid : 19785
         * hpl : http://btn.onlylady.com/201511/b7ff542d927ad246ee513962500deb14.jpg
         * tt :
         * val : http://e.cn.miaozhen.com/r/k=2013629&p=6wyhU&dx=0&rt=2&ns=__IP__&ni=__IESID__&v=__LOC__&nd=__DRA__&np=__POS__&nn=__APP__&vo=33ffb20ef&vr=2&o=http://jump.ztcadx.com/rest?shop_id=106897045&target=https%3A%2F%2Fmaxamhzp.m.tmall.com%2F&mvtype=134&mvsid=[M_CAID]&mvcid=[M_SPID]&mvkeywords=[M_KWID]&mvid=[M_MZID]&mvip=[M_IP]
         */

        private List<AdsBean> ads;

        public List<AdsBean> getAds() {
            return ads;
        }

        public void setAds(List<AdsBean> ads) {
            this.ads = ads;
        }

        public static class AdsBean implements Serializable {
            private String aid;
            private String hpl;
            private String tt;
            private String val;

            public String getAid() {
                return aid;
            }

            public void setAid(String aid) {
                this.aid = aid;
            }

            public String getHpl() {
                return hpl;
            }

            public void setHpl(String hpl) {
                this.hpl = hpl;
            }

            public String getTt() {
                return tt;
            }

            public void setTt(String tt) {
                this.tt = tt;
            }

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }
        }
    }
}
