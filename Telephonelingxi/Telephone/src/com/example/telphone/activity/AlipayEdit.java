package com.example.telphone.activity;

import com.example.telphone.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class AlipayEdit extends Activity{

	private TextView tv_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		
		this.setContentView(R.layout.activity_alipay_edit);
		
		
	}
	
}
