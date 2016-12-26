package com.dner.fast.wxapi;


import com.dner.fast.R;
import com.example.telphone.Constants;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler{
	
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	
    private IWXAPI api;
    
    private TextView tv_pay_result;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        
        tv_pay_result=(TextView)findViewById(R.id.pay_result_tip);
        
    	api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
        api.handleIntent(getIntent(), this);
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
//		Toast.makeText(this, "resp code:"+ resp.errCode+"--"+resp.errStr, Toast.LENGTH_SHORT).show();
		Log.d(TAG, "onPayFinish, errCode = " + resp.errCode);

		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle(R.string.app_tip);
			builder.setMessage("支付失败,请稍后重试"+String.valueOf(resp.errCode));
			builder.show();
			tv_pay_result.setText("支付失败,请稍后重试");
		}else{
			tv_pay_result.setText("支付成功,即将关闭本页面");
		}
		
		Handler handler =new Handler();
		handler.postDelayed(new Runnable(){

			@Override
			public void run() {
				WXPayEntryActivity.this.finish();
			}
			
		}, 1500);
	}
}