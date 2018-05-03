package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.tools.ToastUtils;
import com.google.gson.Gson;
import com.lubandj.customer.httpbean.CancelOrderInfoRequest;
import com.lubandj.customer.httpbean.CancelOrderInfoResponse;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.adapter.CancelOrderServiceAdapter;
import com.lubandj.master.dialog.ToastDialog;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CancleOrderActivity extends TitleBaseActivity {
    @InjectView(R.id.rv_items)
    RecyclerView recyclerView;
    @InjectView(R.id.tv_cancel_reason)
    TextView mTvReason;
    @InjectView(R.id.tv_cancel_amount)
    TextView mTvAmount;
    @InjectView(R.id.ll_cancel_reason)
    LinearLayout mLlReason;

    private CancelOrderServiceAdapter mCancelOrderServiceAdapter;

    private List<CancelOrderInfoResponse.CancelOrderService> itemList = new ArrayList<>();
    private String order_id;

    public static void startActivity(Context context, String order_id) {
        Intent intent = new Intent(context, CancleOrderActivity.class);
        intent.putExtra("orderid", order_id);
        context.startActivity(intent);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_cancle_order;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("取消下单");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);

        order_id = getIntent().getStringExtra("orderid");

        itemList = new ArrayList<>();
        mCancelOrderServiceAdapter = new CancelOrderServiceAdapter(itemList, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new SpacesItemDecoration(0, 0, 2, 0));
        recyclerView.setAdapter(mCancelOrderServiceAdapter);
        initData();
    }

    @Override
    public void initData() {
        initProgressDialog(R.string.txt_loading).show();
        CancelOrderInfoRequest detailOrder = new CancelOrderInfoRequest();
        detailOrder.order_id = order_id;
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_CANCELORDERINFO, detailOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                dialog.dismiss();
                try {
                    CancelOrderInfoResponse response = new Gson().fromJson(s, CancelOrderInfoResponse.class);
                    if (response.code == 0) {
                        if (response.info != null) {
                            mCancelOrderServiceAdapter.setNewData(response.info.service);
                            mTvAmount.setText("¥" + response.info.pay_amount);
                        }
                    } else if (response.code == 104) {
                        CommonUtils.tokenNullDeal(CancleOrderActivity.this);
                    } else {
                        ToastUtils.showShort(CancleOrderActivity.this, response.message);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(CancleOrderActivity.this, "返回数据解析出错");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                dialog.dismiss();
                CommonUtils.fastShowError(CancleOrderActivity.this, volleyError);
            }
        });
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
    }

    @OnClick({R.id.in_get, R.id.ll_cancel_reason})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.in_get://提交
                if (TextUtils.isEmpty(mTvReason.getText().toString())) {
                    new ToastDialog(CancleOrderActivity.this, "请选择原因").show();
                }
                break;
            case R.id.ll_cancel_reason://获取原因

                break;
        }
    }

}