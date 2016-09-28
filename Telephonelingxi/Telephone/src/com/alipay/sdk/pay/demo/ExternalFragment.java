package com.alipay.sdk.pay.demo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.telphone.Constants;
import com.example.telphone.R;
import com.example.telphone.tool.Variable;

public class ExternalFragment extends Fragment {

	private TextView tv_name;
	private TextView tv_des;
	private TextView tv_price;
	
	
	private double money;
	private String phone;
	private String goodsDes;
	private int goodsId; //100元是1 200元是2 400元是3
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.pay_external, container,false);
		tv_name = (TextView) v.findViewById(R.id.product_subject);
		tv_des = (TextView) v.findViewById(R.id.product_des);
		tv_price = (TextView) v.findViewById(R.id.product_price);
		
		return v;
//		return inflater.inflate(R.layout.pay_external, container, false);
	}
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		phone = this.getActivity().getIntent().getStringExtra("phone");
		money = this.getActivity().getIntent().getIntExtra("money", 100);
		goodsDes = this.getActivity().getIntent().getStringExtra("des");
		
		switch((int)money)
		{
		case 100:
			goodsId=1;
			break;
		case 200:
			goodsId=2;
			break;
		case 400:
			goodsId=4;
		}
		
		if(Variable.PAY_DEBUG)
		{
			money = 0.01;
		}
		tv_name.setText(phone);
		tv_price.setText(""+money);
		tv_des.setText(goodsDes);
	}

	public double getPayMoney()
	{
		return money;
	}
	
}
