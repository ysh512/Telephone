package com.example.telphone.activity;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telphone.Constants;
import com.example.telphone.R;
import com.example.telphone.tool.PreferenceUtils;

public class AlipayEdit extends Activity implements View.OnClickListener{

	private static final String TAG= AlipayEdit.class.getSimpleName();
	private TextView tv_title;
	
	private TextView tv_title_right;
	
	private EditText et_alipay_account;
	private EditText et_real_name;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		
		this.setContentView(R.layout.activity_alipay_edit);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("支付宝");
		tv_title_right = (TextView)findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.VISIBLE);
		tv_title_right.setText("保存");
		
		et_alipay_account = (EditText)findViewById(R.id.et_alipay_account);
		et_real_name = (EditText)findViewById(R.id.et_real_name);
		
		tv_title_right.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.tv_title_right:
			SaveAlipay task = new SaveAlipay();
			task.execute();
			break;
			default :
				break;
		}
	}
	
	
	class SaveAlipay extends AsyncTask<Void,Void,Boolean>
	{
		
		private String account;
		private String name;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			account = et_alipay_account.getText().toString();
			name = et_real_name.getText().toString();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(account)||TextUtils.isEmpty(name))
			{
				return false;
			}
			

			HttpClient client = new DefaultHttpClient();  
			String url = String.format(Constants.ALIPAY_ACCOUNT_SAVE_URL,PreferenceUtils.getPhone(),PreferenceUtils.getPass(),account,name );
	        
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

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			if(!result){
				Toast.makeText(AlipayEdit.this, "保存失败", Toast.LENGTH_SHORT).show();
			}else
			{
				Toast.makeText(AlipayEdit.this, "保存成功", Toast.LENGTH_SHORT).show();
			}
		}
		
		
		
	}
}
