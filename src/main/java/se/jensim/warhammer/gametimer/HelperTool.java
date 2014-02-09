package se.jensim.warhammer.gametimer;

public class HelperTool {
	
	public static final int TIME_M = 60 * 1000;
	public static final int TIME_H = 60 * TIME_M;
	
	public static String millsToTimeString(long mills){
		long tmp_remain = mills;
		long timeH = mills / TIME_H;
		tmp_remain -= timeH * TIME_H;
		long timeM = tmp_remain / TIME_M;
		tmp_remain -= timeM * TIME_M;
		long timeS = tmp_remain / 1000;
		tmp_remain -= timeS*1000;
		
		
		return String.format("%02d:%02d:%02d:%03d", timeH, timeM, timeS, tmp_remain);
	}
}
