package com.lubandj.master.worksheet;

import android.util.Log;
import android.view.View;

import com.example.baselibrary.TitleBaseActivity;
import com.lubandj.master.R;

public class WorkSheetDetailsActivity extends TitleBaseActivity {


    public static final String KEY_DETAILS_TYPE = "details_type";
    private int currentType;
    private static final String TAG = "WorkSheetDetailsActivit";

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    public int getLayout() {
        return R.layout.activity_work_sheet_details;
    }

    @Override
    public void initView() {
        setTitleText(R.string.txt_work_sheet_details_title);
        setOKText("客服");
        currentType = getIntent().getIntExtra(KEY_DETAILS_TYPE, 0);
        Log.e(TAG, "initView: "+currentType);


    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
                toast("客服");
                break;
            default:
                break;
        }

    }
}
