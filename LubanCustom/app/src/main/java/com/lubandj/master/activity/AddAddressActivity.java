package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.tools.KeyBorad;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.wheel.kankan.model.CityModel;
import com.example.baselibrary.wheel.kankan.model.DistrictModel;
import com.example.baselibrary.wheel.kankan.model.ProvinceModel;
import com.example.baselibrary.wheel.kankan.service.XmlParserHandler;
import com.google.gson.Gson;
import com.lubandj.master.Canstance;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.R;
import com.lubandj.master.TApplication;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.dialog.AddressDialog;
import com.lubandj.master.dialog.ClickListenerInterface;
import com.lubandj.master.dialog.DoubleSelectDialog;
import com.lubandj.master.dialog.SingleScrollSelectDialog;
import com.lubandj.master.httpbean.AddressListReponse;
import com.lubandj.master.httpbean.BaseEntity;
import com.lubandj.master.httpbean.BaseResponseBean;
import com.lubandj.master.httpbean.DeleteAddressRequest;
import com.lubandj.master.httpbean.UidParamsRequest;
import com.lubandj.master.model.AddAdressModel;
import com.lubandj.master.my.SelectAddressActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.utils.TaskEngine;

import org.apache.http.util.TextUtils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddAddressActivity extends TitleBaseActivity implements BaseQuickAdapter.OnItemClickListener, DataCall, AddressDialog.GetCityAndArea {

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
    @InjectView(R.id.choose_area)
    LinearLayout choose_area;
    @InjectView(R.id.choose_city)
    LinearLayout choose_city;
    @InjectView(R.id.diqu_empty_text)
    TextView mTvEmptydiqu;
    @InjectView(R.id.btn_save_addaddress)
    Button mBtnSave;

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
        setBackImg(R.drawable.back_mark);
        mBean = (AddressBean) getIntent().getSerializableExtra("bean");
        if (mBean.id == 0) {
            setTitleText("新增地址");
        } else {
            setTitleText("修改地址");
            setRightText("删除地址");
        }
        setOkVisibity(false);
        initData();
    }

    @Override
    public void initData() {
        setResult(RESULT_CANCELED);
        initProvinceDatas();
        if (mBean.id == 0) {//新增
            city.setText(Canstance.CITY);
            mBean.city = Canstance.CITY;
            mTvEmptydiqu.setVisibility(View.VISIBLE);
        } else {//修改
            name.setText(mBean.linkman);
            phone.setText(mBean.phone);
            city.setText(mBean.city);
            diqu.setText(mBean.area);
            xiaoqu.setText(mBean.housing_estate);
            louhao.setText(mBean.house_number);
            if (!TextUtils.isEmpty(mBean.area))
                mTvEmptydiqu.setVisibility(View.GONE);
        }
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

    @OnClick({R.id.xiaoqu, R.id.btn_save_addaddress, R.id.choose_city, R.id.choose_area})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.xiaoqu:
                if (!TextUtils.isEmpty(diqu.getText().toString())) {
                    Intent intent = new Intent(AddAddressActivity.this, SelectAddressActivity.class);
                    startActivityForResult(intent, 1010);
                } else {
                    CommonUtils.customShowToast(AddAddressActivity.this, "请选择服务地区");
                }
                break;
            case R.id.choose_city:
                onSelctCity(null);
                break;
            case R.id.choose_area:
            case R.id.diqu_empty_text:
                onSelctQu(null);
                break;
            case R.id.btn_save_addaddress:
                mBean.house_number = louhao.getText().toString();
                mBean.linkman = name.getText().toString();
                mBean.phone = phone.getText().toString();
                if (TextUtils.isEmpty(mBean.linkman) || TextUtils.isEmpty(mBean.phone) || TextUtils.isEmpty(mBean.city) || TextUtils.isEmpty(mBean.area) || TextUtils.isEmpty(mBean.housing_estate) || TextUtils.isEmpty(mBean.house_number)) {
                    ToastUtils.showShort(this, "用户信息不完整");
                } else
                    saveAddress();
                break;
            case R.id.tv_basetitle_right://删除地址
                DialogTagin.getDialogTagin(AddAddressActivity.this).showDialog("确认删除该地址吗？").setDialogSure(new DialogTagin.DialogSure() {
                    @Override
                    public void dialogCall() {
                        deleteAddress();
                    }
                });
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
            AddressBean bean = (AddressBean) data.getSerializableExtra("address");
            if (bean.areapublic.equals(diqu.getText().toString())) {//区相同
                mBean.province = bean.province;
                mBean.city = bean.city;
                mBean.area = bean.areapublic;
                mBean.address = bean.address;
                mBean.housing_estate = bean.housing_estate;
                xiaoqu.setText(mBean.housing_estate);
            } else {
                CommonUtils.customShowToast(AddAddressActivity.this, "所选区域不在服务范围内\n请重新选择");
            }
        }
    }

    @Override
    public void getServiceData(Object data) {
        finish();
    }

    @Override
    public void getContent(String address, String tag) {
        if (tag.equals("1")) {
            city.setText(address);
        } else {
            diqu.setText(address);
        }
    }


    /**
     * 选择市
     *
     * @param view
     */
    public void onSelctCity(View view) {
        SingleScrollSelectDialog dialog = new SingleScrollSelectDialog(AddAddressActivity.this, mProvinceDatas, mCitisDatasMap, new ClickListenerInterface() {
            @Override
            public void doConfirm(String mark) {
                city.setText(mark);
                mBean.city = mark;
            }

            @Override
            public void doCancel(String mark) {

            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    /**
     * 选择区
     *
     * @param view
     */
    public void onSelctQu(View view) {
        SingleScrollSelectDialog dialog = new SingleScrollSelectDialog(AddAddressActivity.this, mDistrictDatasMap.get(mBean.city), null, new ClickListenerInterface() {
            @Override
            public void doConfirm(String mark) {
                diqu.setText(mark);
                mBean.area = mark;
                if (mTvEmptydiqu.getVisibility() == View.VISIBLE) {
                    mTvEmptydiqu.setVisibility(View.GONE);
                }
            }

            @Override
            public void doCancel(String mark) {

            }
        });
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void saveAddress() {
        initProgressDialog("正在保存地址...").show();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_SAVEADDRESS, mBean, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {

                fastDismiss();
                BaseResponseBean bean = new BaseResponseBean();
                bean = CommonUtils.generateEntityByGson(AddAddressActivity.this, s, bean);
                if (bean != null) {
                    ToastUtils.showShort(AddAddressActivity.this, "保存成功");
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(AddAddressActivity.this, volleyError);
            }
        });
    }

    public void deleteAddress() {
        DeleteAddressRequest request = new DeleteAddressRequest(mBean.id);
        initProgressDialog("正在删除地址...").show();
        TaskEngine.getInstance().tokenHttps(Canstance.HTTP_DELETEADDRESS, request, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                fastDismiss();
                BaseResponseBean bean = new BaseResponseBean();
                bean = CommonUtils.generateEntityByGson(AddAddressActivity.this, s, bean);
                if (bean != null) {
                    ToastUtils.showShort(AddAddressActivity.this, "删除成功");
                    String id = CommonUtils.getAddressID();
                    if (id.equals(mBean.id + "")) {//如果删掉的是存储的地址
                        CommonUtils.setAddress("");
                        CommonUtils.setAddressID("");
                    }
                    setResult(RESULT_OK);
                    finish();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                fastDismiss();
                CommonUtils.fastShowError(AddAddressActivity.this, volleyError);
            }
        });
    }

    /**
     * 所有省
     */
    protected ArrayList<String> mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected Map<String, ArrayList<String>> mCitisDatasMap = new HashMap<>();
    /**
     * key - 市 values - 区
     */
    protected Map<String, ArrayList<String>> mDistrictDatasMap = new HashMap<>();

    /**
     * key - 区 values - 邮编
     */
//    protected Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 解析省市区的XML数据
     */
    protected void initProvinceDatas() {
        List<ProvinceModel> provinceList = null;
        AssetManager asset = this.getAssets();
        try {
            InputStream input = asset.open("province_data.xml");
            // 创建一个解析xml的工厂对象
            SAXParserFactory spf = SAXParserFactory.newInstance();
            // 解析xml
            SAXParser parser = spf.newSAXParser();
            XmlParserHandler handler = new XmlParserHandler();
            parser.parse(input, handler);
            input.close();
            // 获取解析出来的数据
            provinceList = handler.getDataList();
            mProvinceDatas = new ArrayList<>();
            for (int i = 0; i < provinceList.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas.add(provinceList.get(i).getName());
                List<CityModel> cityList = provinceList.get(i).getCityList();
                ArrayList<String> cityNames = new ArrayList<>();
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames.add(cityList.get(j).getName());
                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
                    ArrayList<String> distrinctNameArray = new ArrayList<>();
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        distrinctNameArray.add(districtList.get(k).getName());
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityList.get(j).getName(), distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
            }
        } catch (Throwable e) {
            e.printStackTrace();
        } finally {

        }
    }
}