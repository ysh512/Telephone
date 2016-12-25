package com.example.telphone.activity;

import net.sourceforge.simcpux.PayActivity;
import net.sourceforge.simcpux.Util;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.pay.demo.PayDemoActivity;

import com.dner.fast.R;
import com.example.telphone.Constants;
import com.example.telphone.tool.PreferenceUtils;
import com.example.telphone.tool.Variable;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class ChargeWay extends Activity implements OnClickListener{

	private IWXAPI api;
	private static final String TAG=ChargeWay.class.getSimpleName();
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.rlyt_weixin:
			
			WxPayTask task = new WxPayTask();
			task.execute();
			
			break;
		case R.id.rlyt_alipay:
			
			Intent it = new Intent(this,PayDemoActivity.class);
			
			String account = ChargeWay.this.getIntent().getStringExtra("phone");
			int money = ChargeWay.this.getIntent().getIntExtra("money", 100);
			String goodsDes = null;
			if(money==100)
			{
				goodsDes = getResources().getString(R.string.charge_amount_100);
			}else if(money == 200)
			{
				goodsDes = getResources().getString(R.string.charge_amount_200);
			}else if(money ==400)
			{
				goodsDes = getResources().getString(R.string.charge_amount_400);
			}
			
			it.putExtra("phone", account);
			it.putExtra("money", money);
			it.putExtra("des", goodsDes);
			startActivity(it);
			
			break;
		}
	}

	private Boolean wxPay() {
		
		
		String money="100";
		if(Variable.PAY_DEBUG)
		{
			money="0.01";
		}
		
		String url = String.format(Constants.WX_PAY_ORDER, "1",money,PreferenceUtils.getPhone());
//		Button payBtn = (Button) findViewById(R.id.appay_btn);
//		payBtn.setEnabled(false);
		
        try{
			byte[] buf = Util.httpGet(url);
			if (buf != null && buf.length > 0) {
				String content = new String(buf);
				Log.e("get server pay params:",content);
	        	JSONObject json = new JSONObject(content); 
				if(null != json && !json.has("retcode") ){
					PayReq req = new PayReq();
					//req.appId = "wxf8b4f85f3a794e77";  // ������appId
					req.appId			= json.getString("appid");
					api = WXAPIFactory.createWXAPI(this, req.appId);
					api.registerApp(req.appId);
					req.partnerId		= json.getString("partnerid");
					req.prepayId		= json.getString("prepayid");
					req.nonceStr		= json.getString("noncestr");
					req.timeStamp		= json.getString("timestamp");
					req.packageValue	= json.getString("package");
					req.sign			= json.getString("sign");
					req.extData			= "app data"; // optional
					Looper.prepare();
					Toast.makeText(ChargeWay.this, "正常调起支付", Toast.LENGTH_SHORT).show();
					Looper.loop();
					Boolean b = api.sendReq(req);
					Looper.prepare();
					Toast.makeText(ChargeWay.this, "result :"+b, Toast.LENGTH_SHORT).show();
					Looper.loop();
					return Boolean.TRUE;
				}else{
		        	Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
		        	Looper.prepare();
		        	Toast.makeText(ChargeWay.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
		        	Looper.loop();
		        	return Boolean.FALSE;
				}
			}else{
	        	Log.d("PAY_GET", "服务器请求错误了");
	        	Looper.prepare();
	        	Toast.makeText(ChargeWay.this, "服务器请求错误了", Toast.LENGTH_SHORT).show();
	        	Looper.loop();
	        	return Boolean.FALSE;
			}
        }catch(Exception e){
        	Log.e("PAY_GET", "服务器请求错误"+e.getMessage());
        	Looper.prepare();
        	Toast.makeText(ChargeWay.this, "异常"+e.getMessage(), Toast.LENGTH_SHORT).show();
        	Looper.loop();
        	return Boolean.FALSE;
        }
//        payBtn.setEnabled(true);
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		this.setContentView(R.layout.activity_recharge_way);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("充值中心");
		
		int money = this.getIntent().getIntExtra("money",100);
		Log.d(TAG, "[ChargeWay] charge money:"+money);
		TextView tv_rctype = (TextView)findViewById(R.id.tv_rctype);
		Resources resource=  this.getResources();
		switch(money)
		{
		case 100:
			tv_rctype.setText(resource.getString(R.string.charge_amount_100));
			break;
		case 200:
			tv_rctype.setText(resource.getString(R.string.charge_amount_200));
			break;
		case 400:
			tv_rctype.setText(resource.getString(R.string.charge_amount_400));
			break;
			default:
				tv_rctype.setText("充值"+money+"到帐"+money+"元话费");
				break;
		}
		
		
		
		String account = this.getIntent().getStringExtra("phone");
		TextView tv_account = (TextView)findViewById(R.id.tv_account);
		tv_account.setText(account);
	
		RelativeLayout rlyt_dexy = (RelativeLayout)findViewById(R.id.rlyt_weixin);
		rlyt_dexy.setOnClickListener(this);
		RelativeLayout rlyt_alipay = (RelativeLayout)findViewById(R.id.rlyt_alipay);
		rlyt_alipay.setOnClickListener(this);
		
	}

	class WxPayTask extends AsyncTask<Void,Void,Boolean>{

		
		
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			Toast.makeText(ChargeWay.this, "获取订单中...", Toast.LENGTH_SHORT).show();
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
//			super.onPostExecute(result);
			if(!result)
			{
				Toast.makeText(ChargeWay.this, "服务器请求错误", Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			return wxPay();
		}
		
	}
	
}
