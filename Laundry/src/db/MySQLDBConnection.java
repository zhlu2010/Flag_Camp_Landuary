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
	
	public List<Laundry> getAllMachineStatus() {
		List<Laundry> list = new ArrayList<>();
		if(conn == null) {
			return list;
		}
		try {
			String sql = "SELECT * FROM machines";
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			LaundryBuilder builder = new LaundryBuilder();
			
			while(rs.next()) {
				builder.setMachineId(rs.getInt("machine_id"));
				builder.setState(rs.getInt("state"));
				builder.setTimeLeft(rs.getInt("time_left"));
				
				list.add(builder.build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public String getFullname(String userId) {
		return "";
	}
	
	public boolean verifyLogin(String userId, String password) {
		if(conn == null) {
			return false;
		}
		try {
			String sql = "SELECT user_id FROM users WHERE user_id = ? AND password = ?";
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
	
	public boolean isAdmin(String userId) {
		if(conn == null) {
			return false;
		}
		try {
			String sql = "SELECT is_admin FROM users WHERE user_id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, userId);
			ResultSet rs = stmt.executeQuery();
			if(rs.next()) {
				return rs.getBoolean("is_admin");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}
	
	public List<Laundry> getMyLaundryStatus(String userId) {
		List<Laundry> list = new ArrayList<>();
		if(conn == null) {
			return list;
		}
		try {
			String sql = "SELECT machines.machine_id, state, time_left FROM machines, usersmachinesconnection "
					+ "where machines.machine_id = usersmachinesconnection.machine_id and user_id = ?";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, userId);
			ResultSet rs = statement.executeQuery();
			
			LaundryBuilder builder = new LaundryBuilder();
			
			while(rs.next()) {
				builder.setMachineId(rs.getInt("machine_id"));
				builder.setState(rs.getInt("state"));
				builder.setTimeLeft(rs.getInt("time_left"));
				
				list.add(builder.build());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
}
