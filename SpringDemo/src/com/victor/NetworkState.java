package com.victor;
/**
 * @author HuHui
 * @���ܣ�������������Ƿ���ͨ
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
        new Thread(ns).start();//�����߳�  
    } 
 
    // �ж�����״̬  
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
                //System.out.println("����ֵΪ:"+line);  
            } 
            is.close(); 
            isr.close(); 
            br.close(); 
 
            if (null != sb && !sb.toString().equals("")) { 
                String logString = ""; 
                if (sb.toString().indexOf("TTL") > 0) { 
                    // ���糩ͨ  
                    logString = "����������ʱ�� " + this.getCurrentTime(); 
                  System.out.println(logString);  
                } else { 
                    // ���粻��ͨ  
                    logString = "����Ͽ���ʱ�� " + this.getCurrentTime(); 
                  System.out.println(logString);  
                } 
                //this.writeIntoLog(logString); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
 
    // ��õ�ǰʱ��  
    public String getCurrentTime() { 
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss"); 
        String time = sdf.format(new Date()); 
        return time; 
    } 
 
    // ����Ϣд����־�ļ�  
    public void writeIntoLog(String logString) { 
        File file = null; 
        FileWriter fw = null; 
        BufferedWriter bw = null; 
        try { 
            file = new File("C:\\netWorkState.log"); 
            if (!file.exists()) { 
                file.createNewFile();// ��������ڸ��ļ����򴴽�  
                String sets="attrib +H \""+file.getAbsolutePath()+"\""; 
                Runtime.getRuntime().exec(sets);//����־�ļ�����  
            } 
            fw = new FileWriter(file, true); 
            bw = new BufferedWriter(fw); 
            fw.append(logString + "\r\n");// ����  
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
                // ÿ��3���Ӳ���һ�������Ƿ���ͨ  
                Thread.sleep(3000); 
            } catch (InterruptedException e) { 
                // TODO Auto-generated catch block  
                e.printStackTrace(); 
            } 
        } 
    } 
 
} 