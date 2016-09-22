package com.example.telphone.activity;

import java.io.File;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import com.example.sortlistview.CharacterParser;
import com.example.sortlistview.ClearEditText;
import com.example.sortlistview.GroupMemberBean;
import com.example.sortlistview.PinyinComparator;
import com.example.sortlistview.SideBar;
import com.example.sortlistview.SortGroupMemberAdapter;
import com.example.sortlistview.SideBar.OnTouchingLetterChangedListener;
import com.example.telphone.QueryInfo;
import com.example.telphone.R;
import com.example.telphone.TelApplication;
import com.example.telphone.extendview.MarqueeButton;
import com.example.telphone.listener.CallBottomListener;
import com.example.telphone.model.BitmapLoadAdapter;
import com.example.telphone.model.ContainerAdapter;
import com.example.telphone.model.MenuAdAdapter;
import com.example.telphone.model.MenuGridviewAdapter;
import com.example.telphone.model.RecordAdapter;
import com.example.telphone.property.ContractInfo;
import com.example.telphone.property.SingleRecord;
import com.example.telphone.tool.Utils;
import com.example.telphone.tool.Variable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

@SuppressLint("HandlerLeak")
public class Container extends BaseActivity implements OnClickListener ,OnItemClickListener,OnPageChangeListener{

	public static final String TAG = "Container";
	//-----------------UI------------------------
	
	private ViewPager viewPager;
	private TextView im_call;
	private TextView im_record;
	private TextView im_contacts;
	private TextView im_meun;
	
//	private LinearLayout ll_c_bottom;
	
	//page call
	private LinearLayout ll_numPad;
	private ViewPager vp_t_ad;
	private ImageButton num_1;
	private ImageButton num_2;
	private ImageButton num_3;
	private ImageButton num_4;
	private ImageButton num_5;
	private ImageButton num_6;
	private ImageButton num_7;
	private ImageButton num_8;
	private ImageButton num_9;
	private ImageButton num_0;
	private ImageButton num_j;
	private ImageButton num_x;
	
	//page call bottom
	private RelativeLayout ll_call_pad;	
	
	private List<View> views ;
	
	//contacts page
	private ListView sortListView;
	private SideBar sideBar;
	private TextView dialog;
	private SortGroupMemberAdapter adapter;
	private ClearEditText mClearEditText;

	private LinearLayout titleLayout;
	private TextView title;
	private TextView tvNofriends;
	private HashMap<String, ContractInfo> contactsInfo;
	private List<String> nameList;
	
	
	
	//menu page
	private ViewPager vp_menu_ad;
	private ImageView[] mImageViews; // װ

	private GridView gv_m;
	
	
	/**
	 * �ϴε�һ���ɼ�Ԫ�أ����ڹ���ʱ��¼��ʶ��
	 */
	private int lastFirstVisibleItem = -1;
	/**
	 * ����ת����ƴ������
	 */
	private CharacterParser characterParser;
	private List<GroupMemberBean> SourceDateList;
	
	private PinyinComparator pinyinComparator;
	
	//---------------------------------------------------
	private List<SingleRecord> recordsList;
	
	// control timer and thread
	private boolean m_running = true;
//	private SharedPreferences mySharedPreferences;
	private HashMap<String, SoftReference<Bitmap>> imageCache;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		SharedPreferences sharedPreferences= getSharedPreferences(Variable.SHARE_PRE_NAME, 
				Activity.MODE_PRIVATE); 
				// ʹ��getString�������value��ע���2��������value��Ĭ��ֵ 
				String login =sharedPreferences.getString("login", ""); 
				String phone =sharedPreferences.getString("phone", ""); 
		if(!login.equals("true"))
		{
			Intent it = new Intent(Container.this,Login.class);
			startActivity(it);
		}
		
		TelApplication.init(this);
		
		
		
		
//		mySharedPreferences = this.getSharedPreferences(Variable.SHARE_PRE_NAME, Activity.MODE_PRIVATE);
		initView();
		
		TelApplication.add(this);
//		TelApplication app = (TelApplication)getApplicationContext();
//		app.add(this);
		
	}

	
	private void initView() {
		
		this.imageCache = new HashMap<String, SoftReference<Bitmap>>();  
		
		this.tv_title.setText(R.string.company_name);
		this.setLinearView(R.layout.container);
		im_call = (TextView)findViewById(R.id.tv_dial);
		im_record = (TextView)findViewById(R.id.tv_contact);
		im_contacts = (TextView)findViewById(R.id.tv_recmd);
		im_meun = (TextView)findViewById(R.id.tv_account);
//		ll_call_pad = (RelativeLayout)findViewById(R.id.ll_call_pad);
		im_call.setOnClickListener(this);
		this.im_contacts.setOnClickListener(this);
		this.im_record.setOnClickListener(this);
		this.im_meun.setOnClickListener(this);


		viewPager = (ViewPager)findViewById(R.id.vp_c_pager);
		
		views = new ArrayList();
		LayoutInflater inflater = getLayoutInflater();		
		View telView = inflater.inflate(R.layout.tel, null);		
		views.add(telView);	
		initTelView(telView);
		
		View contactsView = inflater.inflate(R.layout.activity_add_friends, null);
		views.add(contactsView);
		initContactsView(contactsView);
		View recommendview = inflater.inflate(R.layout.fragment_recommend, null);
		views.add(recommendview);		
		View menuView = inflater.inflate(R.layout.menu, null);
		views.add(menuView);
		initMenuview(menuView);
		viewPager.setAdapter(new ContainerAdapter(views));
		viewPager.setOnPageChangeListener(this);
		
		String ts = this.getString(R.string.company_name);
		setCurrentPager(0,ts,R.drawable.call_press);
		

		recordsList = Utils.getContacts(this);
		
	}
	
	private void initTelView(View telView) {
		
		ll_numPad = (LinearLayout)telView.findViewById(R.id.ll_tel_numpad);
		vp_t_ad = (ViewPager)telView.findViewById(R.id.vp_t_ad);
		this.mImageViews = new ImageView[3];
		int resIds[]= {R.drawable.ad1,R.drawable.ad2,R.drawable.ad3,R.drawable.ad20};
		for(int i=0;i<mImageViews.length;i++)
		{
			mImageViews[i] = new ImageView(this);
			mImageViews[i].setImageResource(resIds[i]);
		}
		vp_t_ad.setAdapter(new MenuAdAdapter(mImageViews));
		
		ArrayList<ImageView> ivList = new ArrayList<ImageView>();
		for(int i=0;;i++)
		{
			String fileName = QueryInfo.ALBUM_PATH+"ad1"+String.valueOf(i)+".jpg";
			File f = new File(fileName);
			if(!f.exists())
			{
				break;
			}
			
			Bitmap bmp = BitmapFactory.decodeByteArray(Utils.decodeBitmap(fileName), 0, Utils.decodeBitmap(fileName).length);  
            imageCache.put(fileName, new SoftReference<Bitmap>(bmp));  

//			Bitmap bmp = BitmapFactory.decodeFile(fileName, null);
			ImageView iv = new ImageView(this);
			iv.setImageBitmap(bmp);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
			iv.setScaleType(ScaleType.FIT_XY);
			ivList.add(iv);
		}
		if(ivList.size()>0)
		{
			vp_t_ad.setAdapter(new BitmapLoadAdapter(ivList));
		}
		vp_t_ad.setCurrentItem(1);
		if(ivList.size()>0)
		{
			this.scrollPageViewer(vp_t_ad, ivList.size());
		}
		else
		{
			this.scrollPageViewer(vp_t_ad, mImageViews.length);
		}
		
		
		
		num_1 = (ImageButton)telView.findViewById(R.id.ib_num_1);
		num_2 = (ImageButton)telView.findViewById(R.id.ib_num_2);
		num_3 = (ImageButton)telView.findViewById(R.id.ib_num_3);
		num_4 = (ImageButton)telView.findViewById(R.id.ib_num_4);
		num_5 = (ImageButton)telView.findViewById(R.id.ib_num_5);
		num_6 = (ImageButton)telView.findViewById(R.id.ib_num_6);
		num_7 = (ImageButton)telView.findViewById(R.id.ib_num_7);
		num_8 = (ImageButton)telView.findViewById(R.id.ib_num_8);
		num_9 = (ImageButton)telView.findViewById(R.id.ib_num_9);
		num_0 = (ImageButton)telView.findViewById(R.id.ib_num_0);
		num_j = (ImageButton)telView.findViewById(R.id.ib_num_j);
		num_x = (ImageButton)telView.findViewById(R.id.ib_num_x);
		
		TelClickListener t = new TelClickListener();
		
		num_1.setOnClickListener(t);
		num_2.setOnClickListener(t);
		num_3.setOnClickListener(t);
		num_4.setOnClickListener(t);
		num_5.setOnClickListener(t);
		num_6.setOnClickListener(t);
		num_7.setOnClickListener(t);
		num_8.setOnClickListener(t);
		num_9.setOnClickListener(t);
		num_0.setOnClickListener(t);
		num_j.setOnClickListener(t);
		num_x.setOnClickListener(t);
		
		TouchListener tl = new TouchListener();
		num_1.setOnTouchListener(tl);
		num_2.setOnTouchListener(tl);
		num_3.setOnTouchListener(tl);
		num_4.setOnTouchListener(tl);
		num_5.setOnTouchListener(tl);
		num_6.setOnTouchListener(tl);
		num_7.setOnTouchListener(tl);
		num_8.setOnTouchListener(tl);
		num_9.setOnTouchListener(tl);
		num_0.setOnTouchListener(tl);
		num_j.setOnTouchListener(tl);
		num_x.setOnTouchListener(tl);
		
		
		
		
	}
	
	class TouchListener implements OnTouchListener
	{

		@Override
		public boolean onTouch(View v, MotionEvent arg1) {
			ImageButton ib = (ImageButton)v;
			ib.setBackgroundResource(0);
			if(MotionEvent.ACTION_DOWN== arg1.getAction())
			{
				switch (v.getId())
				{
				case R.id.ib_num_0:((ImageButton)v).setBackgroundResource(R.drawable.num_00);				
					break;
				case R.id.ib_num_1:((ImageButton)v).setBackgroundResource(R.drawable.num_10);
					break;
				case R.id.ib_num_2:((ImageButton)v).setBackgroundResource(R.drawable.num_20);
					break;
				case R.id.ib_num_3:((ImageButton)v).setBackgroundResource(R.drawable.num_30);
					break;
				case R.id.ib_num_4:((ImageButton)v).setBackgroundResource(R.drawable.num_40);
					break;
				case R.id.ib_num_5:((ImageButton)v).setBackgroundResource(R.drawable.num_50);
					break;
				case R.id.ib_num_6:((ImageButton)v).setBackgroundResource(R.drawable.num_60);
					break;
				case R.id.ib_num_7:((ImageButton)v).setBackgroundResource(R.drawable.num_70);
					break;
				case R.id.ib_num_8:((ImageButton)v).setBackgroundResource(R.drawable.num_80);
					break;
				case R.id.ib_num_9:((ImageButton)v).setBackgroundResource(R.drawable.num_90);
					break;
				case R.id.ib_num_j:((ImageButton)v).setBackgroundResource(R.drawable.num_j0);
					break;
				case R.id.ib_num_x:((ImageButton)v).setBackgroundResource(R.drawable.num_x0);
					break;
					default:
						break;
				}
			}
			else
			{
				switch (v.getId())
				{
				case R.id.ib_num_0:((ImageButton)v).setBackgroundResource(R.drawable.num_09);
					break;
				case R.id.ib_num_1:((ImageButton)v).setBackgroundResource(R.drawable.num_19);
					break;
				case R.id.ib_num_2:((ImageButton)v).setBackgroundResource(R.drawable.num_29);
					break;
				case R.id.ib_num_3:((ImageButton)v).setBackgroundResource(R.drawable.num_39);
					break;
				case R.id.ib_num_4:((ImageButton)v).setBackgroundResource(R.drawable.num_49);
					break;
				case R.id.ib_num_5:((ImageButton)v).setBackgroundResource(R.drawable.num_59);
					break;
				case R.id.ib_num_6:((ImageButton)v).setBackgroundResource(R.drawable.num_69);
					break;
				case R.id.ib_num_7:((ImageButton)v).setBackgroundResource(R.drawable.num_79);
					break;
				case R.id.ib_num_8:((ImageButton)v).setBackgroundResource(R.drawable.num_89);
					break;
				case R.id.ib_num_9:((ImageButton)v).setBackgroundResource(R.drawable.num_99);
					break;
				case R.id.ib_num_j:((ImageButton)v).setBackgroundResource(R.drawable.num_j9);
					break;
				case R.id.ib_num_x:((ImageButton)v).setBackgroundResource(R.drawable.num_x9);
					break;
					default:
						break;
				}
			}
			
			
			
			return false;
		}
		
	}
	class TelClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			String tag = (String)v.getTag();
			String current = Container.this.tv_title.getText().toString();
			if(current.contains("讯"))
			{
				tv_title.setText(tag);

				//hidekeyboard  tel  delnumber three imageViews on the bottom(default gone)				

//				ll_call_pad.setVisibility(View.VISIBLE);

			}
			else
			{
				tv_title.setText(current+tag);
			}
	
		}
		
	}
	
	private void initMenuview(View menuView) {
		vp_menu_ad = (ViewPager) menuView.findViewById(R.id.vp_m_ad); 
		this.mImageViews = new ImageView[3];
		int resIds[]= {R.drawable.ad30,R.drawable.ad32,R.drawable.ad20};
		for(int i=0;i<mImageViews.length;i++)
		{
			mImageViews[i] = new ImageView(this);
			mImageViews[i].setImageResource(resIds[i]);
		}
		vp_menu_ad.setAdapter(new MenuAdAdapter(mImageViews));
		
//		menuAdIndex = 0;
		
		ArrayList<ImageView> ivList = new ArrayList<ImageView>();
		for(int i=0;;i++)
		{
			String fileName = QueryInfo.ALBUM_PATH+"ad3"+String.valueOf(i)+".jpg";
			File f = new File(fileName);
			if(!f.exists())
			{
				break;
			}
			
			Bitmap bmp = BitmapFactory.decodeByteArray(Utils.decodeBitmap(fileName), 0, Utils.decodeBitmap(fileName).length);  
            imageCache.put(fileName, new SoftReference<Bitmap>(bmp));  

			
//			Bitmap bmp = BitmapFactory.decodeFile(fileName, null);
			ImageView iv = new ImageView(this);
			iv.setImageBitmap(bmp);
			ivList.add(iv);
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));
		}
		if(ivList.size()>0)
		{
			vp_menu_ad.setAdapter(new BitmapLoadAdapter(ivList));
		}
		vp_menu_ad.setCurrentItem(1);
		if(ivList.size()>0)
		{
			this.scrollPageViewer(vp_menu_ad, ivList.size());
		}
		else
		{
			this.scrollPageViewer(vp_menu_ad,mImageViews.length );
		}
			
		gv_m = (GridView)menuView.findViewById(R.id.gv_m);
		int strResId[] = {R.string.gd_1,R.string.gd_2,R.string.gd_3,
				R.string.gd_4,R.string.gd_5,R.string.gd_6,R.string.gd_7,R.string.gd_8};
		
		int picResId[] = {R.drawable.set_nearshop,R.drawable.set_earn_calls,R.drawable.set_balance,R.drawable.set_lottery,
				R.drawable.set_shop,R.drawable.set_find,R.drawable.set_hotcall,R.drawable.set_moresettings};
		
		gv_m.setAdapter(new MenuGridviewAdapter(Container.this,strResId,picResId));
		
		gv_m.setOnItemClickListener(this);
		
		//set scroll ad string
		MarqueeButton mb= (MarqueeButton)menuView.findViewById(R.id.marquee_button);
		
//		TextView tv_ad = (TextView)menuView.findViewById(R.id.tv_m_ad);
		SharedPreferences mySharedPreferences = this.getSharedPreferences(Variable.SHARE_PRE_NAME, Activity.MODE_PRIVATE);
		if(mySharedPreferences.contains(Variable.FILED_MARQUEEN_STR))
		{
			mb.setText(mySharedPreferences.getString(Variable.FILED_MARQUEEN_STR, ""));
		}

		
		
	}

	//init contacts view
	private void initContactsView(View contactsView) {
		Utils.getPhoneContracts(this);
		titleLayout = (LinearLayout) contactsView.findViewById(R.id.title_layout);
		title = (TextView) contactsView.findViewById(R.id.title_layout_catalog);
		tvNofriends = (TextView) contactsView
				.findViewById(R.id.title_layout_no_friends);
		// ʵ��������תƴ����
		characterParser = CharacterParser.getInstance();

		pinyinComparator = new PinyinComparator();

		sideBar = (SideBar) contactsView.findViewById(R.id.sidrbar);
		dialog = (TextView) contactsView.findViewById(R.id.dialog);
		sideBar.setTextView(dialog);

		// �����Ҳഥ������
		sideBar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String s) {
				// ����ĸ�״γ��ֵ�λ��
				int position = adapter.getPositionForSection(s.charAt(0));
				if (position != -1) {
					sortListView.setSelection(position);
				}

			}
		});

		sortListView = (ListView) contactsView.findViewById(R.id.country_lvcountry);
		sortListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// ����Ҫ����adapter.getItem(position)����ȡ��ǰposition����Ӧ�Ķ���
//				String phoneNumber= 
//				
				TextView v = (TextView) view.findViewById(R.id.title);
				String name = (String) v.getText();
				Log.d("Name",name);
				if(contactsInfo.containsKey(name))
				{
					String phoneNumber = contactsInfo.get(name).phone;
					showCallConfirmDialog(phoneNumber,name);
					Log.d(TAG,phoneNumber);
				}
				
			}
		});


		nameList = new ArrayList();
		this.contactsInfo = Utils.getAllContracts(this, nameList);
		
		SourceDateList = filledData(nameList);

		// ����a-z��������Դ����
		if(nameList.size()>0)
		{
			Collections.sort(SourceDateList, pinyinComparator);
			adapter = new SortGroupMemberAdapter(this, SourceDateList);
			adapter.setMapContracts(this.contactsInfo);
			sortListView.setAdapter(adapter);
			sortListView.setOnScrollListener(new OnScrollListener() {
				@Override
				public void onScrollStateChanged(AbsListView view,
						int scrollState) {
				}

				@Override
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					int section = getSectionForPosition(firstVisibleItem);
					int nextSection = getSectionForPosition(firstVisibleItem + 1);
					int nextSecPosition = getPositionForSection(+nextSection);
					if (firstVisibleItem != lastFirstVisibleItem) {
						MarginLayoutParams params = (MarginLayoutParams) titleLayout
								.getLayoutParams();
						params.topMargin = 0;
						titleLayout.setLayoutParams(params);
						title.setText(SourceDateList.get(
								getPositionForSection(section))
								.getSortLetters());
					}
					if (nextSecPosition == firstVisibleItem + 1) {
						View childView = view.getChildAt(0);
						if (childView != null) {
							int titleHeight = titleLayout.getHeight();
							int bottom = childView.getBottom();
							MarginLayoutParams params = (MarginLayoutParams) titleLayout
									.getLayoutParams();
							if (bottom < titleHeight) {
								float pushedDistance = bottom - titleHeight;
								params.topMargin = (int) pushedDistance;
								titleLayout.setLayoutParams(params);
							} else {
								if (params.topMargin != 0) {
									params.topMargin = 0;
									titleLayout.setLayoutParams(params);
								}
							}
						}
					}
					lastFirstVisibleItem = firstVisibleItem;
				}
			});
		}
		mClearEditText = (ClearEditText) contactsView.findViewById(R.id.filter_edit);

		// �������������ֵ�ĸı�����������
		mClearEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// ���ʱ����Ҫ��ѹЧ�� �Ͱ������ص�
				titleLayout.setVisibility(View.GONE);
				// ������������ֵΪ�գ�����Ϊԭ�����б�����Ϊ���������б�
				filterData(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	protected void showCallConfirmDialog(final String phoneNumber,final String name) {
		
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		View v = inflater.inflate(R.layout.dialog_tel, null);
		builder.setView(v);
		Button bt_tel = (Button)v.findViewById(R.id.bt_dt_tel);
		Button bt_cancel = (Button)v.findViewById(R.id.bt_dt_cancel);
		
		final Dialog dialog = builder.show();
		
		bt_tel.setOnClickListener(this);
		bt_cancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				dialog.dismiss();
			}
			
		});
		
		bt_tel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(Container.this,Calling.class);
				it.putExtra("number", phoneNumber);
				it.putExtra("name", name);
				Container.this.startActivity(it);
				dialog.dismiss();
			}
			
		});
	}

	private List<GroupMemberBean> filledData(List<String> nameList)
	{
		List<GroupMemberBean> mSortList = new ArrayList<GroupMemberBean>();

		for (int i = 0; i < nameList.size(); i++) {
			GroupMemberBean sortModel = new GroupMemberBean();
			sortModel.setName(nameList.get(i));
			// ����ת����ƴ��
			String pinyin = characterParser.getSelling(nameList.get(i));
			String sortString = pinyin.substring(0, 1).toUpperCase();

			// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
			if (sortString.matches("[A-Z]")) {
				sortModel.setSortLetters(sortString.toUpperCase());
			} else {
				sortModel.setSortLetters("#");
			}

			mSortList.add(sortModel);
		}
		return mSortList;
	}
		
	/**
	 * ����������е�ֵ���������ݲ�����ListView
	 * 
	 * @param filterStr
	 */
	private void filterData(String filterStr) {
		List<GroupMemberBean> filterDateList = new ArrayList<GroupMemberBean>();

		if (TextUtils.isEmpty(filterStr)) {
			filterDateList = SourceDateList;
			tvNofriends.setVisibility(View.GONE);
		} else {
			filterDateList.clear();
			for (GroupMemberBean sortModel : SourceDateList) {
				String name = sortModel.getName();
				if (name.indexOf(filterStr.toString()) != -1
						|| characterParser.getSelling(name).startsWith(
								filterStr.toString())) {
					filterDateList.add(sortModel);
				}
			}
		}

		// ����a-z��������
		Collections.sort(filterDateList, pinyinComparator);
		adapter.updateListView(filterDateList);
		if (filterDateList.size() == 0) {
			tvNofriends.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	public int getSectionForPosition(int position) {
		return SourceDateList.get(position).getSortLetters().charAt(0);
	}

	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < SourceDateList.size(); i++) {
			String sortStr = SourceDateList.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}
		return -1;
	}
	
	
	private void setCurrentPager(int num,String title,int picId)
	{
//		this.im_call.setBackgroundResource(R.drawable.call);
//		this.im_contacts.setBackgroundResource(R.drawable.contact);
//		this.im_record.setBackgroundResource(R.drawable.record);
//		this.im_meun.setBackgroundResource(R.drawable.menu);
		
		tv_title.setText(title);
		viewPager.setCurrentItem(num);
		
//		btn.setImageResource(picId);
	}

	@Override
	public void onClick(View arg0) {
		switch(arg0.getId())
		{
		case R.id.tv_dial:
			super.showTitle();
			String s = this.getString(R.string.company_name);
			setCurrentPager(0,s,R.drawable.call_press);
			break;
		
		case R.id.tv_contact:
			super.showTitle();
			setCurrentPager(1,"通话记录",R.drawable.record_press);
			break;
		case R.id.tv_recmd:
			super.showTitle();
			setCurrentPager(2,"通讯录",R.drawable.contact_press);
			break;
		case R.id.tv_account:
			super.hideTitle();
			setCurrentPager(3,"",R.drawable.menu_press);
			break;
			default:
				break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		m_running = false;
		super.onDestroy();
//		TelApplication app = ((TelApplication)getApplicationContext());
        TelApplication.remove(this);
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		String url="";
		switch(arg2)
		{
		case 0:
//			url = "http://121.40.100.250:88/reg/";
//			startWebView(url);
			Intent it = new Intent(Container.this,Recharge.class);
			startActivity(it);
			break;
		case 1:
//			Uri uri = Uri.parse(Variable.MAIN_PAGE_URL);
//			Intent it1 = new Intent(Intent.ACTION_VIEW,uri);
//			startActivity(it1);
			break;
		case 2:
			Intent it2 = new Intent(Container.this,QueryBalance.class);
			startActivity(it2);		
			break;
		case 3:
			Intent it3 = new Intent(Container.this,Lottery.class);
			startActivity(it3);
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
//			callCustomService();
			break;
		case 7:
			Intent it7 = new Intent(this,Setting.class);
			startActivity(it7);
			break;
			default:
				break;
		}
		
	}



	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		switch(arg0)
		{
		case 0:
			super.showTitle();
			String s = this.getString(R.string.company_name);
			setCurrentPager(0,s,R.drawable.call_press);
			break;
		case 1:
			super.showTitle();
			setCurrentPager(1,"通话记录",R.drawable.record_press);
			break;
		case 2:
			super.showTitle();
			setCurrentPager(2,"通讯录",R.drawable.contact_press);
			break;
		case 3:
			super.hideTitle();
			setCurrentPager(3,"",R.drawable.menu_press);
			break;
		default:
			break;
		}
		
	}
	
	private void scrollPageViewer(final ViewPager v, final int pages)
	{
		new Handler() { 
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
		    
			    int current = vp_t_ad.getCurrentItem();
			    v.setCurrentItem((current+1)%pages);
			    
//	            Log.d(TAG, "change ad index:"+menuAdIndex);
	            if(m_running)
	            {
	            	sendEmptyMessageDelayed(0, 3000);
	            }
	            
			}
		}.sendEmptyMessageDelayed(0, 3000);	
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
//		this.getWindow().setBackgroundDrawableResource(0);
		super.onResume();
	}
	
	
}
