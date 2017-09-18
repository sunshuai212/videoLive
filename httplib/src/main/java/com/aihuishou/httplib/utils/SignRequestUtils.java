package com.aihuishou.httplib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.util.Pair;

import com.aihuishou.httplib.HttpBaseContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import static android.content.Context.MODE_PRIVATE;


public class SignRequestUtils {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static byte[] mPrivateKeyBinary = null;
    private static String sign;

    public static List<Pair<String, String>> getRequestParams(HttpUrl url) {
        List<Pair<String, String>> requestParams = new ArrayList<>();
        for (int index = 0; index < url.querySize(); index++) {
            requestParams.add(new Pair<>(url.queryParameterName(index), url.queryParameterValue(index)));
        }
        return requestParams;
    }

    public static Request signRequest(Request originalRequest) {
        long timestamp = System.currentTimeMillis() / 1000;  // unit: second
        Request.Builder requestBuilder = originalRequest.newBuilder();
        if ("GET".equals(originalRequest.method())) {
            // GET
            List<Pair<String, String>> listPair = getRequestParams(originalRequest.url());
            if (listPair != null && listPair.size() > 1) {
                Collections.sort(listPair, new PairSort());
            }
            RequestParams params = new RequestParams();
            for (Pair<String, String> pair : listPair) {
                params.put(pair.first, pair.second);
            }
            params.put("timestamp", timestamp + "");
            String data = params.toString();
            if (TextUtils.isEmpty(data)) {
                data = "";
            }
            sign = RSASignature.signBinary(data, getPrivateKeyBinary());
        } else {
            // POST
            StringBuffer arrStr = new StringBuffer();
            if (originalRequest.body() instanceof FormBody) {
                FormBody formBody = (FormBody) originalRequest.body();
                RequestParams params = new RequestParams();
                List<Pair<String, Object>> listPair = new ArrayList<>();
                int paramSize = ((FormBody) originalRequest.body()).size();
                for (int index = 0; index < paramSize; index++) {
                    listPair.add(new Pair(formBody.name(index), formBody.value(index)));
//                    params.put(formBody.name(index), formBody.value(index));
                }
                listPair.add(new Pair("timestamp", timestamp + ""));
                if (listPair != null && listPair.size() > 1) {
                    Collections.sort(listPair, new PairSort());
                }
                for (Pair<String, Object> pair : listPair) {
                    params.put(pair.first, pair.second);
                }
                String data = params.toString();
                sign = RSASignature.signBinary(data, getPrivateKeyBinary());
            } else {
                RequestBody body = originalRequest.body();
                try {
                    Buffer buffer = new Buffer();
                    body.writeTo(buffer);
                    String data = buffer.readUtf8();
                    JSONObject jsonObj = null;
                    RequestParams params = new RequestParams();
                    List<Pair<String, Object>> listPair = new ArrayList<>();
                    try {
                        if (!TextUtils.isEmpty(data)) {
                            jsonObj = new JSONObject(data);
                            Iterator it = jsonObj.keys();
                            while (it.hasNext()) {
                                String str = it.next().toString();
                                Object value = jsonObj.get(str);
                                System.out.println(str + " + " + value);
                                listPair.add(new Pair(str, value));
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        LogUtil.e("JSONException", e);
                    }
                    listPair.add(new Pair("timestamp", timestamp + ""));
                    if (listPair != null && listPair.size() > 1) {
                        Collections.sort(listPair, new PairSort());
                    }
                    for (Pair<String, Object> pair : listPair) {
                        params.put(pair.first, pair.second);
                    }
                    data = params.toString();
                    sign = RSASignature.signBinary(data, getPrivateKeyBinary());
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtil.e("IOException", e);
                }
            }
        }
        return requestBuilder
                .addHeader("sign", sign)
                .addHeader("timestamp", timestamp + "")
                .addHeader("user_access_token", getAssessToken())
                .addHeader("userToken", getAssessToken())
                .addHeader("User-Agent","Android;enjoyChanging_native/"+getVersionName(HttpBaseContext.getContext()))
                .build();
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

    /*
         * return 0:既不是array也不是object
         * return 1：是object
         * return 2 ：是Array
         */
    public static int testIsArrayORObject(String sJSON) {
        try {
            JSONArray array = new JSONArray(sJSON);
            return 2;
        } catch (JSONException e) {// 抛错 说明JSON字符不是数组或根本就不是JSON
            try {
                JSONObject object = new JSONObject(sJSON);
                return 1;
            } catch (JSONException e2) {// 抛错 说明JSON字符根本就不是JSON
                System.out.println("非法的JSON字符串");
                return 0;
            }
        }

    }

    public static byte[] getPrivateKeyBinary() {

        if (mPrivateKeyBinary == null) {
            try {
                //Return an AssetManager instance for your application's package
                InputStream is = HttpBaseContext.getContext().getAssets().open("pkcs8_der.key");
                int size = is.available();

                // Read the entire asset into a local byte buffer.
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();

                // Convert the buffer into a string.
                mPrivateKeyBinary = buffer;

            } catch (IOException e) {
                // Should never happen!
                throw new RuntimeException(e);
            }
        }

        return mPrivateKeyBinary;
    }

    private static final String PREF_NAME = "com.aihuishou.airent";
    private static final String PREF_ASSESS_TOKEN = "assess_token";

    public static String getAssessToken() {
        SharedPreferences sharedPreferences = HttpBaseContext.getContext().getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String user_access_token = sharedPreferences.getString(PREF_ASSESS_TOKEN, "");
        return user_access_token;
    }

}
