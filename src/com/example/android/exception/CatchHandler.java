package com.example.android.exception;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Ht
 * 用作系统抛出错误时,进行处理
 * 1.使用方式:
 *  ① 编写自定义Application 继承 Application并实现onCreate()方法
 *  ② 在onCreate()方法中实现类似如下方法.例:
 *  a.实例化 CatchHandler catchHandler = CatchHandler.getInstance();
 *	b.初始化 catchHandler.init(getApplicationContext());
 *	c.设置处理参数 catchHandler.setParam(int,String,String,String,String);
 *  ③ 在Appmanifest文件中添加Application name属性为自定义Application
 * 2.注意事项:
 *  ① 因为需要将错误信息写入文件,所以,Appmanifest文件中需添加WRITE_EXTERNAL_STORAGE权限
 *  ② 因为需要将错误信息文件上传到服务器,所以,Appmanifest文件中需添加INTERNET权限
 *  
 **/
public class CatchHandler implements UncaughtExceptionHandler {

	private static String LOG = "catchExceptionLog";//异常日志
	
	private static CatchHandler instance;  //单例引用
	private Context context;
	private int catchCase;//选择的处理方式
	private String filePath;//文件保存路径
	private String fileName;//文件名称
	private String serverUrl;//服务器URL
	private String dialogText;//提示内容
	private File saveFile;//保存的文件
	private int toashShowTime;//Toast提示的时长
	
	/**
	 * 处理方式1:仅保存文件
	 **/
	public static int CATCHMETHOD_SAVEFILE = 1;
	/**
	 * 处理方式2:保存文件并上传到服务器
	 **/
	public static int CATCHMETHOD_SAVEANDUPLOAD = 2;
	
	private CatchHandler(){}
    
    public synchronized static CatchHandler getInstance(){  //同步方法，以免单例多线程环境下出现异常
        if (instance == null){
            instance = new CatchHandler();
        }
        return instance;
    }
	
    /**
     * 初始化，把当前对象设置成UncaughtExceptionHandler处理器
     * @param ctx 
     * 上下文对象
     **/
    public void init(Context context){ 
        Thread.setDefaultUncaughtExceptionHandler(this);
        this.context = context;
    }
    
    /**
     * @author Ht
	 * 设置报错处理参数
	 * @param catchCase
	 * 选择的处理方式 1:仅保存文件;2:保存文件并上传到服务器
	 * @param filePath
	 * 文件保存路径(必传)
	 * @param fileName
	 * 文件名称(必传)
	 * @param serverUrl
	 * 服务器URL 可为空字符串或null;当catchCase为2时,不得为空字符串或null
	 * @param dialogText
	 * Toast提示内容(必传)
	 * @param toashShowTime
	 * Toast提示时间
	 **/
    public void setParam(
    		int catchCase,
			String filePath,
			String fileName,
			String serverUrl,
			String dialogText,
			int toashShowTime )
    {
    	this.catchCase = catchCase;
    	this.filePath = filePath;
    	this.fileName = fileName;
    	this.serverUrl = serverUrl;
    	this.dialogText = dialogText;
    	this.toashShowTime = toashShowTime;
    }
    
	@Override
	public void uncaughtException(Thread thread, Throwable ex) {//出现异常时调用
		//报错时错误信息处理方式
		switch (getCatchCase()) {
			case 1://CATCHMETHOD_SAVEANDUPLOAD
				saveExceptionMessageToFile(getFilePath(), getFileName(), ex);
				break;
			case 2://CATCHMETHOD_SAVEANDUPLOAD
				saveExceptionMessageToFile(getFilePath(), getFileName(), ex);
				sendExceptionMessageToServer(getServerUrl(), saveFile);
			default:
				break;
		}
		//报错时程序处理方式
		show();
	}
	
	/**
	 * 使用Toast来显示异常信息  
	 **/
	private void show(){
        new Thread() {  
            @Override  
            public void run() {  
                Looper.prepare();  
                Toast.makeText(getContext(), getDialogText(), getToashShowTime()).show();
                Looper.loop();  
            }  
        }.start(); 
	}
	
	/**
	 * @author Ht
	 * 将错误信息保存至文件
	 * @param filePath
	 * 文件保存路径
	 * @param fileName
	 * 文件名称
	 * @param exception
	 * 异常信息
	 **/
	private void saveExceptionMessageToFile(String filePath,String fileName,Throwable exception){
		FileWriter fw = null;
		try {
			//创建文件夹
			File dir = new File(filePath);
			if(!dir.exists()){
				dir.mkdirs();
			}
			//创建文件 并获取文件写入流
			File file = new File(dir, fileName);
			fw = new FileWriter(file, true);//true代表往后追加的模式
			//获取异常详细信息
			StackTraceElement[] stacks = exception.getStackTrace();
			for(int i=0;i<stacks.length;i++){
				fw.write("----------------Exception----------------");
				fw.write("  ClassName:"+stacks[i].getClassName()+"\n");
				fw.write("-----------------------------------------");
				fw.write("  FileName:"+stacks[i].getFileName()+"\n");
				fw.write("-----------------------------------------");
				fw.write("  LineNumber:"+stacks[i].getLineNumber()+"\n");
				fw.write("-----------------------------------------");
				fw.write("  MethodName:"+stacks[i].getMethodName()+"\n");
				fw.write("-----------------------------------------");
				fw.write("  Description:"+exception.toString()+"\n");
				fw.write("-------------------end-------------------");
			}
			saveFile = file;//保存上传的文件
		} catch (Exception e) {
			Log.e(LOG, e.toString());
		} finally{
			try {
				if(fw!=null){
					fw.close();
				}
			} catch (Exception e2) {
				Log.e(LOG, e2.toString());
			}
		}
	}
	
	/**
	 * @author Ht
	 * 将错误信息发送给服务器
	 * @param serverUrl
	 * 服务器URL
	 * @param exmsgFile
	 * 异常信息文件
	 * @return 上传结果 true:上传成功  false:上传失败
	 **/
	private boolean sendExceptionMessageToServer(String serverUrl,File exmsgFile){
		HttpURLConnection conn = null;
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		try {
			URL url = new URL(serverUrl);
			conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(10000);//设置超时时间
			conn.setReadTimeout(10000);//设置读取时间
			conn.setRequestMethod("GET");//设置访问方式 
			//获取流
			bos = new BufferedOutputStream(conn.getOutputStream());
			fis = new FileInputStream(exmsgFile);
			byte[] buffer = new byte[1024];
			int len;
			while((len = fis.read(buffer))!=-1){
				bos.write(buffer, 0, len);
			}
		} catch (Exception e) {
			Log.e(LOG, e.toString());
			return false;
		} finally{
			if(bos!=null){
				try {
					bos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(LOG, e.toString());
				}
			}
			if(fis!=null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Log.e(LOG, e.toString());
				}
			}
		}
		return true;
	}
	
	
	/*-------------------------------------------*/

	public int getCatchCase() {
		return catchCase;
	}

	public String getFilePath() {
		return filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public String getServerUrl() {
		return serverUrl;
	}
	
	public Context getContext() {
		return context;
	}

	public String getDialogText() {
		return dialogText;
	}

	public int getToashShowTime() {
		return toashShowTime;
	}

	public void setToashShowTime(int toashShowTime) {
		this.toashShowTime = toashShowTime;
	}
	
}

