package darks.log.raiing;

/**
 * Created by damon on 6/3/15.
 * 将log输出到console和 SdCard 的 File中
 */
public class RaiingLog {

    /**
     * verbose级别
     *
     * @param msg 输出内容
     */
    public static void v(Object msg) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.verbose(msg, new Throwable());
    }

    /**
     * debug级别
     *
     * @param msg 输出内容
     */
    public static void d(Object msg) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.debug(msg, new Throwable());
    }

    /**
     * info级别
     *
     * @param msg 输出内容
     */
    public static void i(Object msg) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.info(msg, new Throwable());
    }

    /**
     * wran级别
     *
     * @param msg 输出内容
     */
    public static void w(Object msg) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.warn(msg, new Throwable());
    }

    /**
     * error级别
     *
     * @param msg 输出内容
     */
    public static void e(Object msg) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.error(msg, new Throwable());
    }

    /**
     * debug级别
     *
     * @param msg 输出内容
     */
    public static void d(Object msg, Throwable throwable) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.debug(msg, throwable);
    }

    /**
     * verbose级别
     *
     * @param msg 输出内容
     */
    public static void v(Object msg, Throwable throwable) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.verbose(msg, throwable);
    }

    /**
     * info级别
     *
     * @param msg 输出内容
     */
    public static void i(Object msg, Throwable throwable) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.info(msg, throwable);
    }

    /**
     * wran级别
     *
     * @param msg 输出内容
     */
    public static void w(Object msg, Throwable throwable) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.warn(msg, throwable);
    }

    /**
     * error级别
     *
     * @param msg 输出内容
     */
    public static void e(Object msg, Throwable throwable) {
        if (RaiingLoggerConfig.logger == null) {
            throw new NullPointerException("请先调用RaiingLoggerConfig.initConfig ,以初始化logger对象");
        }
        RaiingLoggerConfig.logger.error(msg, throwable);
    }


}
