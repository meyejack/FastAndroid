package com.example.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.example.android.R;
import com.example.android.bean.net.response.News;
import com.example.android.utils.CommonUtils;

public class NewsAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<News> datas;
	private Context mContext;

	public NewsAdapter(Context context) {
		mContext = context;
		mInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public void setData(List<News> datas) {
		this.datas = datas;
	}

	@Override
	public int getCount() {
		return datas == null ? 0 : datas.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.item_news, parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		News data = datas.get(position);

		if (data == null)
			return null;

		holder.tvTitle.setText("标题: " + data.getTitle());
		holder.tvWriter.setText("作者: " + data.getWriter());
		holder.tvSource.setText("来源: " + data.getSource());
		String dateTime = CommonUtils.formatDate(data.getSenddate());
		holder.tvSendDate.setText("发布日期: " + dateTime);
		holder.tvTypeName.setText("所属类目: " + data.getTypename());

		return convertView;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	static class ViewHolder {
		@InjectView(R.id.tv_title)
		TextView tvTitle;
		@InjectView(R.id.tv_writer)
		TextView tvWriter;
		@InjectView(R.id.tv_source)
		TextView tvSource;
		@InjectView(R.id.tv_senddata)
		TextView tvSendDate;
		@InjectView(R.id.tv_typename)
		TextView tvTypeName;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}
	}

}
