package com.lubandj.master.activity.photo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.baselibrary.AppConstant;
import com.example.baselibrary.R;
import com.example.baselibrary.photomanager.CacheManager;
import com.example.baselibrary.photomanager.Item;
import com.example.baselibrary.photomanager.PhotoUtil;
import com.example.baselibrary.photomanager.PicUtils;
import com.example.baselibrary.tools.ToastUtils;

import java.io.File;
import java.util.ArrayList;

/**
 * 通用的照片拍摄或选取照片//
 * Created by lile on 15-11-15.
 */
public class TakePhotoActivity extends Activity {
    /**
     * 是否为多图选择
     */
    public static final String IS_CHOOSE_MANY = "is_choose_many";
    /**
     * 多图选择，当前选择的张数
     */
    public static final String CHOOSE_MANY_CURRENT_SIZE = "choose_many_current_count";
    /**
     * 是否需要裁剪，对拍照和单图有效
     */
    public static final String NEED_CROP = "need_crop";
    /**
     * 返回结果，单图或拍照返回单张的路径
     */
    public static final String SINGLE_PHOTO_PATH = "single_photo_path";
    /**
     * 多图选择返回的多图路基String arrayList
     */
    public static final String MANY_PHOTO_PATH_ARR = "many_photo_path_arr";
    public static final int REQUEST_PHOTO_CAMERA = 1; // 拍照
    public static final int REQUEST_PHOTO_GARRY = 2; // 相册选择
    public static final int REQUEST_PHOTO_GARRY_SIGLE = 3; // 选择单张照片
    public static final int REQUEST_CROP = 4; // 裁剪
    private static String currentPhotoPath;

    // 拍照获取的
    // 图片选择的总张数
    public int pickImgsCount;
    // 成功压缩的张数
    public int successZoomSize;
    ArrayList<String> manyPaths = new ArrayList<String>();
    private Activity context;
    // 多图选择模式
    private boolean isChooseMany;
    // 是否需要裁剪
    private boolean needCrop;
    // 多图选择当前的张数
    private int chooseManyCurrentCout;
    /**
     * 图片路径
     */
    private String IMG_DIR;
    private View layoutBtns;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    /**
     * 从data中获取返回的路径
     *
     * @param data
     * @return
     */
    public static String getDataPath(Intent data) {
        if (data != null) {
            return data.getStringExtra(SINGLE_PHOTO_PATH);
        }
        return null;
    }

    /**
     * 从data中获取返回的路径
     *
     * @param data 返回单张图片
     * @return
     */
    public static String getsingleDataPath(Intent data) {
        if (currentPhotoPath != null) {
            return currentPhotoPath;
        }
        return null;
    }

    /**
     * 从data中获取多图选择的路径
     *
     * @param data
     * @return
     */
    public static ArrayList<String> getDataPathArr(Intent data) {
        if (data != null) {
            return data.getStringArrayListExtra(MANY_PHOTO_PATH_ARR);
        }
        return null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        isChooseMany = getIntent().getBooleanExtra(IS_CHOOSE_MANY, false);
        chooseManyCurrentCout = getIntent().getIntExtra(CHOOSE_MANY_CURRENT_SIZE, 0);
        needCrop = getIntent().getBooleanExtra(NEED_CROP, false);
        findViewById(R.id.pop_layout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        context = this;
        IMG_DIR = CacheManager.getImgDir(context);
        layoutBtns = findViewById(R.id.layoutBtns);
        // 拍照
        findViewById(R.id.tvCamera).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= 23) {
                    /**
                     * 请求读写SD卡权限
                     */
                    int permission = ActivityCompat.checkSelfPermission(TakePhotoActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);
                    if (permission != PackageManager.PERMISSION_GRANTED) {
                        // We don't have permission so prompt the user
                        ActivityCompat.requestPermissions(TakePhotoActivity.this, PERMISSIONS_STORAGE, 8);
                        return;
                    }
                   /* *
                     * 请求请求拍照权限*/

                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(TakePhotoActivity.this, Manifest.permission.CAMERA);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(TakePhotoActivity.this, new String[]{Manifest.permission.CAMERA}, 7);
                        return;
                    }
                    String currentPhotoName = PhotoUtil.createDefaultName();
                    currentPhotoPath = IMG_DIR + currentPhotoName;
                    PhotoUtil.takePhotoCustomerPath(context, IMG_DIR, currentPhotoName, REQUEST_PHOTO_CAMERA);
                    layoutBtns.setVisibility(View.GONE);
                } else {
                    String currentPhotoName = PhotoUtil.createDefaultName();
                    currentPhotoPath = IMG_DIR + currentPhotoName;
                    PhotoUtil.takePhotoCustomerPath(context, IMG_DIR, currentPhotoName, REQUEST_PHOTO_CAMERA);
                    layoutBtns.setVisibility(View.GONE);
                }
            }
        });
        //相册选择
        findViewById(R.id.tvPhoto).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChooseMany) {
//                    多图选择
                    Intent intent = new Intent(context, PhotoAlbumActivity.class);
                    PhotoAlbumActivity.hasCount = chooseManyCurrentCout;
                    startActivityForResult(intent, REQUEST_PHOTO_GARRY);
                } else {
                    // 相册选择
                    PhotoUtil.pickPhoto(context, REQUEST_PHOTO_GARRY_SIGLE);
                }
                layoutBtns.setVisibility(View.GONE);
            }
        });
        //取消
        findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PHOTO_GARRY:
//                    // 图片多选
                    if (data != null) {
                        ArrayList<Item> items = data.getParcelableArrayListExtra(PhotoAlbumActivity.RESULT_FILES);
                        if (items != null && items.size() > 0) {
                            int size = items.size();
                            pickImgsCount = size;
                            ZoomListener zoomListener = new ZoomListener(true);
                            for (int i = 0; i < size; i++) {
                                // 批量图片压缩,图片执行压缩更多的是因为选取的返回的图片是翻转的
                                Item item = items.get(i);
                               if (!TextUtils.isEmpty(item.getPhotoPath())){
                                   File file = new File(item.getPhotoPath());
                                   if (!file.exists()){
                                       ToastUtils.showShort(this,"文件异常");
                                       continue;
                                   }
                               }
                                manyPaths.add(item.getPhotoPath());
//                                NativeUtil.setPic(item.getPhotoPath(),newpath);
                                // imageAdapter.add(new File(item.getPhotoPath()));
                               /* PhotoUtil.zoomImage(context, Uri.fromFile(new File(item.getPhotoPath())), IMG_DIR
                                                + "uploadimg_" + i + "_" + PhotoUtil.createDefaultName(),
                                        AppConstant.IMG_ZOOM_WIDTH_MAX, AppConstant.IMG_ZOOM_HEIGHT_MAX,
                                        AppConstant.IMG_ZOOM_QUALITY, zoomListener, true);*/
                            }
                            Intent intent = new Intent();
                            intent.putStringArrayListExtra(MANY_PHOTO_PATH_ARR, manyPaths);
                            intent.putExtra("url",IMG_DIR);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }
                    break;
                case REQUEST_PHOTO_GARRY_SIGLE:
                    // 单图选择
                    Uri photoPath = data.getData();
                    currentPhotoPath = PicUtils.getPathByUri4kitkat(this, photoPath);
                    if (needCrop) {
                        PhotoUtil.openCropImage(context, currentPhotoPath, AppConstant.IMG_ZOOM_WIDTH_MAX, AppConstant.IMG_ZOOM_HEIGHT_MAX, REQUEST_CROP);
                    }else{
                        onSingChooseSuccess();
                    }
                    break;
                case REQUEST_PHOTO_CAMERA:
                    // 拍照
                    if (currentPhotoPath != null) {
                        if (needCrop) {
                            PhotoUtil.openCropImage(context, currentPhotoPath, AppConstant.IMG_ZOOM_WIDTH_MAX, AppConstant.IMG_ZOOM_HEIGHT_MAX, REQUEST_CROP);
                        } else {
                            onSingChooseSuccess();
                        }
                    }
                    break;
                case REQUEST_CROP:
                    // 裁剪成功
                    onSingChooseSuccess();
                    break;
                default:
                    break;
            }
        } else {
            finish();
        }
    }

    /**
     * 压缩单张照片
     */
    private void zoomImageSingle(String oldPath) {
        if (null != oldPath)
            PhotoUtil.zoomImage(context, Uri.fromFile(new File(oldPath)),
                    IMG_DIR + "uploadimg_" + "_" + PhotoUtil.createDefaultName(), AppConstant.IMG_ZOOM_WIDTH_MAX,
                    AppConstant.IMG_ZOOM_HEIGHT_MAX, AppConstant.IMG_ZOOM_QUALITY, new ZoomListener(false), true);
    }

    @Override
    public void finish() {
        super.finish();

    }

    /**
     * 单张选择完成
     */
    private void onSingChooseSuccess() {
        Intent intent = new Intent();
        intent.putExtra(SINGLE_PHOTO_PATH, currentPhotoPath);
        setResult(RESULT_OK, intent);
        finish();
    }

    /**
     * 压缩监听 Author:heiyue Email:heiyue623@126.com 2015-4-14下午4:45:26
     */
    class ZoomListener implements PhotoUtil.ImageZoomCallBack {
        // 多图选择标识
        private boolean isMany;

        public ZoomListener(boolean isMany) {
            this.isMany = isMany;
        }

        @Override
        public void onImgZoomStart() {

        }

        @Override
        public synchronized void onImgZoomSuccess(String newPath) {
            if (isMany) {
                // 多图业务执行
                successZoomSize++;
                manyPaths.add(newPath);

                Log.e("takePhoto",newPath+"    -----  "+successZoomSize);
                // 最后一个压缩成功
                if (pickImgsCount > 0 && successZoomSize == pickImgsCount) {
                    Log.e("takePhoto",successZoomSize+"    -----  "+successZoomSize);
                    // 并且不满最大值
                    successZoomSize = 0;
                    pickImgsCount = 0;
                    // 执行完成
                    Intent intent = new Intent();
                    intent.putStringArrayListExtra(MANY_PHOTO_PATH_ARR, manyPaths);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            } else {
                currentPhotoPath = newPath;
                if (needCrop) {
                    // 需要裁剪,裁剪的时候注意 有点手机不支持保存路径和路径一致
                    currentPhotoPath = IMG_DIR + PhotoUtil.createDefaultName();
                    PhotoUtil.openCropImage(context, currentPhotoPath, 720, 1080, REQUEST_CROP);
                } else {
                    // 单图业务执行
                    onSingChooseSuccess();
                }
            }
        }

        @Override
        public void onImgZoomFail() {
            Toast.makeText(context, "图片获取失败", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 8:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    Toast.makeText(TakePhotoActivity.this, "读写SD卡权限被禁止", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            case 7:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                } else {
                    // Permission Denied
                    Toast.makeText(TakePhotoActivity.this, "相机权限被禁止", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}
