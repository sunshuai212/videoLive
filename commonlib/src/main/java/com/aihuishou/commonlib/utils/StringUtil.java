package com.aihuishou.commonlib.utils;

import java.util.List;


/**
 * Created by buffert on 17/1/20.
 */

public class StringUtil {


    public static String formatPhone(String phone){
        if(phone.length()==11) {
            StringBuffer sb = new StringBuffer();
            sb.append(phone.substring(0, 3));
            sb.append("****");
            sb.append(phone.substring(7));
            return sb.toString();
        }
        return "";
    }

    /*** 全角转换为半角
     *
     * @param input
     * @return
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

    /*** 半角转换为全角
     *
     * @param input
     * @return
     */
    public static String ToSBC(String input)
    {
        char[] c=input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i]==32)
            {
                c[i]=(char)12288;
                continue;
            }
            if (c[i]<127)
                c[i]=(char)(c[i]+65248);
        }
        return new String(c);
    }

    public static int compareVersion(String version1, String version2) throws Exception {
        if (version1 == null || version2 == null) {
            throw new Exception("compareVersion error:illegal params.");
        }
        String[] versionArray1 = version1.split("\\.");//注意此处为正则匹配，不能用"."；
        String[] versionArray2 = version2.split("\\.");
        int idx = 0;
        int minLength = Math.min(versionArray1.length, versionArray2.length);//取最小长度值
        int diff = 0;
        while (idx < minLength
                && (diff = versionArray1[idx].length() - versionArray2[idx].length()) == 0//先比较长度
                && (diff = versionArray1[idx].compareTo(versionArray2[idx])) == 0) {//再比较字符
            ++idx;
        }
        //如果已经分出大小，则直接返回，如果未分出大小，则再比较位数，有子版本的为大；
        diff = (diff != 0) ? diff : versionArray1.length - versionArray2.length;
        return diff;
    }

    public static <T extends Object> String mergeList2String( List<T> list ) {
        return mergeList2String( list, ",");
    }

    public static <T extends Object> String mergeList2String( List<T> list, String sperator )
    {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;

        if( list != null ) {
            for( T i : list ) {
                if( !isFirst ) {
                    sb.append( sperator );
                } else {
                    isFirst = false;
                }

                sb.append( i );
            }
        }

        return sb.toString();
    }

}
