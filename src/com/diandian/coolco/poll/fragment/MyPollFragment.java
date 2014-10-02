package com.diandian.coolco.poll.fragment;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.diandian.coolco.poll.R;
import com.diandian.coolco.poll.adapter.PollListViewHolder;
import com.diandian.coolco.poll.adapter.ViewHolderAdapter;
import com.diandian.coolco.poll.db.OrmLiteDBHelper;
import com.diandian.coolco.poll.model.Poll;
import com.diandian.coolco.poll.model.TmpMyPoll;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.Animator.AnimatorListener;

public class MyPollFragment extends Fragment implements OnItemClickListener, OnClickListener{
	
	private final String TAG = getClass().getName();
	
	private View createPollView;
	private OnCreatePollListener onCreatePollListener;
	private OnPollSelectedListener onPollSelectedListener;
	
	private OrmLiteDBHelper dbHelper;

	public MyPollFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_my_poll, container, false);
//		TmpMyPoll myPoll1 = new TmpMyPoll("你快乐吗？", "你投给了快乐", 256, "张凯丽", "2014-09-12");
//		TmpMyPoll myPoll2 = new TmpMyPoll("你觉得幸福吗？", "你投给了幸福", 1337, "魏美慧", "2004-06-12");
//		TmpMyPoll myPoll3 = new TmpMyPoll("你觉得幸福吗？七宗罪：懒惰、贪婪、愤怒、暴食、色欲、傲慢、妒忌", "你投给了快乐", 256, "张凯丽", "2014-09-12");
//		TmpMyPoll myPoll4 = new TmpMyPoll("你快乐吗？", "你投给了快乐，七宗罪：懒惰、贪婪、愤怒、暴食、色欲、傲慢、妒忌", 256, "张凯丽", "2014-09-12");
//		ArrayList<TmpMyPoll> myPolls = new ArrayList<TmpMyPoll>();
//		myPolls.add(myPoll1);
//		myPolls.add(myPoll2);
//		myPolls.add(myPoll3);
//		myPolls.add(myPoll4);
		
		dbHelper = new OrmLiteDBHelper(getActivity());
		List<Poll> polls = dbHelper.findAllPoll();
		
		ListView listView = (ListView) rootView.findViewById(R.id.my_poll_list);
		ViewHolderAdapter<Poll> adapter = new ViewHolderAdapter<Poll>(getActivity(), R.layout.list_item_poll, polls, PollListViewHolder.class);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(this);
		
		createPollView = rootView.findViewById(R.id.create_poll);
		createPollView.setOnClickListener(this);
		
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//		FragmentTransaction transaction = getFragmentManager().beginTransaction();
//		transaction.setCustomAnimations(R.anim.left_in, R.anim.left_out);
//		transaction.add(R.id.container, new PollCreateFragment(), "Detail");
//		transaction.commit();
		onPollSelectedListener.onPollSelected();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			onCreatePollListener = (OnCreatePollListener) activity;
			onPollSelectedListener = (OnPollSelectedListener) activity;
		} catch (ClassCastException e) {
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.create_poll:{
			AnimatorListener listener = new Animator.AnimatorListener(){
				@Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
//                	FragmentTransaction transaction = getFragmentManager().beginTransaction();
//            		transaction.setCustomAnimations(R.anim.rise_from_bottom_right, 0, 0, R.anim.sink_to_bottom_right);
//            		transaction.addToBackStack(null);
//            		transaction.add(R.id.container, new PollCreateFragment(), "Detail").commit();
//            		newImageView.setVisibility(View.VISIBLE);
//            		newImageView.setAlpha(1);
//            		newImageView.setScaleX(1);
//            		newImageView.setScaleY(1);
                	onCreatePollListener.onCreatePoll();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
			};
			YoYo.with(Techniques.TakingOff)
				.duration(400)
				.interpolate(new AccelerateDecelerateInterpolator())
				.withListener(listener)
				.playOn(createPollView);
			break;
		}

		default:
			break;
		}
	}

	public interface OnCreatePollListener {
		public void onCreatePoll();
	}
	
	public interface OnPollSelectedListener {
		public void onPollSelected();
	}
}
