package com.onlylady.beautyapp.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/23.
 */
public class ProgressBean {

    /**
     * 进度
     * code : 0
     * data : {"total_size":2890154,"upload_size":84601}
     * message : 成功
     * total : 1
     */

    private int code;
    private DataEntity data;
    private String message;
    private int total;

    public void setCode(int code) {
        this.code = code;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public int getTotal() {
        return total;
    }

    public static class DataEntity implements Serializable {
        /**
         * total_size : 2890154
         * upload_size : 84601
         */

        private int total_size;
        private int upload_size;

        public void setTotal_size(int total_size) {
            this.total_size = total_size;
        }

        public void setUpload_size(int upload_size) {
            this.upload_size = upload_size;
        }

        public int getTotal_size() {
            return total_size;
        }

        public int getUpload_size() {
            return upload_size;
        }
    }
}
