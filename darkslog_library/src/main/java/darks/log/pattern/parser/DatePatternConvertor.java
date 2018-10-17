/**
 * Copyright 2014 The Darks Logs Project
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

package darks.log.pattern.parser;

import darks.log.LogMessage;
import darks.log.utils.TimeUtil;
import darks.log.utils.time.DateFormater;

/**
 * Format date pattern
 * <p/>
 * DatePatternConvertor.java
 *
 * @author Liu lihua
 * @version 1.0.0
 */
public class DatePatternConvertor extends PatternConvertor {

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private DateFormater formater;

    public DatePatternConvertor() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean format(StringBuilder buf, LogMessage message) {
        if (message == null || message.getDate() == null) {
            return false;
        }
        String pattern = getToken();
        if (pattern == null || "".equals(pattern.trim())) {
            pattern = DEFAULT_PATTERN;
        } else {
            pattern = pattern.trim();
        }
        if (formater == null) {
            formater = TimeUtil.getFormatter(pattern);
        }
        buf.append(formater.format(message.getDate()));
        return true;
    }

}
