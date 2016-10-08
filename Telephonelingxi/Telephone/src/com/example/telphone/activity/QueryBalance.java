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

import com.example.telphone.Constants;
import com.example.telphone.R;
import com.example.telphone.tool.PreferenceUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class QueryBalance extends Activity{

	private static final String TAG="QueryBalance";
	
	private TextView tv_balance;
	private TextView tv_phone;
	private TextView tv_account;
	private TextView tv_validay;
	private TextView tv_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initView();
		QueryTask qt = new QueryTask();
		qt.execute();
	}

	private void initView() {

		this.setContentView(R.layout.activity_balance);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(R.string.query_balance);
		tv_phone = (TextView)findViewById(R.id.tv_phone);
		tv_account = (TextView)findViewById(R.id.tv_account);
		tv_balance = (TextView)findViewById(R.id.tv_blance);
		tv_validay = (TextView)findViewById(R.id.tv_validay);
//		tv_balance = (TextView)findViewById(R.id.tv_balance);
		
		tv_phone.setText(PreferenceUtils.getPhone());
		tv_account.setText(PreferenceUtils.getPhone());
		
	}
	
	class QueryTask extends AsyncTask<Integer, Integer, Boolean>
	{
		private String yuer;
		private String mlimit;
		ProgressDialog dialog ;
		boolean query = false;

		@Override
		protected Boolean doInBackground(Integer... arg0) {
			HttpClient client = new DefaultHttpClient();  
			String url = String.format(Constants.LOGIN_URL,PreferenceUtils.getPhone(),PreferenceUtils.getPass());
	        
			Log.d(TAG, "[doInBackground] login url:"+url);
			HttpGet get = new HttpGet(url);  
	        HttpResponse response= null;
			try {
				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					String result = EntityUtils.toString(response.getEntity());
					Log.d(TAG, "[refreshLogin] result:"+result);
					try {
						JSONObject object = new JSONObject(result);
						yuer = object.getString("yuer");
						mlimit = object.getString("mlimit");
						Log.d(TAG, "[doInBackground] mlimit:"+mlimit);
						return true;
					} catch (JSONException e) {
						e.printStackTrace();
						return false;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	         
			return false;
		}

		protected void onPostExecute(Boolean result) {
			Log.d(TAG, "[onPostExecute] result:"+result);
//			  dialog.dismiss();
			  if(result)
			  {
				  Log.d(TAG, "[onPostExecute] mlimit:"+mlimit);
				  tv_balance.setText(yuer);
				  tv_validay.setText(mlimit);
			  }else{
				  Toast.makeText(QueryBalance.this, "查询失败，请稍候重试",Toast.LENGTH_SHORT).show();
			  }
		}

		protected void onPreExecute() {
			
		}
		
	}

	
}
