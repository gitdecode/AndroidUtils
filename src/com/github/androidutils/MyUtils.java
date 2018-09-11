/**  
 * <p>Title: MyUtils.java</p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2017</p>  
 * <p>Company: www.baidudu.com</p>  
 * @author tsj 
 * @date 2018 9 11  
 * @version 1.0  
 */
package com.github.androidutils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * <p>
 * Title: MyUtils
 * </p>
 * <p>
 * Description:<br>
 * 1.获取应用名称<br>
 * 2.判断网络状态<br>
 * 3.获取屏幕信息（高度、宽度、截图）<br>
 * 4.获取系统时间<br>
 * 5.开关软键盘<br>
 * 6.自定义Toast<br>
 * </p>
 * 
 * @author tsj
 * @date 2018 9 11
 */
public class MyUtils {
	
	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	public static boolean isShow = true;
	
	/**  
	 * <p>Title: getAppName</p>  
	 * <p>Description: 获取应用程序名称</p>  
	 * @param context
	 * @return 返回应用名称
	 */
	public static String getAppName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			int labelRes = packageInfo.applicationInfo.labelRes;
			return context.getResources().getString(labelRes);
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**  
	 * <p>Title: getVersionName</p>  
	 * <p>Description: 获取应用程序版本名称信息</p>  
	 * @param context
	 * @return  当前应用的版本名称
	 */
	public static String getVersionName(Context context) {
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			return packageInfo.versionName;

		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	

	/**  
	 * <p>Title: isConnected</p>  
	 * <p>Description: 判断网络是否连接</p>  
	 * @param context
	 * @return  true：connected
	 */
	public static boolean isConnected(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (null != connectivity) {
			NetworkInfo info = connectivity.getActiveNetworkInfo();
			if (null != info && info.isConnected()) {
				if (info.getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}

	/**  
	 * <p>Title: isWifi</p>  
	 * <p>Description: 判断是否是wifi连接</p>  
	 * @param context
	 * @return  true：链接类型是wifi
	 */
	public static boolean isWifi(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm == null)
			return false;
		return cm.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

	}

	/**  
	 * <p>Title: openSetting</p>  
	 * <p>Description: 打开网络设置界面</p>  
	 * @param activity  当前的activity
	 */
	public static void openSetting(Activity activity) {
		Intent intent = new Intent("/");
		ComponentName cm = new ComponentName("com.android.settings",
				"com.android.settings.WirelessSettings");
		intent.setComponent(cm);
		intent.setAction("android.intent.action.VIEW");
		activity.startActivityForResult(intent, 0);
	}
	
	/**  
	 * <p>Title: getScreenWidth</p>  
	 * <p>Description: 获得屏幕宽度</p>  
	 * @param context 当前上下文
	 * @return  当前的屏幕宽度 int
	 */
	public static int getScreenWidth(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.widthPixels;
	}

	/**  
	 * <p>Title: getScreenHeight</p>  
	 * <p>Description: 获得屏幕高度</p>  
	 * @param context 当前上下文
	 * @return  当前的屏幕宽度 int
	 */
	public static int getScreenHeight(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics.heightPixels;
	}

	/**  
	 * <p>Title: getStatusHeight</p>  
	 * <p>Description: 获得状态栏的高度</p>  
	 * @param context 当前上下文
	 * @return  状态栏的高度 int
	 */
	public static int getStatusHeight(Context context) {

		int statusHeight = -1;
		try {
			Class<?> clazz = Class.forName("com.android.internal.R$dimen");
			Object object = clazz.newInstance();
			int height = Integer.parseInt(clazz.getField("status_bar_height")
					.get(object).toString());
			statusHeight = context.getResources().getDimensionPixelSize(height);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return statusHeight;
	}


	/**  
	 * <p>Title: snapShotWithStatusBar</p>  
	 * <p>Description: 获取当前屏幕截图，包含状态栏</p>  
	 * @param activity 当前的activity
	 * @return  bitmap
	 */
	public static Bitmap snapShotWithStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, 0, width, height);
		view.destroyDrawingCache();
		return bp;

	}

	/**  
	 * <p>Title: snapShotWithoutStatusBar</p>  
	 * <p>Description: 获取当前屏幕截图，不包含状态栏</p>  
	 * @param activity 当前的activity
	 * @return  bitmap
	 */
	public static Bitmap snapShotWithoutStatusBar(Activity activity) {
		View view = activity.getWindow().getDecorView();
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bmp = view.getDrawingCache();
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		int statusBarHeight = frame.top;

		int width = getScreenWidth(activity);
		int height = getScreenHeight(activity);
		Bitmap bp = null;
		bp = Bitmap.createBitmap(bmp, 0, statusBarHeight, width, height
				- statusBarHeight);
		view.destroyDrawingCache();
		return bp;

	}
	
	/**  
	 * <p>Title: getTime</p>  
	 * <p>Description: 将long时间转化为String类型</p>  
	 * @param timeInMillis long
	 * @param dateFormat SimpleDateFormat
	 * @return  String
	 */
	public static String getTime(long timeInMillis, SimpleDateFormat dateFormat) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**  
	 * <p>Title: getTime</p>  
	 * <p>Description: long time to string, format is {@link #DEFAULT_DATE_FORMAT}</p>  
	 * @param timeInMillis long
	 * @return  String
	 */
	public static String getTime(long timeInMillis) {
		return getTime(timeInMillis, DEFAULT_DATE_FORMAT);
	}

	/**  
	 * <p>Title: getCurrentTimeInLong</p>  
	 * <p>Description: 获取当前的时间</p>  
	 * @return  long
	 */
	public static long getCurrentTimeInLong() {
		return System.currentTimeMillis();
	}

	/**  
	 * <p>Title: getCurrentTimeInString</p>  
	 * <p>Description: 获取当前的时间</p>  
	 * @return  String
	 */
	public static String getCurrentTimeInString() {
		return getTime(getCurrentTimeInLong());
	}

	/**  
	 * <p>Title: getCurrentTimeInString</p>  
	 * <p>Description: get current time in milliseconds</p>  
	 * @param dateFormat
	 * @return  
	 */
	public static String getCurrentTimeInString(SimpleDateFormat dateFormat) {
		return getTime(getCurrentTimeInLong(), dateFormat);
	}
  
    /**  
     * <p>Title: openKeybord</p>  
     * <p>Description: 打卡软键盘 </p>  
     * @param mEditText 输入框 
     * @param mContext 上下文 
     */
    public static void openKeybord(EditText mEditText, Context mContext)  
    {  
        InputMethodManager imm = (InputMethodManager) mContext  
                .getSystemService(Context.INPUT_METHOD_SERVICE);  
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);  
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,  
                InputMethodManager.HIDE_IMPLICIT_ONLY);  
    }  


    /**  
     * <p>Title: closeKeybord</p>  
     * <p>Description: 关闭软键盘 </p>  
     * @param mEditText 输入框 
     * @param mContext  上下文 
     */
    public static void closeKeybord(EditText mEditText, Context mContext)  
    {  
        InputMethodManager imm = (InputMethodManager) mContext  
                .getSystemService(Context.INPUT_METHOD_SERVICE);  
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);  
    }
    
	/**  
	 * <p>Title: showToastShort</p>  
	 * <p>Description: 短时间显示Toast</p>  
	 * @param context
	 * @param message  
	 */
	public static void showToastShort(Context context, CharSequence message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**  
	 * <p>Title: showToastShort</p>  
	 * <p>Description: 短时间显示Toast</p>  
	 * @param context 上下文 
	 * @param message  要显示的字符串资源的id
	 */
	public static void showToastShort(Context context, int message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
	}

	/**  
	 * <p>Title: showToastLong</p>  
	 * <p>Description: 长时间显示Toast</p>  
	 * @param context
	 * @param message  
	 */
	public static void showToastLong(Context context, CharSequence message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**  
	 * <p>Title: showToastLong</p>  
	 * <p>Description: 长时间显示Toast</p>  
	 * @param context  上下文
	 * @param message  要显示的字符串资源的id
	 */
	public static void showToastLong(Context context, int message) {
		if (isShow)
			Toast.makeText(context, message, Toast.LENGTH_LONG).show();
	}

	/**  
	 * <p>Title: showToast</p>  
	 * <p>Description: 自定义显示Toast时间</p>  
	 * @param context
	 * @param message
	 * @param duration  
	 */
	public static void showToast(Context context, CharSequence message, int duration) {
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}

	/**  
	 * <p>Title: showToast</p>  
	 * <p>Description: 自定义显示Toast时间</p>  
	 * @param context
	 * @param message
	 * @param duration  
	 */
	public static void showToast(Context context, int message, int duration) {
		if (isShow)
			Toast.makeText(context, message, duration).show();
	}
}
