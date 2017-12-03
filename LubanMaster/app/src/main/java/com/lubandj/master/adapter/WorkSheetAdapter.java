package com.lubandj.master.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.widget.ActionSheetDialog;
import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.R;
import com.lubandj.master.been.TestBean;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2017/11/26.
 */

public class WorkSheetAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {
    private Context context;
    private static final int TYPE_TO_PERFORM = 0;
    private static final int TYPE_ON_ROAD = 1;
    private static final int TYPE_IN_SERVICE = 2;
    private static final int TYPE_COMPLETED = 3;
    private static final int TYPE_CANCELED = 4;
    public WorkSheetAdapter(@Nullable List<TestBean> data,Context context) {
        super(R.layout.item_worksheet, data);
        this.context = context ;
    }

    @Override
    protected void convert(BaseViewHolder helper, TestBean item) {
        initView(helper,item);
    }

    @Override
    public void childViewClick(int position,View view) {
        switch (view.getId()){
            case R.id.finishState:
                switch (position){
                    case 0:
                        finishDialog(2);
                        break;
                    case 1:
                        finishDialog(0);
                        break;
                    default:
                        finishDialog(1);
                        break;
                }
                break;
            case R.id.daohangState:
                new ActionSheetDialog(context)
                        .builder()
                        .setCancelable(true)
                        .setCanceledOnTouchOutside(true)
                        .addSheetItem("高德地图",
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        openMap(false);
                                    }
                                })
                        .addSheetItem("百度地图",
                                ActionSheetDialog.SheetItemColor.Blue,
                                new ActionSheetDialog.OnSheetItemClickListener() {
                                    @Override
                                    public void onClick(int which) {
                                        openMap(true);
                                    }
                                }).show();
                break;
        }
    }

    /**
     * 初始化view
     * @param helper
     * @param item
     */
    private void initView(final BaseViewHolder helper, TestBean item){
        int position = helper.getAdapterPosition();
        TextView finishState =  ((TextView) (helper.getView(R.id.finishState)));
        TextView serviceState = ((TextView) (helper.getView(R.id.serviceState)));
        TextView daohangState= ((TextView) (helper.getView(R.id.daohangState)));
        daohangState.setVisibility(View.GONE);
        daohangState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(helper.getAdapterPosition(),view);
            }
        });
        finishState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                childViewClick(helper.getAdapterPosition(),view);
            }
        });
        switch (position){
            case 0:
                serviceState.setText("服务中");
                finishState.setText("服务完成");
                break;
            case 1:
                serviceState.setText("待执行");
                finishState.setText("开始上门");
                daohangState.setVisibility(View.VISIBLE);
                break;

            default:
                serviceState.setText("正在上门");
                finishState.setText("开始上门");
                daohangState.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void finishDialog(int currentType){
        new AlertDialog(context)
                .builder()
                .setTitle("确认提醒")
                .setMsg(getRemindContent(currentType))
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }
    private String getRemindContent(int currentType){
        String content="";
        switch (currentType) {
            case TYPE_TO_PERFORM:
                content="请确认将开始前往服务地点";
                break;
            case TYPE_ON_ROAD:
                content="请确认开始服务";
                break;
            case TYPE_IN_SERVICE:
                content="请确认服务已完成";
                break;
        }
        return content;
    }
    private void openMap(boolean isBaiduMap) {
        if (!checkApkExist(context, isBaiduMap ? "com.baidu.BaiduMap" : "com.autonavi.minimap")) {
            ToastUtils.showShort(isBaiduMap ? "请安装百度地图" : "请安装高德地图");
            return;
        }
        Intent intent = null;
        try {
            intent = isBaiduMap ? Intent.getIntent("intent://map/marker?location=40.047669,116.313082&title=我的位置&content =百度奎科大厦&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end") :
                    Intent.getIntent("androidamap://viewMap?sourceApplication=厦门通&poiname=百度奎科大厦&lat=40.047669&lon=116.313082&dev=0");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        context.startActivity(intent); //启动调用

    }

    public boolean checkApkExist(Context context, String packageName) {
        if (packageName == null || "".equals(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName,
                    PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}