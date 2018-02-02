package com.lubandj.customer.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.lubandj.master.R;
import com.lubandj.master.activity.CheckStandActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
	private TextView mTv;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
    	api = WXAPIFactory.createWXAPI(this, null);
        api.handleIntent(getIntent(), this);

		mTv = (TextView) findViewById(R.id.tv);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		System.out.println(Thread.currentThread().getName());
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			int errCode = resp.errCode;
			if (errCode == 0){
				CheckStandActivity.mRechargeResult = 1;
			}else if (errCode == -1){
				//ToastUtils.showLong("支付失败");
				CheckStandActivity.mRechargeResult = 2;
			}else if (errCode == -2){
				//ToastUtils.showLong("支付已被取消");
				CheckStandActivity.mRechargeResult = 2;
			}
		}
		finish();
	}
}