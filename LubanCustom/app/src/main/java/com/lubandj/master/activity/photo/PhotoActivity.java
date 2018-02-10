package com.lubandj.master.activity.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.example.baselibrary.R;
import com.example.baselibrary.photomanager.ActivityManager;
import com.example.baselibrary.photomanager.Album;
import com.example.baselibrary.photomanager.Item;
import com.example.baselibrary.photomanager.PicAdappter;
import com.example.baselibrary.tools.Tools;

import java.util.ArrayList;

/**
 * 子级照片
 * Created by xiaobaima on 15-11-15.
 */
public class PhotoActivity extends Activity implements View.OnClickListener {
    /**
     * 前面已经选择完了的张数
     */
    public static int hasChooseCount;
    private GridView gv;
    private Album album;
    private PicAdappter adapter;
    private TextView tv;
    private int chooseNum = 0;
    private Button finishBtn;//完成按钮
    /**
     * 默认最大张数10张
     */
    private int mMaxImages = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muti_list_album_gridview);

        //本地最多选择照片
        mMaxImages = this.getIntent().getIntExtra("max", mMaxImages);


        tv = (TextView) findViewById(R.id.photo_album_chooseNum);
        finishBtn = (Button) findViewById(R.id.finishBtn);
        album = (Album) getIntent().getExtras().get("album");
        /**获取已经选择的图片**/
        for (int i = 0; i < album.getBitList().size(); i++) {
            if (album.getBitList().get(i).isSelect()) {
                chooseNum++;
            }
        }
        gv = (GridView) findViewById(R.id.photo_gridview);
        adapter = new PicAdappter(this, album, gv);
        gv.setAdapter(adapter);
        tv.setText("选中" + chooseNum + "张");
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Item> fileNamesList = new ArrayList<Item>();
                for (int i = 0; i < album.getBitList().size(); i++) {
                    if (album.getBitList().get(i).isSelect()) {
                        fileNamesList.add(album.getBitList().get(i));
                    }
                }
                Intent it = new Intent();
                it.putParcelableArrayListExtra("result_files", fileNamesList);
                ActivityManager.getActivity("PhotoAlbumActivity").setResult(RESULT_OK, it);
                ActivityManager.getActivity("PhotoAlbumActivity").finish();
                ActivityManager.removeActivity("PhotoAlbumActivity");
                PhotoActivity.this.finish();
            }
        });
    }

    public void back(View v) {
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() != null) {
            /**
             *  item.getmSelect().setTag("select_"+position);
             item.getmImageView().setTag("image_"+position);
             */
            if (view.getTag().toString().startsWith("select_")) {
                String vPosition = view.getTag().toString().substring("select_".length());
                doCheckEvent(vPosition);
            } else if (view.getTag().toString().startsWith("image_")) {
                String vPosition = view.getTag().toString().substring("image_".length());
//				showImgView(vPosition);
                doCheckEvent(vPosition);
            }
        }
    }

    private void doCheckEvent(String vPosition) {
        if (album.getBitList().get(Integer.parseInt(vPosition)).isSelect()) {
            album.getBitList().get(Integer.parseInt(vPosition)).setSelect(false);
            chooseNum--;
        } else {
            if (chooseNum + PhotoAlbumActivity.hasCount < mMaxImages) { //最多张数，默认10张
                album.getBitList().get(Integer.parseInt(vPosition)).setSelect(true);
                chooseNum++;
            } else {
                Tools.Toast(this,"不能选择更多的照片啦！",false);
//                Toast.makeText(getApplicationContext(), "最多为" + mMaxImages + "张图",Toast.LENGTH_SHORT).show();
            }

        }
        tv.setText("选中" + chooseNum + "个");
        adapter.notifyDataSetChanged();
    }

    private void showImgView(String vPosition) {
//		Intent intent = new Intent(PhotoActivity.this, ViewPagerActivity.class);
//		final String paths = album.getBitList().get(Integer.parseInt(vPosition)).getPhotoPath();
//
//		//List->>ArrayList
//		ArrayList<Item>  fileNames= new ArrayList<Item> ();
//		for(int i = 0;i<album.getBitList().size();i++){
//			fileNames.add(album.getBitList().get(i));
//		}
//		intent.putExtra(ViewPagerActivity.FILES, fileNames);
//		intent.putExtra(ViewPagerActivity.CURRENT_INDEX, Integer.parseInt(vPosition));
//		startActivity(intent);
    }
}
