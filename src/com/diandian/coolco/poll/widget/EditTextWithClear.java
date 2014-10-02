package com.diandian.coolco.poll.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class EditTextWithClear extends EditText {
	
//	private Drawable drawableLeft;
	private Drawable drawableRight;
	
	public EditTextWithClear(Context context) {
		super(context);
	}
	
	public EditTextWithClear(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public EditTextWithClear(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}
	
	@Override
	protected void onTextChanged(CharSequence text, int start,
			int lengthBefore, int lengthAfter) {
		super.onTextChanged(text, start, lengthBefore, lengthAfter);
		
		if (null == drawableRight) {
			Drawable[] drawables = getCompoundDrawables();
			drawableRight = drawables[2];
		}
		
		if (0 == text.length()) {
			setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
		} else {
			setCompoundDrawablesWithIntrinsicBounds(null, null, drawableRight, null);
		}
		
	}

	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (event.getAction() == MotionEvent.ACTION_UP 
				&& getText().length() != 0 
				&& event.getX() >= (getWidth()-drawableRight.getIntrinsicWidth()-getPaddingRight())) {
			setText("");
			
			//can stop the paste menu from poping out
			int cacheInputType = getInputType();// backup  the input type
            setInputType(InputType.TYPE_NULL);// disable soft input
            super.onTouchEvent(event);// call native handler
            setInputType(cacheInputType);// restore input  type
			return true;
		}
		return super.onTouchEvent(event);
	}

}
