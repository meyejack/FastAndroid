package com.example.android.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.R;

/**
 * 自定义头部布局
 * 
 * @ClassName: HeaderLayout
 * @Description: Activity或者Fragment的头部标题栏
 * @author Ht
 */
public class HeaderLayout extends LinearLayout {
	private LayoutInflater mInflater;
	private View mHeader;
	private LinearLayout mLayoutLeftContainer, mLayoutRightContainer;
	private TextView mTvTitle;
	private ImageButton mLeftImageButton, mRightImageButton;

	public HeaderLayout(Context context) {
		super(context);
		init(context);
	}

	public HeaderLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mInflater = LayoutInflater.from(context);
		mHeader = mInflater.inflate(R.layout.common_header, null);
		addView(mHeader);
		initViews();
	}

	/** 初始化布局 */
	private void initViews() {
		mLayoutLeftContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_leftview_container);
		mLayoutRightContainer = (LinearLayout) findViewByHeaderId(R.id.header_layout_rightview_container);
		mTvTitle = (TextView) findViewByHeaderId(R.id.header_htv_subtitle);

		initLeftImageButton();
		initRightImageButton();

		mLayoutLeftContainer.setVisibility(View.INVISIBLE);
		mLayoutRightContainer.setVisibility(View.INVISIBLE);
		mTvTitle.setVisibility(View.INVISIBLE);
	}

	/** 在TitleBar中查找指定控件 */
	public View findViewByHeaderId(int id) {
		return mHeader.findViewById(id);
	}

	/** 初始化左侧按钮 */
	private void initLeftImageButton() {
		View mleftImageButtonView = mInflater.inflate(
				R.layout.common_header_leftbutton, null);
		mLayoutLeftContainer.addView(mleftImageButtonView);
		mLeftImageButton = (ImageButton) mleftImageButtonView
				.findViewById(R.id.ib_titlebar_left);
	}

	/** 初始化右侧按钮 */
	private void initRightImageButton() {
		View mRightImageButtonView = mInflater.inflate(
				R.layout.common_header_rightbutton, null);
		mLayoutRightContainer.addView(mRightImageButtonView);
		mRightImageButton = (ImageButton) mRightImageButtonView
				.findViewById(R.id.ib_titlebar_right);
	}

	/** 设置TitleBar的标题 */
	public void setTitleBar(CharSequence title) {
		mTvTitle.setText(title);
		mTvTitle.setVisibility(View.VISIBLE);
	}

	/** 设置TitleBar的标题及左侧按钮背景 */
	public void setTitleBar(CharSequence title, int leftRes) {
		setTitleBar(title);
		mLeftImageButton.setBackgroundResource(leftRes);
		mLayoutLeftContainer.setVisibility(View.VISIBLE);
	}

	/** 设置TitleBar的标题及右侧按钮背景 */
	public void setTitleBar(CharSequence title, int leftRes, int rightRes) {
		setTitleBar(title, leftRes);
		mRightImageButton.setBackgroundResource(rightRes);
		mLayoutRightContainer.setVisibility(View.VISIBLE);
	}

	/** 设置左侧按钮点击事件 */
	public void setLeftListener(OnClickListener listener) {
		mLeftImageButton.setOnClickListener(listener);
	}

	/** 设置右侧按钮点击事件 */
	public void setRightListener(OnClickListener listener) {
		mRightImageButton.setOnClickListener(listener);
	}

	/** 自定义左侧控件 */
	public void setLeftView(View view) {
		mLayoutLeftContainer.removeAllViews();
		mLayoutLeftContainer.addView(view);
	}

	/** 自定义右侧控件 */
	public void setRightView(View view) {
		mLayoutRightContainer.removeAllViews();
		mLayoutRightContainer.addView(view);
	}
}
