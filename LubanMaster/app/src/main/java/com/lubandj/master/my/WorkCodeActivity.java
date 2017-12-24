package com.lubandj.master.my;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.databinding.ActivityWorkcodeBinding;
import com.lubandj.master.httpbean.GetAddressReponse;
import com.lubandj.master.httpbean.GetQrcodeReponse;
import com.lubandj.master.httpbean.UidParamsRequest;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.Logger;
import com.lubandj.master.utils.TaskEngine;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class WorkCodeActivity extends BaseActivity {
    private ActivityWorkcodeBinding binding;
    private ImageLoader imageLoader;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_workcode);
        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());
        loadFace(TApplication.context.mUserInfo.face_url);
        binding.tvMenuName.setText(TApplication.context.mUserInfo.nickname + "");
        getQrcode();
    }

    public void loadFace(String url) {
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(binding.ivMenuHeadimg, R.drawable.default_header, R.drawable.default_header);
        imageLoader.get(url, imageListener);
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

    public void getQrcode() {
        initProgressDialog("获取二维码中...").show();
        UidParamsRequest request = new UidParamsRequest(CommonUtils.getUid());
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_QRCODE, request, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                GetQrcodeReponse reponse = new GetQrcodeReponse();
                reponse = (GetQrcodeReponse) CommonUtils.generateEntityByGson(WorkCodeActivity.this, s, reponse);
                if (reponse != null) {
                    ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(binding.ivQrcode, 0, 0);
                    imageLoader.get(reponse.info.link, imageListener);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                if (volleyError != null) {
                    if (volleyError.networkResponse != null) {
                        String format = String.format(getString(R.string.txt_net_connect_error), volleyError.networkResponse.statusCode);
                        ToastUtils.showShort(WorkCodeActivity.this, format);
                    }
                    Logger.e(volleyError.getMessage());
                }
            }
        });
    }
}
