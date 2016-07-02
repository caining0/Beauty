package com.onlylady.beautyapp.beans;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2016/1/7.
 */
public class RtmpBean {

    /**
     * liveNum : 1
     * lives : [{"machine":"1","pushUrl":"rtmp://w.gslb.lecloud.com/live/20160107300112599?sign=05d50e149ad6f4aac3e3f955445a4f69&tm=20160107182451","status":"0","streamId":"20160107300112599"}]
     */

    private int liveNum;
    /**
     * machine : 1
     * pushUrl : rtmp://w.gslb.lecloud.com/live/20160107300112599?sign=05d50e149ad6f4aac3e3f955445a4f69&tm=20160107182451
     * status : 0
     * streamId : 20160107300112599
     */

    private List<LivesEntity> lives;

    public void setLiveNum(int liveNum) {
        this.liveNum = liveNum;
    }

    public void setLives(List<LivesEntity> lives) {
        this.lives = lives;
    }

    public int getLiveNum() {
        return liveNum;
    }

    public List<LivesEntity> getLives() {
        return lives;
    }

    public static class LivesEntity implements Serializable {
        private String machine;
        private String pushUrl;
        private String status;
        private String streamId;

        public void setMachine(String machine) {
            this.machine = machine;
        }

        public void setPushUrl(String pushUrl) {
            this.pushUrl = pushUrl;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setStreamId(String streamId) {
            this.streamId = streamId;
        }

        public String getMachine() {
            return machine;
        }

        public String getPushUrl() {
            return pushUrl;
        }

        public String getStatus() {
            return status;
        }

        public String getStreamId() {
            return streamId;
        }
    }
}
