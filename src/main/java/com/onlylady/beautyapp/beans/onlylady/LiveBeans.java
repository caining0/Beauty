package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;

/**
 * Created by caining on 16/2/4.
 */
public class LiveBeans implements Serializable {
    private String lid;
    private String laid;
    private String tt;
    private String iu;
    private String stat;
    private int stu;//1,进行中，2，未开始，3，结束
    private String usr;
    private String vl;

    private int pm;//0 屏占比 8：5 1 16：9
    private int irse;//预约状态  1,预约， 0否

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

    private String up;
    private String type;
    private boolean first;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
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
