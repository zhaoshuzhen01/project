package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.tools.KeyBorad;
import com.lubandj.master.R;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.my.MyAddressActivity;
import com.lubandj.master.my.SelectAddressActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddAddressActivity extends TitleBaseActivity implements BaseQuickAdapter.OnItemClickListener {
    @InjectView(R.id.name)
    EditText name;
    @InjectView(R.id.phone)
    EditText phone;
    @InjectView(R.id.city)
    TextView city;
    @InjectView(R.id.diqu)
    TextView diqu;
    @InjectView(R.id.xiaoqu)
    TextView xiaoqu;
    @InjectView(R.id.louhao)
    EditText louhao;
    @InjectView(R.id.fankui_button)
    TextView fankuiButton;
    private AddressBean mBean;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, AddAddressActivity.class);
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
        return R.layout.activity_add_address;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("新增地址");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        initData();
        KeyBorad.DelayShow(name,this);
    }

    @Override
    public void initData() {

    }
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }

    @OnClick({R.id.diqu, R.id.xiaoqu, R.id.fankui_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.diqu:
                break;
            case R.id.xiaoqu:
                Intent intent = new Intent(AddAddressActivity.this, SelectAddressActivity.class);
                startActivityForResult(intent, 1010);
                break;
            case R.id.fankui_button:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == 1010) {
            mBean = (AddressBean) data.getSerializableExtra("address");
        }
    }
}