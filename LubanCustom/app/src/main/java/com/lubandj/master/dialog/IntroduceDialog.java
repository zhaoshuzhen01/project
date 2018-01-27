package com.lubandj.master.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class IntroduceDialog extends DialogFragment implements View.OnClickListener{

    ImageView topChose;
    TextView buttonText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(false);
        View view = View.inflate(getActivity(), R.layout.dialog_introduce, null);
        dialog.setContentView(view);
        try {
            initView(view);
        } catch (Exception e) {
        }
        return dialog;

    }

    private void initView(View view) {
        topChose = view.findViewById(R.id.dialog_introduce_close);
        buttonText=view.findViewById(R.id.button_text);
        topChose.setOnClickListener(this);
        buttonText.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_introduce_close:
                dismiss();
                break;
            case R.id.button_text:
                ToastUtils.showShort(getActivity(),"加入购物车");
                break;
        }
    }
}
