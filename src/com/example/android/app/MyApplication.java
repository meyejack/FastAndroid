package com.example.android.app;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.content.Context;

import com.example.android.Cookie;
import com.example.android.DaoMaster;
import com.example.android.DaoMaster.OpenHelper;
import com.example.android.DaoSession;
import com.example.android.bean.net.response.User;
import com.example.android.dao.MyCookieDao;
import com.example.android.exception.CatchHandler;
import com.example.android.utils.CommonUtils;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

/**
 * 自定义应用入口
 * 
 * @author Ht
 * 
 */
public class MyApplication extends Application {
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;
	private static Context mInstance;
	private static List<Cookie> cookies = new ArrayList<Cookie>();
	private static User user;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;

		// initCatchHandler();
		initImageLoader();
		checkRememberPassword();
	}

	/**
	 * 初始化App异常处理器
	 */
	private void initCatchHandler() {
		CatchHandler catchHandler = CatchHandler.getInstance();
		catchHandler.init(getApplicationContext());
		catchHandler.setParam(1, "Package", "Application Name", "Server Url",
				"Dialog Msg", 0);
	}

	/**
	 * 初始化imageloader
	 */
	private void initImageLoader() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "imageloader/Cache");

		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
				.cacheInMemory(true) // 加载图片时会在内存中加载缓存
				.cacheOnDisk(true) // 加载图片时会在磁盘中加载缓存
				.build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.diskCacheExtraOptions(480, 800, null)
				.threadPoolSize(3)
				// default
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// default
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				// default
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new LruMemoryCache(2 * 1024 * 1024))
				.memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13)
				// default
				.diskCache(new UnlimitedDiscCache(cacheDir))
				// default
				.diskCacheSize(50 * 1024 * 1024).diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
				.defaultDisplayImageOptions(defaultOptions) // default
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(config);
	}

	/**
	 * 取得DaoMaster
	 * 
	 * @param context
	 * @return
	 */
	public static DaoMaster getDaoMaster(Context context) {
		if (daoMaster == null) {
			OpenHelper helper = new DaoMaster.DevOpenHelper(context,
					Constants.DB_NAME, null);
			daoMaster = new DaoMaster(helper.getWritableDatabase());
		}
		return daoMaster;
	}

	/**
	 * 取得DaoSession
	 * 
	 * @param context
	 * @return
	 */
	public static DaoSession getDaoSession(Context context) {
		if (daoSession == null) {
			if (daoMaster == null) {
				daoMaster = getDaoMaster(context);
			}
			daoSession = daoMaster.newSession();
		}
		return daoSession;
	}

	/**
	 * 设置Cookie
	 * 
	 * @param cookies
	 */
	public static void setCookies(List<Cookie> cookies) {
		// 如果当前不存在cookie记录则直接创建
		if (MyApplication.cookies == null || MyApplication.cookies.size() == 0) {
			MyApplication.cookies.addAll(cookies);
		} else {
			// 如果已存在则遍历,替换已有的,添加没有的
			for (Cookie cookie : cookies) {
				// 修改标记,true为有修改,false为未修改
				boolean flag = false;
				if (MyApplication.cookies.contains(cookie)) {
					for (Cookie c : MyApplication.cookies) {
						// 复写了equals方法,匹配Cookie中key相同的数据
						if (cookie.equals(c)) {
							// 将已有(key相同)数据替换成新数据
							c.setValue(cookie.getValue());
							c.setDomain(cookie.getDomain());
							c.setExpires(cookie.getExpires());
							c.setPath(cookie.getValue());
							// 改变修改标记
							flag = true;
						}
					}
				}

				// 未修改则新增数据
				if (!flag) {
					MyApplication.cookies.add(cookie);
				}
			}
		}

	}

	/**
	 * 获取当前Cookie
	 * 
	 * @return
	 */
	public static String getCookiesHeader(String url) {
		List<Cookie> cookies;
		MyCookieDao myCookieDao = null;
		// 如果当前内存中存在cookie信息则直接取出,否则从数据库中读取
		if (MyApplication.cookies != null && MyApplication.cookies.size() > 0) {
			cookies = MyApplication.cookies;
			// 请求路径
			StringBuilder cookieStr = new StringBuilder();
			for (int i = 0; i < cookies.size(); i++) {
				// 验证Cookie的有效性
				if (CommonUtils.checkCookie(url, cookies.get(i))) {
					// 验证通过,拼接Cookie
					if (i == cookies.size() - 1) {
						cookieStr.append(cookies.get(i).getKey() + "="
								+ cookies.get(i).getValue());
					} else {
						cookieStr.append(cookies.get(i).getKey() + "="
								+ cookies.get(i).getValue() + ";");
					}
				}
			}

			return cookieStr.toString();
		}

		myCookieDao = MyCookieDao.getInstance(MyApplication.getInstance());
		return myCookieDao.getCookiesHeader(url);
	}

	public static Context getInstance() {
		return mInstance;
	}

	/**
	 * 检查是否记住密码
	 */
	private void checkRememberPassword(){
		// TODO
		
		if(true){
			setUser(new User());
		}
	}
	
	
	/**
	 * 设置已登录用户
	 * 
	 * @return
	 */
	public void setUser(User user) {
		MyApplication.user = user;
	}

	/**
	 * 获取用户登录状态
	 * 
	 * @return
	 */
	public boolean getUserStatus() {
		return (MyApplication.user != null);
	}
}
