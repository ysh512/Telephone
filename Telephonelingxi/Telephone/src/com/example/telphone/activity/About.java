package com.example.telphone.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.dner.fast.R;

public class About extends Activity implements OnClickListener{

	private TextView tv_version;
	
	private TextView tv_title;
	
	private TextView tv_update;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {

		this.setContentView(R.layout.activity_about);
		tv_version = (TextView)findViewById(R.id.tv_version);
		
		String version = String.format(tv_version.getText().toString(), getVersion());
		tv_version.setText(version);
		
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("关于我们");
		
		tv_update = (TextView)findViewById(R.id.tv_uprage);
		tv_update.setOnClickListener(this);
		
	}

	
	/**
	 * 获取版本号
	 * @return 当前应用的版本号
	 */
	public String getVersion() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        String version = info.versionName;
	        return version;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return "1.0.0.0";
	    }
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.tv_uprage:
			Toast.makeText(this, "目前已是最新版本", Toast.LENGTH_SHORT).show();
			break;
		}
	}
}
