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
import com.dner.fast.R;
import com.example.telphone.tool.PreferenceUtils;

public class SetNickname extends Activity implements OnClickListener{

	private static final String TAG=SetNickname.class.getSimpleName();
	private TextView tv_title;
	private EditText et_name;
	
	private TextView tv_save;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.activity_profile);
		tv_title= (TextView)findViewById(R.id.tv_title);
		tv_title.setText("设置昵称");
		
		et_name = (EditText)findViewById(R.id.et_clear);
		tv_save = (TextView)findViewById(R.id.bt_save);
		tv_save.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.bt_save:
			SetNickNameTask setName = new SetNickNameTask();
			setName.execute();
			break;
			default :
				break;
		}
	}

	class SetNickNameTask extends AsyncTask<Void,Void,Boolean>{

		private String nickName;
		
		
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result)
			{
				Toast.makeText(SetNickname.this, "修改成功", Toast.LENGTH_SHORT).show();
			}else
			{
				Toast.makeText(SetNickname.this, "修改昵称失败，请稍候重试", Toast.LENGTH_SHORT).show();
			}
		}


		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			nickName = et_name.getText().toString();
		}


		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(nickName))
			{
				return false;
			}else
			{
				HttpClient client = new DefaultHttpClient();  
				String url = String.format(Constants.MODIFY_NICKNAME_URL,PreferenceUtils.getPhone(),PreferenceUtils.getPass(),nickName );
		        
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
							PreferenceUtils.saveNickName(nickName);
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
