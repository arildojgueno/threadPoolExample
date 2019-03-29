package br.org.ajg.utildev.example.threadpool.genericExample;

import java.time.LocalDateTime;

public class LogUtil {

	static boolean enabled = true;
	
	public static void enable(){
		enabled = true; 
	}
	
	public static void disable(){
		enabled = false; 
	}
	
	public static void log(int myID, String msg) {
		if(enabled){
			System.out.println(String.format("%s: %s", assemblyLogHeader(myID), msg));
		}
	}
	
	public static String assemblyLogHeader(int myID) {
		return String.format("[ %s | %s | %3d ]", LocalDateTime.now().toString(), Thread.currentThread().getName(),
				myID);
	}
}

