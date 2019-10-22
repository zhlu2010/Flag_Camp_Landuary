package business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import db.MySQLDBConnection;
import module.Simulator;
import module.Laundry;

public class Proxy {
		
	private static Proxy proxy;
	private Map<Integer, Simulator> map;
	private MySQLDBConnection connection;
	
	private Proxy() {
		map = new HashMap<>();
		connection = new MySQLDBConnection();
	}
	
	public static Proxy getInstance() {
		if(proxy == null) {
			proxy = new Proxy();
		}
		return proxy;
	}
	
	public boolean registerUser(String userId, String password, String firstname, String lastname, String phoneNumber) {
		return connection.registerUser(userId, password, firstname, lastname, phoneNumber);
	}
	
	public boolean verifyLogin(String userId, String password) {
		return connection.verifyLogin(userId, password);
	}
	
	public boolean isAdmin(String userId) {
		return connection.isAdmin(userId);
	}
	
	public String getFullname(String userId) {
		return connection.getFullname(userId);
	}
	
	public List<Laundry> getAllMachineStatus() {
		return connection.getAllMachineStatus();
	}
	
	public List<Laundry> getMyMachineStatus(String userId) {
		return connection.getMyLaundryStatus(userId);
	}
	
	public boolean bookMachine(String userId, int machineId) {
		if(connection.getMachineState(machineId) != 0 || !connection.addUserMachineRelation(userId, machineId)) {
			return false;
		}
		connection.updateMachineState(machineId, 1, String.valueOf(System.currentTimeMillis()));
		Simulator simulator = map.getOrDefault(machineId, new Simulator(userId, machineId));
		simulator.setUserId(userId);
		simulator.book();		
		map.put(machineId, simulator);
		return true;
	}
	
	public boolean start(String userId, int machineId) {
		if(!connection.checkUserMachineRelation(userId, machineId) || connection.getMachineState(machineId) != 1) {
			return false;
		}
		connection.updateMachineState(machineId, 2, String.valueOf(System.currentTimeMillis()));
		Simulator simulator = map.getOrDefault(machineId, new Simulator(userId, machineId));
		simulator.setUserId(userId);
		simulator.start();
		map.put(machineId, simulator);
		return true;
	}
	
	public boolean pickup(String userId, int machineId) {
		if(connection.deleteUserMachineRelation(userId, machineId)) {
			return false;
		}
		connection.updateMachineState(machineId, 0, String.valueOf(System.currentTimeMillis()));
		Simulator simulator = map.getOrDefault(machineId, new Simulator(userId, machineId));
		simulator.setUserId(userId);
		simulator.pickup();
		map.put(machineId, simulator);
		return true;
	}
		
	public void bookTimeOut(String userId, int machineId) {		
		connection.deleteUserMachineRelation(userId, machineId);
		connection.updateMachineState(machineId, 0, String.valueOf(System.currentTimeMillis()));
		
		//TODO: Send user a message
		String message = "Book time out!";
		String emailAddr = connection.getEmail(userId);
		if(emailAddr != null) {
			EmailUtils.sendEmail(new String[] {emailAddr}, EmailUtils.EMAIL_SUBJECT, message);
		}
		System.out.println(message);
	}
	
	public void washingFinished(String userId, int machineId) {
		connection.updateMachineState(machineId, 3, String.valueOf(System.currentTimeMillis()));
		
		//TODO: Send user a message
		String message = "Washing finished";
		String emailAddr = connection.getEmail(userId);
		if(emailAddr != null) {
			EmailUtils.sendEmail(new String[] {emailAddr}, EmailUtils.EMAIL_SUBJECT, message);
		}
		System.out.println(message);
	}
	
	public void idleTimeOut(String userId, int machineId) {
		connection.updateMachineState(machineId, 4, String.valueOf(System.currentTimeMillis()));
		
		//TODO: Send user a message
		String message = "Idle time out!";
		String emailAddr = connection.getEmail(userId);
		if(emailAddr != null) {
			EmailUtils.sendEmail(new String[] {emailAddr}, EmailUtils.EMAIL_SUBJECT, message);
		}
		System.out.println(message);
	}
	
	public boolean addMachine(int machineId) {
		String curr = System.currentTimeMillis() + "";
		return connection.addMachine(machineId, curr);
	}
	
	public boolean deleteMachine(int machineId) {
		return connection.deleteMachine(machineId);
	}
}
