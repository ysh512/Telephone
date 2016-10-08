package com.example.telphone.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.telphone.Constants;
import com.example.telphone.R;
import com.example.telphone.tool.PreferenceUtils;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;


public class QRCode extends Activity{

	private TextView tv_title;
	
	private ImageView  ic_user_icon;
	private ImageView iv_qr_code;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.activity_my_qrcode);
		
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的二维码");
		
		ic_user_icon = (ImageView)findViewById(R.id.ic_user_icon);
		iv_qr_code = (ImageView)findViewById(R.id.ic_qr_code);
		
		setQrCode(String.format(Constants.RECOMMEND_URL, PreferenceUtils.getPhone()));
	}

	
	private void setQrCode(String content)
	{
		Bitmap qrcodeBitmap;
		try {
			qrcodeBitmap = EncodingHandler.createQRCode(content, 400);
			iv_qr_code.setImageBitmap(qrcodeBitmap);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
