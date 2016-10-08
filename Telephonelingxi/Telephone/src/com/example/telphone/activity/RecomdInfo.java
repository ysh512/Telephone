package com.example.telphone.activity;

import com.example.telphone.R;
import com.example.telphone.tool.PreferenceUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecomdInfo extends Activity implements OnClickListener{

	private TextView tv_title;
	
	private RelativeLayout rlyt_photo;
	private RelativeLayout rlyt_nickname;
	private RelativeLayout rlyt_wexin;
	private RelativeLayout rlyt_qr_code;
	
	private TextView tv_nickname;
	private TextView tv_weixin;
	
	private TextView tv_swithc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.activity_rcmd_info);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("个人资料");
		
		rlyt_photo=(RelativeLayout)findViewById(R.id.rlyt_photo);
		rlyt_nickname = (RelativeLayout)findViewById(R.id.rlyt_nickname);
		rlyt_wexin = (RelativeLayout)findViewById(R.id.rlyt_weixin);
		rlyt_qr_code = (RelativeLayout)findViewById(R.id.rlyt_qr_code);
		tv_swithc = (TextView)findViewById(R.id.tv_swithc);
		
		tv_nickname = (TextView)findViewById(R.id.tv_nickname);
		tv_weixin  = (TextView)findViewById(R.id.tv_weixin);
		rlyt_photo.setOnClickListener(this);
		rlyt_nickname.setOnClickListener(this);
		rlyt_wexin.setOnClickListener(this);
		rlyt_qr_code.setOnClickListener(this);
		tv_swithc.setOnClickListener(this);
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		tv_nickname.setText(PreferenceUtils.getNickName());
		tv_weixin.setText(PreferenceUtils.getWeChat());
	}

	@Override
	public void onClick(View v) {

		switch(v.getId())
		{
		case R.id.rlyt_photo:
			break;
		case R.id.rlyt_nickname:
			Intent itNickName = new Intent(this,SetNickname.class);
			startActivity(itNickName);
			break;
		case R.id.rlyt_weixin:
			Intent itWeixin = new Intent(this,BindWeChat.class);
			startActivity(itWeixin);
			break;
		case R.id.rlyt_qr_code:
			break;
		case R.id.tv_swithc:
			break;
			default:
				break;
		}
	}

	
}
