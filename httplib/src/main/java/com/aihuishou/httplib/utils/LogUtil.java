package com.aihuishou.httplib.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类名称：LogUtil
 * 创建者：Create by liujc
 * 创建时间：Create on 2016/11/15 13:39
 * 描述：log工具类
 */
public class LogUtil {
    public static final String LOG_D = "D";
    public static final String LOG_I = "I";
    public static final String LOG_E = "E";
    public static final String LOG_BEGIN = "begin";
    public static final String LOG_END = "end";
    private static Boolean LOG_SWITCH = true; // 日志文件总开关
    private static Boolean LOG_TO_FILE = true; // 日志写入文件开关
    private static String LOG_TAG = "LogUtil"; // 默认的tag
    private static char LOG_TYPE = 'v';// 输入日志类型，v代表输出所有信息,w则只输出警告...
    private static int LOG_SAVE_DAYS = 7;// sd卡中日志文件的最多保存天数

    private final static SimpleDateFormat LOG_FORMAT = new SimpleDateFormat("yyyyMMdd HH:mm:ss.SSS");// 日志的输出格式
    private final static SimpleDateFormat FILE_SUFFIX = new SimpleDateFormat("yyyy-MM-dd");// 日志文件格式
    private static String LOG_FILE_PATH = getLogFilePath(); // 日志文件保存路径
    private static String LOG_FILE_NAME  = "Log";// 日志文件保存名称前缀
    public static final String LOG_FILE_EXTENSION = ".txt";

    public LogUtil() {
    }

    public static int d(String msg) {
        if (LOG_SWITCH){
            String[] classMethod = getClassMethod(new Exception(), LOG_TAG, LOG_D);
            return d(classMethod[0], classMethod[1], msg);
        }else {
            return 0;
        }
    }

    public static int d(String tag,String msg) {
        if (LOG_SWITCH){
            String[] classMethod = getClassMethod(new Exception(), LOG_TAG, LOG_D);
            return d(tag, classMethod[1], msg);
        }else {
            return 0;
        }
    }
    public static int d(String tag, String method, String msg) {
        int ret1 = Log.d(tag, method + "#" + msg);
        if (LOG_TO_FILE){
            saveLogToFile("D", tag, method, "#", msg);
        }
        return ret1;
    }

    public static int i(String tag, String method, String sipId, String status, String msg) {
        int ret1 = Log.i(tag, method + "-" + sipId + "-" + status + "-" + msg);
        if (LOG_TO_FILE)
            saveLogToFile("I", tag, method, sipId, status, msg);
        return ret1;
    }

    public static int begin(String msg) {
        String[] classMethod = getClassMethod(new Exception(), LOG_TAG, LOG_BEGIN);
        return i(classMethod[0], classMethod[1], "#", LOG_BEGIN, msg);
    }

    public static int end(String msg) {
        String[] classMethod = getClassMethod(new Exception(), LOG_TAG, LOG_END);
        return i(classMethod[0], classMethod[1], "#", LOG_END, msg);
    }

    public static int e(String msg, Throwable e) {
        if (LOG_SWITCH){
            String[] classMethod = getClassMethod(new Exception(), LOG_TAG, LOG_E);
            return e(classMethod[0], classMethod[1], msg, e);
        }else {
            return 0;
        }
    }

    public static int e(String tag, String method, String msg, Throwable e) {
        int ret1 = Log.e(tag, method + "#" + msg, e);
        if (LOG_TO_FILE)
            saveLogToFile(LOG_E, tag, method, "#", msg + ":" + e.getLocalizedMessage());
        return ret1;
    }

    public static void saveLogToFile(String logLevel, String tag, String method, String sipId, String strMessage) {
        saveLogToFile(logLevel, tag, method, sipId, "", strMessage);
    }

    public static String[] getClassMethod(Exception e, String defaultClass,
                                          String defaultMethod) {
        String methodName = "";
        String className = "";
        StackTraceElement el = null;
        try {
            el = e.getStackTrace()[1];
            className = el.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
            methodName = el.getMethodName();
        } catch (Exception ex) {
            if (TextUtils.isEmpty(className)) {
                className = defaultClass;
            }
            if (TextUtils.isEmpty(methodName)) {
                methodName = defaultMethod;
            }
        }
        el = null;
        return new String[] { className, methodName };
    }

    public static String getLogFilePath() {
        String sdCardPath = getSDPath();
        if (TextUtils.isEmpty(sdCardPath)) {
            return "";
        } else {
            return sdCardPath + File.separator + "airent"
                    + File.separator + "log";
        }
    }
    public static String getSDPath() {
        boolean sdCardExist = Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
        if (sdCardExist) {
            return Environment.getExternalStorageDirectory().toString();
        } else {
            return "";
        }
    }

    public synchronized static void saveLogToFile(String logLevel, String tag,
                                           String method, String sipId, String status, String content) {

        if (TextUtils.isEmpty(LOG_FILE_PATH)) {
            Log.d("FileService", "filePath is null");
            return;
        }
        File dest = new File(LOG_FILE_PATH);
        if (!dest.exists()) {
            if (!dest.mkdirs()) {
                return;
            }
        }
        dest = null;

        Date nowtime = new Date();
        String curDate = FILE_SUFFIX.format(nowtime);
        String date = LOG_FORMAT.format(nowtime);
        StringBuilder logCon = new StringBuilder();
        logCon.append("[").append(date).append("-").append(logLevel)
                .append("-").append(android.os.Process.myPid()).append("-").append(tag)
                .append("-").append(method).append("-").append(sipId);
        if (!TextUtils.isEmpty(status)) {
            logCon.append("-").append(status);
        }
        logCon.append("]").append(content);

        File curLogFile = new File(LOG_FILE_PATH, LOG_FILE_NAME + curDate+LOG_FILE_EXTENSION);
        try {
            if (!curLogFile.exists()) {
                curLogFile.createNewFile();
            }

            FileWriter filerWriter = new FileWriter(curLogFile, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(logCon.toString());
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (Exception e) {
            Log.e("FileService", "write log file error", e);
        }
    }
}
