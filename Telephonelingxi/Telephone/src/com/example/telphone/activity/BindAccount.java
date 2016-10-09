package com.example.telphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dner.fast.R;
import com.example.telphone.tool.PreferenceUtils;

public class BindAccount extends Activity implements View.OnClickListener{

	private static final String TAG = BindAccount.class.getSimpleName();
	
	private TextView tv_title;
	
	private LinearLayout ll_alipay_account;
	private RelativeLayout rl_alipay_add;
	
	private TextView tv_bankName;
	private TextView tv_bankAccount;
	private TextView tv_label_add_alipay;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_account_bind);
		
		
		initView();
	}

	private void initView() {
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("兑现设置");
		
		tv_bankName = (TextView)findViewById(R.id.tv_bankname);
		tv_bankAccount = (TextView)findViewById(R.id.tv_account);
//		String account = this.getIntent().getStringExtra("account");
//		String name = this.getIntent().getStringExtra("name");
		
		
		ll_alipay_account = (LinearLayout)findViewById(R.id.ll_bind_account);
		rl_alipay_add = (RelativeLayout)findViewById(R.id.rl_add_alipay);

		
		
		
		
		ll_alipay_account.setOnClickListener(this);
		rl_alipay_add.setOnClickListener(this);
		
		tv_label_add_alipay = (TextView)findViewById(R.id.tv_label_add_alipay);
		tv_label_add_alipay.setOnClickListener(this);
	}

	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		String account = PreferenceUtils.getBindAlipayAccount();
		String name = PreferenceUtils.getBindAlipayName();
		if(TextUtils.isEmpty(account) || TextUtils.isEmpty(name))
		{
			ll_alipay_account.setVisibility(View.GONE);
		}else
		{	
			rl_alipay_add.setVisibility(View.GONE);
			tv_bankName.setText("支付宝");
			tv_bankAccount.setText(account);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Log.d(TAG, "[onClick]"+Integer.toHexString(v.getId()));
		switch(v.getId())
		{
		case R.id.tv_label_add_alipay:
		case R.id.rl_add_alipay:
			
			Intent it = new Intent(this,AlipayEdit.class);
			startActivityForResult(it, 1);
			break;
		case R.id.ll_bind_account:
			Intent it1 = new Intent(this,AlipayEdit.class);
			it1.putExtra("account", this.getIntent().getStringExtra("account"));
			it1.putExtra("name", this.getIntent().getStringExtra("name"));
			startActivityForResult(it1,2);
			break;
		default:
			break;
		}
	}

	
}
