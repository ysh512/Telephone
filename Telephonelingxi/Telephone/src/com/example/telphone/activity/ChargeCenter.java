package com.example.telphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.example.telphone.R;

public class ChargeCenter extends Activity implements OnClickListener{

	
	private TextView tv_title;
	private TextView tv_charge_100;
	private TextView tv_charge_200;
	private TextView tv_charge_400;
	
	private EditText et_account;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.activity_recharge);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("充值中心");
		tv_charge_100 = (TextView)findViewById(R.id.tv_charge_100);
		tv_charge_200 = (TextView)findViewById(R.id.tv_charge_200);
		tv_charge_400 = (TextView)findViewById(R.id.tv_charge_400);
		et_account = (EditText)findViewById(R.id.et_account);
		tv_charge_100.setOnClickListener(this);
		tv_charge_200.setOnClickListener(this);
		tv_charge_400.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		
		case R.id.tv_charge_100:
			Intent it = new Intent(this, ChargeWay.class);
			it.putExtra("phone",et_account.getText().toString());
			it.putExtra("money", 100);
			startActivity(it);
			break;
		case R.id.tv_charge_200:
			Intent it1 = new Intent(this, ChargeWay.class);
			it1.putExtra("money", 200);
			it1.putExtra("phone",et_account.getText().toString());
			startActivity(it1);
			break;
		case R.id.tv_charge_400:
			Intent it2 = new Intent(this,ChargeWay.class);
			it2.putExtra("moeny", 400);
			it2.putExtra("phone",et_account.getText().toString());
			startActivity(it2);
			break;
			default :
				break;
		}
	}

	
}
