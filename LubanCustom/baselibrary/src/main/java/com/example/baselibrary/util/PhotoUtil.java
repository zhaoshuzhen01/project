package com.example.baselibrary.util;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PhotoUtil {
    public static final int TAKE_PICTURE = 2;
    public static final int SELECT_PIC_BY_PICK_PHOTO = 3;
    /**
     * 获取到的图片路径
     */
    public static String picPath = "";
    public static Uri photoUri;
    /* 持有私有静态实例，防止被引用，此处赋值为null，目的是实现延迟加载 */
    private static PhotoUtil instance = null;

    /* 私有构造方法，防止被实例化 */
    public PhotoUtil() {

    }

    /* 懒汉式变种，解决线程安全问题* */
    public static synchronized PhotoUtil getInstance() {
//		if (instance == null) {
        instance = new PhotoUtil();
//		}
        return instance;
    }

    /***
     * 120.* 从相册中取图片
     * <p/>
     * 121.
     */
    public void pickPhoto(Activity context) {
        Intent intent = new Intent();
//        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, SELECT_PIC_BY_PICK_PHOTO);
    }

    /**
     * 调取系统拍照功能
     */
    public void takePhoto(Activity context) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //在图库创建文件
        File path = context.getCacheDir();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.CHINA).format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File image = new File(path, imageFileName + ".jpg");
        picPath = image.getAbsolutePath();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
//            ContentValues values = new ContentValues();
            photoUri = Uri.fromFile(image);
        } else {
            //FileProvider 是一个特殊的 ContentProvider 的子类，
            //它使用 content:// Uri 代替了 file:/// Uri. ，更便利而且安全的为另一个app分享文件
            photoUri = FileProvider.getUriForFile(context,
                    "com.lubandj.master.fileprovider",
                    image);
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        intent.putExtra("return-data", true);
        context.startActivityForResult(intent, TAKE_PICTURE);
    }

    /**
     * 剪切图片
     *
     * @param context
     */
    public void dealPhoto(Activity context, Intent data, boolean isTakePhoto) {
        try {
            if (!isTakePhoto)
                photoUri = data.getData();
//            else {
//                File file = new File(picPath);
//                photoUri = Uri.fromFile(file);
//            }
            Intent intent = new Intent();
            intent.setAction("com.android.camera.action.CROP");
            intent.setDataAndType(photoUri, "image/*");// mUri是已经选择的图片Uri
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);// 裁剪框比例
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 160);// 输出图片大小
            intent.putExtra("outputY", 160);
            intent.putExtra("return-data", true);
//					 intent.putExtra("noFaceDetection", true);
            context.startActivityForResult(intent, 200);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteCache() {
        File file = new File(picPath);
        file.delete();
    }
}
