package com.example.telphone.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.telphone.TelApplication;

public class PreferenceUtils {
	
	private static SharedPreferences shared;
	public static final String KEY_PASSWORD="pass";
	public static final String KEY_PHONE="phone";
	public static final String KEY_ALIPAY_ACCOUNT = "alipay_account";
	public static final String KEY_ALIPAY_NAME = "alipay_name";
	public static final String KEY_NICK_NAME = "nickname";

	public static final String KEY_WE_CHAT = "wechat";
	
	public static final String KEY_USER_ID="userid";
	
	public static String getPhone()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String phone = shared.getString("phone", "");
		return phone;
	}
	
	
	public static void savePass(String pass)
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		Editor ditor = shared.edit();
		ditor.putString(KEY_PASSWORD, pass);
		ditor.commit();
	}
	public static String getPass()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String pass = shared.getString(KEY_PASSWORD, "");
		return pass;
	}
	
	public static String getBindAlipayAccount()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String alipayAccount= shared.getString(KEY_ALIPAY_ACCOUNT,null);
		return alipayAccount;
	}
	
	public static String getBindAlipayName()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String alipayName = shared.getString(KEY_ALIPAY_NAME, null);
		return alipayName;
	}
	
	public static void  saveAlipayAccount(String account,String name)
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putString(KEY_ALIPAY_ACCOUNT, account);
		editor.putString(KEY_ALIPAY_NAME, name);
		editor.commit();
	}
	
	public static void saveNickName(String nickName){
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putString(KEY_NICK_NAME, nickName);
		editor.commit();
		
	}
	public static String getNickName()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String nickname = shared.getString(KEY_NICK_NAME, "求美名");
		return nickname;
	}
	
	public static void saveWechat(String weChat){
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putString(KEY_WE_CHAT,weChat);
		editor.commit();
		
	}
	
	public static String getWeChat()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		String wechat = shared.getString(KEY_WE_CHAT, "暂未绑定");
		return wechat;
	}
	
	public static void saveId(int id)
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		Editor editor = shared.edit();
		editor.putInt(KEY_USER_ID,id);
		editor.commit();
	}
	
	public static int getId()
	{
		SharedPreferences shared = TelApplication.getAppContext().getSharedPreferences(Variable.SHARE_PRE_NAME, Context.MODE_PRIVATE);
		return shared.getInt(KEY_USER_ID, 1);
		
	}
}
