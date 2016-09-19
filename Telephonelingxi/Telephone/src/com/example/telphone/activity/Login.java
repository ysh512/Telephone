package com.example.telphone.activity;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.telphone.R;
import com.example.telphone.TelApplication;
import com.example.telphone.tool.Variable;

public class Login extends BaseActivity implements OnClickListener{
	
	private static final String TAG= "Login";
	
	//---------------------UI-----------------------
	
	//login url : http://121.40.100.250:99/CallReqRet.php?UserID=%s&CallTo=auth
	private TextView btn_login;
	private TextView btn_register;
	private TextView tv_find;
	
	private EditText et_login_phone;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		
		initView();
		
		TelApplication.add(this);
	}

	private void initView() {
		this.setLinearView(R.layout.activity_login);
		btn_login = (TextView)findViewById(R.id.bt_login);
		btn_register = (TextView)findViewById(R.id.tv_reg);
		tv_find=(TextView)findViewById(R.id.tv_find);
		
		et_login_phone = (EditText)findViewById(R.id.et_login_phone);

		tv_find.setOnClickListener(this);
		btn_register.setOnClickListener(this);
		btn_login.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId())
		{
		case R.id.bt_login:
			LoginTask lt = new LoginTask();
			lt.execute();
//			Intent it1 = new Intent(this,Container.class);
//			startActivity(it1);
			break;
		case R.id.tv_find:
			Intent it = new Intent(this,FindPass.class);
			startActivity(it);
			break;
		case R.id.tv_reg:
			Intent it2 = new Intent(this,Register.class);
			startActivity(it2);
			
		default:
			break;
		}
	}
	
	class LoginTask extends AsyncTask<Integer, Integer, String>
	{
		boolean login = false;
		
		ProgressDialog dialog ;
		
		
		@Override
		protected void onPreExecute() {
			dialog = ProgressDialog.show(Login.this, "登录", "正在加载中。。。");
//			dialog.show();
		}

		@Override
		protected String doInBackground(Integer... params) {
			
			HttpClient client = new DefaultHttpClient();  
	        HttpGet get = new HttpGet("http://121.40.100.250:99/CallReqRet.php?UserID="+et_login_phone.getText().toString()+"&CallTo=auth");  
	        HttpResponse response= null;
			try {
				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					login = true;
					String result = EntityUtils.toString(response.getEntity());

					Log.d(TAG, result);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	         
			
			
			return null;
		}
		
		protected void onPostExecute(String result) 
		{
			dialog.dismiss();
			if(login)
			{
				Intent it = new Intent(Login.this,Container.class);
				startActivity(it);
				SharedPreferences mySharedPreferences= getSharedPreferences(Variable.SHARE_PRE_NAME, Activity.MODE_PRIVATE); 
				SharedPreferences.Editor editor = mySharedPreferences.edit(); 
				editor.putString("login", "true"); 
//				editor.putString("phone", et_account.getText().toString()); 
				editor.commit(); 
				finish();

			}
			else
			{
				Toast.makeText(Login.this, "网络连接错误", 5).show();
			}
			
		}
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
//		TelApplication app = ((TelApplication)getApplicationContext());
//        app.remove(this);
		TelApplication.remove(this);
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		TelApplication.finishAll();
		this.finish();
	}
	
	
	
}
