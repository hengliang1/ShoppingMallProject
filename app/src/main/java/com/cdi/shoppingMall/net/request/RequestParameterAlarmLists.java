package com.cdi.shoppingMall.net.request;

/**
 * demo 请求参数
 * Created by liang.heng on 2017/2/18.
 */

public class RequestParameterAlarmLists {
    /**
     * access_token : e0Y1NUFCQ0E1LTEyQTktRjJEQS00MjAzLTY2Rjc5MkM1NUEyRn1LUlhlZFJhZjJw
     * data : {"user_uuid":"684CA859-15A9-41F7-93A9-B0BA7D6D1EAE","begin_timestamp":1484113396,"end_timestamp":1484114000,"count":72,"page":1}
     */

    private String access_token;
    private DataBean data;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * user_uuid : 684CA859-15A9-41F7-93A9-B0BA7D6D1EAE  必须
         * begin_timestamp : 1484113396   非必须
         * end_timestamp : 1484114000    非必须
         * count : 72   非必须
         * page : 1  非必须
         */

        private String user_uuid;
        private int begin_timestamp;
        private int end_timestamp;
        private int count;
        private int page;

        public String getUser_uuid() {
            return user_uuid;
        }

        public void setUser_uuid(String user_uuid) {
            this.user_uuid = user_uuid;
        }

        public int getBegin_timestamp() {
            return begin_timestamp;
        }

        public void setBegin_timestamp(int begin_timestamp) {
            this.begin_timestamp = begin_timestamp;
        }

        public int getEnd_timestamp() {
            return end_timestamp;
        }

        public void setEnd_timestamp(int end_timestamp) {
            this.end_timestamp = end_timestamp;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }
    }
}
