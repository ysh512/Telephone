package com.example.telphone.activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telphone.Constants;
import com.example.telphone.R;
import com.example.telphone.tool.PreferenceUtils;

public class BindWeChat extends Activity implements OnClickListener{

	private static final String TAG=BindWeChat.class.getSimpleName();
	private TextView tv_title;
	private TextView tv_save;
	 
	private EditText et_info;
 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.activity_profile);
		
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("设置微信号");
		tv_save = (TextView)findViewById(R.id.bt_save);
		et_info = (EditText)findViewById(R.id.et_clear);
		et_info.setText(PreferenceUtils.getWeChat());
		tv_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {

		switch(v.getId())
		{
		case R.id.bt_save:
			BindWechatTask task = new BindWechatTask();
			task.execute();
			break;
			default :
				break;
		}
	}

	
	class BindWechatTask extends AsyncTask<Void,Void,Boolean>{

		private String wechat;
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result)
			{
				Toast.makeText(BindWeChat.this, "修改成功", Toast.LENGTH_SHORT).show();
			}else
			{
				Toast.makeText(BindWeChat.this, "微信绑定失败，请稍候重试", Toast.LENGTH_SHORT).show();
			}
		}


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			wechat = et_info.getText().toString();
		}


		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(wechat))
			{
				return false;
			}else
			{
				HttpClient client = new DefaultHttpClient();  
				String url = String.format(Constants.BIND_WECHAT_URL,PreferenceUtils.getPhone(),PreferenceUtils.getPass(),wechat );
		        
				Log.d(TAG, "[doInBackground] save account url:"+url);
				HttpGet get = new HttpGet(url);  
		        HttpResponse response= null;
				try {
					response = client.execute(get);
					if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						String result = EntityUtils.toString(response.getEntity());
						Log.d(TAG, "[doInBackground] result:"+result);
						if(result.contains("ok"))
						{
							PreferenceUtils.saveWechat(wechat);
							return true;
						}else
						{
							return false;
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			
			return false;
		}
		
	}
}
