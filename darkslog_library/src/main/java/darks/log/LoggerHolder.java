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

import java.util.LinkedList;
import java.util.List;

import darks.log.appender.Appender;

/**
 * Appenders's holder for async appender. It will be handled in logger thread.
 * LoggerHolder.java
 *
 * @author Liu lihua 2014-3-21
 * @version 1.0.0
 * @see darks.log.LoggerThread
 */
public class LoggerHolder {

    private List<Appender> appenders;

    private LogMessage msg;

    public LoggerHolder(LogMessage msg) {
        this.msg = msg;
        appenders = new LinkedList<Appender>();
    }

    public List<Appender> getAppenders() {
        return appenders;
    }

    public void addAppender(Appender appender) {
        appenders.add(appender);
    }

    public LogMessage getMsg() {
        return msg;
    }

    public void setMsg(LogMessage msg) {
        this.msg = msg;
    }

    public boolean isEmpty() {
        return appenders.isEmpty();
    }
}
