package com.example.telphone.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.test.PerformanceTestCase;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.dner.fast.R;
import com.example.telphone.Constants;
import com.example.telphone.extendview.AsyncImageView;
import com.example.telphone.extendview.TitlePopup;
import com.example.telphone.extendview.TitlePopup.OnItemOnClickListener;
import com.example.telphone.property.ActionItem;
import com.example.telphone.tool.PreferenceUtils;
import com.google.zxing.WriterException;
import com.zxing.encoding.EncodingHandler;


public class QRCode extends Activity{

	private TextView tv_title;
	
	private AsyncImageView  ic_user_icon;
	private ImageView iv_qr_code;
	
	private TitlePopup titlePopup; 
	
	private ImageView iv_title_right;
	
	private static final String TAG=QRCode.class.getSimpleName();
	
	private Bitmap qrcodeBitmap;
	
	private TextView tv_nikename;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		
		QueryAvatorTask task = new QueryAvatorTask();
		task.execute();
	}

	private void initView() {
		this.setContentView(R.layout.activity_my_qrcode);
		
		
		tv_nikename=(TextView)findViewById(R.id.tv_nikename);
		
		tv_nikename.setText(PreferenceUtils.getNickName());
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText("我的二维码");
		
		ic_user_icon = (AsyncImageView)findViewById(R.id.ic_user_icon);
		iv_qr_code = (ImageView)findViewById(R.id.ic_qr_code);
		
		setQrCode(String.format(Constants.RECOMMEND_URL, PreferenceUtils.getPhone()));
		titlePopup = new TitlePopup(this, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT); 
		initData();
		
		iv_title_right = (ImageView)findViewById(R.id.iv_title_right);
		iv_title_right.setImageResource(R.drawable.ic_qrmore);
		
		
		iv_title_right.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				titlePopup.show(v);
			}
		});
	}

	
	private void setQrCode(String content)
	{
		try {
			qrcodeBitmap = EncodingHandler.createQRCode(content, 400);
			iv_qr_code.setImageBitmap(qrcodeBitmap);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	   private void initData(){  
	        //给标题栏弹窗添加子类  
	        titlePopup.addAction(new ActionItem(this, "分享二维码", R.drawable.ic_share_w));  
	        titlePopup.addAction(new ActionItem(this, "保存到手机", R.drawable.ic_save));  
	        
	        titlePopup.setItemOnClickListener(new OnItemOnClickListener(){

				@Override
				public void onItemClick(ActionItem item, int position) {
					// TODO Auto-generated method stub
					Log.d(TAG, "[onItemClick] position:"+position);
					
					switch(position)
					{
					case 0:
						showShare();
						break;
					case 1:
						saveQrCode();
						Toast.makeText(QRCode.this, "二维码已经保存到储存卡根目录", Toast.LENGTH_SHORT).show();
						break;
					}
				}
	        	
	        });
	    }  
	   
	   private void saveQrCode()
	   {
		   Log.e(TAG, "保存图片");
		   File f = new File("/sdcard/", "QRCODE.png");
		   if (f.exists()) {
		    f.delete();
		   }
		   try {
		    FileOutputStream out = new FileOutputStream(f);
		    qrcodeBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
		    out.flush();
		    out.close();
		    Log.i(TAG, "已经保存");
		   
		   } catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   } catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		   }
	   }
	   
		private void showShare() {
			saveQrCode();
			 ShareSDK.initSDK(this);
			 OnekeyShare oks = new OnekeyShare();
			 //关闭sso授权
			 oks.disableSSOWhenAuthorize(); 
//			 String shareUrl = String.format(Constants.RECOMMEND_URL, String.valueOf(PreferenceUtils.getId()));
			 
			// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
			 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
			 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
//			 oks.setTitle("分享");
			 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
//			 oks.setTitleUrl(shareUrl);
			 // text是分享文本，所有平台都需要这个字段
//			 oks.setText(shareUrl);
			 //分享网络图片，新浪微博分享网络图片需要通过审核后申请高级写入接口，否则请注释掉测试新浪微博
//			 oks.setImageUrl("http://f1.sharesdk.cn/imgs/2014/02/26/owWpLZo_638x960.jpg");
			 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
			 oks.setImagePath("/sdcard/QRCODE.png");//确保SDcard下面存在此张图片
			 // url仅在微信（包括好友和朋友圈）中使用
//			 oks.setUrl(shareUrl);
			 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
//			 oks.setComment("邀请好友赚取话费");
			 // site是分享此内容的网站名称，仅在QQ空间使用
//			 oks.setSite(getString(R.string.app_name));
			 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
//			 oks.setSiteUrl(shareUrl);
			 
			// 启动分享GUI
			 oks.show(this);
			 }
	
		
		 class QueryAvatorTask extends AsyncTask<Void,Void,Boolean>
		    {

		    	private String avator_path;
		    	
				@Override
				protected Boolean doInBackground(Void... params) {
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
								
								int id = object.getInt("id");
								PreferenceUtils.saveId(id);
								if(0==id)
								{
									return false;
								}else
								{
									
									String user_avator = object.getString("pic_path");
									Log.d(TAG, "[refreshLogin] user avator:"+user_avator);
									avator_path = user_avator;
									return true;
								}
								
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

				@Override
				protected void onPostExecute(Boolean result) {
					// TODO Auto-generated method stub
					super.onPostExecute(result);
					
					if(result)
					{
						if(!TextUtils.isEmpty(avator_path))
						{
							ic_user_icon.setImageUrl(avator_path);
						}
					}
				}
				
		    }

		
}
