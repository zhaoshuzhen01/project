package com.example.baselibrary.photomanager;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Display;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class PicUtils {
    public static String externalPath = Environment.getExternalStorageDirectory().getPath();
    public static String FilePath = externalPath + "/chuimi/download";
    private static int _displaywidth = 640;
    private static int _displayheight = 960;
    private static int _displaypixels = _displaywidth * _displayheight;

    /**
     * @param imageUri
     * @return
     */
    @SuppressWarnings("deprecation")
    public static BitmapDrawable getfriendicon(URL imageUri) {

        BitmapDrawable icon = null;
        try {
            HttpURLConnection hp = (HttpURLConnection) imageUri
                    .openConnection();
            icon = new BitmapDrawable(hp.getInputStream());
            hp.disconnect();// 鍏抽棴杩炴帴
        } catch (Exception e) {
        }
        return icon;
    }

    /**
     * dip转成pixel
     *
     * @param dp
     * @return
     */
    public static int dp2px(Context con, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                con.getResources().getDisplayMetrics());
    }

    /**
     * @param imageUri
     * @return
     */
    @SuppressWarnings("deprecation")
    public static BitmapDrawable getcontentPic(String imageUri) {
        URL imgUrl = null;
        try {
            imgUrl = new URL(imageUri);
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
        BitmapDrawable icon = null;
        try {
            HttpURLConnection hp = (HttpURLConnection) imgUrl.openConnection();
            icon = new BitmapDrawable(hp.getInputStream());
            hp.disconnect();
        } catch (Exception e) {
        }
        return icon;
    }

    public static int getDispaly(Activity context) {
        int result = 7;
        Display mDisplay = context.getWindowManager().getDefaultDisplay();
        @SuppressWarnings("deprecation")
        int W = mDisplay.getWidth();
        //获取 屏幕分辨率  并按照以下方法返回数值， 默认返回 1080*720P
//	    	5 – Android  320（低分辨率   市面上很少 可不考虑）
//	    	  6 – Android  480（中）
//	    	  7 - Android  720（高）
//	    	  8 – Android  1080（超高）

        if (W < 480) {
            result = 5;
        } else if (W < 720) {
            result = 6;
        } else if (W < 1080) {
            result = 7;
        } else if (W < 1280) {
            result = 8;
        } else {
            result = 9;
        }


        return result;
    }

    /**
     * @param imageUri
     * @return
     */
    public static Bitmap getusericon(URL imageUri) {

        URL myFileUrl = imageUri;
        Bitmap bitmap = null;
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public static String getEncryWebFile(String url) {
        // 显示网络上的图片
        String dstFile = null;
        try {
            URL myFileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();

            String fileName = getFileName(url);
            File path = new File(FilePath);
            if (!path.exists())
                path.mkdirs();
            dstFile = FilePath + "/" + fileName;
            String temp = dstFile + ".jpg";
            File f = new File(dstFile);
            if (f.exists()) {
                return dstFile;
            }
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(temp);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = is.read(buffer)) > 0) {
                out.write(buffer, 0, r);
                out.flush();
            }
            is.close();
            out.close();
            File t = new File(temp);
            t.delete();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        return dstFile;
    }

    public static String getFileName(String path) {
        int index = path.lastIndexOf("/");
        int last = path.lastIndexOf(".");
        if (last != -1 && last > index + 1)
            return path.substring(index + 1, last + 1);
        else
            return path.substring(index + 1);
    }

    public static String getWebFile(String url) {
        // 显示网络上的图片
        String dstFile = null;
        try {
            URL myFileUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();

            String fileName = getFileName(url);
            File path = new File(FilePath);
            if (!path.exists())
                path.mkdirs();
            dstFile = FilePath + "/" + fileName;
            File f = new File(dstFile);
            if (f.exists()) {
                return dstFile;
            }
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(dstFile);
            byte[] buffer = new byte[1024];
            int r;
            while ((r = is.read(buffer)) > 0) {
                out.write(buffer, 0, r);
                out.flush();
            }
            is.close();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
        return dstFile;
    }

    /**
     * @param imageUri
     * @return
     * @throws MalformedURLException
     */
    public static Bitmap getbitmap(String imageUri) {
        //
        Bitmap bitmap = null;
        try {
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }


    public static boolean downpic(String picName, Bitmap bitmap) {
        boolean nowbol = false;
        try {
            File saveFile = new File(picName);
            if (!saveFile.exists()) {
                saveFile.createNewFile();
            }
            FileOutputStream saveFileOutputStream;
            saveFileOutputStream = new FileOutputStream(saveFile);
            nowbol = bitmap.compress(Bitmap.CompressFormat.PNG, 100,
                    saveFileOutputStream);
            saveFileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nowbol;
    }

    public static void writeTofiles(Context context, Bitmap bitmap,
                                    String filename, int max, Bitmap.CompressFormat type) {
        if (null == type)
            type = Bitmap.CompressFormat.JPEG;
        if (max < 0 || max > 100)
            max = 100;
        BufferedOutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(context.openFileOutput(
                    filename, Context.MODE_PRIVATE));
            bitmap.compress(type, max, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * @param filename
     * @param is
     * @return
     */
    public static String writefile(Context context, String filename,
                                   InputStream is) {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(is);
            outputStream = new BufferedOutputStream(context.openFileOutput(
                    filename, Context.MODE_PRIVATE));
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return context.getFilesDir() + "/" + filename + ".jpg";
    }

    //
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newbmp;
    }

    //
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, drawable
                .getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888
                : Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    //
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {
        if (bitmap == null) {
            return null;
        }

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    //
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        final int reflectionGap = 4;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        Bitmap reflectionImage = Bitmap.createBitmap(bitmap, 0, height / 2,
                width, height / 2, matrix, false);

        Bitmap bitmapWithReflection = Bitmap.createBitmap(width,
                (height + height / 2), Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmapWithReflection);
        canvas.drawBitmap(bitmap, 0, 0, null);
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap, deafalutPaint);

        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);

        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0, bitmap.getHeight(), 0,
                bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff,
                0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);
        // Set the Transfer mode to be porter duff and destination in
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
                + reflectionGap, paint);

        return bitmapWithReflection;
    }

    public static void resetPicture(String path) {
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            exifInterface.setAttribute(ExifInterface.TAG_ORIENTATION, "no");
            exifInterface.saveAttributes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap translatePicture(String path, Bitmap bm, int width, int height) {
        int degree = readPictureDegree(path);

        bm = PicUtils.zoomBitmap(bm, width, height);
        if (degree == 90) {
            bm = rotaingImageView(90, bm);
        } else if (degree == 180) {

            bm = rotaingImageView(180, bm);
        } else if (degree == 270) {
            bm = rotaingImageView(270, bm);
        } else if (degree == 0) {

        }
        return bm;
    }

    public static Bitmap translatePicture(String path, Bitmap bm, int param) {
        int degree = readPictureDegree(path);
        int w = bm.getWidth();
        int h = bm.getHeight();


        if (degree == 90) {
            if (h > param) {
                int zh = w * param / h;
                bm = PicUtils.zoomBitmap(bm, zh, param);
            }
            bm = rotaingImageView(90, bm);
        } else if (degree == 180) {
            if (w > param) {
                int zh = h * param / w;
                bm = PicUtils.zoomBitmap(bm, param, zh);
            }
            bm = rotaingImageView(180, bm);
        } else if (degree == 270) {
            if (h > param) {
                int zh = w * param / h;
                bm = PicUtils.zoomBitmap(bm, zh, param);
            }
            bm = rotaingImageView(270, bm);
        } else if (degree == 0) {
            if (w > param) {
                int zh = h * param / w;
                bm = PicUtils.zoomBitmap(bm, param, zh);
            }
        }
        return bm;
    }

    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            //ExifInterface ef = new ExifInterface();
            ExifInterface exifInterface = new ExifInterface(path);

            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * @param angle
     * @param bitmap
     * @return
     */
    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        //
        Bitmap resizedBitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        return resizedBitmap;
    }

    public static Bitmap getLocalLargeFilePic(String path) {


        Bitmap bmp = null;
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            InputStream stream = new FileInputStream(path);

            byte[] bytes = getBytes(stream);
            // 这3句是处理图片溢出的begin( 如果不需要处理溢出直接 opts.inSampleSize=1;)
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
            opts.inSampleSize = computeSampleSize(opts, -1, _displaypixels);
            // end
            opts.inJustDecodeBounds = false;
            bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;

    }

    /**
     * 数据流转成btyle[]数组
     */
    private static byte[] getBytes(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] b = new byte[2048];
        int len = 0;
        try {
            while ((len = is.read(b, 0, 2048)) != -1) {
                baos.write(b, 0, len);
                baos.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] bytes = baos.toByteArray();
        return bytes;
    }


    /****
     * 处理图片bitmap size exceeds VM budget （Out Of Memory 内存溢出）
     */
    private static int computeSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private static int computeInitialSampleSize(BitmapFactory.Options options,
                                                int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            return lowerBound;
        }
        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 4.4以前通过Uri获取路径：data是Uri，filename是一个String的字符串，用来保存路径。
     * @param context
     * @param data
     * @return
     */
        public static String getPathByUri(Context context, Uri data) {
        String filename=null;
        if (data.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(data, new String[] { "_data" }, null, null, null);
            if (cursor.moveToFirst()) {
                filename = cursor.getString(0);
            }
        } else if (data.getScheme().toString().compareTo("file") == 0) {// file:///开头的uri
            filename = data.toString();
            filename = data.toString().replace("file://", "");// 替换file://
            if (!filename.startsWith("/mnt")) {// 加上"/mnt"头
                filename += "/mnt";
            }
        }

        return filename;
    }

    // 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }
}
