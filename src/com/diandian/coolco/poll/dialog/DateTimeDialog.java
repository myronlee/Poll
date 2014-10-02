package com.diandian.coolco.poll.dialog;

import java.util.Calendar;

import com.diandian.coolco.poll.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.TimePicker;

public class DateTimeDialog extends Dialog{

	public DateTimeDialog(Context context, int theme) {
		super(context, theme);
	}

	public DateTimeDialog(Context context) {
		super(context, R.style.customDialogTheme);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dialog_datetime);
		
		setWidth();
		
		DatePicker datePicker = (DatePicker) findViewById(R.id.date);
		Calendar today = Calendar.getInstance();
//		datePicker.setMinDate(today.getTimeInMillis());
		datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH)+1, today.get(Calendar.DAY_OF_MONTH), null);
		TimePicker timePicker = (TimePicker) findViewById(R.id.time);
		timePicker.setIs24HourView(true);
		timePicker.setCurrentHour(today.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(today.get(Calendar.MINUTE));
	}

	/**
	 * Set the dialog's width as 90% of the screen
	 */
	private void setWidth() {
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		params.width = (int) (getContext().getResources().getDisplayMetrics().widthPixels * 0.9);
		window.setAttributes(params);
	}
	
}
