package com.example.telphone.extendview;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;

public class MyViewPager extends ViewPager{
	
	public MyViewPager(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int arg0, int arg1) {
		// TODO Auto-generated method stub
		super.onMeasure(arg0, arg1);
		
		Resources resouce = this.getContext().getResources();
		DisplayMetrics dm = resouce.getDisplayMetrics();
		int width = dm.widthPixels;
		int height = (width*3)/4;
		this.setMeasuredDimension(width, height);
		
	}
	
}
