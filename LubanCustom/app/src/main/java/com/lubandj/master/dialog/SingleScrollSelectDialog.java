package com.lubandj.master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.lubandj.master.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


/**
 * 滑动选择弹窗
 *
 * @创建时间：2015/12/22 09:38
 */
public class SingleScrollSelectDialog extends Dialog implements View.OnClickListener {
    private Button mBtnSelect;
    private Button mBtnCancel;
    private Context context;
    private ClickListenerInterface clickListenerInterface;
    private WheelView mWvText;
    private WheelView.WheelViewStyle style;

    private ArrayList<String> textList;

    public SingleScrollSelectDialog(Context context, ArrayList<String> textList, ClickListenerInterface clickListenerInterface) {
        super(context, R.style.DialogTheme);
        this.context = context;
        this.textList = textList;
        this.clickListenerInterface = clickListenerInterface;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_singlescrollselect, null);
        setContentView(view);
        mBtnSelect = (Button) view.findViewById(R.id.btn_cancel_scrollselect);
        mBtnCancel = (Button) view.findViewById(R.id.btn_select_scrollselect);
        mBtnSelect.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);

        mWvText = (WheelView) view.findViewById(R.id.wv_text);

        style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 20;
        style.textSize = 16;
        style.selectedTextColor = Color.parseColor("#333333");
        style.textColor = Color.parseColor("#999999");
        style.holoBorderColor = Color.parseColor("#dddddd");
        setStyle(mWvText);
        mWvText.setWheelData(textList);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = d.widthPixels;
        dialogWindow.setAttributes(lp);

        dialogWindow.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        dialogWindow.setWindowAnimations(R.style.progressinsertstyle);  //添加动画
    }


    public void setStyle(WheelView wv) {
        wv.setWheelAdapter(new ArrayWheelAdapter(context));
        wv.setLoop(false);
        wv.setSkin(WheelView.Skin.Holo);
        wv.setStyle(style);
        wv.setWheelSize(5);
    }

    public void setClicklistener(ClickListenerInterface clickListenerInterface) {
        this.clickListenerInterface = clickListenerInterface;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_select_scrollselect:
                SingleScrollSelectDialog.this.dismiss();
                String text = (String) mWvText.getSelectionItem();
                clickListenerInterface.doConfirm(text);
                break;
            case R.id.btn_cancel_scrollselect:
                SingleScrollSelectDialog.this.dismiss();
                break;
        }
    }
}
