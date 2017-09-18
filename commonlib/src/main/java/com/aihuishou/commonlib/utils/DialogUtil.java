package com.aihuishou.commonlib.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Rect;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.aihuishou.commonlib.BaseAppContext;
import com.aihuishou.commonlib.R;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.DialogPlusBuilder;
import com.orhanobut.dialogplus.ViewHolder;
import com.orhanobut.dialogplus.listener.OnClickListener;

/**
 * 类名称：DialogUtil
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/14
 * 描述：弹框相关工具类
 */
public class DialogUtil {
    public static void setDialogToFullScreenWidth(Dialog dialog) {
        Rect frame = new Rect();
        Window win = dialog.getWindow();
        win.getDecorView().getWindowVisibleDisplayFrame(frame);
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);
    }

    public static Dialog createLoadingDialog(Context context) {

        LayoutInflater inflater = null;

        if (context != null) {
            inflater = LayoutInflater.from(context);
        } else {
            inflater = LayoutInflater.from(BaseAppContext.getContext());
        }

        View v = inflater.inflate(R.layout.dialog_loading_layout, null);
        FrameLayout layout = (FrameLayout) v.findViewById(R.id.dialog_view);
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return loadingDialog;
    }

    public static DialogPlus createCustomDialog(Context context, int layoutId, int contentBgColor,
                                                int overlayBgColor, OnClickListener onClickListener) {
        View customView = LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(customView))
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(contentBgColor)
                .setOverlayBackgroundResource(overlayBgColor)
                .setGravity(Gravity.CENTER)
                .setOnClickListener(onClickListener)
                .create();
        return dialogPlus;
    }

    public static DialogPlusBuilder createCustomDialogBuilder(Context context, View customView, int contentBgColor,
                                                              int overlayBgColor, OnClickListener onClickListener) {
        return DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(customView))
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(contentBgColor)
                .setOverlayBackgroundResource(overlayBgColor)
                .setGravity(Gravity.CENTER)
                .setOnClickListener(onClickListener);
    }

    public static DialogPlus createCustomBootomDialog(Context context, int layoutId, int contentBgColor,
                                                      int overlayBgColor, OnClickListener onClickListener) {
        View customView = LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(customView))
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(contentBgColor)
                .setOverlayBackgroundResource(overlayBgColor)
                .setGravity(Gravity.BOTTOM)
                .setOnClickListener(onClickListener)
                .create();
        return dialogPlus;
    }

    public static DialogPlus createCustomBootomDialog(Context context, View layout, OnClickListener onClickListener) {
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(layout))
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(R.color.transparent_color)
                .setOverlayBackgroundResource(R.color.mask_fg_color)
                .setGravity(Gravity.BOTTOM)
                .setOnClickListener(onClickListener)
                .create();
        return dialogPlus;
    }

    public static DialogPlusBuilder createCustomBootomBuilderDialog(Context context, int layoutId, int contentBgColor,
                                                             int overlayBgColor, OnClickListener onClickListener) {
        View customView = LayoutInflater.from(context.getApplicationContext()).inflate(layoutId, null);
        return DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(customView))
                .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(contentBgColor)
                .setOverlayBackgroundResource(overlayBgColor)
                .setGravity(Gravity.BOTTOM)
                .setOnClickListener(onClickListener);
    }

    public static DialogPlus createCustomDialog(Context context, View customView, int contentBgColor,
                                                int overlayBgColor, OnClickListener onClickListener) {
        DialogPlus dialogPlus = DialogPlus.newDialog(context)
                .setContentHolder(new ViewHolder(customView))
                .setContentWidth(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                .setContentBackgroundResource(contentBgColor)
                .setOverlayBackgroundResource(overlayBgColor)
                .setGravity(Gravity.CENTER)
                .setOnClickListener(onClickListener)
                .create();
        return dialogPlus;
    }

    public static DialogPlus createCommonBtnDialog(Context context, String title, String content, String btnText, OnClickListener onClickListener) {

        DialogPlus dialogPlus = createCustomDialog(context, R.layout.dialog_common_btn_layout, R.color.white, R.color.mask_fg_color, onClickListener);

        ((TextView) dialogPlus.findViewById(R.id.tv_title)).setText(title);
        ((TextView) dialogPlus.findViewById(R.id.tv_content)).setText(content);
        ((TextView) dialogPlus.findViewById(R.id.tv_ok)).setText(btnText);

        return dialogPlus;
    }

    public static DialogPlus createCommonBtnTwoDialog(Context context, String title, String content, String btnOkText, String btnCancelText, OnClickListener onClickListener) {
        DialogPlus dialogPlus = createCustomDialog(context, R.layout.dialog_common_btn_two, R.color.white, R.color.mask_fg_color, onClickListener);

        ((TextView) dialogPlus.findViewById(R.id.tv_title)).setText(title);
        ((TextView) dialogPlus.findViewById(R.id.tv_content)).setText(content);
        ((TextView) dialogPlus.findViewById(R.id.tv_ok)).setText(btnOkText);
        ((TextView) dialogPlus.findViewById(R.id.tv_cancel)).setText(btnCancelText);

        return dialogPlus;
    }


}
