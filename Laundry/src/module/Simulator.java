package module;

import java.util.Timer;
import java.util.TimerTask;

import business.Proxy;

public class Simulator {
	public static final int BOOK_TIME = 20;
	public static final int WASHING_TIME = 60;
	public static final int IDLE_TIME = 15;
	
	private int machineId;
	private Timer timer;
	
	public Simulator(int machineId) {
		timer = new Timer();
		this.machineId = machineId;
	}
	
	public int getMachineId() {
		return machineId;
	}
	
	public void book() {
		timer.cancel();
		timer.purge();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			public void run() {
				Proxy proxy = Proxy.getInstance();
				proxy.bookTimeOut(machineId);
			}
			
		}, BOOK_TIME * 60 * 1000);
	}
	
	public void start() {
		timer.cancel();
		timer.purge();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			public void run() {
				Proxy proxy = Proxy.getInstance();
				proxy.washingFinished(machineId);
				waitingForPickup();
			}
			
		}, WASHING_TIME * 1000);
	}
	
	public void waitingForPickup() {
		timer.cancel();
		timer.purge();
		timer = new Timer();
		timer.schedule(new TimerTask() {
			
			public void run() {
				Proxy proxy = Proxy.getInstance();
				proxy.idleTimeOut(machineId);
			}
			
		}, IDLE_TIME * 2 * 1000);
	}
	
	public void pickup() {
		timer.cancel();
		timer.purge();
	}
	
	public static int timeLeft(int state, String startTime) {
		long timePast = (System.currentTimeMillis() - Long.valueOf(startTime)) / 60 / 1000;
		switch(state) {
			case 1: return BOOK_TIME - (int)timePast;
			case 2: return WASHING_TIME - (int)timePast;
			case 3: return IDLE_TIME - (int)timePast;
		}
		return -1;
	}

}
