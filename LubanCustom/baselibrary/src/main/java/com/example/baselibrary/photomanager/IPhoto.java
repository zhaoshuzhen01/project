package com.example.baselibrary.photomanager;

import java.io.Serializable;

/**
 * 图片信息
 * Created by xiaobaima on 15-11-15.
 */
public interface IPhoto extends Serializable {
    /**
     * 获取图片路径
     *
     * @return
     */
    public String getPhotoPath();

    /**
     * 项目里的图片路径
     *
     * @return
     */
    public int getPhotoRes();
}
