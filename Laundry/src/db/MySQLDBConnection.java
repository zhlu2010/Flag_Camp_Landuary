package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import module.Laundry;
import module.Laundry.LaundryBuilder;
import module.Simulator;

public class MySQLDBConnection {
	private Connection conn;

	public MySQLDBConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			conn = DriverManager.getConnection(MySQLDBUtil.URL);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void close() {
		// TODO Auto-generated method stub
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean registerUser(String userId, String password, String firstname, String lastname, String phone) {
		if (conn == null) {
			System.err.println("DB connection failed");
			return false;
		}
		try {
			String sql = "INSERT IGNORE INTO users VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setString(2, password);
			ps.setString(3, firstname);
			ps.setString(4, lastname);
			ps.setString(5, phone);
			ps.setBoolean(6, false);
			return ps.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean verifyLogin(String userId, String password) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "SELECT * FROM users WHERE user_id = ? AND password = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			stmt.setString(2, password);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public String getFullname(String userId) {
		if (conn == null) {
			return "";
		}
		String name = "";
		try {
			String sql = "SELECT first_name, last_name FROM users WHERE user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				name = rs.getString("first_name") + " " + rs.getString("last_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return name;
	}

	public boolean isAdmin(String userId) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "SELECT is_admin FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getBoolean("is_admin");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public List<Laundry> getAllMachineStatus() {
		List<Laundry> list = new ArrayList<>();
		if (conn == null) {
			return list;
		}
		try {
			String sql = "SELECT * FROM machines";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();

			LaundryBuilder builder = new LaundryBuilder();

			while (rs.next()) {
				int machineId = rs.getInt("machine_id");
				builder.setMachineId(machineId);
				int state = rs.getInt("state");
				String startTime = rs.getString("start_time");
				int timeLeft = Simulator.timeLeft(state, startTime);
				builder.setState(state);
				builder.setTimeLeft(Math.abs(timeLeft));

				list.add(builder.build());
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<Laundry> getMyLaundryStatus(String userId) {
		List<Laundry> list = new ArrayList<>();
		if (conn == null) {
			return list;
		}
		try {
			String sql = "SELECT machines.machine_id, state, start_time FROM machines, usersmachinesconnection "
					+ "where machines.machine_id = usersmachinesconnection.machine_id and user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();

			LaundryBuilder builder = new LaundryBuilder();

			while (rs.next()) {
				int machineId = rs.getInt("machine_id");
				builder.setMachineId(machineId);
				int state = rs.getInt("state");
				String startTime = rs.getString("start_time");
				int timeLeft = Simulator.timeLeft(state, startTime);
				builder.setState(state);
				builder.setTimeLeft(timeLeft);
				list.add(builder.build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void updateMachineState(int machineId, int state, String startTime) {
		if (conn == null) {
			return;
		}
		try {
			String updateSql = "UPDATE machines SET state = ?, start_time = ? WHERE machine_id = ?";
			PreparedStatement updStmt = conn.prepareStatement(updateSql);
			updStmt.setInt(1, state);
			updStmt.setString(2, startTime);
			updStmt.setInt(3, machineId);
			updStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int getMachineState(int machineId) {
		try {
			String sql = "SELECT state FROM machines WHERE machine_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, machineId);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt("state");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public boolean checkUserMachineRelation(String userId, int machineId) {
		if(conn == null) {
			return false;
		}
		try {
			String sql = "SELECT * FROM usersmachinesconnection WHERE user_id = ? AND machine_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, machineId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean addUserMachineRelation(String userId, int machineId) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "INSERT IGNORE INTO usersMachinesConnection VALUES(?, ?)";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, machineId);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteUserMachineRelation(String userId, int machineId) {
		if (conn == null) {
			return false;
		}
		try {
			String sql = "DELETE FROM usersmachinesconnection WHERE user_id = ? AND machine_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, userId);
			ps.setInt(2, machineId);
			return ps.executeUpdate() == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean releaseMachine(int machineId) {
		if(conn == null) {
			return false;
		}
		try {
			String sql = "DELETE FROM usersmachinesconnection WHERE machine_id = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, machineId);
			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
