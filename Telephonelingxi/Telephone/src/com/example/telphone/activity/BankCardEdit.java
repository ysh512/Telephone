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

import com.dner.fast.R;
import com.example.telphone.Constants;
import com.example.telphone.tool.PreferenceUtils;

public class BankCardEdit extends Activity implements OnClickListener{

	private static final String TAG=BankCardEdit.class.getSimpleName();
	
	private EditText etBankName;
	private EditText etBankCard;
	private EditText etBankAccount;
	
	private TextView tv_title_right;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
	}

	private void initView() {
		this.setContentView(R.layout.activity_bank_edit);
		
		etBankName = (EditText)findViewById(R.id.et_bank);
		etBankCard = (EditText)findViewById(R.id.et_bank_num);
		etBankAccount = (EditText)findViewById(R.id.et_bank_username);
		
		TextView title = (TextView)findViewById(R.id.tv_title);
		title.setText("绑定银行卡");
		tv_title_right = (TextView)findViewById(R.id.tv_title_right);
		tv_title_right.setVisibility(View.VISIBLE);
		tv_title_right.setText("保存");
		
		tv_title_right.setOnClickListener(this);
	}

	class SaveBankCard extends AsyncTask<Void,Void,Boolean>
	{
		
		private String bankCardNo;
		private String bankAccount;
		private String bankName;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			bankCardNo = etBankCard.getText().toString();
			bankName = etBankName.getText().toString();
			bankAccount = etBankAccount.getText().toString();
		}

		@Override
		protected Boolean doInBackground(Void... params) {
			// TODO Auto-generated method stub
			if(TextUtils.isEmpty(bankCardNo)||TextUtils.isEmpty(bankAccount) || TextUtils.isEmpty(bankName))
			{
				return false;
			}
			

			HttpClient client = new DefaultHttpClient();  
			String url = String.format(Constants.BIND_BANK_CARD,PreferenceUtils.getPhone(),PreferenceUtils.getPass(),bankAccount,bankName,bankCardNo );
	        
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
				Toast.makeText(BankCardEdit.this, "保存失败", Toast.LENGTH_SHORT).show();
			}else
			{
				Toast.makeText(BankCardEdit.this, "保存成功", Toast.LENGTH_SHORT).show();
			}
		}
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.tv_title_right:
			SaveBankCard task = new SaveBankCard();
			task.execute();
			break;
		}
	}
}
