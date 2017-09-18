package com.aihuishou.commonlib.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 类名称：CommonUtil
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/14
 * 描述：通用工具类
 */
public class CommonUtil {
    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean isListEmpty( List list ) {
        if( list != null && list.size() > 0 ) {
            return false;
        }

        return true;
    }

    public static boolean isInList( Object object, List objectList ) {
        boolean ret = false;

        if( !isListEmpty(objectList) ) {
            for( Object o : objectList ) {
                if( o.equals( object ) ) {
                    ret = true;
                    break;
                }
            }
        }

        return ret;
    }

    /**
     *
     * @param context
     * @param url  跳转到指定应用的url
     */
    public static void jumpToTargetApp(Context context,String url){
        if (hasApplication(context, url)) {
            Intent action = new Intent(Intent.ACTION_VIEW);
            action.setData(Uri.parse(url));
            context.startActivity(action);
        }else {
            ToastUitl.showToast("还未安装该应用");
        }
    }
    /**
     *
     * @param context
     * @param url
     * @return
     */
    private static boolean hasApplication(Context context, String url) {
        PackageManager manager = context.getPackageManager();
        Intent action = new Intent(Intent.ACTION_VIEW);
        action.setData(Uri.parse(url));
        List list = manager.queryIntentActivities(action, PackageManager.GET_RESOLVED_FILTER);
        return list != null && list.size() > 0;
    }

    public static String getVersionName(Context context){
        PackageInfo packageInfo = null;
        try {
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo.versionName;
    }

    public static Map readKeyValueTxtToMap(String keyValueStr) {
        //循环直至返回map
        final HashMap keyValueMap = new HashMap();//保存读取数据keyValueMap
        //每一个循环读取一组key=value
        while (true) {
            final StringTokenizer allLine = new StringTokenizer(keyValueStr, "&");//以"&"作为key=value的分解标志
            while (allLine.hasMoreTokens()) {
                final StringTokenizer oneLine = new StringTokenizer(allLine.nextToken(), "=");//以"="作为分解标志
                final String leftKey = oneLine.nextToken();//读取第一个字符串key
                if (!oneLine.hasMoreTokens()) {
                    break;
                }
                final String rightValue = oneLine.nextToken();//读取第二个字符串value
                keyValueMap.put(leftKey, rightValue);
            }
            return keyValueMap;
        }
    }
}
