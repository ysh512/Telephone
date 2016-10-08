package com.example.telphone;

public class Constants {

	public static final String LOGIN_URL = "http://60.205.168.68:88/wapb/CallReqRet.php?UserID=%s&UserPass=%s&CallTo=login&Wap=json";
	public static final String LEVEL_ONE="一级";
	public static final String LEVEL_TWO="二级";
	public static final String LEVEL_THREE="三级";
	
	public static final String RECOMMEND_URL = "http://60.205.168.68:88/reg/?id=%s";
	
	public static final String ALIPAY_ACCOUNT_SAVE_URL = "http://60.205.168.68:88/wapb/CallReqRet.php?UserID=%s&UserPass=%s&CallTo=zhifubao&email=%s&account=%s";
	
	public static final String RESET_PWD_URL = "http://60.205.168.68:88/wapb/CallReqRet.php?UserID=%s&UserPass=%s&CallTo=resetpass&newpass=%s";
	
	public static final String MODIFY_NICKNAME_URL = "http://60.205.168.68:88/wapb/CallReqRet.php?UserID=%s&UserPass=%s&CallTo=nickname&nickname=%s";
	
	public static final String BIND_WECHAT_URL="http://60.205.168.68:88/wapb/CallReqRet.php?UserID=%s&UserPass=%s&CallTo=wxid&wxid=%s";
	
	//提现
	public static final String CASH_URL="http://60.205.168.68:88/wapb/CallReqRet.php?UserID=%s&UserPass=%s&CallTo=apply&money=%s";
	public static final String MES="尊敬的%s,欢迎您使用%s，快推荐您的朋友使用并领取丰厚报仇吧您的推荐人是：%s";
}
