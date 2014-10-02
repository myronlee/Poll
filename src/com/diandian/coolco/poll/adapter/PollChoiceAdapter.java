package com.diandian.coolco.poll.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;

import com.diandian.coolco.poll.R;

public class PollChoiceAdapter extends BaseAdapter{
	private Context mContext;
	private List<String> mStrs;
	private int checkedPosition = -1;

	public PollChoiceAdapter(Context context, List<String> strs) {
		super();
		mContext = context;
		mStrs = strs;
	}

	@Override
	public int getCount() {
		return mStrs.size();
	}

	@Override
	public Object getItem(int position) {
		return mStrs.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.list_item_poll_choice, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.radioButton = (RadioButton) convertView.findViewById(R.id.choice);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.radioButton.setText(mStrs.get(position));
		viewHolder.radioButton.setChecked(position == checkedPosition ? true : false);
		return convertView;
	}
	
	public int getCheckedPosition() {
		return checkedPosition;
	}

	public void setCheckedPosition(int checkedPosition) {
		this.checkedPosition = checkedPosition;
	}

	class ViewHolder {
		public RadioButton radioButton;
	}
}
