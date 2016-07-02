package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/2.
 */
public class UserInfo implements Serializable {

    /**
     * ud : 125
     * ue : 樱桃22
     * ul : http://olupic.ol-img.com/012/21/55/84_avatar_small.jpg
     * ux : 0
     * un : c541d010d2
     * sjs : vl1djm9
     */

    private int ud;
    private String ue;
    private String ul;
    private String ux;
    private String un;
    private String sjs;
    private int ilv;//权限  1 有 0 没有

    public int getIlv() {
        return ilv;
    }

    public void setIlv(int ilv) {
        this.ilv = ilv;
    }

    public void setUd(int ud) {
        this.ud = ud;
    }

    public void setUe(String ue) {
        this.ue = ue;
    }

    public void setUl(String ul) {
        this.ul = ul;
    }

    public void setUx(String ux) {
        this.ux = ux;
    }

    public void setUn(String un) {
        this.un = un;
    }

    public void setSjs(String sjs) {
        this.sjs = sjs;
    }

    public int getUd() {
        return ud;
    }

    public String getUe() {
        return ue;
    }

    public String getUl() {
        return ul;
    }

    public String getUx() {
        return ux;
    }

    public String getUn() {
        return un;
    }

    public String getSjs() {
        return sjs;
    }

}
