package com.lubandj.master.DialogUtil;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.baselibrary.widget.AlertDialog;
import com.lubandj.master.Canstance;
import com.lubandj.master.dialog.TipDialog;
import com.lubandj.master.login.LoginActivity;
import com.lubandj.master.utils.CommonUtils;
import com.lubandj.master.worksheet.WorkSheetDetailsActivity;
import com.lubandj.master.worksheet.WorkSheetListActivity;

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

    public DialogTagin messageShow(String currentType){
        finishDialog(currentType);
        return dialogTagin ;
    }
    public void setDialogSure(DialogSure dialogSure) {
        this.dialogSure = dialogSure;
    }
    public   void finishDialog(final String currentType){
        TipDialog outDialog = new TipDialog(context);
        outDialog.setNoPomptTitle();
        outDialog.setTextDes(getRemindContent(currentType));
        outDialog.setButton1("确定", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                dialog.dismiss();
                if (dialogSure==null){
                    Intent intent = new Intent(context, WorkSheetDetailsActivity.class);
//                            intent.putExtra(WorkSheetDetailsActivity.KEY_DETAILS_ID,currentType);
                    context.startActivity(intent);
                }else {
                    dialogSure.dialogCall();
                }
            }
        });
        outDialog.setButton2("取消", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                dialog.dismiss();
            }
        });
        outDialog.setCancelable(false);
        outDialog.setCanceledOnTouchOutside(false);
        outDialog.show();
    }

    public DialogTagin showDialog(String text){
        TipDialog outDialog = new TipDialog(context);
        outDialog.setNoPomptTitle();
        outDialog.setTextDes(text);
        outDialog.setButton1("确定", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                dialog.dismiss();
                if (dialogTagin!=null)
                    dialogSure.dialogCall();
            }
        });
        outDialog.setButton2("取消", new TipDialog.DialogButtonOnClickListener() {
            @Override
            public void onClick(View button, TipDialog dialog) {
                dialog.dismiss();
            }
        });
        outDialog.setCancelable(false);
        outDialog.setCanceledOnTouchOutside(false);
        outDialog.show();
        return dialogTagin;
    }
    private String getRemindContent(String currentType){
        String content="";
        switch (currentType) {
            case Canstance.KEY_SHEET_STATUS_TO_PERFORM:
                content="开始前往服务地点";
                break;
            case Canstance.KEY_SHEET_STATUS_ON_ROAD:
                content="开始服务";
                break;
            case Canstance.KEY_SHEET_STATUS_IN_SERVICE:
                content="已完成服务";
                break;
        }
        return content;
    }

    public interface  DialogSure{
        void dialogCall();
    }
}
