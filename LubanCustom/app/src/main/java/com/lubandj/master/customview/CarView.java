package com.lubandj.master.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.example.baselibrary.util.NetworkUtils;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.LocalleCarData;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.activity.MainCantainActivity;
import com.lubandj.master.adapter.ShoppingCartAdapter;
import com.lubandj.master.been.CarListBeen;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.model.CarListModel;
import com.lubandj.master.model.ClearCarListsModel;
import com.lubandj.master.model.UpCarModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ${zhaoshuzhen} on 2018/1/28.
 */

public class CarView extends LinearLayout implements View.OnClickListener,  ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface,DialogTagin.DialogSure, PullToRefreshAndPushToLoadView6.PullToRefreshAndPushToLoadMoreListener,IbaseView<CarListBeen.InfoBean>,DataCall {

    //全选
    private  CheckBox ckAll;
    private boolean chooseAll = true ;
    protected PullToRefreshAndPushToLoadView6 pullToRefreshAndPushToLoadView;
    RecyclerView list_shopping_cart;
    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean flag = false;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private boolean mSelect;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private Context context ;
    private TextView cleartext;
    private RelativeLayout carView;
    private BaseReflushPresenter msgCenterPresenter;
    private ClearCarListsModel clearCarListsModel;
    private UpCarModel upCarModel ;
    private ImageView main_car ;
    public void setCar_msgCount(RelativeLayout carView,TextView car_msgCount,ImageView main_car,TextView tv_show_price) {
        this.car_msgCount = car_msgCount;
        this.carView = carView ;
        this.main_car = main_car ;
        this.tv_show_price =tv_show_price;
    }

    private TextView car_msgCount ,tv_show_price;

    public CarView(Context context) {
        super(context);
        initView(context);
    }

    public CarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.context = context ;
        View view = LayoutInflater.from(context).inflate(R.layout.view_car, this);
        ButterKnife.inject(this);

        ckAll= (CheckBox) view.findViewById(R.id.ck_all);
        cleartext = view.findViewById(R.id.cleartext);
        cleartext.setOnClickListener(this);
        list_shopping_cart= (RecyclerView) view.findViewById(R.id.list_shopping_cart);
        list_shopping_cart.setLayoutManager(new LinearLayoutManager(context));
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6) view.findViewById(R.id.prpt);
        pullToRefreshAndPushToLoadView.setCanRefresh(false);
        pullToRefreshAndPushToLoadView.setCanLoadMore(true);
        ckAll.setChecked(true);
        ckAll.setOnClickListener(this);
        initData(context);
    }
    public void initData(Context context) {
        shoppingCartAdapter = new ShoppingCartAdapter(shoppingCartBeanList,context);
        shoppingCartAdapter.isShow(true);
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        list_shopping_cart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
        clearCarListsModel = new ClearCarListsModel(context,this);
        upCarModel = new UpCarModel(context);
        pullToRefreshAndPushToLoadView.finishRefreshing();
        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(context, this, new CarListModel(context));
        msgCenterPresenter.getReflushData(0);
    }

    public void getData(Context context){
        msgCenterPresenter.getReflushData(0);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //全选按钮
            case R.id.ck_all:
                if (shoppingCartBeanList.size() != 0) {
                    if (ckAll.isChecked()) {
                        chooseAll = true ;
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        chooseAll = false ;
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.tv_settlement: //结算
                lementOnder();
                break;
            case R.id.cleartext:
                DialogTagin.getDialogTagin(context).showDialog("清空购物车").setDialogSure(this);
                break;
        }
    }

    /**
     * 结算订单、支付
     */
    private void lementOnder() {
        //选中的需要提交的商品清单
        for (ShoppingCartBean bean:shoppingCartBeanList ){
            boolean choosed = bean.isChoosed();
            if (choosed){
                String shoppingName = bean.getShoppingName();
                int count = bean.getCount();
                double price = bean.getPrice();
                int size = bean.getDressSize();
                String attribute = bean.getAttribute();
                int id = bean.getId();
                Log.d("deal",id+"----id---"+shoppingName+"---"+count+"---"+price+"--size----"+size+"--attr---"+attribute);
            }
        }
//        toast(this,"总价："+totalPrice);
        //跳转到支付界面
    }
    /**
     * 单选
     * @param position  组元素位置
     * @param isChecked 组元素选中与否
     */
    @Override
    public void checkGroup(int position, boolean isChecked) {
        shoppingCartBeanList.get(position).setChoosed(isChecked);
        if (isAllCheck())
            ckAll.setChecked(true);
        else
            ckAll.setChecked(false);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }
    /**
     * 遍历list集合
     * @return
     */
    private boolean isAllCheck() {

        for (ShoppingCartBean group : shoppingCartBeanList) {
            if (!group.isChoosed())
                return false;
        }
        return true;
    }
    /**
     * 统计操作
     * 1.先清空全局计数器<br>
     * 2.遍历所有子元素，只要是被选中状态的，就进行相关的计算操作
     * 3.给底部的textView进行数据填充
     */
    public void statistics() {
        totalCount = 0;
        totalPrice = 0.00;
        LocalleCarData.newInstance().clear();
        LocalleCarData.newInstance().setTotalPrice(0.00);
        int serverNum = 0;
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
                LocalleCarData.newInstance().setShoppingCartBeanList(shoppingCartBean);
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getCount();
                serverNum+=shoppingCartBean.getCount();
                car_msgCount.setText(serverNum+"");
            }
        }
        LocalleCarData.newInstance().setTotalPrice(totalPrice);
        tv_show_price.setText("¥ " +totalPrice);
        if (totalCount>0){
            car_msgCount.setVisibility(VISIBLE);
            main_car.setImageResource(R.drawable.car);
            main_car.setTag(R.drawable.car);
        }else {
            car_msgCount.setVisibility(GONE);
            main_car.setImageResource(R.drawable.nocar);
            if (shoppingCartBeanList.size()==0)
            main_car.setTag(R.drawable.nocar);
        }
       /* tvShowPrice.setText("合计: ¥" + totalPrice);
        tvSettlement.setText("预约下单");*/
    }
    /**
     * 增加
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doIncrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        currentCount++;
        upCarModel.upData(shoppingCartBean.getId()+"",currentCount);
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        car_msgCount.setText((Integer.parseInt(car_msgCount.getText().toString())+1)+"");
        statistics();
    }
    /**
     * 删减
     *
     * @param position      组元素位置
     * @param showCountView 用于展示变化后数量的View
     * @param isChecked     子元素选中与否
     */
    @Override
    public void doDecrease(int position, View showCountView, boolean isChecked) {
        ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        int currentCount = shoppingCartBean.getCount();
        if (currentCount == 0) {
            return;
        }
        currentCount--;
        upCarModel.upData(shoppingCartBean.getId()+"",currentCount);
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        if (currentCount==0){
            shoppingCartAdapter.remove(position);
        }
        shoppingCartAdapter.notifyDataSetChanged();
        car_msgCount.setText((Integer.parseInt(car_msgCount.getText().toString())-1)+"");
        statistics();
        if (totalCount==0){
            carView.setVisibility(GONE);
            main_car.setImageResource(R.drawable.nocar);
            main_car.setTag(R.drawable.nocar);
        }
    }
    /**
     * 删除
     *
     * @param position
     */
    @Override
    public void childDelete(int position) {
        shoppingCartBeanList.remove(position);
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
    }

    /**
     * 清空购物车
     */
    @Override
    public void dialogCall() {
//        toast(this,"删除");
        clearCarListsModel.getData("0");
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        if (!NetworkUtils.isNetworkAvailable(context)){
            pullToRefreshAndPushToLoadView.finishLoading();
        }else {
            msgCenterPresenter.getMoreData(0);
        }
    }

    @Override
    public void getDataLists(List<CarListBeen.InfoBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        if (shoppingCartBeanList.size()==0&&datas==null){
            return;
        }
        int serverNum = 0 ;
        shoppingCartBeanList.clear();
        LocalleCarData.newInstance().clear();
        totalPrice = 0 ;
        for (CarListBeen.InfoBean bean:datas){
            ShoppingCartBean bean1 = new ShoppingCartBean(bean.getId(),bean.getService_name(),"",0,Double.parseDouble(bean.getPrice()),bean.getNum(),bean.getService_type(),bean.getService_id(),bean.getSpec_id());
            bean1.setImageUrl(bean.getService_icon());
            if (chooseAll){
                bean1.setChoosed(true);
                serverNum+=bean.getNum();
                car_msgCount.setText(serverNum+"");
                car_msgCount.setVisibility(VISIBLE);
                main_car.setTag(R.drawable.car);
                LocalleCarData.newInstance().setShoppingCartBeanList(bean1);
                totalPrice += bean1.getPrice() * bean1.getCount();
                LocalleCarData.newInstance().setTotalPrice(totalPrice);
                tv_show_price.setText("¥ " +totalPrice);
            }else {
                tv_show_price.setText("¥ 0.00");
            }
            shoppingCartBeanList.add(bean1);
        }
        if (shoppingCartBeanList.size()==0){
            main_car.setImageResource(R.drawable.nocar);
            main_car.setTag(R.drawable.nocar);
            tv_show_price.setText("购物车为空");
        }
        shoppingCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void getServiceData(Object data) {
        LocalleCarData.newInstance().clear();
        ToastUtils.showShort(context,"购物车已清空");
        carView.setVisibility(GONE);
        car_msgCount.setVisibility(GONE);
        car_msgCount.setText("0");
        main_car.setImageResource(R.drawable.nocar);
        main_car.setTag(R.drawable.nocar);
    }
}

