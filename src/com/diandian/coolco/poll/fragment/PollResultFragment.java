package com.diandian.coolco.poll.fragment;

import java.util.ArrayList;
import java.util.Arrays;

import com.diandian.coolco.poll.R;
import com.diandian.coolco.poll.adapter.PollResultAdapter;
import com.diandian.coolco.poll.model.TmpPollResult;
import com.diandian.coolco.poll.util.Util;
import com.diandian.coolco.poll.widget.MagicTextView;
import com.diandian.coolco.poll.widget.SwipeDismissListView;
import com.diandian.coolco.poll.widget.SwipeDismissListView.OnDismissCallback;
import com.nhaarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.nhaarman.listviewanimations.swinginadapters.prepared.SwingRightInAnimationAdapter;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.ViewGroup;
import android.widget.ListView;

public class PollResultFragment extends Fragment{
	
	private View resultBarView;
	private MagicTextView resultNumMagicTextView;
	private OnClickListener listener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
			scaleAnimation.setDuration(500);
			scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
			resultBarView.startAnimation(scaleAnimation);
			int toX = Util.dp2px(getActivity(), 200);
			TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.ABSOLUTE, toX, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
			translateAnimation.setDuration(500);
			translateAnimation.setFillEnabled(true);
			translateAnimation.setFillAfter(true);
			translateAnimation.setFillBefore(true);
			translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
			resultNumMagicTextView.startAnimation(translateAnimation);
			resultNumMagicTextView.startAnimation(52);
		}
	};

	public PollResultFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_poll_result, container, false);
		
		TmpPollResult[] pollResults = new TmpPollResult[]{new TmpPollResult("在我以前被自杀这个念头困扰的时候，博尔赫斯救了我。他曾经也认真想过自杀这件事，但后来想想，既然还有这样一个终极武器在手里，不如再多活活看，反正，最后总会死。嗯。", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不知道", 60, 0.666f),new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不知道", 60, 0.666f),new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f),new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不知道", 60, 0.666f),new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不知道", 60, 0.666f), new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f), new TmpPollResult("不知道", 60, 0.666f), new TmpPollResult("不幸福", 5, 0.50f), new TmpPollResult("幸福", 35, 0.356f)};
		final PollResultAdapter adapter = new PollResultAdapter(getActivity(), new ArrayList<TmpPollResult>(Arrays.asList(pollResults)));
		
		SwipeDismissListView pollResultListView = (SwipeDismissListView) rootView.findViewById(R.id.poll_result_list);
//		pollResultListView.setAdapter(adapter);
		
		AnimationAdapter animAdapter = new SwingRightInAnimationAdapter(adapter);
		animAdapter.setAbsListView(pollResultListView);
		pollResultListView.setAdapter(animAdapter);
		
		pollResultListView.setOnDismissCallback(new OnDismissCallback() {  
            
            @Override  
            public void onDismiss(int dismissPosition) {  
                 adapter.remove(adapter.getItem(dismissPosition));   
            }  
        });  
//		resultBarView = rootView.findViewById(R.id.result_bar);
//		resultNumMagicTextView = (MagicTextView) rootView.findViewById(R.id.result_num);
//		rootView.setOnClickListener(listener );
		return rootView;
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1, 1, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 1);
			scaleAnimation.setDuration(500);
			scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
			resultBarView.startAnimation(scaleAnimation);
			int toX = Util.dp2px(getActivity(), 200);
			TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.ABSOLUTE, toX, Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0);
			translateAnimation.setDuration(500);
			translateAnimation.setFillEnabled(true);
			translateAnimation.setFillAfter(true);
			translateAnimation.setFillBefore(true);
			translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
			resultNumMagicTextView.startAnimation(translateAnimation);
			resultNumMagicTextView.startAnimation(52);
		}
	}
}
