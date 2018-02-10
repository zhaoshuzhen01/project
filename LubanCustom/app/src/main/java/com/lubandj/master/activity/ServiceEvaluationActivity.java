package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.adapter.PhotoGridAdapter;
import com.example.baselibrary.photomanager.IPhoto;
import com.example.baselibrary.photomanager.Photo;
import com.example.baselibrary.photomanager.PhotoUtil;
import com.example.baselibrary.photomanager.TagsAdapter;
import com.lubandj.master.activity.photo.TakePhotoActivity;
import com.example.baselibrary.recycleview.NoScrollGridView;
import com.example.baselibrary.tools.KeyBorad;
import com.lubandj.master.R;

import net.bither.util.NativeUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ServiceEvaluationActivity extends TitleBaseActivity {

    @InjectView(R.id.ask_edit)
    EditText askEdit;
    @InjectView(R.id.gridview)
    GridView gridview;
    @InjectView(R.id.add_icon_gridView)
    NoScrollGridView addIconGridView;
    public static final int REQUEST_PHOTO = 1;
    private PhotoGridAdapter mPhotoGridAdapter;
    private static final int TOTAL_IMAGE_COUNT = 4;

    private List<String> mImgPath = new ArrayList<String>();
    private List<IPhoto> mImgs;
    private static final int REQUEST_CODE_BIG_IMAGE = 100;
    private int editTextMaxSize = 200;
    private volatile int mCount = 0;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, ServiceEvaluationActivity.class);
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
        KeyBorad.DelayShow(askEdit, this);
        askEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() >= editTextMaxSize) {
                    toast(ServiceEvaluationActivity.this, "抱歉，您输入的字数太多了，请控制在" + editTextMaxSize + "字以内");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mPhotoGridAdapter = new PhotoGridAdapter(this);
        initData();
    }

    @Override
    public void initData() {
        float dp_5 = getResources().getDimension(R.dimen.h_5dp);
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
                        Intent intent = new Intent(ServiceEvaluationActivity.this, PhotoViewActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putCharSequenceArrayList("dataBean", allImage);
                        intent.putExtras(bundle);
                        intent.putExtra("currentPosition", position);
                        startActivity(intent);
                    }
                } else {
                    // 获取照片
                    Intent intent = new Intent(getBaseContext(), TakePhotoActivity.class);
                    intent.putExtra(TakePhotoActivity.IS_CHOOSE_MANY, true);
                    intent.putExtra(TakePhotoActivity.NEED_CROP, false);
                    intent.putExtra(TakePhotoActivity.CHOOSE_MANY_CURRENT_SIZE, mPhotoGridAdapter.getCount() - 1);
                    startActivityForResult(intent, REQUEST_PHOTO);
                }
            }
        });
        gridview.setAdapter(mPhotoGridAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.inject(this);
    }


    @Override
    public void onClick(View view) {
        toast(this, "完成");
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
                            int count = mPhotoGridAdapter.getCount();
                            mPhotoGridAdapter.remove(count - 1);
                            mPhotoGridAdapter.add(new Photo(dataPath, R.drawable.my_add));
                            if (count < TOTAL_IMAGE_COUNT) {
                                mPhotoGridAdapter.add(new Photo(null, R.drawable.my_add));
                            }
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
                        int count = mPhotoGridAdapter.getCount();
                        mPhotoGridAdapter.remove(count - 1);
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
                                            mPhotoGridAdapter.add(new Photo(newpath, 0));
                                            ++mCount;
                                            if (mCount == patharr.size()) {
                                                mCount = 0;
                                                int count = mPhotoGridAdapter.getCount();
                                                if (count < TOTAL_IMAGE_COUNT) {
                                                    mPhotoGridAdapter.add(new Photo(null, R.drawable.my_add));
                                                }
                                            }
                                        }
                                    });
                                }
                            }).start();
                        }
                    }
                    break;
                case REQUEST_CODE_BIG_IMAGE:
                    if (null != data) {
                        mImgs.clear();
                        mPhotoGridAdapter.removeAll();
                        Bundle bundle = data.getBundleExtra("data");
                        if (bundle != null && bundle.size() > 0) {
                            int size = bundle.size();
                            for (int i = 0; i < size; i++) {
                                Photo o = (Photo) bundle.get("img" + i);
                                mImgs.add(o);
                            }
                            mPhotoGridAdapter.addData(mImgs);
                            int count = mPhotoGridAdapter.getCount();
                            if (count < TOTAL_IMAGE_COUNT) {
                                mPhotoGridAdapter.add(new Photo(null, R.drawable.my_add));
                            }
                        } else {
                            int count = mPhotoGridAdapter.getCount();
                            if (count < TOTAL_IMAGE_COUNT) {
                                mPhotoGridAdapter.add(new Photo(null, R.drawable.my_add));
                            }
                        }
                    }
                    break;
            }
        }
    }
}