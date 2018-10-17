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

package darks.log.pattern;

import darks.log.LogMessage;

/**
 * Indicate to convert message to target pattern format.
 * <p/>
 * ConvertPattern.java
 *
 * @author Liu lihua
 * @version 1.0.0
 */
public interface ConvertPattern {

    /**
     * Set pattern string
     *
     * @param pattern Pattern string
     */
    boolean setPattern(String pattern);

    /**
     * Format message by pattern
     *
     * @param message Log message
     * @return Log message after formated
     */
    String format(LogMessage message);

}