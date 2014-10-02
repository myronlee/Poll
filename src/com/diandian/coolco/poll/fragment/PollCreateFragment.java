package com.diandian.coolco.poll.fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.diandian.coolco.poll.R;
import com.diandian.coolco.poll.dialog.DateTimeDialog;
import com.diandian.coolco.poll.net.NetHelper;
import com.diandian.coolco.poll.util.Constant.URL;
import com.diandian.coolco.poll.util.Util;
import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;
import com.sleepbot.datetimepicker.time.TimePickerDialog.OnTimeSetListener;

public class PollCreateFragment extends Fragment implements OnClickListener, OnDateSetListener, OnTimeSetListener {

	private final static String TAG = PollCreateFragment.class.getName();
	
	private ViewGroup rootView;
	private ViewGroup moreSettingContainer;
	private View moreSettingView;
	private ViewGroup optionsContainer;
	private TextView optionEditText;
	private TextView endDateTextView;
	private TextView endTimeTextView;
	private EditText pollTitle;
	private EditText pollDescription;
	private EditText legalOptionNum;
	private CheckBox isAnonymous;
	private static CheckBox canSeeResultFirst;
	private CheckBox canChooseAgain;
	private CheckBox canChooseAnonymously;

	public PollCreateFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = (ViewGroup) inflater.inflate(R.layout.fragment_poll_create, container, false);
		View moreSettingLabelView = rootView.findViewById(R.id.more_setting_label);
		moreSettingLabelView.setOnClickListener(this);
		moreSettingView = rootView.findViewById(R.id.more_setting);
		moreSettingView.setPivotY(0);

		optionsContainer = (ViewGroup) rootView.findViewById(R.id.options_container);
		optionEditText = (TextView) rootView.findViewById(R.id.new_option);
		optionEditText.addTextChangedListener(new CustomTextWatcher(getActivity(), optionsContainer, optionEditText));
		optionEditText.setPivotY(0);
		
		moreSettingContainer = (ViewGroup) rootView.findViewById(R.id.more_setting_container);
		
		endDateTextView = (TextView) rootView.findViewById(R.id.end_date);
		endTimeTextView = (TextView) rootView.findViewById(R.id.end_time);
		endDateTextView.setPaintFlags(endDateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		endTimeTextView.setPaintFlags(endTimeTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		endDateTextView.setOnClickListener(this);
		endTimeTextView.setOnClickListener(this);
		
		pollTitle = (EditText) rootView.findViewById(R.id.poll_title);
		pollDescription = (EditText) rootView.findViewById(R.id.poll_description);
		legalOptionNum = (EditText) rootView.findViewById(R.id.legal_option_num);
		isAnonymous = (CheckBox) rootView.findViewById(R.id.is_anonymous);
		canSeeResultFirst = (CheckBox) rootView.findViewById(R.id.can_see_result_first);
		canChooseAgain = (CheckBox) rootView.findViewById(R.id.can_choose_again);
		canChooseAnonymously = (CheckBox) rootView.findViewById(R.id.can_choose_anonymously);
		
		rootView.findViewById(R.id.poll_create).setOnClickListener(this);
		
		initLayoutAnimation();

		return rootView;
	}

	public void initLayoutAnimation() {
		LayoutTransition choicesTransition = new LayoutTransition();
		setupAnimations(choicesTransition);
		optionsContainer.setLayoutTransition(choicesTransition);

	}

	private void setupAnimations(LayoutTransition transition) {
		transition.setStagger(LayoutTransition.CHANGE_APPEARING, 0);
		transition.setStagger(LayoutTransition.CHANGE_DISAPPEARING, 0);
		transition.setStartDelay(LayoutTransition.CHANGE_APPEARING, 0);
		transition.setStartDelay(LayoutTransition.CHANGE_DISAPPEARING, 0);
		transition.setDuration(LayoutTransition.DISAPPEARING, 0);
		transition.setDuration(LayoutTransition.APPEARING, 200);

		ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "scaleY", 0f, 1f).setDuration(
				transition.getDuration(LayoutTransition.APPEARING));
		animIn.setInterpolator(new DecelerateInterpolator());
		transition.setAnimator(LayoutTransition.APPEARING, animIn);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.more_setting_label: {
			if (moreSettingView.getVisibility() == View.VISIBLE) {
				moreSettingView.setVisibility(View.GONE);
			} else {
				moreSettingView.setVisibility(View.VISIBLE);
			}
			break;
		}
		case R.id.end_date: {
			Calendar calendar = Calendar.getInstance();
	        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), false);
	        datePickerDialog.setVibrate(false);
            datePickerDialog.setYearRange(1985, 2028);
            datePickerDialog.setCloseOnSingleTapDay(false);
            datePickerDialog.show(getChildFragmentManager(), "date");
			break;
		}
		case R.id.end_time: {
			Calendar calendar = Calendar.getInstance();
	        TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);
	        timePickerDialog.setVibrate(false);
            timePickerDialog.setCloseOnSingleTapMinute(false);
            timePickerDialog.show(getChildFragmentManager(), "time");
			break;
		}
		case R.id.poll_create:{
			new CreatePollAsyncTask().execute();
			break;
		}
		default:
			break;
		}
	}
	
	class CustomTextWatcher implements TextWatcher{
		private View watchingEditText;
		private ViewGroup editTextContainer;
		private boolean empty;
		private Context context;
		private LayoutInflater inflater;
		
		public CustomTextWatcher(Context context, ViewGroup editTextContainer, View watchingEditText){
			inflater = LayoutInflater.from(context);
			this.context = context;
			this.editTextContainer = editTextContainer;
			this.watchingEditText = watchingEditText;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			//Becoming empty
			if (0 == start && count == s.length() && 0 == after) {
				editTextContainer.removeView(watchingEditText);
				return;
			}
			//Becoming unempty
			if (0 == s.length() && after > 0) {
				EditText newEditText = (EditText) inflater.inflate(R.layout.new_choice, null);
				newEditText.setPivotY(0);
				newEditText.addTextChangedListener(new CustomTextWatcher(context, editTextContainer, newEditText));
				editTextContainer.addView(newEditText);
			}
//			empty = (s.length() == 0);
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void afterTextChanged(Editable s) {
		}

	}

	@Override
	public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
		endDateTextView.setText(year+"-"+month+"-"+day);
	}

	@Override
	public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {
		endTimeTextView.setText(hourOfDay+":"+minute);
	}

	class CreatePollAsyncTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			JSONObject reqJson = new JSONObject();
			try {
				reqJson.put("title", pollTitle.getText().toString());
				reqJson.put("description", pollDescription.getText().toString());
				//Need validate
				reqJson.put("legalOptionNum", legalOptionNum.getText().toString());
				reqJson.put("isAnonymous", isAnonymous.isChecked()?1:0);
				reqJson.put("canSeeResultFirst", canSeeResultFirst.isChecked()?1:0);
				reqJson.put("canChooseAgain", canChooseAgain.isChecked()?1:0);
				reqJson.put("canChooseAnonymously", canChooseAnonymously.isChecked()?1:0);
				reqJson.put("closeDateTime", endDateTextView.getText()+" "+endTimeTextView.getText()+":00");
				reqJson.put("createDateTime", Util.getCurrentDateTime());
				
				JSONArray optionArray = new JSONArray();
				for (int i = 0; i < optionsContainer.getChildCount(); i++) {
					TextView optionTextView = (TextView) optionsContainer.getChildAt(i);
					optionArray.put(optionTextView.getText());
				}
				
				reqJson.put("options", optionArray);
				
				reqJson.put("createUsername", "ligang");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			String res = NetHelper.request(new DefaultHttpClient(), URL.CREATE_POLL, reqJson.toString());
//			Log.e(TAG, res);
			
			return null;
		}
		
	}
}