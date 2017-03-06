package com.victor;
/**
 * @author HuHui
 * @功能：持续检测网络是否连通
 */ 
 
import java.io.BufferedReader; 
import java.io.BufferedWriter; 
import java.io.File; 
import java.io.FileWriter; 
import java.io.IOException; 
import java.io.InputStream; 
import java.io.InputStreamReader; 
import java.text.SimpleDateFormat; 
import java.util.Date; 
 
public class NetworkState implements Runnable {
	private static String url=""; 
	public NetworkState(String url){
		this.url=url;
	}
	public NetworkState(){
	}
	
    public static void main(String[] args) { 
        // TODO Auto-generated method stub  
        NetworkState ns = new NetworkState("www.baidu.com"); 
        new Thread(ns).start();//启动线程  
    } 
 
    // 判断网络状态  
    public void isConnect(String url) { 
        Runtime runtime = Runtime.getRuntime(); 
        try { 
            Process process = runtime.exec("ping " + url); 
            InputStream is = process.getInputStream(); 
            InputStreamReader isr = new InputStreamReader(is); 
            BufferedReader br = new BufferedReader(isr); 
            String line = null; 
            StringBuffer sb = new StringBuffer(); 
            while ((line = br.readLine()) != null) { 
                sb.append(line);
                //System.out.println("返回值为:"+line);  
            } 
            is.close(); 
            isr.close(); 
            br.close(); 
 
            if (null != sb && !sb.toString().equals("")) { 
                String logString = ""; 
                if (sb.toString().indexOf("TTL") > 0) { 
                    // 网络畅通  
                    logString = "网络正常，时间 " + this.getCurrentTime(); 
                  System.out.println(logString);  
                } else { 
                    // 网络不畅通  
                    logString = "网络断开，时间 " + this.getCurrentTime(); 
                  System.out.println(logString);  
                } 
                //this.writeIntoLog(logString); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
 
    // 获得当前时间  
    public String getCurrentTime() { 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
        String time = sdf.format(new Date()); 
        return time; 
    } 
 
    // 将信息写入日志文件  
    public void writeIntoLog(String logString) { 
        File file = null; 
        FileWriter fw = null; 
        BufferedWriter bw = null; 
        try { 
            file = new File("C:\\netWorkState.log"); 
            if (!file.exists()) { 
                file.createNewFile();// 如果不存在该文件，则创建  
                String sets="attrib +H \""+file.getAbsolutePath()+"\""; 
                Runtime.getRuntime().exec(sets);//将日志文件隐藏  
            } 
            fw = new FileWriter(file, true); 
            bw = new BufferedWriter(fw); 
            fw.append(logString + "\r\n");// 换行  
        } catch (Exception e) { 
            // TODO: handle exception  
            e.printStackTrace(); 
        } finally { 
            try { 
                bw.close(); 
                fw.close(); 
            } catch (IOException e) { 
                // TODO Auto-generated catch block  
                e.printStackTrace(); 
            } 
        } 
 
    } 
 
    @Override 
    public void run() { 
        // TODO Auto-generated method stub  
        while (true) { 
            this.isConnect(""); 
            try { 
                // 每隔3秒钟测试一次网络是否连通  
                Thread.sleep(3000); 
            } catch (InterruptedException e) { 
                // TODO Auto-generated catch block  
                e.printStackTrace(); 
            } 
        } 
    } 
 
} 