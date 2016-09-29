package com.example.telphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.telphone.R;

public class BindAccount extends Activity implements View.OnClickListener{

	private TextView tv_title;
	
	private LinearLayout ll_alipay_account;
	private RelativeLayout rl_alipay_add;
	
	private TextView tv_bankName;
	private TextView tv_bankAccount;
	
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
		
		String account = this.getIntent().getStringExtra("account");
		String name = this.getIntent().getStringExtra("name");
		
		ll_alipay_account = (LinearLayout)findViewById(R.id.ll_bind_account);
		rl_alipay_add = (RelativeLayout)findViewById(R.id.rl_add_alipay);

		
		
		if(TextUtils.isEmpty(account) || TextUtils.isEmpty(name))
		{
			ll_alipay_account.setVisibility(View.GONE);
		}else
		{	
			rl_alipay_add.setVisibility(View.GONE);
			tv_bankName.setText("支付宝");
			tv_bankAccount.setText(account);
		}
		
		ll_alipay_account.setOnClickListener(this);
		rl_alipay_add.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.rl_add_alipay:
			
			Intent it = new Intent(this,AlipayEdit.class);
			startActivityForResult(it, 1);
			break;
		case R.id.ll_bind_account:
			Intent it1 = new Intent(this,AlipayEdit.class);
			it1.putExtra("account", this.getIntent().getStringExtra("account"));
			it1.putExtra("name", this.getIntent().getStringExtra("name"));
			startActivityForResult(it1,2);
//			Intent it = new Intent(this,);
			break;
		default:
			break;
		}
	}

	
}
