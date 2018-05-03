package com.lubandj.master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
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
public class CancelReasonSelectDialog extends Dialog implements View.OnClickListener {
    private TextView mTvOne;
    private TextView mTvTwo;
    private TextView mTvThree;
    private Context context;
    private SelectClickListenter clickListenerInterface;


    public CancelReasonSelectDialog(Context context, SelectClickListenter clickListenerInterface) {
        super(context, R.style.dialog);
        this.context = context;
        this.clickListenerInterface = clickListenerInterface;

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_cancelreasonselect, null);
        setContentView(view);
        mTvOne = (TextView) view.findViewById(R.id.tv_firstreason);
        mTvTwo = (TextView) view.findViewById(R.id.tv_secondreason);
        mTvThree = (TextView) view.findViewById(R.id.tv_thirdreason);

        findViewById(R.id.tv_cancel_cancelreason).setOnClickListener(this);
        mTvOne.setOnClickListener(this);
        mTvTwo.setOnClickListener(this);
        mTvThree.setOnClickListener(this);
        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        win.setAttributes(lp);
        win.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
        win.setWindowAnimations(R.style.progressinsertstyle);  //添加动画
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.tv_firstreason:
            case R.id.tv_secondreason:
            case R.id.tv_thirdreason:
                CancelReasonSelectDialog.this.dismiss();
                clickListenerInterface.clickString(((TextView) v).getText().toString());
                break;
            case R.id.tv_cancel_doubleselect:
                CancelReasonSelectDialog.this.dismiss();
                break;
        }
    }

    public interface SelectClickListenter {
        public void clickString(String strng);
    }
}
