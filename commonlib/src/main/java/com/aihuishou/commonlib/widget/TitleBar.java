package com.aihuishou.commonlib.widget;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aihuishou.commonlib.R;

/**
 * 类名称：TitleBar
 * 创建者：Create by liujc
 * 创建时间：Create on 2017/8/14
 * 描述：通用标题栏
 */
public class TitleBar {

    private Activity mActivity = null;

    // 返回按钮
    private Button backBtn = null;
    private Button back_btn_tx = null;

    private LinearLayout leftView = null;
    // 自定义按钮
    private LinearLayout rightView = null;
    private Button rightBtn = null;
    // 标题
    private TextView titleTxt = null;
    // 副标题
    private TextView subTitleTxt = null;
    // 标题
    private RelativeLayout titleLine = null;

    private View titleView = null;

    public TitleBar(Activity activity, View parent) {
        mActivity = activity;
        initWidget(parent);
    }

    public void setTitle(String title) {
        titleTxt.setText(title);
    }

    public void setTitle(int titleRes) {
        titleTxt.setText(titleRes);
    }

    public void setSubTitle(String subTitle) {
        if (!TextUtils.isEmpty(subTitle)) {
            subTitleTxt.setVisibility(View.VISIBLE);
            subTitleTxt.setText(subTitle);
        } else {
            subTitleTxt.setVisibility(View.GONE);
        }
    }

    public void setBackground(int index){
        if(titleView != null) titleView.setBackgroundColor(index);
    }

    public void enableBack() {
        backBtn.setVisibility(View.VISIBLE);
        // 默认返回事件，关闭当前activity
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

    public Button getBackBtn(){
        return backBtn;
    }

    /**
     * @Title: setBack
     * @Description: setBack返回键
     * @param backStr
     * @param clickListener
     */
    public void setBack(String backStr, View.OnClickListener clickListener) {
        backBtn.setVisibility(View.VISIBLE);
        if (backStr != null) {
            backBtn.setText(backStr);
        }
        if (clickListener != null) {
            // 用户自定义返回事件
            backBtn.setOnClickListener(clickListener);
        } else {
            // 默认返回事件，关闭当前activity
            backBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.finish();
                }
            });
        }
    }

    public void setBackBtn(boolean isClicked,String btnStr,int color,View.OnClickListener clickListener){
        backBtn.setVisibility(View.GONE);
        back_btn_tx.setVisibility(View.VISIBLE);
        back_btn_tx.setTextColor(color);
        back_btn_tx.setText(btnStr);
        if(!isClicked){
            back_btn_tx.setEnabled(false);
        }else{
            back_btn_tx.setEnabled(true);
            back_btn_tx.setOnClickListener(clickListener);
        }
    }

    /**
     * @Title: enableRightBtn
     * @Description: enable右侧按钮
     * @param btnStr
     * @param icon
     * @param clickListener
     */
    public void enableRightBtn(String btnStr, int icon,
                               View.OnClickListener clickListener) {
        rightBtn.setVisibility(View.VISIBLE);
        if (btnStr != null) {
            rightBtn.setText(btnStr);
        }
        if (icon > 0) {
            rightBtn.setCompoundDrawablesWithIntrinsicBounds(0, 0, icon, 0);
        }
        if (clickListener != null) {
            // 用户自定义事件
            rightBtn.setOnClickListener(clickListener);
        }
    }

    public void setTopRightBtn(boolean isClicked,String btnStr,int color,View.OnClickListener clickListener){
        rightBtn.setVisibility(View.VISIBLE);
        rightBtn.setTextColor(color);
        rightBtn.setText(btnStr);
        if(!isClicked){
            rightBtn.setEnabled(false);
        }else{
            rightBtn.setEnabled(true);
            rightBtn.setOnClickListener(clickListener);
        }
    }
    public void setTopRightBtnText(String text){
        rightBtn.setText(text);
    }
    public void setRightBtnVisibility(int visibility) {
        rightBtn.setVisibility(visibility);
    }

    public void addCustomTitleView(View view) {
        if (titleLine != null) {
            titleLine.addView(view);
        }
    }

    public void addCustomRightView(View view) {
        if (rightView != null) {
            rightView.addView(view);
        }
    }

    public void addCustomLeftView(View view) {
        if (leftView != null) {
            leftView.removeAllViews();
            leftView.addView(view);
        }
    }
    public void removeCustomLeftView() {
        if (leftView != null) {
            leftView.removeAllViews();
        }
    }

    public boolean isShowing() {
        if (titleView.getVisibility() == View.VISIBLE) {
            return true;
        } else {
            return false;
        }
    }

    public void hide() {
        Animation mHiddenAction = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        mHiddenAction.setDuration(200);
        titleView.startAnimation(mHiddenAction);
        titleView.setVisibility(View.GONE);
    }

    public void show() {
        Animation mShowAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        mShowAction.setDuration(200);
        titleView.startAnimation(mShowAction);
        titleView.setVisibility(View.VISIBLE);
    }

    public void setTitleVisibility(int visibility) {
        titleView.setVisibility(visibility);
    }

    /**
     * @Title: initWidget
     * @Description: 初始化控件
     * @param parent
     */
    private void initWidget(View parent) {
        backBtn = (Button) parent.findViewById(R.id.back_btn);
        back_btn_tx = (Button) parent.findViewById(R.id.back_btn_tx);

        leftView = (LinearLayout) parent.findViewById(R.id.title_left);
        rightView = (LinearLayout) parent.findViewById(R.id.title_right);
        rightBtn = (Button) parent.findViewById(R.id.right_btn);
        titleTxt = (TextView) parent.findViewById(R.id.title_txt);
        subTitleTxt = (TextView) parent.findViewById(R.id.sub_title_txt);
        titleLine = (RelativeLayout) parent.findViewById(R.id.title_center);
        titleView = parent.findViewById(R.id.title);
    }



}
