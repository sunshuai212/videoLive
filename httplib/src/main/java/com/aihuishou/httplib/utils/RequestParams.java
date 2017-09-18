//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.aihuishou.httplib.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RequestParams {
    protected boolean isRepeatable;
    protected ConcurrentHashMap<String, String> urlParams;
    protected ConcurrentHashMap<String, StreamWrapper> streamParams;
    protected ConcurrentHashMap<String, FileWrapper> fileParams;
    protected ConcurrentHashMap<String, Object> urlParamsWithObjects;

    public RequestParams() {
        this((Map) null);
    }

    public RequestParams(Map<String, String> source) {
        this.isRepeatable = false;
        this.init();
        if (source != null) {
            Iterator var2 = source.entrySet().iterator();

            while (var2.hasNext()) {
                Entry entry = (Entry) var2.next();
                this.put((String) entry.getKey(), (String) entry.getValue());
            }
        }

    }

    public RequestParams(final String key, final String value) {
        this((Map) (new HashMap() {
            {
                this.put(key, value);
            }
        }));
    }

    public RequestParams(Object... keysAndValues) {
        this.isRepeatable = false;
        this.init();
        int len = keysAndValues.length;
        if (len % 2 != 0) {
            throw new IllegalArgumentException("Supplied arguments must be even");
        } else {
            for (int i = 0; i < len; i += 2) {
                String key = String.valueOf(keysAndValues[i]);
                String val = String.valueOf(keysAndValues[i + 1]);
                this.put(key, val);
            }

        }
    }

    public void put(String key, String value) {
        if (key != null && value != null) {
            this.urlParams.put(key, value);
        }

    }

    public void put(String key, File file) throws FileNotFoundException {
        this.put(key, (File) file, (String) null);
    }

    public void put(String key, File file, String contentType) throws FileNotFoundException {
        if (key != null && file != null) {
            this.fileParams.put(key, new RequestParams.FileWrapper(file, contentType));
        }

    }

    public void put(String key, InputStream stream) {
        this.put(key, (InputStream) stream, (String) null);
    }

    public void put(String key, InputStream stream, String name) {
        this.put(key, stream, name, (String) null);
    }

    public void put(String key, InputStream stream, String name, String contentType) {
        if (key != null && stream != null) {
            this.streamParams.put(key, new RequestParams.StreamWrapper(stream, name, contentType));
        }

    }

    public void put(String key, Object value) {
        if (key != null && value != null) {
            this.urlParamsWithObjects.put(key, value);
        }

    }

    public void add(String key, String value) {
        if (key != null && value != null) {
            Object params = this.urlParamsWithObjects.get(key);
            if (params == null) {
                params = new HashSet();
                this.put(key, params);
            }

            if (params instanceof List) {
                ((List) params).add(value);
            } else if (params instanceof Set) {
                ((Set) params).add(value);
            }
        }

    }

    public void remove(String key) {
        this.urlParams.remove(key);
        this.streamParams.remove(key);
        this.fileParams.remove(key);
        this.urlParamsWithObjects.remove(key);
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        Iterator params = this.urlParams.entrySet().iterator();

        Entry entry;
        while (params.hasNext()) {
            entry = (Entry) params.next();
            if (result.length() > 0) {
                result.append("&");
            }

            result.append((String) entry.getKey());
            result.append("=");
            result.append((String) entry.getValue());
        }

        params = this.streamParams.entrySet().iterator();

        while (params.hasNext()) {
            entry = (Entry) params.next();
            if (result.length() > 0) {
                result.append("&");
            }

            result.append((String) entry.getKey());
            result.append("=");
            result.append("STREAM");
        }

        params = this.fileParams.entrySet().iterator();

        while (params.hasNext()) {
            entry = (Entry) params.next();
            if (result.length() > 0) {
                result.append("&");
            }

            result.append((String) entry.getKey());
            result.append("=");
            result.append("FILE");
        }

        List params1 = this.getParamsList((String) null, this.urlParamsWithObjects);
        Iterator entry1 = params1.iterator();

        while (entry1.hasNext()) {
            BasicNameValuePair kv = (BasicNameValuePair) entry1.next();
            if (result.length() > 0) {
                result.append("&");
            }

            result.append(kv.getName());
            result.append("=");
            result.append(kv.getValue());
        }

        return result.toString();
    }

    public void setHttpEntityIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
    }

    public HttpEntity getEntity(ResponseHandlerInterface progressHandler) throws IOException {
        return this.streamParams.isEmpty() && this.fileParams.isEmpty() ? this.createFormEntity() : this.createMultipartEntity(progressHandler);
    }

    private HttpEntity createFormEntity() {
        try {
            return new UrlEncodedFormEntity(this.getParamsList(), "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return null;
        }
    }

    private HttpEntity createMultipartEntity(ResponseHandlerInterface progressHandler) throws IOException {
        SimpleMultipartEntity entity = new SimpleMultipartEntity(progressHandler);
        entity.setIsRepeatable(this.isRepeatable);
        Iterator params = this.urlParams.entrySet().iterator();

        while (params.hasNext()) {
            Entry entry = (Entry) params.next();
            entity.addPart((String) entry.getKey(), (String) entry.getValue());
        }

        List params1 = this.getParamsList((String) null, this.urlParamsWithObjects);
        Iterator entry2 = params1.iterator();

        while (entry2.hasNext()) {
            BasicNameValuePair entry1 = (BasicNameValuePair) entry2.next();
            entity.addPart(entry1.getName(), entry1.getValue());
        }

        entry2 = this.streamParams.entrySet().iterator();

        Entry entry3;
        while (entry2.hasNext()) {
            entry3 = (Entry) entry2.next();
            RequestParams.StreamWrapper fileWrapper = (RequestParams.StreamWrapper) entry3.getValue();
            if (fileWrapper.inputStream != null) {
                entity.addPart((String) entry3.getKey(), fileWrapper.name, fileWrapper.inputStream, fileWrapper.contentType);
            }
        }

        entry2 = this.fileParams.entrySet().iterator();

        while (entry2.hasNext()) {
            entry3 = (Entry) entry2.next();
            RequestParams.FileWrapper fileWrapper1 = (RequestParams.FileWrapper) entry3.getValue();
            entity.addPart((String) entry3.getKey(), fileWrapper1.file, fileWrapper1.contentType);
        }

        return entity;
    }

    private void init() {
        this.urlParams = new ConcurrentHashMap();
        this.streamParams = new ConcurrentHashMap();
        this.fileParams = new ConcurrentHashMap();
        this.urlParamsWithObjects = new ConcurrentHashMap();
    }

    protected List<BasicNameValuePair> getParamsList() {
        LinkedList lparams = new LinkedList();
        Iterator var2 = this.urlParams.entrySet().iterator();

        while (var2.hasNext()) {
            Entry entry = (Entry) var2.next();
            lparams.add(new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue()));
        }

        lparams.addAll(this.getParamsList((String) null, this.urlParamsWithObjects));
        return lparams;
    }

    private List<BasicNameValuePair> getParamsList(String key, Object value) {
        LinkedList params = new LinkedList();
        Object nestedValue1 = null;
        if (value instanceof Map) {
            Map set = (Map) value;
            ArrayList list = new ArrayList(set.keySet());
            Collections.sort(list);
            Iterator nestedValue = list.iterator();

            while (nestedValue.hasNext()) {
                String nestedKey = (String) nestedValue.next();
                nestedValue1 = set.get(nestedKey);
                if (nestedValue1 != null) {
                    params.addAll(this.getParamsList(key == null ? nestedKey : String.format("%s[%s]", new Object[]{key, nestedKey}), nestedValue1));
                }
            }
        } else {
            Iterator var12;
            Object var14;
            if (value instanceof List) {
                List var9 = (List) value;
                var12 = var9.iterator();

                while (var12.hasNext()) {
                    var14 = var12.next();
                    params.addAll(this.getParamsList(String.format("%s[]", new Object[]{key}), var14));
                }
            } else if (value instanceof Object[]) {
                Object[] var10 = (Object[]) ((Object[]) value);
                Object[] var13 = var10;
                int var15 = var10.length;

                for (int var16 = 0; var16 < var15; ++var16) {
                    nestedValue1 = var13[var16];
                    params.addAll(this.getParamsList(String.format("%s[]", new Object[]{key}), nestedValue1));
                }
            } else if (value instanceof Set) {
                Set var11 = (Set) value;
                var12 = var11.iterator();

                while (var12.hasNext()) {
                    var14 = var12.next();
                    params.addAll(this.getParamsList(key, var14));
                }
            } else if (value instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) value;
                int var15 = jsonArray.length();
                for (int i = 0; i < var15; i++) {
                    try {
                        nestedValue1 = jsonArray.get(i);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    params.addAll(this.getParamsList(String.format("%s[]", new Object[]{key}), nestedValue1));
                }
            }  else if (value instanceof String) {
                params.add(new BasicNameValuePair(key, (String) value));
            } else {
                params.add(new BasicNameValuePair(key, value.toString()));
            }
        }

        return params;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(this.getParamsList(), "UTF-8");
    }

    private static class StreamWrapper {
        public InputStream inputStream;
        public String name;
        public String contentType;

        public StreamWrapper(InputStream inputStream, String name, String contentType) {
            this.inputStream = inputStream;
            this.name = name;
            this.contentType = contentType;
        }
    }

    private static class FileWrapper {
        public File file;
        public String contentType;

        public FileWrapper(File file, String contentType) {
            this.file = file;
            this.contentType = contentType;
        }
    }
}
