package com.onlylady.beautyapp.beans;

import java.io.Serializable;

/**
 * Created by Administrator on 2015/12/23.
 */
public class UpdateBean {

    /**
     * code : 0
     * message : 成功
     * total : 1
     * data : {"video_id":"19763606","video_unique":"d834b5b278","upload_url":"http://118.26.58.36/api/fileupload?offset=0&token=bWGy_GtrnV9ItxdYG_yJCYVYMJa_-zBTdtsPCU4RvtLzPHuKiKuZPacKwOzDpSE4lTrG0hNQEGwaXtB7jQbEtOi78Ki35mGw19Uh5S9twUVTwfXw0-IE3rVccJVHNLizgN9IU89VqXOFwsQ8fSeWUiRNU-DgDq_5eB94x0AD5Me5cppTOIQLX5Y7dsjWDqqj-4DWLX6UwKfw4Wv72bCceEE0iRJJ6F7e-Qd7vt3tTdMML9biBENd3qJzZyPEFM16V2hQDQCgTuSUrtotUTIyVqF7EmX_rGiFzSp71X3veA4kms1dH_m4br1zryCgGmy4Scre6c6zreIPXVZAM-t1HQp_1JNbHxj0t3uEEcJrbm0FWzEvNxb2SO-Dt0lQMcAUwaKysUfsc8PEEX-6aGosCsBEFwUKkesQyNnbdJ8qBd4~&fmt=cjson","progress_url":"http://118.26.58.36/api/uploadprogress?token=bWGy_GtrnV9ItxdYG_yJCYVYMJa_-zBTdtsPCU4RvtLzPHuKiKuZPacKwOzDpSE4lTrG0hNQEGwaXtB7jQbEtOi78Ki35mGw19Uh5S9twUVTwfXw0-IE3rVccJVHNLizgN9IU89VqXOFwsQ8fSeWUiRNU-DgDq_5eB94x0AD5Me5cppTOIQLX5Y7dsjWDqqj-4DWLX6UwKfw4Wv72bCceEE0iRJJ6F7e-Qd7vt3tTdMML9biBENd3qJzZyPEFM16V2hQDQCgTuSUrtotUTIyVqF7EmX_rGiFzSp71X3veA4kms1dH_m4br1zryCgGmy4Scre6c6zreIPXVZAM-t1HQp_1JNbHxj0t3uEEcJrbm0FWzEvNxb2SO-Dt0lQMcAUwaKysUfsc8PEEX-6aGosCsBEFwUKkesQyNnbdJ8qBd4~&fmt=cjson","token":"bWGy_GtrnV9ItxdYG_yJCYVYMJa_-zBTdtsPCU4RvtLzPHuKiKuZPacKwOzDpSE4lTrG0hNQEGwaXtB7jQbEtOi78Ki35mGw19Uh5S9twUVTwfXw0-IE3rVccJVHNLizgN9IU89VqXOFwsQ8fSeWUiRNU-DgDq_5eB94x0AD5Me5cppTOIQLX5Y7dsjWDqqj-4DWLX6UwKfw4Wv72bCceEE0iRJJ6F7e-Qd7vt3tTdMML9biBENd3qJzZyPEFM16V2hQDQCgTuSUrtotUTIyVqF7EmX_rGiFzSp71X3veA4kms1dH_m4br1zryCgGmy4Scre6c6zreIPXVZAM-t1HQp_1JNbHxj0t3uEEcJrbm0FWzEvNxb2SO-Dt0lQMcAUwaKysUfsc8PEEX-6aGosCsBEFwUKkesQyNnbdJ8qBd4~","uploadtype":0,"isdrm":0}
     */

    private int code;
    private String message;
    private int total;
    private DataEntity data;

    public void setCode(int code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getTotal() {
        return total;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity implements Serializable {
        /**
         * video_id : 19763606
         * video_unique : d834b5b278
         * upload_url : http://118.26.58.36/api/fileupload?offset=0&token=bWGy_GtrnV9ItxdYG_yJCYVYMJa_-zBTdtsPCU4RvtLzPHuKiKuZPacKwOzDpSE4lTrG0hNQEGwaXtB7jQbEtOi78Ki35mGw19Uh5S9twUVTwfXw0-IE3rVccJVHNLizgN9IU89VqXOFwsQ8fSeWUiRNU-DgDq_5eB94x0AD5Me5cppTOIQLX5Y7dsjWDqqj-4DWLX6UwKfw4Wv72bCceEE0iRJJ6F7e-Qd7vt3tTdMML9biBENd3qJzZyPEFM16V2hQDQCgTuSUrtotUTIyVqF7EmX_rGiFzSp71X3veA4kms1dH_m4br1zryCgGmy4Scre6c6zreIPXVZAM-t1HQp_1JNbHxj0t3uEEcJrbm0FWzEvNxb2SO-Dt0lQMcAUwaKysUfsc8PEEX-6aGosCsBEFwUKkesQyNnbdJ8qBd4~&fmt=cjson
         * progress_url : http://118.26.58.36/api/uploadprogress?token=bWGy_GtrnV9ItxdYG_yJCYVYMJa_-zBTdtsPCU4RvtLzPHuKiKuZPacKwOzDpSE4lTrG0hNQEGwaXtB7jQbEtOi78Ki35mGw19Uh5S9twUVTwfXw0-IE3rVccJVHNLizgN9IU89VqXOFwsQ8fSeWUiRNU-DgDq_5eB94x0AD5Me5cppTOIQLX5Y7dsjWDqqj-4DWLX6UwKfw4Wv72bCceEE0iRJJ6F7e-Qd7vt3tTdMML9biBENd3qJzZyPEFM16V2hQDQCgTuSUrtotUTIyVqF7EmX_rGiFzSp71X3veA4kms1dH_m4br1zryCgGmy4Scre6c6zreIPXVZAM-t1HQp_1JNbHxj0t3uEEcJrbm0FWzEvNxb2SO-Dt0lQMcAUwaKysUfsc8PEEX-6aGosCsBEFwUKkesQyNnbdJ8qBd4~&fmt=cjson
         * token : bWGy_GtrnV9ItxdYG_yJCYVYMJa_-zBTdtsPCU4RvtLzPHuKiKuZPacKwOzDpSE4lTrG0hNQEGwaXtB7jQbEtOi78Ki35mGw19Uh5S9twUVTwfXw0-IE3rVccJVHNLizgN9IU89VqXOFwsQ8fSeWUiRNU-DgDq_5eB94x0AD5Me5cppTOIQLX5Y7dsjWDqqj-4DWLX6UwKfw4Wv72bCceEE0iRJJ6F7e-Qd7vt3tTdMML9biBENd3qJzZyPEFM16V2hQDQCgTuSUrtotUTIyVqF7EmX_rGiFzSp71X3veA4kms1dH_m4br1zryCgGmy4Scre6c6zreIPXVZAM-t1HQp_1JNbHxj0t3uEEcJrbm0FWzEvNxb2SO-Dt0lQMcAUwaKysUfsc8PEEX-6aGosCsBEFwUKkesQyNnbdJ8qBd4~
         * uploadtype : 0
         * isdrm : 0
         */

        private String video_id;
        private String video_unique;
        private String upload_url;
        private String progress_url;
        private String token;
        private int uploadtype;
        private int isdrm;

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }

        public void setVideo_unique(String video_unique) {
            this.video_unique = video_unique;
        }

        public void setUpload_url(String upload_url) {
            this.upload_url = upload_url;
        }

        public void setProgress_url(String progress_url) {
            this.progress_url = progress_url;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public void setUploadtype(int uploadtype) {
            this.uploadtype = uploadtype;
        }

        public void setIsdrm(int isdrm) {
            this.isdrm = isdrm;
        }

        public String getVideo_id() {
            return video_id;
        }

        public String getVideo_unique() {
            return video_unique;
        }

        public String getUpload_url() {
            return upload_url;
        }

        public String getProgress_url() {
            return progress_url;
        }

        public String getToken() {
            return token;
        }

        public int getUploadtype() {
            return uploadtype;
        }

        public int getIsdrm() {
            return isdrm;
        }
    }
}
