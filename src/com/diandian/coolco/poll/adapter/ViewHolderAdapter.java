package com.diandian.coolco.poll.adapter;

import java.util.List;

import com.diandian.coolco.poll.model.Poll;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class ViewHolderAdapter<T> extends BaseAdapter {

	private int mResource;
	private LayoutInflater mInflater;
	private Class<? extends ViewHolder<T>> mClass;
	private List<T> mDatas;

	public ViewHolderAdapter(Context context, int resource, List<T> datas, Class<? extends ViewHolder<T>> class1) {
		super();
		mResource = resource;
		mInflater = LayoutInflater.from(context);
		mClass = class1;
		mDatas = datas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public View getView(int position, View convert, ViewGroup parent) {
		ViewHolder<T> holder = null;
		if (convert == null) {
			convert = mInflater.inflate(mResource, parent, false);
			try {
				holder = mClass.getDeclaredConstructor(View.class).newInstance(convert);
			} catch (Exception e) {
				e.printStackTrace();
			}
			convert.setTag(holder);
		} else {
			holder = (ViewHolder<T>) convert.getTag();
		}
		holder.setItem(mDatas.get(position));
		return convert;
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
}