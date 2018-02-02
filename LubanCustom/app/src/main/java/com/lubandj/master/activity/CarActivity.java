package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.R;
import com.lubandj.master.adapter.ShoppingCartAdapter;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.customview.BackLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CarActivity extends TitleBaseActivity implements  ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface,DialogTagin.DialogSure{
    private BackLayout backLayout;
    @InjectView(R.id.car_contaner)
    LinearLayout workFragmentContaner;
    Button btnBack;
    //全选
    CheckBox ckAll;
    //总额
    TextView tvShowPrice;
    //结算
    TextView tvSettlement;
    //删除
    TextView tv_clear;//tv_edit


    ListView list_shopping_cart;
    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean flag = false;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private boolean mSelect;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    public  static  void startActivity(Context context){
        Intent intent = new Intent(context, CarActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void titleLeftClick() {
        finish();
    }

    @Override
    protected void clickMenu() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_car;
    }

    @Override
    public void initView() {
        ButterKnife.inject(this);
        setTitleText(R.string.car_center);
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        backLayout = new BackLayout(this);
        backLayout.setOnclick(this);
        workFragmentContaner.addView(backLayout);
        backLayout.setOnclick(this);
        backLayout.setNodataText("购物车空空如也,快去逛逛吧");
        backLayout.setButtonText("进入首页");
        setRightText("编辑");
        tv_clear = findView(R.id.tv_clear);
        tv_clear.setOnClickListener(this);
        tv_clear.setVisibility(View.GONE);
        ckAll= (CheckBox) findViewById(R.id.ck_all);
        tvShowPrice= (TextView) findViewById(R.id.tv_show_price);
        tvSettlement= (TextView) findViewById(R.id.tv_settlement);
        list_shopping_cart= (ListView) findViewById(R.id.list_shopping_cart);

        ckAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        initData();
    }

    @Override
    public void initData() {
        for (int i = 0; i < 2; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setShoppingName("空调保养");
            shoppingCartBean.setDressSize(20);
            shoppingCartBean.setId(i);
            shoppingCartBean.setPrice(30.6);
            shoppingCartBean.setCount(1);
            shoppingCartBean.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
            shoppingCartBeanList.add(shoppingCartBean);
        }
        for (int i = 0; i < 2; i++) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.setShoppingName("瑞士正品夜光男女士手表情侣精钢带男表防水石英学生非天王星机械");
            shoppingCartBean.setAttribute("黑白色");
            shoppingCartBean.setPrice(89);
            shoppingCartBean.setId(i+2);
            shoppingCartBean.setCount(3);
            shoppingCartBean.setImageUrl("https://gd1.alicdn.com/imgextra/i1/2160089910/TB2M_NSbB0kpuFjSsppXXcGTXXa_!!2160089910.jpg");
            shoppingCartBeanList.add(shoppingCartBean);
        }
        shoppingCartAdapter = new ShoppingCartAdapter(this);
        shoppingCartAdapter.isShow(false);
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        list_shopping_cart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
        backLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //全选按钮
            case R.id.ck_all:
                if (shoppingCartBeanList.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }
                statistics();
                break;
            case R.id.tv_basetitle_right:
                flag = !flag;
                if (flag) {
                    tv_clear.setVisibility(View.VISIBLE);
                    tv_basetitle_right.setText("取消");
//                    ckAll.setChecked(true);
//                    shoppingCartAdapter.isShow(false);
                } else {
                    tv_clear.setVisibility(View.GONE);
                    tv_basetitle_right.setText("编辑");
//                    ckAll.setChecked(false);
//                    shoppingCartAdapter.isShow(true);
                }
               /* if (shoppingCartBeanList.size() != 0) {
                    if (ckAll.isChecked()) {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(true);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    } else {
                        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
                            shoppingCartBeanList.get(i).setChoosed(false);
                        }
                        shoppingCartAdapter.notifyDataSetChanged();
                    }
                }*/
                break;
            case R.id.tv_settlement: //结算
                lementOnder();
            BookOrderActivity.startActivity(this);
                break;
            case R.id.tv_clear:
                DialogTagin.getDialogTagin(this).showDialog("删除服务").setDialogSure(this);
                break;
                default:
                    MainCantainActivity.startActivity(this);
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
        toast(this,"总价："+totalPrice);
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
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                totalCount++;
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getCount();
            }
        }
        tvShowPrice.setText("合计: ¥" + totalPrice);
        tvSettlement.setText("预约下单");
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
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
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
        if (currentCount == 1) {
            return;
        }
        currentCount--;
        shoppingCartBean.setCount(currentCount);
        ((TextView) showCountView).setText(currentCount + "");
        shoppingCartAdapter.notifyDataSetChanged();
        statistics();
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

    @Override
    public void dialogCall() {
        toast(this,"删除");
    }
}
