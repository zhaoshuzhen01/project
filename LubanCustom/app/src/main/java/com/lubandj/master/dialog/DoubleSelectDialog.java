package com.lubandj.master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.lubandj.master.R;


/**
 * 双选择弹窗
 *
 * @创建时间：2015/12/22 09:38
 */
public class DoubleSelectDialog extends Dialog implements View.OnClickListener {
    private TextView mTvOne;
    private TextView mTvTwo;
    private Context context;
    private DoubleClickListenerInterface clickListenerInterface;

    private String selectOne;
    private String selectTwo;

    public DoubleSelectDialog(Context context, String selectOne, String selectTwo, DoubleClickListenerInterface clickListenerInterface) {
        super(context, R.style.dialog);
        this.context = context;
        this.clickListenerInterface = clickListenerInterface;
        this.selectOne = selectOne;
        this.selectTwo = selectTwo;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_doubleselect, null);
        setContentView(view);
        mTvOne = (TextView) view.findViewById(R.id.tv_selectone_doubleselect);
        mTvTwo = (TextView) view.findViewById(R.id.tv_selecttwo_doubleselect);
        mTvOne.setText(selectOne);
        mTvTwo.setText(selectTwo);

        findViewById(R.id.tv_cancel_doubleselect).setOnClickListener(this);
        mTvOne.setOnClickListener(this);
        mTvTwo.setOnClickListener(this);

        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        DisplayMetrics d = context.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
//        lp.width = d.widthPixels;
//        dialogWindow.setAttributes(lp);

        dialogWindow.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        dialogWindow.setWindowAnimations(R.style.progressinsertstyle);  //添加动画
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_selectone_doubleselect:
                DoubleSelectDialog.this.dismiss();
                clickListenerInterface.doFirstClick();
                break;
            case R.id.tv_selecttwo_doubleselect:
                DoubleSelectDialog.this.dismiss();
                clickListenerInterface.doSecondClick();
                break;
            case R.id.tv_cancel_doubleselect:
                DoubleSelectDialog.this.dismiss();
                break;
        }
    }

    public interface DoubleClickListenerInterface {
        public void doFirstClick();

        public void doSecondClick();
    }
}
