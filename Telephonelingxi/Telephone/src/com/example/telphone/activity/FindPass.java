package com.example.telphone.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.telphone.R;

public class FindPass extends Activity{

	private TextView tv_title;
	private TextView tv_right_title;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		
		this.setContentView(R.layout.activity_passwordfind);
		
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_right_title=(TextView)findViewById(R.id.tv_title_right);
		
		tv_title.setText("找回密码");
		tv_right_title.setText("完成");
	}
	
	
}
