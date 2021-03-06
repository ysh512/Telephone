package com.example.sortlistview;

import java.util.HashMap;
import java.util.List;

import com.dner.fast.R;
import com.example.telphone.property.ContractInfo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class SortGroupMemberAdapter extends BaseAdapter implements SectionIndexer {
	private List<GroupMemberBean> list = null;
	private Context mContext;
	private HashMap<String,ContractInfo> mapContracts;

	private static int[] res = new int[]{R.drawable.ic11,R.drawable.ic22,R.drawable.ic33,R.drawable.ic44,R.drawable.ic55,R.drawable.ic66,R.drawable.ic77};
	
	public SortGroupMemberAdapter(Context mContext, List<GroupMemberBean> list) {
		this.mContext = mContext;
		this.list = list;
	}
	
	public void setMapContracts(HashMap<String,ContractInfo> contracts)
	{
		this.mapContracts = contracts;
	}
	/**
	 * ��ListView���ݷ����仯ʱ,���ô˷���������ListView
	 * 
	 * @param list
	 */
	public void updateListView(List<GroupMemberBean> list) {
		this.list = list;
		notifyDataSetChanged();
	}

	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View view, ViewGroup arg2) {
		ViewHolder viewHolder = null;
		final GroupMemberBean mContent = list.get(position);
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.activity_group_member_item, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.title);
			viewHolder.tvLetter = (TextView) view.findViewById(R.id.catalog);
			viewHolder.tvPhoneNumber = (TextView)view.findViewById(R.id.tv_phone_number);
			viewHolder.ivCircle = (ImageView)view.findViewById(R.id.iv_circle);
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}

		// ����position��ȡ���������ĸ��Char asciiֵ
		int section = getSectionForPosition(position);

		// �����ǰλ�õ��ڸ÷�������ĸ��Char��λ�� ������Ϊ�ǵ�һ�γ���
		if (position == getPositionForSection(section)) {
			viewHolder.tvLetter.setVisibility(View.VISIBLE);
			viewHolder.tvLetter.setText(mContent.getSortLetters());
		} else {
			viewHolder.tvLetter.setVisibility(View.GONE);
		}

		String name = this.list.get(position).getName();
		viewHolder.tvTitle.setText(name);
		if(null!=this.mapContracts && this.mapContracts.containsKey(name))
		{
			viewHolder.tvPhoneNumber.setText(this.mapContracts.get(name).phone);
		}
		
		viewHolder.ivCircle.setImageResource(res[position%res.length]);

		return view;

	}

	final static class ViewHolder {
		TextView tvLetter;
		TextView tvTitle;
		
		TextView tvPhoneNumber;
		
		ImageView ivCircle;
	}

	/**
	 * ����ListView�ĵ�ǰλ�û�ȡ���������ĸ��Char asciiֵ
	 */
	public int getSectionForPosition(int position) {
		return list.get(position).getSortLetters().charAt(0);
	}

	/**
	 * ���ݷ��������ĸ��Char asciiֵ��ȡ���һ�γ��ָ�����ĸ��λ��
	 */
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			String sortStr = list.get(i).getSortLetters();
			char firstChar = sortStr.toUpperCase().charAt(0);
			if (firstChar == section) {
				return i;
			}
		}

		return -1;
	}

	/**
	 * ��ȡӢ�ĵ�����ĸ����Ӣ����ĸ��#���档
	 * 
	 * @param str
	 * @return
	 */
	private String getAlpha(String str) {
		String sortStr = str.trim().substring(0, 1).toUpperCase();
		// ������ʽ���ж�����ĸ�Ƿ���Ӣ����ĸ
		if (sortStr.matches("[A-Z]")) {
			return sortStr;
		} else {
			return "#";
		}
	}

	@Override
	public Object[] getSections() {
		return null;
	}
}