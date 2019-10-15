package module;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Landuary {
	
	private int machineId;
	private int state;
	private int timeLeft;
	
	private Landuary(LanduaryBuilder builder) {
		this.machineId = builder.machineId;
		this.state = builder.state;
		this.timeLeft = builder.timeLeft;
	}
	
	public int getMachineId() {
		return machineId;
	}

	public int getState() {
		return state;
	}

	public int getTimeLeft() {
		return timeLeft;
	}
	
	public JSONObject toJSONObject() {
		JSONObject obj = new JSONObject();
		try {
			obj.put("machineId", machineId);
			obj.put("state", state);
			obj.put("timeLeft", timeLeft);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj;
	}
		
	public static class LanduaryBuilder {
		private int machineId;
		private int state;
		private int timeLeft;
		
		public int getMachineId() {
			return machineId;
		}
		public void setMachineId(int machineId) {
			this.machineId = machineId;
		}
		public int getState() {
			return state;
		}
		public void setState(int state) {
			this.state = state;
		}
		public int getTimeLeft() {
			return timeLeft;
		}
		public void setTimeLeft(int timeLeft) {
			this.timeLeft = timeLeft;
		}

		public Landuary build() {
			return new Landuary(this);
		}
	}
	
	public static List<Landuary> getDummyData() {
		List<Landuary> list = new ArrayList<>();
		LanduaryBuilder builder = new LanduaryBuilder();
		builder.setMachineId(10);
		builder.setState(0);
		builder.setTimeLeft(50);
		list.add(builder.build());
		builder.setMachineId(4);
		builder.setState(1);
		builder.setTimeLeft(14);
		list.add(builder.build());
		return list;
	} 
}
