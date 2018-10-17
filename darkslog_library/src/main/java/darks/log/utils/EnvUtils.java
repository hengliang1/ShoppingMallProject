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

/**
 * EnvUtils.java
 *
 * @author Liu lihua
 * @version 1.0.0
 */
public final class EnvUtils {

    private EnvUtils() {
    }

    /**
     * Check whether system is andorid
     *
     * @return If android, return true.
     */
    public static boolean isAndroidEnv() {
        try {
            Class.forName("android.text.format.DateFormat");
            Class.forName("android.content.Context");
            Class.forName("android.app.Application");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
