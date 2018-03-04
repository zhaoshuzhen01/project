package com.lubandj.master.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.LocalleCarData;
import com.lubandj.master.R;
import com.lubandj.master.been.ServiceDetailBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.model.AddCarModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class IntroduceDialog extends DialogFragment implements View.OnClickListener,DataCall{

    ImageView topChose;
    TextView buttonText;

    private ImageView jiaView,jianView;
    private TextView countView ;
    private int count ;
    private AddCarModel addCarModel ;

    private ServiceDetailBeen data;
    private ImageView main_car ;
    private TextView  car_msgCount,tv_show_price;


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
        dialog.setCanceledOnTouchOutside(true);
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
        jianView = view.findViewById(R.id.jian);
        jiaView = view.findViewById(R.id.jia);
        countView = view.findViewById(R.id.count);
        jianView.setOnClickListener(this);
        jiaView.setOnClickListener(this);
        topChose.setOnClickListener(this);
        buttonText.setOnClickListener(this);
    }
    public void setData(ServiceDetailBeen data, ImageView main_car,TextView car_msgCount,TextView tv_show_price) {
        this.data = data;
        this.main_car = main_car ;
        this.car_msgCount = car_msgCount ;
        this.tv_show_price = tv_show_price ;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void clickShow(FragmentManager manager, String tag, Context context){
        this.show(manager,"");
        addCarModel = new AddCarModel(context,this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_introduce_close:
                dismiss();
                break;
            case R.id.button_text:
                if (count>0)
                addCarModel.addCar("1",data.getInfo().getService_id(),"2",data.getInfo().getName(),"99.00",count+"");
                else
                    ToastUtils.showShort(getActivity(),"数量不能为0个");
                break;
            case R.id.jian:
                if (count>0){
                    --count;
                    countView.setText(count+"");
                }
                break;
            case R.id.jia:
                ++count;
                countView.setText(count+"");
                break;
        }
    }

    @Override
    public void getServiceData(Object mdata) {
        ToastUtils.showShort(getActivity(),"加入购物车");
        main_car.setImageResource(R.drawable.car);
        main_car.setTag(R.drawable.car);
        dismiss();
        car_msgCount.setVisibility(View.VISIBLE);
        String mcount = car_msgCount.getText().toString();
        car_msgCount.setText((TextUtils.isEmpty(mcount)?0:Integer.parseInt(mcount)+count)+"");

        ShoppingCartBean bean1 = new ShoppingCartBean(1,data.getInfo().getName(),"",0,99.00,count,1,Integer.parseInt(data.getInfo().getService_id()),2);
        bean1.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
        LocalleCarData.newInstance().setShoppingCartBeanList(bean1);
        double totalPrice = LocalleCarData.newInstance().getTotalPrice();
        totalPrice += bean1.getPrice() * bean1.getCount();
        LocalleCarData.newInstance().setTotalPrice(totalPrice);
        tv_show_price.setText("¥ " +totalPrice);
    }
}
