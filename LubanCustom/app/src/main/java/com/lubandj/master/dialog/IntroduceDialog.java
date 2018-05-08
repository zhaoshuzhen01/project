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
import com.lubandj.master.customview.ChooseXingHao;
import com.lubandj.master.model.AddCarModel;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ${zhaoshuzhen} on 2018/1/27.
 */

public class IntroduceDialog extends DialogFragment implements View.OnClickListener,DataCall,ChooseXingHao.XingHao{

    ImageView topChose;
    TextView buttonText;
    private TextView top_name ;
    private TextView top_price ;
    private ImageView jiaView,jianView;
    private TextView countView ;
    private ChooseXingHao chooseXingHao ;
    private int count ;
    private AddCarModel addCarModel ;

    private ServiceDetailBeen data;
    private ServiceDetailBeen.InfoBean.ItemsBean xinghao ;
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
        top_name = view.findViewById(R.id.top_name);
        top_price = view.findViewById(R.id.top_price);
        chooseXingHao = view.findViewById(R.id.introde_middle);
        chooseXingHao.setData(data.getInfo().getItems(),this);
        countView = view.findViewById(R.id.count);
        jianView.setOnClickListener(this);
        jiaView.setOnClickListener(this);
        topChose.setOnClickListener(this);
        buttonText.setOnClickListener(this);
        top_name.setText(data.getInfo().getName());
        if (xinghao!=null){
            double price = Double.parseDouble(xinghao.getPrice())*count;
            top_price.setText("¥ " +price);
            countView.setText(count+"");
            top_name.setText(xinghao.getItem_name()+"");
        }
    }
    public void setData(ServiceDetailBeen data, ImageView main_car,TextView car_msgCount,TextView tv_show_price) {
        this.data = data;
        this.main_car = main_car ;
        this.car_msgCount = car_msgCount ;
        this.tv_show_price = tv_show_price ;
        initData(data);
    }

    /**
     * 填充数据
     * @param data
     */
    private void initData(ServiceDetailBeen data){
        this.data = data ;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void clickShow(FragmentManager manager, String tag, Context context){
        count=0;
        this.show(manager,"");
        addCarModel = new AddCarModel(context,this);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dialog_introduce_close:
                dismiss();
                break;
            case R.id.button_text:
                if (xinghao==null){
                    ToastUtils.showShort(getActivity(),"请选择型号");
                    return;
                }
                if (count>0)
                addCarModel.addCar(xinghao.getService_type(),xinghao.getService_id(),xinghao.getSpec_id(),xinghao.getItem_name(),xinghao.getPrice(),count+"");
                else
                    ToastUtils.showShort(getActivity(),"数量不能为0个");
                break;
            case R.id.jian:
                if (count>0){
                    --count;
                    countView.setText(count+"");
                    double price = Double.parseDouble(xinghao.getPrice())*count;
                    top_price.setText("¥ "+price+"");
                }
                break;
            case R.id.jia:
                ++count;
                countView.setText(count+"");
                double price = Double.parseDouble(xinghao.getPrice())*count;
                top_price.setText("¥ "+price+"");
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
        car_msgCount.setText(((TextUtils.isEmpty(mcount)?0:Integer.parseInt(mcount))+count)+"");

        ShoppingCartBean bean1 = new ShoppingCartBean(1,xinghao.getItem_name(),"",0, Double.parseDouble(xinghao.getPrice()),count,Integer.parseInt(xinghao.getService_type()),Integer.parseInt(xinghao.getService_id()),Integer.parseInt(xinghao.getSpec_id()));
        bean1.setImageUrl(xinghao.getItem_pic());
        LocalleCarData.newInstance().setShoppingCartBeanList(bean1);
        double totalPrice = LocalleCarData.newInstance().getTotalPrice();
        totalPrice += bean1.getPrice() * bean1.getCount();
        LocalleCarData.newInstance().setTotalPrice(totalPrice);
        tv_show_price.setText("¥ " +totalPrice);
    }

    @Override
    public void getXingHao(ServiceDetailBeen.InfoBean.ItemsBean xinghao) {
        this.xinghao = xinghao ;
        top_name.setText(xinghao.getItem_name()+"");
        double price = Double.parseDouble(xinghao.getPrice())*count;
        top_price.setText("¥ " +price);
    }
}
