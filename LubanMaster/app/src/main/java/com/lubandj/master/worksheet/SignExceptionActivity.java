package com.lubandj.master.worksheet;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SignExceptionActivity extends TitleBaseActivity implements RadioGroup.OnCheckedChangeListener {


    @InjectView(R.id.radio_group)
    RadioGroup radioGroup;
    @InjectView(R.id.edit_reason)
    EditText editReason;
    @InjectView(R.id.fl_reason)
    FrameLayout flReason;
    @InjectView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    public void titleLeftClick() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_sign_exception;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("标记异常");
        setOKText("客服");
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        new AlertDialog(this)
                .builder()
                .setTitle("提交成功")
                .setMsg("已成功提交异常工单，请及时联系客服进行审核")
                .setPositiveButton("联系客服", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setNegativeButton("稍后拨打", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
       if(checkedId==R.id.radio_btn_4){
           flReason.setVisibility(View.VISIBLE);
       }else{
           flReason.setVisibility(View.GONE);

       }
    }
}
