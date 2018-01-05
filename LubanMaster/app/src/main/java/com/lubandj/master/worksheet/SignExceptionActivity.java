package com.lubandj.master.worksheet;

import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.AlertDialog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.been.ExceptionListBean;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.lubandj.master.worksheet.WorkSheetDetailsActivity.KEY_DETAILS_ID;

public class SignExceptionActivity extends PermissionActivity implements RadioGroup.OnCheckedChangeListener, TextWatcher {


    @InjectView(R.id.radio_group)
    RadioGroup radioGroup;
    @InjectView(R.id.edit_reason)
    EditText editReason;
    @InjectView(R.id.fl_reason)
    FrameLayout flReason;
    @InjectView(R.id.btn_submit)
    Button btnSubmit;
    private String mStrSeason = "";
    private int infoSize;
    private int problemId=0;
    private String workSheetId;

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_sign_exception;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText(R.string.txt_sign_exception_page_title);
        setBackImg(R.drawable.back_mark);
        setOKImg(R.drawable.ic_service);
        radioGroup.setOnCheckedChangeListener(this);
        editReason.addTextChangedListener(this);
        workSheetId = getIntent().getStringExtra(KEY_DETAILS_ID);
        initData();
    }

    @Override
    public void initData() {
        initProgressDialog(R.string.txt_loading).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", 1);
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_EXCEPTION_LIST, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                Logger.e(s);
                try {
                    ExceptionListBean exceptionListBean = new Gson().fromJson(s, ExceptionListBean.class);
                    if (exceptionListBean.getCode() == 0) {
                        refreshPage(exceptionListBean);
                    } else {
                        ToastUtils.showShort(SignExceptionActivity.this, exceptionListBean.getMsg());
                    }
                } catch (Exception e) {
                    Logger.e(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(SignExceptionActivity.this, format);
                    }
                }
            }
        });
    }

    private void refreshPage(ExceptionListBean exceptionListBean) {
        if (exceptionListBean == null || exceptionListBean.getInfo() == null) {
            return;
        }

        List<ExceptionListBean.InfoBean> info = exceptionListBean.getInfo();

        infoSize = info.size();

        for (int i = 0; i < infoSize; i++) {
            ExceptionListBean.InfoBean infoBean = info.get(i);
            RadioButton radioButton = new RadioButton(this);

            RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            if (i != 0) {
                layoutParams.topMargin = (int) getResources().getDimension(R.dimen.dp_11);
            }
            radioButton.setLayoutParams(layoutParams);
            radioButton.setTextColor(ContextCompat.getColor(this, R.color.color_333333));
            radioButton.setTextSize(15);
            radioButton.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(this, R.drawable.selector_radio_button_bg), null, null, null);
            radioButton.setCompoundDrawablePadding((int) getResources().getDimension(R.dimen.dp_12));
            radioButton.setButtonDrawable(null);
            radioButton.setId(infoBean.getProblemId());
            radioButton.setText(infoBean.getText());
            radioGroup.addView(radioButton);
        }
    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        if(problemId==0){
            ToastUtils.showShort(this, R.string.txt_choose_exception);
            return;
        }

        if (problemId == infoSize && TextUtils.isEmpty(mStrSeason)) {
            ToastUtils.showShort(this, R.string.txt_exception_empty);
            return;
        }

        initProgressDialog(R.string.txt_submit).show();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ticketId", workSheetId);
        jsonObject.addProperty("problemId", problemId);
        if (!TextUtils.isEmpty(mStrSeason)) {
            jsonObject.addProperty("content", mStrSeason);
        }
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_SIGN_EXCEPTION, jsonObject, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                Logger.e(s);
                try {
                    BaseEntity baseEntity = new Gson().fromJson(s, BaseEntity.class);
                    if (baseEntity.getCode() == 0) {
                        new AlertDialog(SignExceptionActivity.this)
                                .builder()
                                .setTitle(getString(R.string.txt_submit_success))
                                .setMsg(getString(R.string.txt_submit_success_service))
                                .setPositiveButton(getString(R.string.txt_contact_service), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        call("10086");
                                    }
                                })
                                .setNegativeButton(getString(R.string.txt_later_call), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                    }
                                }).show();
                    } else {
                        ToastUtils.showShort(SignExceptionActivity.this, baseEntity.getMessage());
                    }
                } catch (Exception e) {
                    Logger.e(e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(SignExceptionActivity.this, format);
                    }
                }
            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        problemId = checkedId;
        if (checkedId == infoSize) {
            flReason.setVisibility(View.VISIBLE);
        } else {
            flReason.setVisibility(View.GONE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        mStrSeason = s.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_basetitle_ok:
                String serviceNum = "10086";
                callToClient(serviceNum,  String.format(getString(R.string.txt_confirm_call_service), serviceNum));
                break;
            default:
                break;
        }
    }

}
