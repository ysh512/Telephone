package com.example.telphone.model;




import java.util.List;

import com.example.telphone.activity.Container.PageInstatedListener;


import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;


public class ContainerAdapter extends PagerAdapter {
    public List<View> mListViews;

    public PageInstatedListener mPageInstated;
    
    public ContainerAdapter(List<View> mListViews) {
        this.mListViews = mListViews;
    }

    public ContainerAdapter(List<View> mListViews,PageInstatedListener pageInstated)
    {
    	this(mListViews);
    	this.mPageInstated = pageInstated;
    }
    

	@Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
		if(null!=mPageInstated)
		{
			mPageInstated.onDestroy(arg1);
		}
        ((ViewPager) arg0).removeView(mListViews.get(arg1));
    }

    @Override
    public void finishUpdate(View arg0) {
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public Object instantiateItem(View arg0, int arg1) {
        ((ViewPager) arg0).addView(mListViews.get(arg1), 0);
        
        if(null!=this.mPageInstated)
        {
        	mPageInstated.onInstated(arg1);
        }
        
        return mListViews.get(arg1);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == (arg1);
    }

    @Override
    public void restoreState(Parcelable arg0, ClassLoader arg1) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public void startUpdate(View arg0) {
    }


    
}
