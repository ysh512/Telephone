package com.example.telphone.activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.Camera.PreviewCallback;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dner.fast.R;
import com.example.telphone.Constants;
import com.example.telphone.tool.PreferenceUtils;

public class Cash extends Activity implements OnClickListener{

	private static final String TAG= Cash.class.getSimpleName();
	
	private TextView tv_title;
	
	
	private TextView tv_save;
	
	private EditText et_money;
	
	//支付宝
	private RelativeLayout rl_bank1;
	
	//银行卡
	private RelativeLayout rl_bank2;
	
	private int type= 0;
	
	private static final int GREEN_COLOR = Color.parseColor("#BFF2E8");
	private static final int WHITE_COLOR = Color.parseColor("#ffffff");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		
	}


	private void initView() {
		this.setContentView(R.layout.activity_rcmd_convert);
		rl_bank1 = (RelativeLayout)findViewById(R.id.rl_bank_layout);
		
		
		rl_bank2 = (RelativeLayout)findViewById(R.id.rl_bank_layout2);
		
		if(TextUtils.isEmpty(PreferenceUtils.getBankName()) || TextUtils.isEmpty(PreferenceUtils.getBankCardNo()))
		{
			rl_bank2.setVisibility(View.GONE);
		}else
		{
			((TextView)findViewById(R.id.tv_bankname2)).setText(PreferenceUtils.getBankName());
			((TextView)findViewById(R.id.tv_account2)).setText(PreferenceUtils.getBankCardNo());
		}
		
		rl_bank1.setOnClickListener(this);
		rl_bank2.setOnClickListener(this);
		
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("兑现");
		
		et_money = (EditText)findViewById(R.id.et_convert_money);
		
		tv_save = (TextView)findViewById(R.id.bt_convert);
		
		tv_save.setOnClickListener(this);
		
		if(TextUtils.isEmpty(PreferenceUtils.getBindAlipayAccount()))
		{
			rl_bank1.setVisibility(View.GONE);
		}else{
			rl_bank1.setVisibility(View.VISIBLE);
			((TextView)findViewById(R.id.tv_bankname)).setText("支付宝");
			((TextView)findViewById(R.id.tv_account)).setText(PreferenceUtils.getBindAlipayAccount());
		}
		
		
		if(rl_bank1.getVisibility()==View.VISIBLE && rl_bank2.getVisibility()==View.VISIBLE)
		{
			type = 0;
			rl_bank1.setBackgroundColor(GREEN_COLOR);
			rl_bank2.setBackgroundColor(WHITE_COLOR);
		}else if(rl_bank1.getVisibility()==View.VISIBLE)
		{
			type=0;
			rl_bank1.setBackgroundColor(GREEN_COLOR);
		}else if (rl_bank2.getVisibility() == View.VISIBLE){
			type=1;
			rl_bank2.setBackgroundColor(GREEN_COLOR);
		}else
		{
			Toast.makeText(this, "请先绑定银行卡或支付宝再进行提现操作", Toast.LENGTH_LONG).show();
		}
	}


	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bt_convert:
			CashTask task = new CashTask();
			task.execute();
			break;
		case R.id.rl_bank_layout:
			rl_bank1.setBackgroundColor(Color.parseColor("#BFF2E8"));
			rl_bank2.setBackgroundColor(Color.parseColor("#ffffff"));
			type=0;
			break;
		case R.id.rl_bank_layout2:
			rl_bank2.setBackgroundColor(Color.parseColor("#BFF2E8"));
			rl_bank1.setBackgroundColor(Color.parseColor("#ffffff"));
			type=1;
			break;
			default :
				break;
		}
	}

	class CashTask extends AsyncTask<Void,Void,Boolean>
	{
		
		private String money;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			money = et_money.getText().toString();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(money)|| !TextUtils.isDigitsOnly(money))
			{
				return false;
			}
			

			HttpClient client = new DefaultHttpClient();  
			String url = String.format(Constants.CASH_URL,PreferenceUtils.getPhone(),PreferenceUtils.getPass(),money,String.valueOf(type));
	        
			Log.d(TAG, "[doInBackground] save account url:"+url);
			HttpGet get = new HttpGet(url);  
	        HttpResponse response= null;
			try {
				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(response.getEntity());
					Log.d(TAG, "[doInBackground] result:"+result);
					if(result.contains("ok"))
					{
						return true;
					}else
					{
						return false;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	         
			return false;
		

		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(!result){
				Toast.makeText(Cash.this, "提现申请提交失败,请稍候重试", Toast.LENGTH_SHORT).show();
			}else
			{
				Toast.makeText(Cash.this, "提现申请提交成功", Toast.LENGTH_SHORT).show();
			}
		}
		
		
		
	}
}
