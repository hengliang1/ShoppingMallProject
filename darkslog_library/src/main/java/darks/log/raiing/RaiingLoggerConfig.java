package darks.log.raiing;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import darks.log.Logger;
import darks.log.LoggerConfig;
import darks.log.LoggerFactory;
import darks.log.LoggerHolder;
import darks.log.LoggerThread;
import darks.log.appender.Appender;
import darks.log.appender.AppenderManager;
import darks.log.externs.AndroidCrashHandler;
import darks.log.loader.ConfigLoader;

/**
 * Created by damon on 6/3/15.
 */
public class RaiingLoggerConfig {

    public static final String TAG = "darkslog-->>";
    /**
     * category的tag
     */
    public static final String TAG_CATEGROY_NAME = "rt";
    /**
     * log文件和crash文件存储路径,必须指定,否则崩溃
     */
    public static String mLogPath;


    /**
     * 内部输出的logger对象
     */
    protected static Logger logger;


    /**
     * 初始化,或切换log存储路径时,必须调用此方法.
     *
     * @param logPath     log文件和crash文件存储路径(不能为空)
     * @param application Context 记录crash使用(PS: 是application级别的Context)
     */
    public static void initConfig(String logPath, Application application) {
        if (null == logPath || logPath.isEmpty()) {
            throw new IllegalArgumentException("log的存储路径为空");
        }
        String logPropertiesPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        mLogPath = logPath;
        try {
            saveLogProperties2SdCard(logPath, logPropertiesPath);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NullPointerException("保存log.properties文件失败");
        }
        AppenderManager.appenders = new ConcurrentHashMap<String, Appender>();
        LoggerThread.holders = new ConcurrentLinkedQueue<LoggerHolder>();
        Logger.Config = new LoggerConfig();
        Logger.Android.setApplication(application);
        File logProperties = new File(logPropertiesPath, "log.properties");
        Logger.Android.setConfigPath(logProperties.getAbsolutePath());
        LoggerFactory.loader = new ConfigLoader();
        LoggerFactory.inited = LoggerFactory.loader.initConfig();

        logger = Logger.getLogger(TAG_CATEGROY_NAME);
        AndroidCrashHandler.log = Logger.getLogger(TAG_CATEGROY_NAME);
        AndroidCrashHandler.instance = null;
        //将crash文件append到该log文件下
        Logger.Android.registerCrashHandler();
        boolean isSuccess3 = logProperties.delete();
        RaiingLog.d("RaiingLog加载完毕");
        if (!isSuccess3) {
            Log.d(TAG, "删除log.properties文件失败");
        }
    }

    /**
     * * @param logPath           log的存储路径(不能为空)
     *
     * @param logPropertiesPath log.properties的存储路径(不能为空)
     *                          将log.properties存储到本地.
     */
    private static void saveLogProperties2SdCard(String logPath, String logPropertiesPath) throws Exception {

//        String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath();
//        String rootFilePath = rootPath + File.separator + "test";
//        File rootFile = new File(rootFilePath);
//        if (!rootFile.exists()) {
//            boolean isSuccess = rootFile.mkdir();
//            Log.d(TAG, "创建文件夹是否成功-->>" + isSuccess + ", path-->>" + rootFilePath);
//            if (!isSuccess) {
//                throw new Exception("创建文件夹失败,路径-->>"+rootFile.getAbsolutePath());
//            }
//        }
//        File logProperties = new File(rootFilePath, "log.properties");
        File logProperties = new File(logPropertiesPath, "log.properties");
        File logFileRoot = new File(logPath);
        if (!logFileRoot.isDirectory() || !logFileRoot.exists()) {
            boolean isSuccess = logFileRoot.mkdirs();
            if (!isSuccess) {
                throw new Exception("创建文件夹失败,路径-->>" + logFileRoot.getAbsolutePath());
            }
        }
        Log.d(TAG, "log.properties的路径为-->>" + logProperties.getAbsolutePath());
        if (!logProperties.exists()) {
            boolean isSuccess2 = logProperties.createNewFile();
//                Log.d(TAG, "创建文件是否成功2-->>" + isSuccess2 + ", path-->>" + logProperties.getAbsolutePath());
            if (!isSuccess2) {
                throw new Exception("创建文件夹失败,路径-->>" + logProperties.getAbsolutePath());
            }
        }
        FileOutputStream fos = new FileOutputStream(logProperties);
        StringBuilder builder = new StringBuilder();
        builder.append("logd.root=verbose,console\n");
        builder.append("logd.logger.rt=verbose,file\n");
        builder.append("\n");
        builder.append("logd.appender.console=ConsoleAppender  \n");
        builder.append("logd.appender.console.layout=PatternLayout  \n");
        builder.append("logd.appender.console.layout.convertor=DefaultPattern  \n");
        builder.append("logd.appender.console.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} [%p]  %c %F %L-->>%m%n\n");
        builder.append("logd.appender.console.async=false  \n");
        builder.append("logd.appender.console.filter=LevelRangeFilter  \n");
        builder.append("logd.appender.console.filter.levelMin=debug  \n");
        builder.append("logd.appender.console.filter.levelMax=error  \n");
        builder.append("logd.appender.console.filter.accept=false  \n");
        builder.append("\n");
        builder.append("logd.appender.file=FileDateSizeAppender  \n");
        builder.append("logd.appender.file.layout=PatternLayout  \n");
        builder.append("logd.appender.file.layout.pattern=%d{yyyy-MM-dd HH:mm:ss} [%p]  %c %F %L-->>%m%n\n");
        builder.append("logd.appender.file.fileName=");
        builder.append(logPath);
        builder.append("/${Dyyyy-MM-dd-HH-mm-ss}.txt");
        builder.append("\n");
//            builder.append("logd.appender.file.fileName=${sdcard}/ifertracker/log/${Dyyyy-MM-dd-HH-mm-ss}.txt\n");
        builder.append("logd.appender.file.buffered=false  \n");
        builder.append("logd.appender.file.maxSize=10485760  \n");
        builder.append("logd.appender.file.keepDay=30");

        fos.write(builder.toString().getBytes("utf-8"));
        fos.close();

    }

}
