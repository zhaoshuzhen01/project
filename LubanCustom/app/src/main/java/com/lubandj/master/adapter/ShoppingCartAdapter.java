package com.lubandj.master.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.baselibrary.refresh.BaseQuickAdapter;
import com.example.baselibrary.refresh.BaseViewHolder;
import com.lubandj.master.R;
import com.lubandj.master.been.HomeBeen;
import com.lubandj.master.been.ShoppingCartBean;
import com.lubandj.master.utils.StringUtil;
import java.util.List;

/**
 * Created by AYD on 2016/11/21.
 * <p/>
 * 购物车Adapter
 */
public class ShoppingCartAdapter extends BaseQuickAdapter<ShoppingCartBean, BaseViewHolder> {

    private boolean isShow = true;//是否显示编辑/完成
    private List<ShoppingCartBean> shoppingCartBeanList;
    private CheckInterface checkInterface;
    private ModifyCountInterface modifyCountInterface;
    private Context context;

    public ShoppingCartAdapter(@Nullable List<ShoppingCartBean> data,Context context) {
        super(R.layout.item_shopping_cart_layout, data);
        this.context = context;
    }

    public void setShoppingCartBeanList(List<ShoppingCartBean> shoppingCartBeanList) {
        this.shoppingCartBeanList = shoppingCartBeanList;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ShoppingCartBean item) {
       /* int position = helper.getAdapterPosition();
        ImageView iconMsg = ((ImageView) (helper.getView(R.id.state_img)));
        Glide.with(context).load(item.getService_pic()).skipMemoryCache(false).into(iconMsg);
        TextView title = ((TextView) (helper.getView(R.id.home_list_title)));
        title.setText(item.getName());
        TextView price = ((TextView) (helper.getView(R.id.home_list_price)));
        price.setText(item.getPrice());*/
       final CheckBox ckOneChose = ((CheckBox)(helper.getView(R.id.ck_chose)));
       ImageView ivShowPic = ((ImageView) (helper.getView(R.id.iv_show_pic)));
        ImageView ivSub = ((ImageView)(helper.getView(R.id.iv_sub)));
        ImageView ivAdd = ((ImageView)(helper.getView(R.id.iv_add)));
       TextView tvCommodityName = ((TextView)(helper.getView(R.id.tv_commodity_name)));
        TextView tvCommodityAttr = ((TextView)(helper.getView(R.id.tv_commodity_attr)));
        TextView tvCommodityPrice = ((TextView)(helper.getView(R.id.tv_commodity_price)));
        TextView tvCommodityNum = ((TextView)(helper.getView(R.id.tv_commodity_num)));
        final TextView tvCommodityShowNum = ((TextView)(helper.getView(R.id.tv_commodity_show_num)));
        ImageView tvCommodityDelete = ((ImageView) (helper.getView(R.id.tv_commodity_delete)));
        LinearLayout  rlEdit = ((LinearLayout)(helper.getView(R.id.rl_edit)));
       final int position = helper.getAdapterPosition();
        final ShoppingCartBean shoppingCartBean = shoppingCartBeanList.get(position);
        boolean choosed = shoppingCartBean.isChoosed();
        if (choosed){
            ckOneChose.setChecked(true);
        }else{
            ckOneChose.setChecked(false);
        }
        String attribute = shoppingCartBean.getAttribute();
        if (!TextUtils.isEmpty(attribute)){
            tvCommodityAttr.setText(attribute);
        }else{
            tvCommodityAttr.setText(shoppingCartBean.getShoppingName()+"");
        }
        tvCommodityName.setText(shoppingCartBean.getShoppingName());
        tvCommodityPrice.setText("￥ "+shoppingCartBean.getPrice()+"");
        tvCommodityNum.setText(" X"+shoppingCartBean.getCount()+"");
        tvCommodityShowNum.setText(shoppingCartBean.getCount()+"");
        Glide.with(context).load(shoppingCartBean.getImageUrl()).skipMemoryCache(false).into(ivShowPic);
        //单选框按钮
        ckOneChose.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shoppingCartBean.setChoosed(((CheckBox) v).isChecked());
                        checkInterface.checkGroup(position, ((CheckBox) v).isChecked());//向外暴露接口
                    }
                }
        );
        //增加按钮
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doIncrease(position, tvCommodityShowNum, ckOneChose.isChecked());//暴露增加接口
            }
        });
        //删减按钮
        ivSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modifyCountInterface.doDecrease(position, tvCommodityShowNum, ckOneChose.isChecked());//暴露删减接口
            }
        });
        //删除弹窗
        tvCommodityDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog alert = new AlertDialog.Builder(context).create();
                alert.setTitle("操作提示");
                alert.setMessage("您确定要将这些商品从购物车中移除吗？");
                alert.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                return;
                            }
                        });
                alert.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                modifyCountInterface.childDelete(position);//删除 目前只是从item中移除

                            }
                        });
                alert.show();
            }
        });
        //判断是否在编辑状态下
        if (isShow) {
//            tvCommodityName.setVisibility(View.VISIBLE);
            tvCommodityPrice.setVisibility(View.GONE);
            rlEdit.setVisibility(View.VISIBLE);
        } else {
//            tvCommodityName.setVisibility(View.VISIBLE);
            rlEdit.setVisibility(View.GONE);
            tvCommodityPrice.setVisibility(View.VISIBLE);
        }
        tvCommodityNum.setVisibility(View.VISIBLE);
        tvCommodityDelete.setVisibility(View.GONE);
    }

    @Override
    public void childViewClick(int position, View view) {

    }

    /**
     * 单选接口
     *
     * @param checkInterface
     */
    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    /**
     * 改变商品数量接口
     *
     * @param modifyCountInterface
     */
    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }



    /**
     * 是否显示可编辑
     *
     * @param flag
     */
    public void isShow(boolean flag) {
        isShow = flag;
    }
    /**
     * 复选框接口
     */
    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param position  元素位置
         * @param isChecked 元素选中与否
         */
        void checkGroup(int position, boolean isChecked);
    }


    /**
     * 改变数量的接口
     */
    public interface ModifyCountInterface {
        /**
         * 增加操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doIncrease(int position, View showCountView, boolean isChecked);

        /**
         * 删减操作
         *
         * @param position      元素位置
         * @param showCountView 用于展示变化后数量的View
         * @param isChecked     子元素选中与否
         */
        void doDecrease(int position, View showCountView, boolean isChecked);

        /**
         * 删除子item
         *
         * @param position
         */
        void childDelete(int position);
    }
}
