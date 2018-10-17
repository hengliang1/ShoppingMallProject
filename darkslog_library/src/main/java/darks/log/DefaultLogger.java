/**
 * Copyright 2014 The Darks Logs Project (Liu lihua)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package darks.log;

import java.util.Date;

import darks.log.appender.Appender;
import darks.log.appender.AppenderManager;
import darks.log.utils.TimeUtil;

/**
 * Default logger object used to log message and do appenders by default
 * <p/>
 * DefaultLogger.java
 *
 * @author Liu lihua 2014-3-21
 * @version 1.0.0
 */
public class DefaultLogger extends Logger {

    /**
     * logger thread for async appender
     */
    private static volatile LoggerThread thread = new LoggerThread();
    private static Object mutex = new Object();
    private Category category;
    private String tag;

    public DefaultLogger(Category category, String tag) {
        this.category = category;
        this.tag = tag;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void log(Level level, Object msg, Throwable t) {
        try {
            //如果打印的数据过大,会出现OOM异常.此处的解决方案为try catch
            if (category.getLevel().compare(level) > 0) {
                return;
            }
            StackTraceElement el = null;
            if (t == null) {
                el = new Throwable().getStackTrace()[3];
            } else {
                el = new Throwable().getStackTrace()[3];
//                    el = t.getStackTrace()[3];
            }
//            ThrowableInfo info = new ThrowableInfo(el, t);
            ThrowableInfo info = new ThrowableInfo(el, null);
            String message = msg == null ? "null" : msg.toString();
            LogMessage logMsg = buildMessage(level, message, info);
            doLogger(logMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Dispacher log message for doing appenders directly or keep with holder
     * for log thread
     *
     * @param logMsg log message
     */
    private void doLogger(LogMessage logMsg) {
        LoggerHolder holder = null;
        Category cate = category;
        while (cate != null) {
            for (Appender appender : cate.getAppenderMap().values()) {
                if (appender.isAsync()) {
                    if (holder == null) {
                        holder = new LoggerHolder(logMsg);
                    }
                    holder.addAppender(appender);
                } else {
                    appender.doAppend(logMsg);
                }
            }
            if (cate.isInherit()) {
                cate = cate.getParent();
            } else {
                break;
            }
        }
        if (holder != null && !holder.isEmpty()) {
            if (thread == null || !thread.isAlive()) {
                synchronized (mutex) {
                    if (thread == null || !thread.isAlive()) {
                        thread = new LoggerThread();
                        thread.start();
                    }
                }
            }
            LoggerThread.getHolders().offer(holder);
        }
    }

    private LogMessage buildMessage(Level level, String msg, ThrowableInfo info) {
        LogMessage logMsg = new LogMessage();
        logMsg.setCategory(category);
        logMsg.setClassName(info.getCallerClass());
//        logMsg.setDate(new Date());
        //设置log内容的时间为服务器时间
        Date date = new Date();
        date.setTime(TimeUtil.getServerTime());
        logMsg.setDate(date);


        logMsg.setLevel(level);
        logMsg.setMessage(msg);
        logMsg.setNamespace(tag);
        logMsg.setThreadName(Thread.currentThread().getName());
        logMsg.setThrowableInfo(info);
//        logMsg.setTimeStamp(System.currentTimeMillis());

        logMsg.setTimeStamp(TimeUtil.getServerTime());
        return logMsg;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAppender(Appender appender) {
        if (AppenderManager.registerAppender(appender)) {
            category.getAppenderMap().put(appender.getName(), appender);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Appender removeAppender(String name) {
        return category.getAppenderMap().remove(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDebugEnabled() {
        return category != null
                && (category.getLevel().getLevel() <= Level.DEBUG.getLevel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInfoEnabled() {
        return category != null
                && (category.getLevel().getLevel() <= Level.INFO.getLevel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLevelEnabled(Level level) {
        return category != null
                && (category.getLevel().getLevel() <= level.getLevel());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInherit(boolean inheritRoot) {
        if (category != null) {
            category.setInherit(inheritRoot);
        }
    }

}
