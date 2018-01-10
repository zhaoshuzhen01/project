package com.lubandj.master.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;
import com.lubandj.master.InstanceUtil.NotifyMsgInstance;
import com.lubandj.master.TApplication;
import com.lubandj.master.been.MsgCenterBeen;

import java.util.List;

/**
 * Created by ${zhaoshuzhen} on 2018/1/10.
 */

public class DbInstance {
    private MsgDatahelper msgDatahelper ;
    private SQLiteDatabase db ;
    private static class SingletonHolder {
        private static DbInstance instance = new DbInstance();
    }

    /**
     * 私有的构造函数
     */
    private DbInstance() {
        msgDatahelper = new MsgDatahelper(TApplication.context);
    }

    public static DbInstance getInstance() {
        return DbInstance.SingletonHolder.instance;
    }

    public void insertDatas(){
        db = msgDatahelper.getWritableDatabase();
        db.beginTransaction();
        List<String>msgs = NotifyMsgInstance.getInstance().getMsgs();
        try {
            for (String msg:msgs){
                db.execSQL("INSERT INTO "+ msgDatahelper.TABLE_NAME +" VALUES(null, ?)", new Object[]{msg});
            }
            db.setTransactionSuccessful();  //设置事务成功完成
        } finally {
            db.endTransaction();    //结束事务
        }
        NotifyMsgInstance.getInstance().clearMsgs();
        NotifyMsgInstance.getInstance().clearNotifyBeens();
        closeDB();
    }

    public void queryDatas(){
        db = msgDatahelper.getReadableDatabase();
        NotifyMsgInstance.getInstance().clearNotifyBeens();
        Cursor c = db.rawQuery("SELECT * FROM "+msgDatahelper.TABLE_NAME, null);
        while (c.moveToNext()) {
            String msg = c.getString(c.getColumnIndex("msg"));
            MsgCenterBeen.InfoBean.ListBean listBean = new Gson().fromJson(msg,MsgCenterBeen.InfoBean.ListBean.class);
            NotifyMsgInstance.getInstance().addNotifyBeens(listBean);
        }
        c.close();
        closeDB();
    }
    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }
}
