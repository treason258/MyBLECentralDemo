package com.mjiayou.trecorelib.util;

import android.app.Activity;
import android.util.Log;

import java.util.LinkedList;
import java.util.Stack;

/**
 * Log输出封装
 * <p>
 * Created by treason on 16/5/14.
 */
public class LogUtil {

    private static final String TAG = LogUtil.class.getSimpleName();

    private static boolean mShow = true; // 配置是否显示LOG，默认显示
    private static boolean mShowPath = false; // 配置是否显示路径，默认隐藏
    private static int mPathLine = 3; // 配置显示路径的行数，默认3行

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
     * Send a VERBOSE log message.
     */
    public static void v(String tag, String msg) {
        if (mShow) {
            Log.v(TAG + "-" + tag, buildMessage(msg));
        }
    }

    public static void v(String msg) {
        if (mShow) {
            Log.v(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBUG log message.
     */
    public static void d(String tag, String msg) {
        if (mShow) {
            Log.d(TAG + "-" + tag, buildMessage(msg));
        }
    }

    public static void d(String msg) {
        if (mShow) {
            Log.d(TAG, buildMessage(msg));
        }
    }

    /**
     * Send an INFO log message.
     */
    public static void i(String tag, String msg) {
        if (mShow) {
            Log.i(TAG + "-" + tag, buildMessage(msg));
        }
    }

    public static void i(String msg) {
        if (mShow) {
            Log.i(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message
     */
    public static void w(String tag, String msg) {
        if (mShow) {
            Log.w(TAG + "-" + tag, buildMessage(msg));
        }
    }

    public static void w(String msg) {
        if (mShow) {
            Log.w(TAG, buildMessage(msg));
        }
    }

    /**
     * Send an ERROR log message.
     */
    public static void e(String tag, String msg, Throwable throwable) {
        if (mShow) {
            Log.e(TAG + "-" + tag, buildMessage(msg), throwable);
        }
    }

    public static void e(String tag, String msg) {
        if (mShow) {
            Log.e(TAG + "-" + tag, buildMessage(msg));
        }
    }

    public static void e(String msg) {
        if (mShow) {
            Log.e(TAG, buildMessage(msg));
        }
    }

    public static void e(String msg, Throwable throwable) {
        if (mShow) {
            Log.e(TAG, buildMessage(msg), throwable);
        }
    }

    /**
     * TraceTime
     */
    public static final String TAG_TRACE_TIME = "TraceTime";

    private static Stack<Long> traceTimeStack = new Stack<>();

    public static void traceStart(String tag) {
        traceTimeStack.push(System.currentTimeMillis());
        LogUtil.i(TAG_TRACE_TIME + "-" + tag, "startTime = " + System.currentTimeMillis());
    }

    public static void traceStop(String tag) {
        if (traceTimeStack.isEmpty()) {
            LogUtil.e("traceTimeStack.isEmpty()");
            return;
        }
        long startTime = traceTimeStack.pop();
        long durationTime = System.currentTimeMillis() - startTime;
        LogUtil.i(TAG_TRACE_TIME + "-" + tag, "endTime = " + System.currentTimeMillis());
        LogUtil.i(TAG_TRACE_TIME + "-" + tag, "durationTime = " + durationTime + "ms");
    }

    public static void traceReset() {
        traceTimeStack.clear();
    }

    /**
     * 打印初始化信息
     */
    public static void printInit(String module) {
        LogUtil.i(module, "初始化数据 -> " + module);
    }

    /**
     * 打印异常信息
     */
    public static void printStackTrace(Throwable throwable) {
        LogUtil.e(buildMessage("printStackTrace"), throwable);
        throwable.printStackTrace();
    }

    /**
     * 打印 ActivityManager
     */
    public static void printActivityList(LinkedList<Activity> activityList) {
        StringBuilder builder = new StringBuilder();
        builder.append("printActivityList | size -> ").append(activityList.size());
        for (int i = 0; i < activityList.size(); i++) {
            builder.append("\n").append(i).append(" -> ").append(activityList.get(i).getClass().getSimpleName());
        }
        LogUtil.i(builder.toString());
    }

    /**
     * Building Message
     */
    private static String buildMessage(String msg) {
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
    }
}