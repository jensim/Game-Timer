package se.jensim.warhammer.gametimer;

public class HelperTool {

	public static final int TIME_M = 60 * 1000;
	public static final int TIME_H = 60 * TIME_M;

	/**
	 * format: hh:mm:ss:MMM
	 * @param mills
	 * @return
	 */
	public static String millsToTimeString(long mills) {
		long tmp_remain = mills;
		long timeH = mills / TIME_H;
		tmp_remain -= timeH * TIME_H;
		long timeM = tmp_remain / TIME_M;
		tmp_remain -= timeM * TIME_M;
		long timeS = tmp_remain / 1000;
		tmp_remain -= timeS * 1000;

		return String.format("%02d:%02d:%02d:%03d", timeH, timeM, timeS, tmp_remain);
	}

	/**
	 * format: 0h 0m 0s
	 * @param mills
	 * @return
	 */
	public static String millsToTimeString2(long mills) {
		long timeH = mills / TIME_H;
		long timeM = mills % TIME_H;
		timeM /= TIME_M;
		long timeS = mills % TIME_M;
		timeS /= 1000;
		
		
		StringBuilder sb = new StringBuilder();
		if(mills < 0){
			sb.append("- ");
		}
		if (Math.abs(timeH) > 0) {
			sb.append(Math.abs(timeH)).append("h ");
		}
		if (Math.abs(timeM) > 0) {
			sb.append(Math.abs(timeM)).append("m ");
		}
		if (Math.abs(timeS) > 0) {
			sb.append(Math.abs(timeS)).append("s");
		}
		
		return sb.toString();
		//return String.format("%02dh %02dm %02ds", timeH, timeM, timeS); 
	}
}
