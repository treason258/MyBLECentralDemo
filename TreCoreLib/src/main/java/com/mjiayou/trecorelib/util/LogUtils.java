package com.mjiayou.trecorelib.util;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Log输出封装--OK
 * <p>
 * Created by treason on 16/5/14.
 */
public class LogUtils {

    private static final String TAG = LogUtils.class.getSimpleName();
    private static final String TAG_TRACE_TIME = "trace_time";
    private static final String TAG_LIFE_CYCLE = "life_cycle";

    private static boolean mShow = true; // 配置是否显示LOG，默认显示
    private static boolean mShowPath = false; // 配置是否显示路径，默认隐藏
    private static int mPathLine = 5; // 配置显示路径的行数，默认5行
    private static boolean mShowLifeRecycle = false; // 配置是否显示生命周期，默认隐藏

    // ******************************** 开关 ********************************

    /**
     * 配置是否显示LOG
     */
    public static void setEnable(boolean show) {
        mShow = show;
    }

    /**
     * 配置是否显示路径
     */
    public static void setShowPath(boolean show) {
        mShowPath = show;
    }

    /**
     * 配置显示路径的行数
     */
    public static void setPathLine(int pathLine) {
        mPathLine = pathLine;
    }

    /**
     * 配置是否显示生命周期
     */
    public static void setShowLifeRecycle(boolean show) {
        mShowLifeRecycle = show;
    }

    // ******************************** 封装 ********************************

    /**
     * Send a VERBOSE log message.
     */
    public static void v(String tag, String msg) {
        try {
            if (mShow) {
                Log.v(TAG + "-" + tag, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void v(String msg) {
        try {
            if (mShow) {
                Log.v(TAG, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * Send a DEBUG log message.
     */
    public static void d(String tag, String msg) {
        try {
            if (mShow) {
                Log.d(TAG + "-" + tag, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void d(String msg) {
        try {
            if (mShow) {
                Log.d(TAG, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * Send an INFO log message.
     */
    public static void i(String tag, String msg) {
        try {
            if (mShow) {
                Log.i(TAG + "-" + tag, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void i(String msg) {
        try {
            if (mShow) {
                Log.i(TAG, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * Send a WARN log message
     */
    public static void w(String tag, String msg) {
        try {
            if (mShow) {
                Log.w(TAG + "-" + tag, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void w(String msg) {
        try {
            if (mShow) {
                Log.w(TAG, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * Send an ERROR log message.
     */
    public static void e(String tag, String msg) {
        try {
            if (mShow) {
                Log.e(TAG + "-" + tag, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void e(String msg) {
        try {
            if (mShow) {
                Log.e(TAG, buildMessage(msg));
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void e(String tag, String msg, Throwable throwable) {
        try {
            if (mShow) {
                Log.e(TAG + "-" + tag, buildMessage(msg), throwable);
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void e(String msg, Throwable throwable) {
        try {
            if (mShow) {
                Log.e(TAG, buildMessage(msg), throwable);
            }
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * Building Message
     */
    private static String buildMessage(String msg) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(msg);
            if (mShowPath) {
                for (int i = 0; i < mPathLine; i++) {
                    if (i == 0 || i == 1) { // 因为封装过程中已经有两层为固定的
                        continue;
                    }

                    StackTraceElement caller = new Throwable().fillInStackTrace().getStackTrace()[i];
                    if (caller == null) {
                        continue;
                    }

                    builder.append("\n");
                    builder.append("at ");
                    builder.append(caller.getClassName());
                    builder.append(".");
                    builder.append(caller.getMethodName());
                    builder.append("(");
                    builder.append(caller.getFileName());
                    builder.append(":");
                    builder.append(caller.getLineNumber());
                    builder.append(")");
                }
            }
            return builder.toString();
        } catch (Exception e) {
            printStackTrace(e);
            return "";
        }
    }

    // ******************************** TraceTime ********************************

    private static Stack<Long> traceTimeStack = new Stack<>();

    public static void traceStart(String tag) {
        try {
            traceTimeStack.push(System.currentTimeMillis());
            LogUtils.i(TAG_TRACE_TIME + "-" + tag, "startTime = " + System.currentTimeMillis());
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void traceStop(String tag) {
        try {
            if (traceTimeStack.isEmpty()) {
                LogUtils.e("traceTimeStack.isEmpty()");
                return;
            }
            long startTime = traceTimeStack.pop();
            long durationTime = System.currentTimeMillis() - startTime;
            LogUtils.i(TAG_TRACE_TIME + "-" + tag, "endTime = " + System.currentTimeMillis());
            LogUtils.i(TAG_TRACE_TIME + "-" + tag, "durationTime = " + durationTime + "ms");
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    public static void traceReset() {
        try {
            traceTimeStack.clear();
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    // ******************************** 定制 ********************************

    /**
     * 打印声明周期
     */
    public static void printLifeRecycle(String tag, String msg) {
        if (mShowLifeRecycle) {
            LogUtils.i(tag, TAG_LIFE_CYCLE + " | " + msg);
        }
    }

    /**
     * 打印初始化信息
     */
    public static void printInit(String module) {
        try {
            LogUtils.i(module, "初始化数据 -> " + module);
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * 打印ActivityManager
     */
    public static void printActivityList(LinkedList<Activity> activityList) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append("printActivityList | size -> ").append(activityList.size());
            for (int i = 0; i < activityList.size(); i++) {
                builder.append("\n").append(i).append(" -> ").append(activityList.get(i).getClass().getSimpleName());
            }
            LogUtils.i(builder.toString());
        } catch (Exception e) {
            printStackTrace(e);
        }
    }

    /**
     * 打印异常信息
     */
    public static void printStackTrace(Throwable throwable) {
        throwable.printStackTrace();
        try {
            LogUtils.e(buildMessage("printStackTrace"), throwable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}