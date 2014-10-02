package com.diandian.coolco.poll.adapter;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.diandian.coolco.poll.R;
import com.diandian.coolco.poll.model.Poll;
import com.diandian.coolco.poll.model.TmpMyPoll;

public class PollListViewHolder extends ViewHolder<Poll>{
	private TextView questionTextView;
	private TextView myChoiceTextView;
	private TextView votesNumTextView;
	private TextView createPersonTextView;
	private TextView createDateTextView;
	private int emphasizeColor;
	
	public PollListViewHolder(View v){
		emphasizeColor = v.getResources().getColor(R.color.main);
		questionTextView = (TextView) v.findViewById(R.id.question);
		myChoiceTextView = (TextView) v.findViewById(R.id.my_choice);
		votesNumTextView = (TextView) v.findViewById(R.id.votes_num);
		createPersonTextView = (TextView) v.findViewById(R.id.create_person);
		createDateTextView = (TextView) v.findViewById(R.id.create_date);
	}

	@Override
	public void setItem(Poll item) {
		questionTextView.setText(item.getTitle());
		questionTextView.setSelected(true);
//		myChoiceTextView.setText(highlight(item.myChoice, 4, item.myChoice.length()));
//		myChoiceTextView.setSelected(true);
//		String voteNumString = item.votesNum+"人投票";
//		votesNumTextView.setText(highlight(voteNumString, 0, voteNumString.length()-3));
		String createPersonString = item.getCreateUserObj().getUsername()+"发起";
		createPersonTextView.setText(highlight(createPersonString, 0, createPersonString.length()-2));
		createDateTextView.setText(item.getCreateDateTime());
	}
	
	public Spannable highlight(String string, int start,int end){  
        SpannableStringBuilder spannable = new SpannableStringBuilder(string);
        ForegroundColorSpan span=new ForegroundColorSpan(emphasizeColor);  
        spannable.setSpan(span, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);  
        return spannable;
    } 

}
