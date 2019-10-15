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
}
