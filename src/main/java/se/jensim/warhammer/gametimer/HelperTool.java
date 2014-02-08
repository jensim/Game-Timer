package se.jensim.warhammer.gametimer;

public class HelperTool {
	
	public static final int TIME_M = 60 * 1000;
	public static final int TIME_H = 60 * TIME_M;
	
	public static String millsToTimeString(int mills){
		int tmp_remain = mills;
		int timeH = mills / TIME_H;
		tmp_remain -= timeH * TIME_H;
		int timeM = tmp_remain / TIME_M;
		tmp_remain -= timeM * TIME_M;
		int timeS = tmp_remain / 1000;
		
		return String.format("%02d:%02d:%02d", timeH, timeM, timeS);
	}
}
