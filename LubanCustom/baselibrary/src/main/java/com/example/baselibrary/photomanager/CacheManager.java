package com.example.baselibrary.photomanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;



import java.io.File;

/**
 * 路径及缓存管理
 * Created by lile on 15-11-15.
 */
public class CacheManager {
    public static final String DIR_CACHE = Environment.getExternalStorageDirectory() + "/";

    /**
     * 获取图片缓存路径 如果不存在，则创建路径
     *
     * @param context
     * @return
     */
    public static String getImgDir(Context context) {
        String packageName = context.getPackageName();
        String path = DIR_CACHE + "luban_image/camera/";
//		String path = DIR_CACHE + "/.img/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 清除缓存图片
     */
    public static void chearImgCache(Context context) {
        try {
            String imgDir = getImgDir(context);
            File dir = new File(imgDir);
            if (dir != null && dir.isDirectory()) {
                File[] files = dir.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }
        } catch (Exception e) {
        }

    }
}
