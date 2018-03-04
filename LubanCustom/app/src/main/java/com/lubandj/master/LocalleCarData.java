package com.lubandj.master;

import com.lubandj.master.been.ShoppingCartBean;

import java.util.ArrayList;
import java.util.List;

/**购物车数据本地存储
 * Created by ${zhaoshuzhen} on 2018/3/2.
 */

public class LocalleCarData {
    private List<ShoppingCartBean> shoppingCartBeanList = new ArrayList<>();

    private double totalPrice = 0.00;// 购买的商品总价

    //内部类，在装载该内部类时才会去创建单利对象
    private static class SingletonHolder{
        public static LocalleCarData instance = new LocalleCarData();
    }

    private LocalleCarData(){}

    public static LocalleCarData newInstance(){
        return SingletonHolder.instance;
    }

    public void setShoppingCartBeanList(ShoppingCartBean bean){
        //do something
        this.shoppingCartBeanList.add(bean) ;
    }

    public List<ShoppingCartBean> getShoppingBeenList(){
        return shoppingCartBeanList ;
    }

    public void clear(){
        shoppingCartBeanList.clear();
        totalPrice = 0.00;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
