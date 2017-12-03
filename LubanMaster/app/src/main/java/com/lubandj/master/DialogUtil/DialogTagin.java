package com.lubandj.master.DialogUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.Canstance;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;

/**
 * Created by ${zhaoshuzhen} on 2017/12/3.
 */

public class DialogTagin {
    private static DialogTagin dialogTagin = null;
    private Context context ;
    private DialogSure dialogSure ;
    private DialogTagin(Context context){
        this.context = context ;
    }
    public static DialogTagin getDialogTagin(Context context){
                dialogTagin = new DialogTagin(context);
        return dialogTagin;
    }

    public DialogTagin messageShow(int currentType){
        finishDialog(currentType);
        return dialogTagin ;
    }
    public void setDialogSure(DialogSure dialogSure) {
        this.dialogSure = dialogSure;
    }
    public   void finishDialog(final int currentType){
        new AlertDialog(context)
                .builder()
                .setTitle("确认提醒")
                .setMsg(getRemindContent(currentType))
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (dialogSure==null){
                            Intent intent = new Intent(context, WorkSheetDetailsActivity.class);
                            intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_TYPE,currentType);
                            context.startActivity(intent);
                        }else {
                            dialogSure.dialogCall();
                        }
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }
    private String getRemindContent(int currentType){
        String content="";
        switch (currentType) {
            case Canstance.TYPE_TO_PERFORM:
                content="请确认将开始前往服务地点";
                break;
            case Canstance.TYPE_ON_ROAD:
                content="请确认开始服务";
                break;
            case Canstance.TYPE_IN_SERVICE:
                content="请确认服务已完成";
                break;
        }
        return content;
    }

    public interface  DialogSure{
        void dialogCall();
    }
}
