package com.example.baselibrary.tools;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ${zhaoshuzhen} on 2018/2/10.
 */

public class KeyBorad {

    //打开软键盘
    public static void showKeyboard(EditText editText,Context context){
        InputMethodManager inputMethodManager = (InputMethodManager)context. getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }

    public static void DelayShow(final EditText editText, final Context context){
        Timer timer = new Timer();
        timer.schedule(new TimerTask()   {
            public void run() {
               showKeyboard(editText,context);
            }
        }, 300);
    }
}
