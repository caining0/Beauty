package com.onlylady.beautyapp.beans.onlylady;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/1/29.
 */
public class HomeBeans implements Serializable {
    private String lid;
    private String laid;
    private String stat;
    private int stu;
    private String aid;
    private String iu;
    private String type;
    private String tt;
    private String cl;
    private String usr;
    private String up;
    private String vl;
    private String chne;

    public String getChne() {
        return chne;
    }

    public void setChne(String chne) {
        this.chne = chne;
    }

    private boolean first;

    public boolean isFirst() {
        return first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public HomeBeans(int myType) {
        this.myType = myType;
    }

    public String getVl() {
        return vl;
    }

    public void setVl(String vl) {
        this.vl = vl;
    }

    private int myType;

    public int getMyType() {
        return myType;
    }

    public void setMyType(int myType) {
        this.myType = myType;
    }

    public void setLid(String lid) {
        this.lid = lid;
    }

    public void setLaid(String laid) {
        this.laid = laid;
    }

    public void setIu(String iu) {
        this.iu = iu;
    }

    public void setTt(String tt) {
        this.tt = tt;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public void setStu(int stu) {
        this.stu = stu;
    }

    public String getLid() {
        return lid;
    }

    public String getLaid() {
        return laid;
    }

    public String getIu() {
        return iu;
    }

    public String getTt() {
        return tt;
    }

    public String getStat() {
        return stat;
    }

    public int getStu() {
        return stu;
    }


    public void setAid(String aid) {
        this.aid = aid;
    }


    public void setType(String type) {
        this.type = type;
    }


    public void setCl(String cl) {
        this.cl = cl;
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


    public String getType() {
        return type;
    }


    public String getCl() {
        return cl;
    }

    public String getUsr() {
        return usr;
    }

    public String getUp() {
        return up;
    }


}
