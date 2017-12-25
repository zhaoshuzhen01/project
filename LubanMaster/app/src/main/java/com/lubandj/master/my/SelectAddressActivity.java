package com.lubandj.master.my;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.TextureMapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.example.baselibrary.BaseActivity;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.adapter.SelectAddressAdapter;
import com.lubandj.master.been.AddressBean;
import com.lubandj.master.databinding.ActivityMyaddressBinding;
import com.lubandj.master.databinding.ActivitySelectaddressBinding;

/**
 * function:
 * author:yangjinjian
 * date: 2017-11-30
 * company:九州宏图
 */
public class SelectAddressActivity extends BaseActivity {
    private ActivitySelectaddressBinding binding;
    private SelectAddressAdapter mAdapter;

    private BaiduMap mBaiduMap;
    private SuggestionSearch mSuggestionSearch;

    public LocationClient mLocationClient = null;
    private MyLocationListener myListener = new MyLocationListener();

    private GeoCoder mSearch;

    private boolean isFirstGps = false;
    private SuggestionResult.SuggestionInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_selectaddress);

        mAdapter = new SelectAddressAdapter(SelectAddressActivity.this);
        binding.lvAddress.setAdapter(mAdapter);

        mBaiduMap = binding.mbaiduMap.getMap();

        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(listener);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);

        // 设置定位图层的配置（定位模式，是否允许方向信息，用户自定义定位图标）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromResource(R.drawable.point);
        MyLocationConfiguration config = new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
        mBaiduMap.setMyLocationConfiguration(config);
        initLocation();

        mSearch = GeoCoder.newInstance();
        mSearch.setOnGetGeoCodeResultListener(geolistener);

        binding.lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                info = (SuggestionResult.SuggestionInfo) mAdapter.getItem(position);
                mSearch.reverseGeoCode(new ReverseGeoCodeOption().location(info.pt));
            }
        });

        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(new MapStatus.Builder().zoom(15).build()));
    }

    public void initLocation() {
        mLocationClient = new LocationClient(getApplicationContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数

        LocationClientOption option = new LocationClientOption();

        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);
//可选，设置定位模式，默认高精度
//LocationMode.Hight_Accuracy：高精度；
//LocationMode. Battery_Saving：低功耗；
//LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
//可选，设置返回经纬度坐标类型，默认gcj02
//gcj02：国测局坐标；
//bd09ll：百度经纬度坐标；
//bd09：百度墨卡托坐标；
//海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(1000);
//可选，设置发起定位请求的间隔，int类型，单位ms
//如果设置为0，则代表单次定位，即仅定位一次，默认为0
//如果设置非0，需设置1000ms以上才有效

        option.setOpenGps(true);
//可选，设置是否使用gps，默认false
//使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(true);
//可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
//可选，定位SDK内部是一个service，并放到了独立进程。
//设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
//可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
//可选，7.2版本新增能力
//如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
//可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        option.setIsNeedAddress(true);
//可选，是否需要地址信息，默认为不需要，即参数为false
//如果开发者需要获得当前点的地址信息，此处必须为true

        mLocationClient.setLocOption(option);
//mLocationClient为第二步初始化过的LocationClient对象
//需将配置好的LocationClientOption对象，通过setLocOption方法传递给LocationClient对象使用
//更多LocationClientOption的配置，请参照类参考中LocationClientOption类的详细说明
        mLocationClient.start();
//mLocationClient为第二步初始化过的LocationClient对象
//调用LocationClient的start()方法，便可发起定位请求
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


    @Override
    protected void onResume() {
        super.onResume();
        binding.mbaiduMap.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        binding.mbaiduMap.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBaiduMap.setMyLocationEnabled(false);
        mSuggestionSearch.destroy();
        binding.mbaiduMap.onDestroy();
    }

    public void onSearch(View view) {
        String place = binding.etPalceSelectaddress.getText().toString();
        String city = binding.tvCitySelectaddress.getText().toString();
        if (!TextUtils.isEmpty(place)) {
            mSuggestionSearch.requestSuggestion((new SuggestionSearchOption()).keyword(place).city(city).citylimit(true));
        } else {
            ToastUtils.showShort(SelectAddressActivity.this, "请先输入地址");
        }
    }

    OnGetSuggestionResultListener listener = new OnGetSuggestionResultListener() {
        public void onGetSuggestionResult(SuggestionResult res) {
            if (res == null || res.getAllSuggestions() == null) {
                //未找到相关结果
                ToastUtils.showShort(SelectAddressActivity.this, "未找到相关的地址");
                mAdapter.clearData();
                return;
            }
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(getCurrentFocus().getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            //获取在线建议检索结果
            mAdapter.setData(res.getAllSuggestions());
        }
    };

    public class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            //以下只列举部分获取经纬度相关（常用）的结果信息
            //更多结果信息获取说明，请参照类参考中BDLocation类中的说明

            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
//            float radius = location.getRadius();    //获取定位精度，默认值为0.0f

            // 构造定位数据
            MyLocationData locData = new MyLocationData.Builder()// 此处设置开发者获取到的方向信息，顺时针0-360
                    .accuracy(location.getRadius()).direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();

            // 设置定位数据
            mBaiduMap.setMyLocationData(locData);
            //初次的话设置所在城市
            if (!isFirstGps) {
                isFirstGps = true;
                String city = location.getCity();
                if (city.endsWith("市"))
                    binding.tvCitySelectaddress.setText(city.replace("市", ""));
                else
                    binding.tvCitySelectaddress.setText(city);
                LatLng latLng = new LatLng(latitude, longitude);
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.animateMapStatus(msu);
            }
//            String coorType = location.getCoorType();
            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准

//            int errorCode = location.getLocType();
            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明
        }
    }

    OnGetGeoCoderResultListener geolistener = new OnGetGeoCoderResultListener() {

        public void onGetGeoCodeResult(GeoCodeResult result) {

            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                //没有检索到结果
            }

            //获取地理编码结果
        }

        @Override
        public void onGetReverseGeoCodeResult(ReverseGeoCodeResult result) {
            if (result == null || result.error != SearchResult.ERRORNO.NO_ERROR) {
                ToastUtils.showShort(SelectAddressActivity.this, "未找到对应的地址");
            }
            //获取反向地理编码结果
            AddressBean bean = new AddressBean();
            ReverseGeoCodeResult.AddressComponent component = result.getAddressDetail();

            bean.province = component.province;
            bean.city = component.city;
            bean.areapublic = component.district;
            bean.address = component.street + component.streetNumber;
            bean.housing_estate = info.key;

            Intent intent = new Intent();
            intent.putExtra("address", bean);
            setResult(RESULT_OK, intent);
            finish();
        }
    };
}