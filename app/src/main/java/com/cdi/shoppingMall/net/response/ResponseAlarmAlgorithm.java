package com.cdi.shoppingMall.net.response;

import java.util.List;

/**
 * demo 请求结果
 * Created by liang.heng on 2017/3/14.
 */

public class ResponseAlarmAlgorithm {

    /**
     * message : ok
     * code : 0
     * status : 200
     * data : {"algorithm_data":[{"sn":"12345654","device_data":[{"uuid":"284331A8-DD66-42F4-BB5D-8907B9B65D1F","begin_timestamp":1484113396,"end_timestamp":1484113399,"time_zone":86400,"value":35,"alarm_type":2,"alarm_source":2,"alarm_name":601,"alarm_level":1,"alarm_config":"","alg_version":"11.22.333","alarm_flag":1}]}],"timestamp":1489718928023}
     */

    private String message;
    private int code;
    private int status;
    private ResponseAlarmAlgorithm.DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ResponseAlarmAlgorithm.DataBean getData() {
        return data;
    }

    public void setData(ResponseAlarmAlgorithm.DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * algorithm_data : [{"sn":"12345654","device_data":[{"uuid":"284331A8-DD66-42F4-BB5D-8907B9B65D1F","begin_timestamp":1484113396,"end_timestamp":1484113399,"time_zone":86400,"value":35,"alarm_type":2,"alarm_source":2,"alarm_name":601,"alarm_level":1,"alarm_config":"","alg_version":"11.22.333","alarm_flag":1}]}]
         * timestamp : 1489718928023
         */

        private long timestamp;
        private List<AlgorithmDataBean> algorithm_data;

        public long getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(long timestamp) {
            this.timestamp = timestamp;
        }

        public List<AlgorithmDataBean> getAlgorithm_data() {
            return algorithm_data;
        }

        public void setAlgorithm_data(List<AlgorithmDataBean> algorithm_data) {
            this.algorithm_data = algorithm_data;
        }

        public static class AlgorithmDataBean {
            /**
             * sn : 12345654
             * device_data : [{"uuid":"284331A8-DD66-42F4-BB5D-8907B9B65D1F","begin_timestamp":1484113396,"end_timestamp":1484113399,"time_zone":86400,"value":35,"alarm_type":2,"alarm_source":2,"alarm_name":601,"alarm_level":1,"alarm_config":"","alg_version":"11.22.333","alarm_flag":1}]
             */

            private String sn;
            private List<DeviceDataBean> device_data;

            public String getSn() {
                return sn;
            }

            public void setSn(String sn) {
                this.sn = sn;
            }

            public List<DeviceDataBean> getDevice_data() {
                return device_data;
            }

            public void setDevice_data(List<DeviceDataBean> device_data) {
                this.device_data = device_data;
            }

            public static class DeviceDataBean {
                /**
                 * uuid : 284331A8-DD66-42F4-BB5D-8907B9B65D1F
                 * begin_timestamp : 1484113396
                 * end_timestamp : 1484113399
                 * time_zone : 86400
                 * value : 35
                 * alarm_type : 2
                 * alarm_source : 2
                 * alarm_name : 601
                 * alarm_level : 1
                 * alarm_config :
                 * alg_version : 11.22.333
                 * alarm_flag : 1
                 * update_time: 1484113399
                 */

                private String uuid;
                private long begin_timestamp;
                private long end_timestamp;
                private int time_zone;
                private int value;
                private int alarm_type;
                private int alarm_source;
                private int alarm_name;
                private int alarm_level;
                private String alarm_config;
                private String alg_version;
                private int alarm_flag;
                private long update_time;

                public String getUuid() {
                    return uuid;
                }

                public void setUuid(String uuid) {
                    this.uuid = uuid;
                }

                public long getBegin_timestamp() {
                    return begin_timestamp;
                }

                public void setBegin_timestamp(long begin_timestamp) {
                    this.begin_timestamp = begin_timestamp;
                }

                public long getEnd_timestamp() {
                    return end_timestamp;
                }

                public void setEnd_timestamp(long end_timestamp) {
                    this.end_timestamp = end_timestamp;
                }

                public int getTime_zone() {
                    return time_zone;
                }

                public void setTime_zone(int time_zone) {
                    this.time_zone = time_zone;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }

                public int getAlarm_type() {
                    return alarm_type;
                }

                public void setAlarm_type(int alarm_type) {
                    this.alarm_type = alarm_type;
                }

                public int getAlarm_source() {
                    return alarm_source;
                }

                public void setAlarm_source(int alarm_source) {
                    this.alarm_source = alarm_source;
                }

                public int getAlarm_name() {
                    return alarm_name;
                }

                public void setAlarm_name(int alarm_name) {
                    this.alarm_name = alarm_name;
                }

                public int getAlarm_level() {
                    return alarm_level;
                }

                public void setAlarm_level(int alarm_level) {
                    this.alarm_level = alarm_level;
                }

                public String getAlarm_config() {
                    return alarm_config;
                }

                public void setAlarm_config(String alarm_config) {
                    this.alarm_config = alarm_config;
                }

                public String getAlg_version() {
                    return alg_version;
                }

                public void setAlg_version(String alg_version) {
                    this.alg_version = alg_version;
                }

                public int getAlarm_flag() {
                    return alarm_flag;
                }

                public void setAlarm_flag(int alarm_flag) {
                    this.alarm_flag = alarm_flag;
                }

                public long getUpdate_time() {
                    return update_time;
                }

                public void setUpdate_time(long update_time) {
                    this.update_time = update_time;
                }
            }
        }
    }
}
