package com.example.telphone.activity;

import com.dner.fast.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Web extends Activity{

	private WebView wv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		initView();
		
	}
	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.webview);
		wv = (WebView)findViewById(R.id.webView1);
		
		Intent it = this.getIntent();
		String url = it.getStringExtra("url");
		wv.loadUrl(url);
		wv.setWebViewClient(new WebViewClientDemo());
	}

	private class WebViewClientDemo extends WebViewClient {
	    @Override
	    public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        view.loadUrl(url);
	        return true;
	    }
	}
}
