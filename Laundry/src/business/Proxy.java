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
		Simulator simulator = map.getOrDefault(machineId, new Simulator(machineId));
		simulator.book();
		map.put(machineId, simulator);
		return true;
	}
	
	public boolean start(String userId, int machineId) {
		if(!connection.checkUserMachineRelation(userId, machineId) || connection.getMachineState(machineId) != 1) {
			return false;
		}
		connection.updateMachineState(machineId, 2, String.valueOf(System.currentTimeMillis()));
		Simulator simulator = map.getOrDefault(machineId, new Simulator(machineId));
		simulator.start();
		map.put(machineId, simulator);
		return true;
	}
	
	public boolean pickup(String userId, int machineId) {
		if(!connection.deleteUserMachineRelation(userId, machineId)) {
			return false;
		}
		connection.updateMachineState(machineId, 0, String.valueOf(System.currentTimeMillis()));
		Simulator simulator = map.getOrDefault(machineId, new Simulator(machineId));
		simulator.pickup();
		map.put(machineId, simulator);
		return true;
	}
		
	public void bookTimeOut(int machineId) {		
		connection.releaseMachine(machineId);
		connection.updateMachineState(machineId, 0, String.valueOf(System.currentTimeMillis()));
		
		//TODO: Send user a message
		System.out.println("Book time out!");
	}
	
	public void washingFinished(int machineId) {
		connection.updateMachineState(machineId, 3, String.valueOf(System.currentTimeMillis()));
		
		//TODO: Send user a message
		System.out.println("Washing finished");
	}
	
	public void idleTimeOut(int machineId) {
		connection.updateMachineState(machineId, 4, String.valueOf(System.currentTimeMillis()));
		
		//TODO: Send user a message
		System.out.println("Idle time out!");
	}
}
