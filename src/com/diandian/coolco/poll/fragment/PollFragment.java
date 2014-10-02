package com.diandian.coolco.poll.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.diandian.coolco.poll.R;
import com.diandian.coolco.poll.fragment.PollChoiceFragment.onPollListener;

public class PollFragment extends Fragment{
	private PollChoiceFragment pollChoiceFragment;
	private PollResultFragment pollResultFragment;
	private onPollListener onPollListener = new onPollListener() {
		
		@Override
		public void onPoll(int position) {
//			getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).hide(pollChoiceFragment).commit();
//			getFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in, R.anim.fade_out).show(pollResultFragment).commit();
//			getFragmentManager().beginTransaction().setCustomAnimations(R.anim.right_in, R.anim.left_out).replace(R.id.bottom_container, pollResultFragment).commit();
			FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.setCustomAnimations(0, R.anim.left_out, R.anim.left_in, R.anim.nothing);
			transaction.addToBackStack(null);
			transaction.replace(R.id.bottom_container, pollResultFragment).commit();
			
		}
	};

	public PollFragment() {
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_poll, container, false);
		
		pollChoiceFragment = new PollChoiceFragment();
		pollResultFragment = new PollResultFragment();
		getFragmentManager().beginTransaction().add(R.id.bottom_container, pollChoiceFragment).commit();
//		getFragmentManager().beginTransaction().add(R.id.bottom_container, pollResultFragment).commit();
//		getFragmentManager().beginTransaction().hide(pollResultFragment).commit();
//		getFragmentManager().beginTransaction().show(pollChoiceFragment).commit();
		
		pollChoiceFragment.setOnPollListener(onPollListener );
		
		return rootView;
	}

}
