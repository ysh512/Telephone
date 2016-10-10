package com.example.telphone.activity;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.telphone.Constants;
import com.dner.fast.R;
import com.example.telphone.tool.PreferenceUtils;
import com.kevin.crop.UCrop;
import com.kevin.imagecrop.activity.CropActivity;
import com.kevin.imagecrop.view.SelectPicturePopupWindow;
import com.kevin.imagecrop.view.SelectPicturePopupWindow.OnSelectedListener;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecomdInfo extends Activity implements OnClickListener,OnSelectedListener{

	private static final String TAG=RecomdInfo.class.getSimpleName();
	private TextView tv_title;
	
	private RelativeLayout rlyt_photo;
	private RelativeLayout rlyt_nickname;
	private RelativeLayout rlyt_wexin;
	private RelativeLayout rlyt_qr_code;
	
	private TextView tv_nickname;
	private TextView tv_weixin;
	
	private TextView tv_swithc;
	
	private SelectPicturePopupWindow mSelectPicturePopupWindow;
    private static final int GALLERY_REQUEST_CODE = 0;    // 相册选图标记
    private static final int CAMERA_REQUEST_CODE = 1;    // 相机拍照标记
    
    private String mTempPhotoPath;

    private Uri mDestinationUri;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		 mTempPhotoPath = Environment.getExternalStorageDirectory() + File.separator + "photo.jpeg";
	}

	private void initView() {
		this.setContentView(R.layout.activity_rcmd_info);
		tv_title=(TextView)findViewById(R.id.tv_title);
		tv_title.setText("个人资料");
		
		rlyt_photo=(RelativeLayout)findViewById(R.id.rlyt_photo);
		rlyt_nickname = (RelativeLayout)findViewById(R.id.rlyt_nickname);
		rlyt_wexin = (RelativeLayout)findViewById(R.id.rlyt_weixin);
		rlyt_qr_code = (RelativeLayout)findViewById(R.id.rlyt_qr_code);
		tv_swithc = (TextView)findViewById(R.id.tv_swithc);
		
		tv_nickname = (TextView)findViewById(R.id.tv_nickname);
		tv_weixin  = (TextView)findViewById(R.id.tv_weixin);
		rlyt_photo.setOnClickListener(this);
		rlyt_nickname.setOnClickListener(this);
		rlyt_wexin.setOnClickListener(this);
		rlyt_qr_code.setOnClickListener(this);
		tv_swithc.setOnClickListener(this);
	}
	
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		tv_nickname.setText(PreferenceUtils.getNickName());
		tv_weixin.setText(PreferenceUtils.getWeChat());
	}

	@Override
	public void onClick(View v) {

		switch(v.getId())
		{
		case R.id.rlyt_photo:
			mSelectPicturePopupWindow = new SelectPicturePopupWindow(this);
			mSelectPicturePopupWindow.showPopupWindow(this);
			mSelectPicturePopupWindow.setOnSelectedListener(this);
			break;
		case R.id.rlyt_nickname:
			Intent itNickName = new Intent(this,SetNickname.class);
			startActivity(itNickName);
			break;
		case R.id.rlyt_weixin:
			Intent itWeixin = new Intent(this,BindWeChat.class);
			startActivity(itWeixin);
			break;
		case R.id.rlyt_qr_code:
			Intent itQrCode = new Intent(this,QRCode.class);
			startActivity(itQrCode);
			break;
		case R.id.tv_swithc:
			break;
			default:
				break;
		}
	}

	@Override
	public void OnSelected(View v, int position) {
		// TODO Auto-generated method stub
        switch (position) {
        case 0:
            // "拍照"按钮被点击了
        	Log.d(TAG, "[OnSelected] takephoto");
            takePhoto();
            break;
        case 1:
            // "从相册选择"按钮被点击了
        	Log.d(TAG, "[OnSelected] pickfromgallery");
            pickFromGallery();
            break;
        case 2:
            // "取消"按钮被点击了
        	Log.d(TAG, "[OnSelected] dismissPopupWindow");
            mSelectPicturePopupWindow.dismissPopupWindow();
            break;
    }
	}

	private void pickFromGallery() {
        mSelectPicturePopupWindow.dismissPopupWindow();
        Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, GALLERY_REQUEST_CODE);
	}

	private void takePhoto()
	{

//	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // Permission was added in API Level 16
//	                && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
//	                != PackageManager.PERMISSION_GRANTED) {
//	            requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,
//	                    getString(R.string.permission_write_storage_rationale),
//	                    REQUEST_STORAGE_WRITE_ACCESS_PERMISSION);
//	        } else {
	            mSelectPicturePopupWindow.dismissPopupWindow();
	            Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	            //下面这句指定调用相机拍照后的照片存储的路径
	            takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mTempPhotoPath)));
	            startActivityForResult(takeIntent, CAMERA_REQUEST_CODE);
//	        }

	}
	public void onActivityResult(int requestCode, int resultCode, Intent data) {  
//	    if (resultCode == mActivity.RESULT_OK) {  
	        switch (requestCode) {  
	            case CAMERA_REQUEST_CODE:     
	                // TODO: 调用相机拍照  
	            	mDestinationUri = Uri.fromFile(new File(mTempPhotoPath));
	            	startCropActivity(mDestinationUri);
	                break;  
	            case GALLERY_REQUEST_CODE:
	            	mDestinationUri = Uri.fromFile(new File(mTempPhotoPath));
	            	startCropActivity(data.getData());
	            	break;
	            case UCrop.REQUEST_CROP: 
	            	if(resultCode == RESULT_OK)
	            	{
	            		
	            		Uri uri = data.getParcelableExtra(UCrop.EXTRA_OUTPUT_URI);
	            		Log.d(TAG,"[onActivity] 裁剪成功"+uri.toString());
	            		NetworkTask task = new NetworkTask();
	            		String path = uri.getHost()+uri.getPath();
	            		Log.d(TAG, "[onActivity] path:"+path);
	            		task.execute(path);
	            	}
	            	if(resultCode == UCrop.RESULT_ERROR)
	            	{
	            		Log.d(TAG,"[onActivity] 裁剪失败");
	            	}
	            	break; 
	        }  
//	    }  
	    super.onActivityResult(requestCode, resultCode, data);  
	}  
	
    public void startCropActivity(Uri uri) {
        UCrop.of(uri, mDestinationUri)
                .withAspectRatio(1, 1)
                .withMaxResultSize(512, 512)
                .withTargetActivity(CropActivity.class)
                .start(this);
    }
    
    class NetworkTask extends AsyncTask<String, Integer, String> {  
    	  
        @Override  
        protected void onPreExecute() {  
            super.onPreExecute();  
        }  
      
        @Override  
        protected String doInBackground(String... params) {  
            return doPost(params[0]);  
        }  
      
        @Override  
        protected void onPostExecute(String result) {  
            Log.i(TAG, "服务器响应" + result);  
        }  
    } 
    private String doPost(String imagePath) {  
        OkHttpClient mOkHttpClient = new OkHttpClient();  
      
        String result = "error";  
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);  
        builder.addFormDataPart("file", imagePath,  
                RequestBody.create(MediaType.parse("image/jpeg"), new File(imagePath)));  
        builder.addFormDataPart("UserID", PreferenceUtils.getPhone());
        builder.addFormDataPart("CallTo", "file");
        RequestBody requestBody = builder.build();  
        Request.Builder reqBuilder = new Request.Builder();  
//        String uploadUrl = String.format(Constants.UPLOAD_AVATAR_URL,PreferenceUtils.getPhone(),PreferenceUtils.getPass());
        String uploadUrl = Constants.UPLOAD_AVATAR_URL;
        Request request = reqBuilder  
                .url(uploadUrl)  
                .post(requestBody)  
                .build();  
        
        
        Log.d(TAG, "请求地址 " +uploadUrl);  
        try{  
            Response response = mOkHttpClient.newCall(request).execute();  
            Log.d(TAG, "响应码 " + response.code());  
            if (response.isSuccessful()) {  
                String resultValue = response.body().string();  
                Log.d(TAG, "响应体 " + resultValue);  
                return resultValue;  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return result;  
    }  
}
