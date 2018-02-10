package com.example.baselibrary.photomanager;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baselibrary.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lile on 15-11-15.
 */
public class AlbumAdapter extends BaseAdapter {
    private List<Album> albumList;
    private Context context;
    private ViewHolder holder;
    private Map<String, Bitmap> cacheBms = new HashMap<String, Bitmap>();

    public AlbumAdapter(List<Album> list, Context context) {
        this.albumList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return albumList.size();
    }

    @Override
    public Object getItem(int position) {
        return albumList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.muti_list_album_item, null);
            holder = new ViewHolder();
            holder.iv = (ImageView) convertView.findViewById(R.id.photoalbum_item_image);
            holder.tv = (TextView) convertView.findViewById(R.id.photoalbum_item_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (cacheBms.get(position + "") == null) {
            Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), albumList.get(position).getBitmap(), MediaStore.Video.Thumbnails.MINI_KIND, null);
            int rotate = PictureManageUtil.getCameraPhotoOrientation(albumList.get(position).getBitList().get(0).getPhotoPath());
            bitmap = PictureManageUtil.rotateBitmap(bitmap, rotate);
            holder.iv.setImageBitmap(bitmap);
            cacheBms.put(position + "", bitmap);
        } else {
            holder.iv.setImageBitmap(cacheBms.get(position + ""));
        }
        holder.tv.setText(albumList.get(position).getName() + " ( " + albumList.get(position).getCount() + " )");
        return convertView;
    }

    static class ViewHolder {
        ImageView iv;
        TextView tv;
    }
}
