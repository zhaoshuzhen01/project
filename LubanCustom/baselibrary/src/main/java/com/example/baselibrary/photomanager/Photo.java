package com.example.baselibrary.photomanager;

/**
 * Created by LE on 2016/8/5.
 */
public class Photo implements IPhoto {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    String path;
    int res;

    public Photo(String path, int res) {
        this.path = path;
        this.res = res;
    }

    @Override
    public String getPhotoPath() {
        return path;
    }

    @Override
    public int getPhotoRes() {
        return res;
    }
}
