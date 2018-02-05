package com.lubandj.customer.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.RegexUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.databinding.ActivityModifynicknameBinding;
import com.lubandj.master.databinding.ActivityModifyphoneBinding;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.httpbean.ModifyPhoneRequest;
import com.lubandj.master.httpbean.SendSmsBean;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class ModifyNickNameActivity extends BaseActivity {
    private ActivityModifynicknameBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_modifynickname);
        binding.btnModifynickname.setEnabled(false);
        binding.etNickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String editable = binding.etNickname.getText().toString();
                String str = stringFilter(editable.toString());
                if (!editable.equals(str)) {
                    binding.etNickname.setText(str);
                    //设置新的光标所在位置
                    binding.etNickname.setSelection(str.length());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    @Override
    public int getLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    /**
     * 返回
     *
     * @param view
     */
    public void onBack(View view) {
        finish();
    }

    /**
     * 修改手机号
     *
     * @param view
     */
    public void onModifyPhone(View view) {
        final String nickName = binding.etNickname.getText().toString().trim();
        if (TextUtils.isEmpty(nickName)) {
            ToastUtils.showShort(ModifyNickNameActivity.this, "昵称不能为空");
            return;
        }
        if (nickName.equals(TApplication.context.mUserInfo.nickname)) {
            ToastUtils.showShort(ModifyNickNameActivity.this, "新昵称不能与原昵称相同");
            return;
        }

        initProgressDialog("正在保存昵称...").show();

        ModifyPhoneRequest request = new ModifyPhoneRequest();
//        request.mobile = mPhoneNum;
//        request.verifyCode = mPhoneNum;
//        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_MODIFYPHONE, request, new Response.Listener<String>() {
//
//            @Override
//            public void onResponse(String s) {
//                dialog.dismiss();
//                BaseResponseBean response = new BaseResponseBean();
//                response = CommonUtils.generateEntityByGson(ModifyNickNameActivity.this, s, response);
//                if (response != null) {
//                    ToastUtils.showShort(ModifyNickNameActivity.this, response.message);
//                    TApplication.context.mUserInfo.mobile = mPhoneNum;
//                    setResult(RESULT_OK);
//                    finish();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                fastDismiss();
//                CommonUtils.fastShowError(ModifyNickNameActivity.this, volleyError);
//            }
//        });
    }

}
