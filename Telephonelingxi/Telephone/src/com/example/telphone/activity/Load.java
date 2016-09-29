package com.example.telphone.activity;

import com.example.telphone.R;
import com.example.telphone.tool.Variable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Load extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setContentView(R.layout.load);

		new Handler().postDelayed(new Runnable() {
			public void run() {
				// Go to main activity, and finish load activity

				SharedPreferences sharedPreferences = getSharedPreferences(
						Variable.SHARE_PRE_NAME, Activity.MODE_PRIVATE);
						String login = sharedPreferences.getString("login", "");
				String phone = sharedPreferences.getString("phone", "");
				if (!login.equals("true")) {
					Intent it = new Intent(Load.this, Login.class);
					startActivity(it);
					finish();
				} else {
					Intent mainIntent = new Intent(Load.this, Container.class);
					Load.this.startActivity(mainIntent);
					Load.this.finish();

				}
			}
		}, 500);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
