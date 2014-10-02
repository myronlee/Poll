package com.diandian.coolco.poll.fragment;

import java.util.ArrayList;
import java.util.List;

import com.diandian.coolco.poll.R;
import com.diandian.coolco.poll.adapter.PollChoiceAdapter;
import com.diandian.coolco.poll.util.Util;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class PollChoiceFragment extends Fragment{
	private onPollListener onPollListener;

	// private AnimationSet shakeAnimationSet;
	private Animation shakeAnimation;
	private Button pollButton;
	private ListView choiceListView;
	private PollChoiceAdapter adapter;
	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			adapter.setCheckedPosition(position-1);
			adapter.notifyDataSetChanged();
			
			if (!pollButton.isSelected()) {
				pollButton.setSelected(true);
			}
		}
	};
	private OnClickListener onClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if (pollButton.isSelected()) {
				if (onPollListener != null) {
					onPollListener.onPoll(adapter.getCheckedPosition());
				}
//				getFragmentManager().beginTransaction().add(R.id.bottom_container, pollResultFragment).commit();
//				getFragmentManager().beginTransaction().hide(PollFragment.this).commit();
			} else {
				choiceListView.startAnimation(animationSet);
				Util.showShortToast(getActivity(), "请先选择");
			}
		}
	};

	private AnimationSet animationSet;
	
	public PollChoiceFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_poll_choice, container, false);
		
		choiceListView = (ListView) rootView.findViewById(R.id.choice_list);
		List<String> choiceStrings = new ArrayList<String>();
		choiceStrings.add("幸福");
		choiceStrings.add("不幸福");
		choiceStrings.add("不知道");
		adapter = new PollChoiceAdapter(getActivity(), choiceStrings);
		View headerView = inflater.inflate(R.layout.divider_horizonal, null);
		View footerView = inflater.inflate(R.layout.divider_horizonal, null);
		choiceListView.addHeaderView(headerView);
		choiceListView.addFooterView(footerView);
		choiceListView.setAdapter(adapter);
		choiceListView.setOnItemClickListener(onItemClickListener);
		
		//I want to make user know the button is unclickable, and I also want to capture the click event
		//So, I use selected state instead of enabled state
		pollButton = (Button) rootView.findViewById(R.id.poll);
		pollButton.setSelected(false);
		pollButton.setOnClickListener(onClickListener );
		
		// shakeAnimationSet = new AnimationSet(false);
        // animations should be applied on the finish line
		// shakeAnimationSet.setFillAfter(true);
		// shakeAnimationSet.setInterpolator(new AccelerateDecelerateInterpolator());
		int offset = Util.dp2px(getActivity(), 12);
		animationSet = new AnimationSet(true);
		animationSet.setInterpolator(new AccelerateDecelerateInterpolator());
		TranslateAnimation translateAnimation1 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		translateAnimation1.setStartOffset(0);
		translateAnimation1.setDuration(35);
		TranslateAnimation translateAnimation2 = new TranslateAnimation(Animation.ABSOLUTE, -offset, Animation.ABSOLUTE, offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		translateAnimation2.setStartOffset(35);
		translateAnimation2.setDuration(70);
		TranslateAnimation translateAnimation3 = new TranslateAnimation(Animation.ABSOLUTE, offset, Animation.ABSOLUTE, -offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		translateAnimation3.setStartOffset(105);
		translateAnimation3.setDuration(70);
		TranslateAnimation translateAnimation11 = new TranslateAnimation(Animation.ABSOLUTE, -offset, Animation.ABSOLUTE, offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		translateAnimation11.setStartOffset(170);
		translateAnimation11.setDuration(70);
		TranslateAnimation translateAnimation22 = new TranslateAnimation(Animation.ABSOLUTE, offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		translateAnimation22.setStartOffset(240);
		translateAnimation22.setDuration(35);
//		TranslateAnimation translateAnimation33 = new TranslateAnimation(Animation.ABSOLUTE, offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
//		translateAnimation33.setStartOffset(0);
//		TranslateAnimation translateAnimation111 = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
//		translateAnimation111.setStartOffset(0);
//		TranslateAnimation translateAnimation222 = new TranslateAnimation(Animation.ABSOLUTE, -offset, Animation.ABSOLUTE, offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
//		translateAnimation222.setStartOffset(0);
//		TranslateAnimation translateAnimation333 = new TranslateAnimation(Animation.ABSOLUTE, offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
//		translateAnimation333.setStartOffset(0);
		animationSet.addAnimation(translateAnimation1);
		animationSet.addAnimation(translateAnimation2);
		animationSet.addAnimation(translateAnimation3);
		animationSet.addAnimation(translateAnimation11);
		animationSet.addAnimation(translateAnimation22);
//		animationSet.addAnimation(translateAnimation33);
//		animationSet.addAnimation(translateAnimation111);
//		animationSet.addAnimation(translateAnimation222);
//		animationSet.addAnimation(translateAnimation333);
//		animationSet.setDuration(50);
//		animationSet.setRepeatCount(5);
//		animationSet.setRepeatMode(Animation.RESTART);
		
		shakeAnimation = new TranslateAnimation(Animation.ABSOLUTE, -offset, Animation.ABSOLUTE, offset, Animation.ABSOLUTE, 0, Animation.ABSOLUTE, 0);
		shakeAnimation.setDuration(50);
		shakeAnimation.setRepeatMode(Animation.REVERSE);
		shakeAnimation.setRepeatCount(5);
		shakeAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
		//shakeAnimationSet.addAnimation(shakeAnimation);
//		shakeAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
		
		return rootView;
	}
	
	public void setOnPollListener(onPollListener onPollListener) {
		this.onPollListener = onPollListener;
	}
	
	public interface onPollListener{
		public void onPoll(int position);
	}
}
