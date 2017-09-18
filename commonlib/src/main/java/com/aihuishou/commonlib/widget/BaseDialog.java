package com.aihuishou.commonlib.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;

public class BaseDialog extends Dialog implements View.OnClickListener {

    private Context context;

    public BaseDialog(Context context) {
        super(context);
        this.context = context;
    }

    public BaseDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;

    }

    protected BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.context = context;
    }

    public void show() {
        if( context != null && context instanceof Activity) {
            if( ((Activity) context).isFinishing() ) {
                Log.e("TAG", "Activity is finishing, does not show it");
                return;
            }
        }
        super.show();
    }

    @Override
    public void onClick(View v) {

    }
}
