package com.lubandj.master.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.baselibrary.TitleBaseActivity;
import com.example.baselibrary.refresh.view.PullToRefreshAndPushToLoadView6;
import com.example.baselibrary.tools.ToastUtils;
import com.lubandj.master.DialogUtil.DialogTagin;
import com.lubandj.master.Iview.DataCall;
import com.lubandj.master.Iview.IbaseView;
import com.lubandj.master.LocalleCarData;
import com.lubandj.master.Presenter.BaseReflushPresenter;
import com.lubandj.master.R;
import com.lubandj.master.adapter.ShoppingCartAdapter;
import com.lubandj.master.been.CarListBeen;
import com.lubandj.master.been.MsgCenterBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.customview.BackLayout;
import com.lubandj.master.model.CarListModel;
import com.lubandj.master.model.ClearCarListsModel;
import com.lubandj.master.model.DeleteCarModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CarActivity extends TitleBaseActivity implements  ShoppingCartAdapter.CheckInterface, ShoppingCartAdapter.ModifyCountInterface,DialogTagin.DialogSure, PullToRefreshAndPushToLoadView6.PullToRefreshAndPushToLoadMoreListener,IbaseView<CarListBeen.InfoBean> ,DataCall {
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
    private LinearLayout rl_bottom ;
    protected PullToRefreshAndPushToLoadView6 pullToRefreshAndPushToLoadView;

    RecyclerView list_shopping_cart;
    private ShoppingCartAdapter shoppingCartAdapter;
    private boolean flag = false;
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();
    private boolean mSelect;
    private double totalPrice = 0.00;// 购买的商品总价
    private int totalCount = 0;// 购买的商品总数量
    private BaseReflushPresenter msgCenterPresenter;
    private DeleteCarModel clearCarListsModel;
    private List<Integer>ids = new ArrayList<>();
    private List<Integer>positons  = new ArrayList<>();
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
        pullToRefreshAndPushToLoadView = (PullToRefreshAndPushToLoadView6)findViewById(R.id.prpt);
        pullToRefreshAndPushToLoadView.setCanRefresh(false);
        pullToRefreshAndPushToLoadView.setCanLoadMore(true);
        setTitleText(R.string.car_center);
        setBackImg(R.drawable.back_mark);
        setOkVisibity(false);
        backLayout = new BackLayout(this);
        backLayout.setOnclick(this);
        workFragmentContaner.addView(backLayout,0);
        backLayout.setOnclick(this);
        backLayout.setNodataText("购物车空空如也,快去逛逛吧");
        backLayout.setButtonText("进入首页");
        setRightText("编辑");
        tv_clear = findView(R.id.tv_clear);
        tv_clear.setOnClickListener(this);
        tv_clear.setVisibility(View.GONE);
        rl_bottom = findView(R.id.rl_bottom);
        ckAll= (CheckBox) findViewById(R.id.ck_all);
        tvShowPrice= (TextView) findViewById(R.id.tv_show_price);
        tvSettlement= (TextView) findViewById(R.id.tv_settlement);
        list_shopping_cart= (RecyclerView) findViewById(R.id.list_shopping_cart);
        list_shopping_cart.setLayoutManager(new LinearLayoutManager(this));
        ckAll.setOnClickListener(this);
        tvSettlement.setOnClickListener(this);
        clearCarListsModel = new DeleteCarModel(this,this);

        initData();
    }

    @Override
    public void initData() {
        shoppingCartAdapter = new ShoppingCartAdapter(shoppingCartBeanList,this);
        shoppingCartAdapter.isShow(false);
        shoppingCartAdapter.setCheckInterface(this);
        shoppingCartAdapter.setModifyCountInterface(this);
        list_shopping_cart.setAdapter(shoppingCartAdapter);
        shoppingCartAdapter.setShoppingCartBeanList(shoppingCartBeanList);
        backLayout.setVisibility(View.GONE);
        msgCenterPresenter = new BaseReflushPresenter<MsgCenterBeen.InfoBean.ListBean>(this, this, new CarListModel(this));
        msgCenterPresenter.getReflushData(0);
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
                    rl_bottom.setVisibility(View.GONE);
                    tv_basetitle_right.setText("取消");
//                    ckAll.setChecked(true);
//                    shoppingCartAdapter.isShow(false);
                } else {
                    tv_clear.setVisibility(View.GONE);
                    tv_basetitle_right.setText("编辑");
                    rl_bottom.setVisibility(View.VISIBLE);
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
            if (totalCount==0){
                ToastUtils.showShort(this,"请选择服务");
                return;
            }
            BookOrderActivity.startActivity(this);
                break;
            case R.id.tv_clear:
                if (totalCount==0){
                    ToastUtils.showShort(this,"请选择服务");
                    return;
                }
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
        ids.clear();
        positons.clear();
        totalPrice = 0.00;
        LocalleCarData.newInstance().clear();
        LocalleCarData.newInstance().setTotalPrice(0.00);
        for (int i = 0; i < shoppingCartBeanList.size(); i++) {
            ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(i);
            if (shoppingCartBean.isChoosed()) {
                ids.add(shoppingCartBean.getId());
                positons.add(i);
                LocalleCarData.newInstance().setShoppingCartBeanList(shoppingCartBean);
                totalCount++;
                totalPrice += shoppingCartBean.getPrice() * shoppingCartBean.getCount();
            }
        }
        LocalleCarData.newInstance().setTotalPrice(totalPrice);
        tvShowPrice.setText("¥" + totalPrice);
        tvSettlement.setText("预约下单");
        if (totalCount==0){
            tv_clear.setBackgroundColor(getResources().getColor(R.color.alertdialog_line));
        }else {
            tv_clear.setBackgroundColor(getResources().getColor(R.color.color_e94b4e));
        }
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
        String content = "";
        for (Integer id:ids){
            content = content+id+",";
        }
        content=content.substring(0,content.length()-1);
        clearCarListsModel.getData(content+"");
    }

    @Override
    public void getDataLists(List<CarListBeen.InfoBean> datas) {
        pullToRefreshAndPushToLoadView.finishRefreshing();
        pullToRefreshAndPushToLoadView.finishLoading();
        if (shoppingCartBeanList.size()==0&&datas==null){
            return;
        }
        shoppingCartBeanList.clear();
        for (CarListBeen.InfoBean bean:datas){
            ShoppingCartBean bean1 = new ShoppingCartBean(bean.getId(),bean.getService_name(),"",0,Double.parseDouble(bean.getPrice()),bean.getNum(),bean.getService_type(),bean.getService_id(),bean.getSpec_id());
            bean1.setImageUrl("https://img.alicdn.com/bao/uploaded/i2/TB1YfERKVXXXXanaFXXXXXXXXXX_!!0-item_pic.jpg_430x430q90.jpg");
            shoppingCartBeanList.add(bean1);
        }
        shoppingCartAdapter.notifyDataSetChanged();
        if (shoppingCartBeanList.size()==0){
            backLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void getServiceData(Object data) {
        for (Integer position: positons){
            int mpositon = position ;
            shoppingCartBeanList.remove(mpositon);
        }
        Log.e("deal",shoppingCartBeanList.size()+"");
        shoppingCartAdapter.notifyDataSetChanged();
        if (shoppingCartBeanList.size()==0){
            backLayout.setVisibility(View.VISIBLE);
        }
    }
}
