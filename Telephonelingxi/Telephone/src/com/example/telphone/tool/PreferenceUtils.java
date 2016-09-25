package com.example.telphone.tool;

import com.example.telphone.TelApplication;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtils {
	
	private static SharedPreferences shared;
	public static final String KEY_PASSWORD="pass";
	public static final String KEY_PHONE="phone";
	
	public static String getPhone()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String phone = shared.getString("phone", "");
		return phone;
	}
	
	public static String getPass()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String pass = shared.getString(KEY_PASSWORD, "");
		return pass;
	}
}
