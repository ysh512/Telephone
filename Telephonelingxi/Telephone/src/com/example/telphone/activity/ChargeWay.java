package com.example.telphone.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.alipay.sdk.pay.demo.PayDemoActivity;

import com.dner.fast.R;

public class ChargeWay extends Activity implements OnClickListener{

	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.rlyt_deyx:
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		this.setContentView(R.layout.activity_recharge_way);
		TextView tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("充值中心");
		
		int money = this.getIntent().getIntExtra("money",100);
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
	
		RelativeLayout rlyt_dexy = (RelativeLayout)findViewById(R.id.rlyt_deyx);
		rlyt_dexy.setOnClickListener(this);
		RelativeLayout rlyt_alipay = (RelativeLayout)findViewById(R.id.rlyt_alipay);
		rlyt_alipay.setOnClickListener(this);
		
	}

	
}
