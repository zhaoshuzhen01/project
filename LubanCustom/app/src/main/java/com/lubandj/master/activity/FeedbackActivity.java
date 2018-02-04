package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.lubandj.master.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FeedbackActivity extends TitleBaseActivity {

    @InjectView(R.id.fankui_text)
    EditText fankuiText;
    @InjectView(R.id.fankui_button)
    TextView fankuiButton;

    @Override
    public int getLayout() {
        return R.layout.activity_feedback;
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, FeedbackActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText(R.string.msg_center);
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
    }

    @Override
    public void initData() {

    }


    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick(R.id.fankui_button)
    public void onClick() {
    }
}
