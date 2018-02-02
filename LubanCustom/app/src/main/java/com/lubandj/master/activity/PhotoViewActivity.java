package com.lubandj.master.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.photoview.MyImageAdapter;
import com.example.photoview.PhotoViewPager;
import com.lubandj.master.R;

import java.util.ArrayList;
import java.util.List;

public class PhotoViewActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = PhotoViewActivity.class.getSimpleName();
    private PhotoViewPager mViewPager;
    private int currentPosition;
    private MyImageAdapter adapter;
    private TextView mTvImageCount;
    private TextView mTvSaveImage;
    private List<String> urls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_viewer);
        initView();
        initData();
    }

    private void initView() {
        mViewPager = (PhotoViewPager) findViewById(R.id.view_pager_photo);
        mTvImageCount = (TextView) findViewById(R.id.tv_image_count);
       /* mTvSaveImage = (TextView) findViewById(R.id.tv_save_image_photo);
        mTvSaveImage.setOnClickListener(this);*/

    }

    private void initData() {
        urls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1517240971435&di=c2f0dbeec5618ca5024442e8ddf7559b&imgtype=jpg&src=http%3A%2F%2Fimg1.imgtn.bdimg.com%2Fit%2Fu%3D1640122246%2C9744894%26fm%3D214%26gp%3D0.jpg");
        urls.add("http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_1.jpg");
        urls.add("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
        urls.add("http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_1.jpg");
        urls.add("http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_1.jpg");
        urls.add("http://7xla0x.com1.z0.glb.clouddn.com/picJarvanIV_1.jpg");

        Intent intent = getIntent();
        currentPosition = intent.getIntExtra("currentPosition", 0);
        adapter = new MyImageAdapter(urls, this);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(currentPosition, false);
        mTvImageCount.setText(currentPosition + 1 + "/" + urls.size());
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                mTvImageCount.setText(currentPosition + 1 + "/" + urls.size());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}