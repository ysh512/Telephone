package com.example.telphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dner.fast.R;

public class ChargeCenter extends Activity implements OnClickListener{

	
	private TextView tv_title;
	
	private RelativeLayout rl_charge_100;
	private RelativeLayout rl_charge_200;
	private RelativeLayout rl_charge_400;
	
	private EditText et_account;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_recharge);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("充值中心");
		et_account = (EditText)findViewById(R.id.et_account);
		
		rl_charge_100 = (RelativeLayout)findViewById(R.id.rl_charge_100);
		rl_charge_200 = (RelativeLayout)findViewById(R.id.rl_charge_200);
		rl_charge_400 = (RelativeLayout)findViewById(R.id.rl_charge_400);
		
		rl_charge_100.setOnClickListener(this);
		rl_charge_200.setOnClickListener(this);
		rl_charge_400.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		
		case R.id.rl_charge_100:
			if(!checkAccount())
				return;
			Intent it = new Intent(this, ChargeWay.class);
			it.putExtra("phone",et_account.getText().toString());
			it.putExtra("money", 100);
			startActivity(it);
			break;
		case R.id.rl_charge_200:
			if(!checkAccount())
				return;
			Intent it1 = new Intent(this, ChargeWay.class);
			it1.putExtra("money", 200);
			it1.putExtra("phone",et_account.getText().toString());
			startActivity(it1);
			break;
		case R.id.rl_charge_400:
			if(!checkAccount())
				return;
			Intent it2 = new Intent(this,ChargeWay.class);
			it2.putExtra("moeny", 400);
			it2.putExtra("phone",et_account.getText().toString());
			startActivity(it2);
			break;
			default :
				break;
		}
	}

	private boolean  checkAccount() {
		// TODO Auto-generated method stub
		if(TextUtils.isEmpty(et_account.getText().toString()))
		{
			Toast.makeText(this, "充值账户不能为空", Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}
	
}
