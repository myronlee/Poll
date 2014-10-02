package com.diandian.coolco.poll.adapter;

import android.view.View;

public abstract class ViewHolder<T> {
	public abstract void setItem(T item);

	public static Class<?>[] getConsParamList() {
		return new Class<?>[] { View.class };
	}
}
