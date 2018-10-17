package com.cdi.shoppingMall.common;


import android.os.Environment;

import java.io.File;

/**
 * 本地缓存相关的常量
 * Created by jun on 2016/11/2.
 */
public class FileConstant {
    /**
     * SD卡的根路径
     */
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    /**
     * 程序的名字
     */
    public static final String APP_NAME = "";
    /**
     * 所有数据存储的根路径
     */
    public static final String ROOT_PATH = SD_PATH + APP_NAME + File.separator;
    /**
     * 网络数据请求保存本地的路径
     */
    public static final String NET_LOG = ROOT_PATH + "net" + File.separator;
    /**
     * log存储的根路径
     */
    public static final String LOG_PATH = ROOT_PATH + "log" + File.separator;
    /**
     * log删除的根路径
     */
    public static final String LOG_PATH_DELETE = ROOT_PATH + "log";
    /**
     * PATH_LOGZIP: 已经压缩过的zip,未上传过服务器的放在此文件夹下面 *
     */
    public static String LOG_UNUPLOAD_PATH = LOG_PATH + "unUpload" + File.separator;

    /**
     * log文件大小 单位MB
     */
    public static final int FREE_LEAST_SIZE = 1024;

    /**
     * 检查log文件大小的周期2小时 单位毫秒
     */
    public static final int DEFAULT_GET_SDCARD_SIZE = 2 * 60 * 60 * 1000;
}
