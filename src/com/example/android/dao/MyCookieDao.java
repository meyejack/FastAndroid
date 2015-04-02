package com.example.android.dao;

import java.util.List;

import org.apache.http.client.methods.HttpUriRequest;

import com.example.android.Cookie;
import com.example.android.CookieDao;
import com.example.android.DaoSession;
import com.example.android.Note;
import com.example.android.NoteDao;
import com.example.android.app.MyApplication;
import com.example.android.utils.CommonUtils;
import com.example.android.utils.LogUtils;

import android.content.Context;

/**
 * 测试数据库操作类,用于操作sqlite
 * 
 * @author Ht
 * 
 */
public class MyCookieDao {
	private static MyCookieDao mInstance;
	private DaoSession mDaoSession;

	private CookieDao cookieDao;

	private MyCookieDao() {
	}

	public static MyCookieDao getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new MyCookieDao();
			mInstance.mDaoSession = MyApplication.getDaoSession(context);
			mInstance.cookieDao = mInstance.mDaoSession.getCookieDao();
		}

		return mInstance;
	}

	/**
	 * 增加或修改某条数据
	 * 
	 * @param note
	 */
	public void saveCookie(Cookie cookie) {
		cookieDao.insertOrReplace(cookie);
	}

	/**
	 * 批量添加数据(开启线程)
	 * 
	 * @param list
	 */
	public void saveCookieLists(final List<Cookie> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		cookieDao.getSession().runInTx(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < list.size(); i++) {
					Cookie cookie = list.get(i);
					// 查询要插入的数据是否已存在
					Cookie cacheCookie = queryCookie("where KEY=?",
							cookie.getKey());
					// 没有有效期的Cookie无需存储
					if (cookie.getExpires() != null) {
						// 已存在的Cookie直接更新
						if (cacheCookie != null) {
							cookie.setId(cacheCookie.getId());
						}

						cookieDao.insertOrReplace(cookie);
					}
				}
			}
		});
	}

	/**
	 * 删除指定id的数据
	 * 
	 * @param id
	 */
	public void deleteCookie(long id) {
		cookieDao.deleteByKey(id);
	}

	/**
	 * 删除指定的数据
	 * 
	 * @param note
	 */
	public void deleteCookie(Cookie cookie) {
		cookieDao.delete(cookie);
	}

	/**
	 * 删除全部数据
	 */
	public void deleteAllCookie() {
		cookieDao.getSession().runInTx(new Runnable() {

			@Override
			public void run() {
				cookieDao.deleteAll();
			}
		});
	}

	/**
	 * 查找指定数据
	 * 
	 * @param id
	 * @return
	 */
	public Cookie loadCookie(long id) {
		return cookieDao.load(id);
	}

	/**
	 * 查找全部数据
	 * 
	 * @return
	 */
	public List<Cookie> loadAllCookie() {
		return cookieDao.loadAll();
	}

	/**
	 * 获取当前Cookie
	 * 
	 * @return
	 */
	public String getCookiesHeader(String url) {
		List<Cookie> cookies = loadAllCookie();
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
			} else {
				// 验证不通过,删除无效Cookie
				deleteCookie(cookies.get(i));
			}
		}
		return cookieStr.toString();
	}

	/**
	 * 根据条件查找多条数据
	 * 
	 * @param where
	 * @param params
	 * @return
	 */
	public List<Cookie> queryCookies(String where, String... params) {
		return cookieDao.queryRaw(where, params);
	}

	/**
	 * 根据条件查找单条数据
	 * 
	 * @param where
	 * @param params
	 * @return
	 */
	public Cookie queryCookie(String where, String... params) {
		List<Cookie> queryRaw = cookieDao.queryRaw(where, params);
		if (queryRaw != null && queryRaw.size() > 0)
			return queryRaw.get(0);
		return null;
	}

}
