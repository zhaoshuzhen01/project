package com.example.baselibrary.photomanager;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.baselibrary.R;
import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.tools.Tools;


/**
 * @author Jack_SJ
 * @version v
 *          Copyright Yjlc Co. Ltd.
 * @Title CropActivity.java
 * @Description: TODO(图片剪裁)
 * @date 2016-5-14 下午2:09:55
 */
public class CropActivity extends TitleBaseActivity {
    private CropImageView mView;
    private String path;
    private ImageView resultimg;
    @Override
    public void titleLeftClick() {

    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_crop;
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
   /*     switch (v.getId()) {
            case R.id.btn_ok:

                if (mView.getVisibility() == View.GONE)
                    return;
//                try{
                Bitmap mBitmap = mView.getCropImage();
                String picPath= Tools.getDataRootPath(this)+System.currentTimeMillis()+".jpg";
                boolean saveSuccess = PhotoUtil.saveImageFileByBitmap(path, picPath, 80, mBitmap, true);
                if (saveSuccess) {
                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();
                    //bundle.putParcelable("bitmap", mBitmap);//添加要返回给页面1的数据
                    bundle.putString("path", picPath);
                    intent.putExtras(bundle);
                    setResult(Activity.RESULT_OK, intent);//返回页面1
                    finish();
                }
//                }catch(Exception e){
//                    e.printStackTrace();
//                }




                break;
            case R.id.btn_cancel: {
                finish();
                break;
            }
        }*/

    }

    @Override
    public void initView() {
        mView = (CropImageView) findView(R.id.cropimage);
        resultimg = (ImageView) findView(R.id.resultimg);
        this.findView(R.id.btn_ok).setOnClickListener(this);
        this.findView(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void initData() {
        //设置资源和默认长宽
        Bundle b = this.getIntent().getExtras();
        if (null != b) {
            path = b.getString("path");
            int w = b.getInt("witdh",640);
            int h = b.getInt("height",640);
            try {
                Drawable d = Drawable.createFromPath(path);
                mView.setDrawable(d, w, h);
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
        }
    }

}
