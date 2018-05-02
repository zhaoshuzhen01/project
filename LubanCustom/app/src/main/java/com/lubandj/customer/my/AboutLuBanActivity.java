package com.lubandj.customer.my;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.tools.Tools;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.master.R;
import com.lubandj.master.databinding.ActivityAboutlubanBinding;
import com.lubandj.master.my.AgreementActivity;
import com.lubandj.master.utils.CommonUtils;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */

public class AboutLuBanActivity extends BaseActivity {
    private ActivityAboutlubanBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_aboutluban);

        String versionName = Tools.getVersionName(AboutLuBanActivity.this);
        binding.tvVersion.setText("当前版本: " + versionName);
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
     * 检查更新
     *
     * @param view
     */
    public void onUpdate(View view) {
        if (!NetworkUtils.isNetworkAvailable(AboutLuBanActivity.this)) {
            return;
        }
        initProgressDialog("正在检查更新...");
        CommonUtils.upgradeApp(AboutLuBanActivity.this, dialog);
    }


    /**
     * 用户协议
     *
     * @param view
     */
    public void onUserAggrement(View view) {
        Intent intent = new Intent(AboutLuBanActivity.this, AgreementActivity.class);
        String url = "https://wx.lubandj.com/h5/protocol/lbdj";
        intent.putExtra("url", url);
        startActivity(intent);
    }

    /**
     * 去评分
     *
     * @param view
     */
    public void onGoRate(View view) {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            ToastUtils.showShort(AboutLuBanActivity.this, "尚未安装应用市场，无法评分");
        }
    }
}
