package module;

public class Simulator {
	public static final int WASHING_TIME = 60;
	public static final int IDLE_TIME = 15;
	
	public static int timeLeft(String startTime) {
		long st = Long.valueOf(startTime);
		long timePast = (System.currentTimeMillis() - st) / 1000 / 60;
		if(timePast > WASHING_TIME + IDLE_TIME) {
			return -1 - WASHING_TIME;
		}
		return WASHING_TIME - (int)timePast;
		
	}
	
	
	//status: 0 for vacant, 1 for using, 2 for finished, 3 for idle time out
	public static int changeStatus(int timeLeft) {
		if(timeLeft > 0) {
			return 1;
		} else if(timeLeft >= IDLE_TIME * -1) {
			return 2;
		} else {
			return 3;
		}
	}
}
