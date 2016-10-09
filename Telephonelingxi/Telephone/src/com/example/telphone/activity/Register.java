package com.example.telphone.activity;

import com.dner.fast.R;

import android.app.Activity;
import android.os.Bundle;

public class Register extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		
	}

	private void initView() {
		this.setContentView(R.layout.activity_reg);
	}
	

}
