package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.tools.KeyBorad;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.dialog.AddressDialog;
import com.lubandj.master.model.AddAdressModel;
import com.lubandj.master.my.MyAddressActivity;
import com.lubandj.master.my.SelectAddressActivity;

import org.apache.http.util.TextUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddAddressActivity extends TitleBaseActivity implements BaseQuickAdapter.OnItemClickListener,DataCall,AddressDialog.GetCityAndArea {
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
    @InjectView(R.id.choose_area)
    LinearLayout choose_area;
    @InjectView(R.id.choose_city)
    LinearLayout choose_city ;
    @InjectView(R.id.addressLog)
    AddressDialog addressDialog ;
    private AddressBean mBean;
    private AddAdressModel addAdressModel;
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
        addAdressModel = new AddAdressModel(this,this);
        mBean = new AddressBean();
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addressDialog.setVisibility(View.GONE);
        return super.onTouchEvent(event);
    }

    @OnClick({ R.id.xiaoqu, R.id.fankui_button,R.id.choose_city,R.id.choose_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiaoqu:
                Intent intent = new Intent(AddAddressActivity.this, SelectAddressActivity.class);
                startActivityForResult(intent, 1010);
                break;
            case R.id.fankui_button:
                mBean.id = 0;
                mBean.city = city.getText().toString();
                mBean.province = mBean.city;
                mBean.area = diqu.getText().toString();
                mBean.address = xiaoqu.getText().toString();
                mBean.housing_estate = mBean.address;
                mBean.house_number = louhao.getText().toString();
                mBean.linkman = name.getText().toString();
                mBean.phone = phone.getText().toString();
                if (TextUtils.isEmpty(mBean.linkman)||TextUtils.isEmpty(mBean.phone)||TextUtils.isEmpty(mBean.city)||TextUtils.isEmpty(mBean.area)||TextUtils.isEmpty(mBean.address)||TextUtils.isEmpty(mBean.house_number)){
                    ToastUtils.showShort(this,"用户信息不完整");
                    return;
                }
                    addAdressModel.saveAddress(mBean);
                break;
            case R.id.choose_city:
                addressDialog.setTag("1",this);
                break;
            case R.id.choose_area:
                addressDialog.setTag("2",this);
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

    @Override
    public void getServiceData(Object data) {
        finish();
    }

    @Override
    public void getContent(String address, String tag) {
        if (tag.equals("1")){
            city.setText(address);
        }else {
            diqu.setText(address);
        }
    }
}