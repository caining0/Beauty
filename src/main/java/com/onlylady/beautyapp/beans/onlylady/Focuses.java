package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/29.
 */
public class Focuses {

    /**
     * rd : 1001
     * errcode : 0
     * errmsg :
     * data : {"focuses":[{"aid":"3786833","hpl":"http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a6dd4b63bd1_331.jpg","type":"ar","tt":"卡地亚匠心献礼猴年祥瑞","val":""},{"aid":"19776","iu":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg","type":"ad","tt":"","val":"http://e.cn.miaozhen.com/r/k=2012995&p=6wt0u&dx=0&ni=__IESID__&mo=__OS__&ns=__IP__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__&m2=__IMEI__&m4=__AAID__&m5=__IDFA__&m6=__MAC1__&m6a=__MAC__&nd=__DRA__&np=__POS__&nn=__APP__&o=http://sale.jd.com/m/act/25wmZHqGiN1.html"},{"aid":"3786834","hpl":"http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a6dceb8b5c6_683.jpg","type":"va","tt":"RADO瑞士雷达表Pre-basel新品鉴赏","val":""},{"aid":"19777","iu":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg","type":"ad","tt":"","val":"http://e.cn.miaozhen.com/r/k=2012995&p=6wt0u&dx=0&ni=__IESID__&mo=__OS__&ns=__IP__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__&m2=__IMEI__&m4=__AAID__&m5=__IDFA__&m6=__MAC1__&m6a=__MAC__&nd=__DRA__&np=__POS__&nn=__APP__&o=http://sale.jd.com/m/act/25wmZHqGiN1.html"},{"aid":"3786835","hpl":"http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a6dcb5c7110_184.jpg","type":"ar","tt":"爱是最长情的告白 这些腕表与爱相关","val":""},{"aid":"19778","iu":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg","type":"ad","tt":"","val":"http://e.cn.miaozhen.com/r/k=2012995&p=6wt0u&dx=0&ni=__IESID__&mo=__OS__&ns=__IP__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__&m2=__IMEI__&m4=__AAID__&m5=__IDFA__&m6=__MAC1__&m6a=__MAC__&nd=__DRA__&np=__POS__&nn=__APP__&o=http://sale.jd.com/m/act/25wmZHqGiN1.html"},{"aid":"19779","iu":"http://btn.onlylady.com/201510/ba519c1d15bbf03dad8357b3270f59e4.jpg","type":"ad","tt":"","val":"http://e.cn.miaozhen.com/r/k=2012995&p=6wt0u&dx=0&ni=__IESID__&mo=__OS__&ns=__IP__&m0=__OPENUDID__&m0a=__DUID__&m1=__ANDROIDID1__&m1a=__ANDROIDID__&m2=__IMEI__&m4=__AAID__&m5=__IDFA__&m6=__MAC1__&m6a=__MAC__&nd=__DRA__&np=__POS__&nn=__APP__&o=http://sale.jd.com/m/act/25wmZHqGiN1.html"}]}
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
         * aid : 3786833
         * hpl : http://new-img1.ol-img.com/moudlepic/1969_module_images/201601/56a6dd4b63bd1_331.jpg
         * type : ar
         * tt : 卡地亚匠心献礼猴年祥瑞
         * val :
         */

        private List<FocusesEntity> focuses;

        public void setFocuses(List<FocusesEntity> focuses) {
            this.focuses = focuses;
        }

        public List<FocusesEntity> getFocuses() {
            return focuses;
        }

        public static class FocusesEntity implements Serializable {
            private String aid;
            private String hpl;
            private String type;
            private String tt;
            private String val;

            public void setAid(String aid) {
                this.aid = aid;
            }

            public void setHpl(String hpl) {
                this.hpl = hpl;
            }

            public void setType(String type) {
                this.type = type;
            }

            public void setTt(String tt) {
                this.tt = tt;
            }

            public void setVal(String val) {
                this.val = val;
            }

            public String getAid() {
                return aid;
            }

            public String getHpl() {
                return hpl;
            }

            public String getType() {
                return type;
            }

            public String getTt() {
                return tt;
            }

            public String getVal() {
                return val;
            }
        }
    }
}
