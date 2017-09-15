package com.mjiayou.trecorelib.util;

import java.io.Closeable;

/**
 * Created by treason on 16/5/14.
 */
public class StreamUtils {

    public static void closeQuietly(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (Exception e) {
            LogUtils.printStackTrace(e);
        }
    }
}
