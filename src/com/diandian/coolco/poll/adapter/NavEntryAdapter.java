package com.diandian.coolco.poll.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.diandian.coolco.poll.R;

public class NavEntryAdapter extends BaseAdapter{
	private Context mContext;
	private List<Integer> mImgs;
	private List<String> mStrs;
	private int selectedPosition = -1;

	public void setSelectedPosition(int selectedPosition) {
		this.selectedPosition = selectedPosition;
	}

	public NavEntryAdapter(Context context, List<Integer> imgs, List<String> strs) {
		super();
		mContext = context;
		mImgs = imgs;
		mStrs = strs;
	}

	@Override
	public int getCount() {
		return mImgs.size();
	}

	@Override
	public Object getItem(int position) {
		return mImgs.get(position);
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
			convertView = inflater.inflate(R.layout.list_item_nav_entry, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
			viewHolder.txt = (TextView) convertView.findViewById(R.id.txt);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.img.setImageResource(mImgs.get(position));
		viewHolder.txt.setText(mStrs.get(position));
		viewHolder.txt.setSelected(position == selectedPosition);
		return convertView;
	}

	class ViewHolder {
		public ImageView img;
		public TextView txt;
	}
}
