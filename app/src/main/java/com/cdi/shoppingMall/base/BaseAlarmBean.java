package com.cdi.shoppingMall.base;

/**
 * Created by jiao.zhu on 2017/1/7.
 * 基本报警类型
 */

public class BaseAlarmBean extends BaseRaiingBean {

    /**
     * 用户关联属性
     */
    private String uuid;
    /**
     * 时间戳，单位s
     */
    private long startTime;
    /**
     * 时间戳，单位s, 暂定和报警开始相同
     */
    private long endTime;
    /**
     * 时区 单位s
     */
    private int timeZone;
    /**
     * 报警当前数值 默认为0
     */
    private int value;
    /**
     * 生理报警1，技术报警2 
     */
    private int alarmType;
    /*   //报警code， 本地算法的返回的Code
       private int alarmCode;*/


//    /**
//     * 数据名称编码 新添加参数
//     */
//    private int alarmCode;

    /**
     * 是否开启报警  新添加参数
     */
    private int alarmFlag;

    /**
     * 报警来源
     * ecg->1
     * resp->2
     * temp->3
     * b2w电源->4
     * 下位机电源->5
     * 心电->6
     * 呼吸->7
     * 心电／呼吸传感器->8
     * 心电／呼吸传感器->9
     * 体温->10
     * 其他->11
     */
    private int alarmSource;

    /**
     * ecg->101,102, 103,104,105,106,107
     * resp->201, 202， 203
     * temp,
     * b2w电源
     * ....
     * 其他->1101, 1102
     * <p>
     * 每种报警来源下包含几种不同的报警类型，对于不同的报警类型， 也进行了统一的编码。 报警来源1(ECG)，对应的子报警类型编码为101,102..  后两位用于区分具体的时间类型。 前面的数值用于区分报警来源
     */
    private int alarmName;

    /**
     * 报警级别
     * 高 1, 中 2, 低 3
     */
    private int alarmLevel;

    /**
     * 报警配置
     * ECG室速: {“时速”：130}
     * HR报警设置： {“报警高限”：30，“报警低限”：8}
     */
    private String alarmConfig;

    /**
     * 算法版本
     * 格式？？？
     */
    private String algVersion;

    /**
     * 备注
     */
    private String remarks;

    // 是否确认报警,取值：1-确认，2-未确认 如果不传默认为2
    private int confirm_tag = 2;


    /**
     * 报警更新时间，单位s
     */
    private long update_time;

    /**
     * 空构造
     */
    public BaseAlarmBean() {
    }


    public BaseAlarmBean(String uuid,
                         long startTime,
                         long endTime,
                         int timeZone,
                         int value,
                         int alarmType,
                         int alarmFlag,
                         int alarmSource,
                         int alarmName,
                         int alarmLevel,
                         String alarmConfig,
                         String algVersion,
                         String remarks,
                         int confirm_tag,
                         long updateTime) {
        this.uuid = uuid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.timeZone = timeZone;
        this.value = value;
        this.alarmType = alarmType;
        this.alarmFlag = alarmFlag;
        this.alarmSource = alarmSource;
        this.alarmName = alarmName;
        this.alarmLevel = alarmLevel;
        this.alarmConfig = alarmConfig;
        this.algVersion = algVersion;
        this.remarks = remarks;
        this.confirm_tag = confirm_tag;
        this.update_time = updateTime;
    }

//    /**
//     * 带参构造
//     *
//     * @param uuid        用户id
//     * @param startTime   开始时间戳
//     * @param endTime     结束时间戳
//     * @param timeZone    时区
//     * @param value       报警当前数值
//     * @param alarmType   报警类型
//     * @param alarmSource 报警来源
//     * @param alarmName   报警名称
//     * @param alarmLevel  报警级别
//     * @param alarmConfig 报警配置
//     * @param algVersion  算法版本
//     * @param remarks     备注
//     *                    //     * @param alarmCode   数据名称编码
//     * @param alarmFlag   是否开启报警
//     */
//    public BaseAlarmBean(String uuid,
//                         long startTime,
//                         long endTime,
//                         int timeZone,
//                         int value,
//                         int alarmType,
//                         int alarmSource,
//                         int alarmName,
//                         int alarmLevel,
//                         String alarmConfig,
//                         String algVersion,
//                         String remarks,
////                         int alarmCode,
//                         int alarmFlag) {
//        this.uuid = uuid;
//        this.startTime = startTime;
//        this.endTime = endTime;
//        this.timeZone = timeZone;
//        this.value = value;
//        this.alarmType = alarmType;
//        this.alarmSource = alarmSource;
//        this.alarmName = alarmName;
//        this.alarmLevel = alarmLevel;
//        this.alarmConfig = alarmConfig;
//        this.algVersion = algVersion;
//        this.remarks = remarks;
////        this.alarmCode = alarmCode;
//        this.alarmFlag = alarmFlag;
//    }

    /**
     * get/set方法
     *
     * @return
     */
//    public int getAlarmCode() {
//        return alarmCode;
//    }
//
//    public void setAlarmCode(int alarmCode) {
//        this.alarmCode = alarmCode;
//    }
    public int getAlarmFlag() {
        return alarmFlag;
    }

    public void setAlarmFlag(int alarmFlag) {
        this.alarmFlag = alarmFlag;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(int timeZone) {
        this.timeZone = timeZone;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(int alarmType) {
        this.alarmType = alarmType;
    }

    public int getAlarmSource() {
        return alarmSource;
    }

    public void setAlarmSource(int alarmSource) {
        this.alarmSource = alarmSource;
    }

    public int getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(int alarmName) {
        this.alarmName = alarmName;
    }

    public int getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(int alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmConfig() {
        return alarmConfig;
    }

    public void setAlarmConfig(String alarmConfig) {
        this.alarmConfig = alarmConfig;
    }

    public String getAlgVersion() {
        return algVersion;
    }

    public void setAlgVersion(String algVersion) {
        this.algVersion = algVersion;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getConfirm_tag() {
        return confirm_tag;
    }

    public void setConfirm_tag(int confirm_tag) {
        this.confirm_tag = confirm_tag;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    @Override
    public String toString() {
        return "BaseAlarmBean{" +
                "uuid='" + uuid + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", timeZone=" + timeZone +
                ", value=" + value +
                ", alarmType=" + alarmType +
                ", alarmFlag=" + alarmFlag +
                ", alarmSource=" + alarmSource +
                ", alarmName=" + alarmName +
                ", alarmLevel=" + alarmLevel +
                ", alarmConfig='" + alarmConfig + '\'' +
                ", algVersion='" + algVersion + '\'' +
                ", remarks='" + remarks + '\'' +
                ", confirm_tag=" + confirm_tag +
                ", update_time=" + update_time +
                '}';
    }
}
