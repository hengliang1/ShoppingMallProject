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

package darks.log.utils;

import java.util.Date;

/**
 * StringUtils.java
 *
 * @author Liu lihua
 * @version 1.0.0
 */
public final class StringUtils {
    public static final String LINE_RETURN = System
            .getProperty("line.separator");

    public static final int LINE_RETURN_LEN = LINE_RETURN.length();

    /**
     * Buffer and append strings with StringBuilder
     *
     * @param args Object array
     * @return String
     */
    public static String bufferString(Object... args) {
        if (args.length == 1) {
            return args[0].toString();
        }
        StringBuilder buf = new StringBuilder();
        for (Object arg : args) {
            buf.append(arg);
        }
        return buf.toString();
    }

    public static String replacePropertyVar(String value) {
        StringBuilder buf = new StringBuilder();
        int index = 0;
        int len = value.length();
        while (index < len) {
            char ch = value.charAt(index++);
            switch (ch) {
                case '$':
                    index = parsePropertyVar(buf, value, index);
                    if (index < 0) {
                        return value;
                    }
                    break;
                default:
                    buf.append(ch);
                    break;
            }
        }
        return buf.toString();
    }

    private static int parsePropertyVar(StringBuilder buf, String value,
                                        int index) {
        char ch = value.charAt(index++);
        if (ch != '{') {
            return -1;
        }
        int len = value.length();
        StringBuilder tmp = new StringBuilder();
        boolean end = false;
        for (int i = index; i < len && !end; i++) {
            ch = value.charAt(index++);
            switch (ch) {
                case '}':
                    end = true;
                    break;
                default:
                    tmp.append(ch);
                    break;
            }
        }
        if (!end) {
            return -1;
        }
        String env = tmp.toString();
        if (env.startsWith("D") && env.length() > 1) {
            String pattern = env.substring(1);

            //设置log的.txt文件的时间为服务器时间
            Date date1 = new Date();
            date1.setTime(TimeUtil.getServerTime());
//            String date = TimeUtil.getFormatter(pattern).format(new Date());
            String date = TimeUtil.getFormatter(pattern).format(date1);
            buf.append(date);
        } else {
            String val = System.getProperty(env);// System.getenv().get(env);
            val = (val == null) ? "" : val;
            buf.append(val);
        }
        return index;
    }

    public static String getTagLayer(String tag, int layer) {
        String[] args = tag.split("\\.");
        if (args.length > layer && layer > 0) {
            StringBuilder tagBuf = new StringBuilder(32);
            int len = args.length;
            for (int i = len - layer; i < len; i++) {
                tagBuf.append(args[i]);
                if (i != len - 1) {
                    tagBuf.append('.');
                }
            }
            tag = tagBuf.toString();
        }
        return tag;
    }
}
