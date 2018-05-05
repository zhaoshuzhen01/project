package com.lubandj.master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lubandj.master.R;


/**
 * 提示diaolog
 *
 * @创建时间：2015/12/22 09:38
 */
public class ToastDialog extends Dialog {
    private TextView mTvContent;//主要内容
    private Context context;

    private String strContent;

    public ToastDialog(Context context, String strContent) {
        super(context);
        this.context = context;
        this.strContent = strContent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_toast, null);
        setContentView(view);

        mTvContent = (TextView) view.findViewById(R.id.dialog_tv_noticecontent);
        mTvContent.setText(strContent);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        lp.width = (int) (d.widthPixels * 0.6);
        dialogWindow.setAttributes(lp);
    }
}
