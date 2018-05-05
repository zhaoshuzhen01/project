package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.example.baselibrary.BaseEntity;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.adapter.PhotoGridAdapter;
import com.example.baselibrary.eventbus.BusEvent;
import com.example.baselibrary.eventbus.RxBus;
import com.example.baselibrary.photomanager.IPhoto;
import com.example.baselibrary.photomanager.Photo;
import com.example.baselibrary.photomanager.PhotoUtil;
import com.example.baselibrary.photomanager.TagsAdapter;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.activity.photo.TakePhotoActivity;
import com.example.baselibrary.recycleview.NoScrollGridView;
import com.example.baselibrary.tools.KeyBorad;
import com.lubandj.master.R;
import com.lubandj.master.adapter.DetailPingAdapter;
import com.lubandj.master.been.OrderListBeen;
import com.lubandj.master.model.DetailPingJiaModel;
import com.lubandj.master.utils.CommonUtils;

import net.bither.util.NativeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ServiceEvaluationActivity extends TitleBaseActivity implements DataCall<BaseEntity> {
    @InjectView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<String> msgBeens = new ArrayList<>();
    private OrderListBeen.InfoBean infoBean;
    private DetailPingJiaModel detailPingJiaModel;
    private DetailPingAdapter detailPingAdapter;
    public static final int REQUEST_PHOTO = 1;
    private static final int REQUEST_CODE_BIG_IMAGE = 100;
    private static final int TOTAL_IMAGE_COUNT = 4;
    private List<String> mImgPath = new ArrayList<String>();
    private List<IPhoto> mImgs;
    List<OrderListBeen.InfoBean.ItemsBean> datas;
    public static int index = 0;

    public static void startActivity(Context context, OrderListBeen.InfoBean infoBean) {
        Intent intent = new Intent(context, ServiceEvaluationActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtra("info", infoBean);
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
        return R.layout.activity_service_evaluation;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText("服务评价");
        setRightText("完成");
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        msgBeens.add("");
        msgBeens.add("");
        infoBean = (OrderListBeen.InfoBean) getIntent().getSerializableExtra("info");
//        infoBean.getItems().addAll(infoBean.getItems());
        datas = infoBean.getItems();
        detailPingAdapter = new DetailPingAdapter(datas, this);
        LinearLayoutManager manager = new LinearLayoutManager(this); //spanCount为列数，默认方向vertical
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(detailPingAdapter);
        detailPingJiaModel = new DetailPingJiaModel(this, this);
        initData();
    }

    @Override
    public void initData() {
        float dp_5 = getResources().getDimension(R.dimen.h_5dp);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    @Override
    public void onClick(View view) {
        if (!NetworkUtils.isNetworkAvailable(this)){
            return;
        }
        initProgressDialog("正在发布").show();
        JSONObject upBeen = new JSONObject();
        try {
            upBeen.put("id", infoBean.getId());

            JSONArray jsonArray = new JSONArray();
            for (OrderListBeen.InfoBean.ItemsBean itemsBean : datas) {
                JSONObject object = new JSONObject();
                try {
                    object.put("content", TextUtils.isEmpty(itemsBean.getContent()) ? "" : itemsBean.getContent());
                    object.put("service_id", itemsBean.getService_id());
                    object.put("star", itemsBean.getStar());
                    List<String> pics = new ArrayList<>();
                    for (Photo photo : itemsBean.getPhotos()) {
                        String path = photo.getPhotoPath();
                        if (!TextUtils.isEmpty(path)) {
                            Bitmap headPhoto = BitmapFactory.decodeFile(path);
                            pics.add(CommonUtils.Bitmap2StrByBase64(headPhoto));
                        }
                    }
                    object.put("content_img", pics);
                    jsonArray.put(object);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            upBeen.put("data", jsonArray);
            detailPingJiaModel.getPingJiaData(upBeen);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO:
                    //获取图片成功
                    if (null != data) {
                        String dataPath = TakePhotoActivity.getDataPath(data);
                        if (!TextUtils.isEmpty(dataPath)) {
                            mImgPath.add(dataPath);
                        }
                        if (null != dataPath) {
                            NativeUtil.compressBitmap(dataPath, dataPath);
                            datas.get(ServiceEvaluationActivity.index).getPhotos().add(new Photo(dataPath, 0));
                            detailPingAdapter.notifyDataSetChanged();
                        }
                    }
                    //多图选择返回
                    final ArrayList<String> patharr = TakePhotoActivity.getDataPathArr(data);
                    final String IMG_DIR = data.getStringExtra("url");
                    if (null != patharr) {
                        for (String url : patharr) {
                            mImgPath.add(url);
                        }
                    }
                    if (null != patharr && patharr.size() > 0) {

                        int i = 0;
                        for (final String path : patharr) {
                            final String newpath = IMG_DIR
                                    + "uploadimg_" + i + "_" + PhotoUtil.createDefaultName();
                            i++;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    NativeUtil.compressBitmap(path, newpath);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            datas.get(ServiceEvaluationActivity.index).getPhotos().add(new Photo(newpath, 0));
                                            detailPingAdapter.notifyDataSetChanged();
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void getServiceData(BaseEntity data) {
        RxBus.getInstance().post(new BusEvent(BusEvent.PINGLUN_CHENEGG));

        dialog.dismiss();
        toast(ServiceEvaluationActivity.this, data.getMessage());
        finish();
    }
}