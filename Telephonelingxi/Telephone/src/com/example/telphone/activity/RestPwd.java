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
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telphone.Constants;
import com.dner.fast.R;
import com.example.telphone.tool.PreferenceUtils;

public class RestPwd extends Activity implements OnClickListener{

	private static final String TAG= RestPwd.class.getSimpleName();
	
	private TextView tv_title;
	private TextView tv_right_title;
	
	private EditText et_opwd;
	private EditText et_npwd;
	private EditText et_spwd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.activity_passwordset2);
		
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("修改密码");
		tv_right_title = (TextView)findViewById(R.id.tv_title_right);
		tv_right_title.setText("保存");
		tv_right_title.setVisibility(View.VISIBLE);
		tv_right_title.setOnClickListener(this);
		et_opwd = (EditText)findViewById(R.id.et_opwd);
		et_npwd = (EditText)findViewById(R.id.et_npwd);
		et_spwd = (EditText)findViewById(R.id.et_spwd);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.tv_title_right:
			ResetPwdTask task = new ResetPwdTask();
			task.execute();
			break;
		default:
			break;
		}
	}
	class ResetPwdTask extends AsyncTask<Void,Void,Boolean>{

		private String oldPass;
		private String newPass;
		private String againPass;
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			oldPass = et_opwd.getText().toString();
			newPass = et_npwd.getText().toString();
			againPass = et_spwd.getText().toString();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(null==newPass || null==againPass || !newPass.equals(againPass) || null==oldPass)
			{
				
				Log.d(TAG,"[doInBackground] info wrong"+newPass+","+againPass+","+oldPass);
				return false;
			}else
			{
				HttpClient client = new DefaultHttpClient();  
				String url = String.format(Constants.RESET_PWD_URL,PreferenceUtils.getPhone(),oldPass,newPass );
		        
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
		         
				return false;
			
			}

		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(result)
			{
				Toast.makeText(RestPwd.this, "修改成功", Toast.LENGTH_SHORT).show();
				PreferenceUtils.savePass(newPass);
			}else
			{
				Toast.makeText(RestPwd.this, "修改失敗，请稍候重试", Toast.LENGTH_SHORT).show();
			}
		}
		
		
		
	}
}
