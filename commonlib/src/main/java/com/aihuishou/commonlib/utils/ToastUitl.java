package com.aihuishou.commonlib.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.aihuishou.commonlib.BaseAppContext;
import com.aihuishou.commonlib.R;

/**
 * 类名称：ToastUitl
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/14
 * 描述：Toast统一管理类
 */
public class ToastUitl {

    private static void showToast(String message, int layoutId, boolean isError) {
        LayoutInflater inflater = (LayoutInflater) BaseAppContext.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(
                layoutId,
                null);

        TextView messageText = (TextView) layout.findViewById(R.id.text_message);
        messageText.setText(message);
        TextView tvIcon = (TextView) layout.findViewById(R.id.tv_icon);
        if (isError) {
            tvIcon.setVisibility(View.VISIBLE);
            FontHelper.injectFont(tvIcon,"e626");
        } else {
            tvIcon.setVisibility(View.GONE);
        }
        Toast toast = new Toast(BaseAppContext.getContext());
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public static void showErrorToast(String message) {
        showToast(message, R.layout.toast_error, true);
    }

    public static void showToast(String message) {
        showToast(message, R.layout.toast_error, false);
    }

    public static void showShort(String localizedMessage) {
        showErrorToast(localizedMessage);
    }
}
