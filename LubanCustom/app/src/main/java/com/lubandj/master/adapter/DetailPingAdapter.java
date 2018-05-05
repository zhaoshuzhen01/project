package com.lubandj.master.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.bumptech.glide.Glide;
import com.example.baselibrary.adapter.PhotoGridAdapter;
import com.example.baselibrary.photomanager.IPhoto;
import com.example.baselibrary.photomanager.Photo;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;
import com.lubandj.master.activity.DemoActivity;
import com.lubandj.master.activity.PhotoViewActivity;
import com.lubandj.master.activity.ServiceEvaluationActivity;
import com.lubandj.master.activity.photo.TakePhotoActivity;
import com.lubandj.master.been.OrderListBeen;
import com.lubandj.master.utils.BitmapCache;
import com.lubandj.master.utils.TaskEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/5/2.
 */

public class DetailPingAdapter extends BaseQuickAdapter<OrderListBeen.InfoBean.ItemsBean, BaseViewHolder> {
    private Context context;
    private static final int TOTAL_IMAGE_COUNT = 4;

    private List<String> mImgPath = new ArrayList<String>();
    private List<IPhoto> mImgs;
    private static final int REQUEST_CODE_BIG_IMAGE = 100;
    private int editTextMaxSize = 20;
    private volatile int mCount = 0;
    public static final int REQUEST_PHOTO = 1;
    private ImageLoader imageLoader;

    public DetailPingAdapter(@Nullable List<OrderListBeen.InfoBean.ItemsBean> data, Context context) {
        super(R.layout.item_service_pingjia, data);
        this.context = context;
        imageLoader = new ImageLoader(TaskEngine.getInstance().getQueue(), new BitmapCache());

    }

    @Override
    protected void convert(BaseViewHolder helper, final OrderListBeen.InfoBean.ItemsBean item) {
        final int position = helper.getAdapterPosition();
        final EditText askEdit = ((EditText) (helper.getView(R.id.ask_edit)));
        TextView name = ((TextView) (helper.getView(R.id.pingjia_num)));
        name.setText(item.getService_name() + "");
        ImageView imageView = ((ImageView) (helper.getView(R.id.pingjiatitle)));
//        Glide.with(context).load("http://lubandj.oss-cn-beijing.aliyuncs.com/service/20180503/2018050313443730525.jpg").into(imageView);
        ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(imageView, R.drawable.homelistdefaut, R.drawable.homelistdefaut);
        imageLoader.get(item.getService_icon(), imageListener);
        GridView gridview = ((GridView) (helper.getView(R.id.gridview)));
        askEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= editTextMaxSize) {
                    ToastUtils.showShort(context, "抱歉，您输入的字数太多了，请控制在" + editTextMaxSize + "字以内");
                } else {
                    item.setContent(s + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                Editable editable = askEdit.getText();
                int len = editable.length();
                if (len > editTextMaxSize) {
                    int selEndIndex = Selection.getSelectionEnd(editable);
                    String str = editable.toString();
                    //截取新字符串
                    String newStr = str.substring(0, editTextMaxSize);
                    askEdit.setText(newStr);
                    editable = askEdit.getText();

                    //新字符串的长度
                    int newLen = editable.length();
                    //旧光标位置超过字符串长度
                    if (selEndIndex > newLen) {
                        selEndIndex = editable.length();
                    }
                    //设置新光标所在的位置
                    Selection.setSelection(editable, selEndIndex);
                    item.setContent(askEdit.getText().toString() + "");
                }
            }
        });
        PhotoGridAdapter mPhotoGridAdapter = new PhotoGridAdapter(context);
        initData(mPhotoGridAdapter, gridview, item, position);
        RatingBar ratingBar = ((RatingBar) (helper.getView(R.id.ratingBar)));
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                item.setStar(v + "");
            }
        });
    }

    public void initData(final PhotoGridAdapter mPhotoGridAdapter, final GridView gridview, OrderListBeen.InfoBean.ItemsBean item, final int mposition) {
        float dp_5 = context.getResources().getDimension(R.dimen.h_5dp);
        mPhotoGridAdapter.setPhotoSize(3, (int) (dp_5 * 3));//padding设置15dp所以左右有5*6，三张图片有两个空隙是5*2
        mPhotoGridAdapter.setShowMinus(true);
        mPhotoGridAdapter.setOnMinusClickListener(new PhotoGridAdapter.OnPhotoMunisClickListener() {
            @Override
            public void onItemClick(IPhoto item, int position) {
                //删除图片
                mPhotoGridAdapter.remove(position);
                int count = mPhotoGridAdapter.getCount();
                if (count > 0) {
                    IPhoto iPhoto = mPhotoGridAdapter.getItem(count - 1);
                    if (!TextUtils.isEmpty(iPhoto.getPhotoPath())) {
                        mPhotoGridAdapter.add(new Photo(null, R.drawable.my_add));
                    }
                }
            }
        });
        mPhotoGridAdapter.add(new Photo(null, R.drawable.my_add));
        mPhotoGridAdapter.setOnPhotoClickListener(new PhotoGridAdapter.OnPhotoClickListener() {
            @Override
            public void onItemClick(IPhoto item, int position) {
                if (!TextUtils.isEmpty(item.getPhotoPath())) {
                    //显示删除菜单
                    mImgs = new ArrayList<IPhoto>(mPhotoGridAdapter.getList());
                    if (null != mImgs && mImgs.size() > 0) {
                        IPhoto iPhoto = mImgs.get(mImgs.size() - 1);
                        if (TextUtils.isEmpty(iPhoto.getPhotoPath())) {
                            mImgs.remove(mImgs.size() - 1);
                        }
                        ArrayList<CharSequence> allImage = new ArrayList<>();
                        for (int i = 0; i < mImgs.size(); i++) {
                            allImage.add(mImgs.get(i).getPhotoPath());
                        }
//大图浏览
                        Intent intent = new Intent(context, PhotoViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putCharSequenceArrayList("dataBean", allImage);
                        intent.putExtras(bundle);
                        intent.putExtra("currentPosition", position);
                        context.startActivity(intent);
                    }
                } else {
                    ServiceEvaluationActivity.index = mposition;
                    // 获取照片
                    Intent intent = new Intent(context, TakePhotoActivity.class);
                    intent.putExtra(TakePhotoActivity.IS_CHOOSE_MANY, true);
                    intent.putExtra(TakePhotoActivity.NEED_CROP, false);
                    intent.putExtra(TakePhotoActivity.CHOOSE_MANY_CURRENT_SIZE, mPhotoGridAdapter.getCount() - 1);
                    ((Activity) context).startActivityForResult(intent, REQUEST_PHOTO);
                }
            }
        });
        gridview.setAdapter(mPhotoGridAdapter);
        List<Photo> photos = item.getPhotos();
        if (photos != null && photos.size() > 0) {
            for (Photo data : photos) {
                int count = mPhotoGridAdapter.getCount();
                mPhotoGridAdapter.remove(count - 1);
                mPhotoGridAdapter.add(data);
                int mcount = mPhotoGridAdapter.getCount();
                if (mcount < TOTAL_IMAGE_COUNT) {
                    mPhotoGridAdapter.add(new Photo(null, R.drawable.my_add));
                }
            }
        }
    }

    @Override
    public void childViewClick(int position, View view) {
        ToastUtils.showShort(context, getItem(position) + "");
        ((Activity) context).finish();
    }
}