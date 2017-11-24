package com.example.baselibrary.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import java.util.Stack;

/**
 *
 * @author zhao shuzhen
 * @created 2017-8-31
 */
public class AppManager {
	
	private static Stack<Activity> activityStack;
	private static AppManager instance;
	
	private AppManager(){}
	/**
	 * 实例化管理工具
	 */
	public static AppManager getAppManager(){
		if(instance==null){
			instance=new AppManager();
		}
		return instance;
	}
	/**
	 * 添加Activity 对象
	 */
	public void addActivity(Activity activity){
		/*if(activityStack==null){
			activityStack=new Stack<Activity>();
		}
		activityStack.add(activity);*/
	}

	/**
	 * 删除 Activity 对象
	 */
	public void removeActivity(Activity activity){
		/*if(activityStack != null && activityStack.contains(activity)){
			activityStack.remove(activity);
		}*/
	}

	/**
	 *  获取当前Activity 对象
	 */
	public Activity currentActivity(){
		Activity activity=activityStack.lastElement();
		return activity;
	}
	/**
	 *  销毁最后一条数据
	 */
	public void finishActivity(){
		Activity activity=activityStack.lastElement();
		finishActivity(activity);
	}
	/**
	 * 销毁数据
	 */
	public void finishActivity(Activity activity){
		if(activity!=null){
			activityStack.remove(activity);
			activity.finish();
			activity=null;
		}
	}

	/**
	 * 销毁指定对象
	 */
	public void finishActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				finishActivity(activity);
			}
		}
	}
	/**
	 *   空间是否有指定对象
	 */
	public boolean existsActivity(Class<?> cls){
		for (Activity activity : activityStack) {
			if(activity.getClass().equals(cls) ){
				return true;
			}
		}
		return false;
	}
	/**
	 * 销毁所有对象
	 */
	public void finishAllActivity(){
		/*for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();*/
	}
	/**
	 * 销毁所有对象,除了最后一个
	 */
	public void finishNoLastActivity(){
		/*for (int i = 0, size = activityStack.size(); i < size-1; i++){
            if (null != activityStack.get(i)){
            	activityStack.get(i).finish();
            }
	    }
		activityStack.clear();*/
	}

    /**
     * 销毁指定对象以外的所有对象
	 * @param cls
	 */
	public void finishNotSpecifiedActivity(Class<?> cls){
		for (int i = 0, size = activityStack.size(); i < size; i++){
			if (null != activityStack.get(i)&& activityStack.get(i).getClass()!=cls){
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

    /**
     *空间保存数量
	 * @return
     */
	public int activietyCounts(){
		if (null!=activityStack){
			return activityStack.size();
		}
		return 0;
	}
	/**
	 * 退出整个程序
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {	}
	}

	public interface GetMess{
		void getMess();
	}
	private GetMess getMess ;
	public void setGetMess(GetMess getMess){
		this.getMess=getMess;
	}
}