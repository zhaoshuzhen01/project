package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.recycleview.SpacesItemDecoration;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Canstance;
import com.lubandj.master.R;
import com.lubandj.master.adapter.ChooseAddressAdapter;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.httpbean.AddressListReponse;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.httpbean.UidParamsRequest;
import com.lubandj.master.my.MyAddressActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class CustomAddressActivity extends TitleBaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    @InjectView(R.id.btn_add_addresslist)
    TextView fankuiButton;
    private List<AddressBean> msgBeens = new ArrayList<>();
    private ChooseAddressAdapter chooseCityAdapter;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomAddressActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_custom_address;
    }

    @Override
    protected void onResume() {
        super.onResume();
//        initData();
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("我的地址");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        chooseCityAdapter = new ChooseAddressAdapter(msgBeens, this);
        chooseCityAdapter.setOnItemClickListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new SpacesItemDecoration(10, 10, 20, 10));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(chooseCityAdapter);
        initData();
    }

    @Override
    public void initData() {
        getAddress();
    }

    @OnClick(R.id.btn_add_addresslist)
    public void onClick() {
        Intent intent = new Intent(CustomAddressActivity.this, AddAddressActivity.class);
        AddressBean bean = new AddressBean();
        bean.id = 0;
        intent.putExtra("bean", bean);
        startActivityForResult(intent, 1001);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = getIntent();
        AddressBean bean = chooseCityAdapter.getItem(position);
        intent.putExtra("data", bean);
        setResult(1, intent);
        finish();
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 获取地址
     */
    public void getAddress() {
        UidParamsRequest request = new UidParamsRequest(CommonUtils.getUid());
        initProgressDialog("正在获取地址列表...").show();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_GETADDRESS, request, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                fastDismiss();
                AddressListReponse bean = new AddressListReponse();
                bean = (AddressListReponse) CommonUtils.generateEntityByGson(CustomAddressActivity.this, s, bean);
                if (bean != null) {
                    chooseCityAdapter.setNewData(bean.info);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(CustomAddressActivity.this, volleyError);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {//地址数据有变更
                getAddress();
            }
        }
    }
}