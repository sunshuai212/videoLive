//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.aihuishou.httplib.utils;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.net.URI;

public interface ResponseHandlerInterface {
    void sendResponseMessage(HttpResponse var1) throws IOException;

    void sendStartMessage();

    void sendFinishMessage();

    void sendProgressMessage(int var1, int var2);

    void sendSuccessMessage(int var1, Header[] var2, byte[] var3);

    void sendFailureMessage(int var1, Header[] var2, byte[] var3, Throwable var4);

    void sendRetryMessage();

    URI getRequestURI();

    Header[] getRequestHeaders();

    void setRequestURI(URI var1);

    void setRequestHeaders(Header[] var1);

    void setUseSynchronousMode(boolean var1);
}
