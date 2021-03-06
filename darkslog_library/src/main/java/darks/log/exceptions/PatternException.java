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

package darks.log.exceptions;

/**
 * PatternException.java
 *
 * @author Liu lihua
 * @version 1.0.0
 */
public class PatternException extends LogException {

    private static final long serialVersionUID = 3212318854403981539L;

    public PatternException() {
        super();
    }

    public PatternException(String message, Throwable cause) {
        super(message, cause);
    }

    public PatternException(String message) {
        super(message);
    }

    public PatternException(Throwable cause) {
        super(cause);
    }

}
