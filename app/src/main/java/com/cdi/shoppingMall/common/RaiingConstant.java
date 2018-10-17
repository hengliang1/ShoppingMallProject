package com.cdi.shoppingMall.common;


import android.os.Environment;

import java.io.File;

/**
 * Created by jiao.zhu on 2016/12/1.
 * 常量类
 */

public class RaiingConstant {

    /**
     * SD卡的根路径
     */
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;

    /**
     * 文件缓存路径
     */
    public static final String BASE_SIMULATION_DATA_PATH = "/data/data/com.raiing.salad/files/simulation/";

    /**
     * 程序的名字
     */
    private static final String APP_NAME = "";
    /**
     * 所有数据存储的根路径
     */
    public static final String ROOT_PATH = SD_PATH + APP_NAME + File.separator;
}
