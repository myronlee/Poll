
package com.diandian.coolco.poll.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.widget.TextView;

public class MagicTextView extends TextView{
    // 递减/递增 的变量值
    private double mRate;
    // view 设置的值
//    private double mValue;
    // 当前显示的值
    private double mCurValue;
    // 当前变化后最终状态的目标值
    private double mGalValue;
    // 控制加减法
//    private int rate = 1;
    // 当前变化状态(增/减/不变)
//    private int mState = 0;
//    private boolean refreshing;
    private static final int REFRESH = 1;
//    DecimalFormat fnum = new DecimalFormat("0.00");
//    DecimalFormat fnum = new DecimalFormat("0");

    @SuppressLint("HandlerLeak") private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {

                case REFRESH:
                    if (mCurValue < mGalValue) {
//                    	if (rate * mCurValue < mGalValue) {
//                        refreshing = true;
                        setText(((int) mCurValue)+"");
                        mCurValue += mRate;
                        mHandler.sendEmptyMessageDelayed(REFRESH, 50);
                    } else {
//                        refreshing = false;
                        setText(((int) mCurValue)+"");
                    }
                    break;
                default:
                    break;
            }
        };
    };

    public MagicTextView(Context context) {
        super(context);
    }

    public MagicTextView(Context context, AttributeSet set) {
        super(context, set);
    }

    public MagicTextView(Context context, AttributeSet set, int defStyle) {
        super(context, set, defStyle);
    }

    public void setValue(double value) {
        mCurValue = 0.00;
//        mGalValue = isShown() ? value : 0;
        mGalValue = value;
//        mValue = value;
        mRate = (double) (value / 5.00);
//        BigDecimal b = new BigDecimal(mRate);
//        mRate = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public void startAnimation(double value) {
    	setValue(value);
        mHandler.sendEmptyMessage(REFRESH);
    }
}
