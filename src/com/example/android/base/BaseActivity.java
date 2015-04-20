package com.example.android.base;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.Toast;
import butterknife.ButterKnife;

import com.example.android.R;
import com.example.android.app.AppManager;
import com.example.android.net.NetCenter;
import com.example.android.ui.custom.HeaderLayout;

public abstract class BaseActivity extends Activity implements IBaseView {
	private ProgressDialog pd;

	/** 初始化布局 */
	public abstract void initContentView();

	/** 初始化控件 */
	public abstract void initView();

	/** 初始化控制中心 */
	public abstract void initPresenter();

	/** 初始化监听事件 */
	public void initListener() {
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 隐藏标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		initContentView();
		// 将该Activity加入堆栈
		AppManager.getAppManager().addActivity(this);
		// 初始化View注入
		ButterKnife.inject(this);
		initPresenter();
		initView();
		initListener();
	}

	@Override
	protected void onDestroy() {
		// TODO 该方法运行在子线程,可能导致Activity回收时Context被持有,导致内存泄露
		// 清除网络请求队列
		NetCenter.clearRequestQueue();
		// 将该Activity从堆栈移除
		AppManager.getAppManager().removeActivity();
		super.onDestroy();
	}

	/** 初始化标题栏 */
	protected HeaderLayout getTitleBar() {
		HeaderLayout mHeaderLayout = (HeaderLayout) findViewById(R.id.titleBar);
		return mHeaderLayout;
	}

	@Override
	public void showProgress(boolean flag, String message) {
		if (pd == null) {
			pd = new ProgressDialog(this);
			pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pd.setCancelable(flag);
			pd.setCanceledOnTouchOutside(false);
			pd.setMessage(message);
			pd.show();
		} else {
			pd.show();
		}
	}

	@Override
	public void hideProgress() {
		if (pd == null)
			return;

		new AsyncTask<Void, Integer, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);

				if (pd != null && pd.isShowing()) {
					pd.dismiss();
				}
			}
		}.execute();
	}

	@Override
	public void showToast(int resId) {
		showToast(getString(resId));
	}

	@Override
	public void showToast(String msg) {
		if (!isFinishing()) {
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void showNetError() {
		showToast("网络异常,请稍后重试");
	}

	@Override
	public void showParseError() {
		showToast("数据解析异常");
	}

}
