package com.diandian.coolco.poll.adapter;

import java.text.NumberFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.diandian.coolco.poll.R;
import com.diandian.coolco.poll.model.TmpPollResult;
import com.diandian.coolco.poll.util.Util;
import com.diandian.coolco.poll.widget.MagicTextView;

public class PollResultAdapter extends BaseAdapter{
	
	private Context mContext;
	private List<TmpPollResult> pollResults;
	private int totalLen;
	private int[] colors;
	private NumberFormat percentFormat;

	public PollResultAdapter(Context context, List<TmpPollResult> pollResults) {
		super();
		mContext = context;
		this.pollResults = pollResults;
		totalLen = context.getResources().getDisplayMetrics().widthPixels - 2*Util.dp2px(context, 12);
		int green = context.getResources().getColor(R.color.light_green);
		int blue = context.getResources().getColor(R.color.blue);
		int yellow = context.getResources().getColor(R.color.yellow);
		int qing = context.getResources().getColor(R.color.qing);
		int grey = context.getResources().getColor(R.color.grey);
		colors = new int[]{qing, yellow, grey, green, blue};
		
		percentFormat = NumberFormat.getPercentInstance();
		percentFormat.setMaximumFractionDigits(1);
		percentFormat.setMinimumFractionDigits(1);
	}

	@Override
	public int getCount() {
		return pollResults.size();
	}

	@Override
	public Object getItem(int position) {
		return pollResults.get(position);
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
			convertView = inflater.inflate(R.layout.list_item_poll_result, null);
			viewHolder = new ViewHolder();
			viewHolder.resultBar = convertView.findViewById(R.id.result_bar);
			viewHolder.choice = (TextView) convertView.findViewById(R.id.choice);
			viewHolder.resultNum = (MagicTextView) convertView.findViewById(R.id.result_num);
			viewHolder.resultFraction = (MagicTextView) convertView.findViewById(R.id.result_fraction);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		TmpPollResult pollResult = pollResults.get(position);
		viewHolder.choice.setText(pollResult.choice);
		viewHolder.choice.setSelected(true);
		viewHolder.resultNum.setText(pollResult.resultNum+"票");
		viewHolder.resultFraction.setText(percentFormat.format(pollResult.resultFraction));
		
		LayoutParams layoutParams = (LayoutParams) viewHolder.resultBar.getLayoutParams();
		layoutParams.width = (int) (totalLen*pollResult.resultFraction);
		viewHolder.resultBar.setLayoutParams(layoutParams);
		int len = colors.length;
		int color = colors[position%len];
		viewHolder.resultBar.setBackgroundColor(color);
		
		ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
		scaleAnimation.setStartOffset(600);
		scaleAnimation.setDuration(1000);
		scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		viewHolder.resultBar.startAnimation(scaleAnimation);
		
//		System.out.println("PollResultAdapter.getView()----"+position);
		
		return convertView;
	}

	class ViewHolder {
		public View resultBar;
		public TextView choice;
		public MagicTextView resultNum;
		public MagicTextView resultFraction;
	}

	public void remove(Object item) {
		// TODO Auto-generated method stub
		pollResults.remove(item);
		notifyDataSetChanged();
	}
}
