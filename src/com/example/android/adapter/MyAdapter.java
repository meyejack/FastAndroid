package com.example.android.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.android.R;
import com.example.android.base.CustomBaseAdapter;
import com.example.android.bean.net.response.News;

public class MyAdapter extends CustomBaseAdapter<News> {

	public MyAdapter(Context context) {
		super(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHoler holder;
		News data = getItemData(position);

		if (convertView == null) {
			convertView = getItemView(R.layout.item_news, parent);
			holder = new ViewHoler(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHoler) convertView.getTag();
		}

		holder.tvTitle.setText(data.getTitle());

		return convertView;
	}

	static class ViewHoler {
		@InjectView(R.id.tv_title)
		TextView tvTitle;

		public ViewHoler(View view) {
			ButterKnife.inject(this, view);
		}
	}

}
