package db;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Connection;

public class MySQLTableCreation {
	// Run this as Java application to reset db schema.
	public static void main(String[] args) {
		try {
			// Step 1 Connect to MySQL.
			System.out.println("Connecting to " + MySQLDBUtil.URL);
			Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
			Connection conn = DriverManager.getConnection(MySQLDBUtil.URL);
			if (conn == null) {
				return;
			}

			// Step 2 Drop tables in case they exist.
			Statement statement = conn.createStatement();
			String sql = "DROP TABLE IF EXISTS usersMachinesConnection";
			statement.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS report";
			statement.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS machines";
			statement.executeUpdate(sql);

			sql = "DROP TABLE IF EXISTS users";
			statement.executeUpdate(sql);

			// create machine table state number (0:ok, 1:in use, 2:finished, 3:...)
			sql = "CREATE TABLE machines ("
					+ "machine_id VARCHAR(255) NOT NULL,"
					+ "state INT NOT NULL,"
					+ "start_time VARCHAR(255) NOT NULL,"
					+ "PRIMARY KEY (machine_id)"
					+ ")";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE users ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "password VARCHAR(255) NOT NULL,"
					+ "first_name VARCHAR(255),"
					+ "last_name VARCHAR(255),"
					+ "phone VARCHAR(255),"
					+ "is_admin BOOLEAN NOT NULL,"
					+ "PRIMARY KEY (user_id)"
					+ ")";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE report ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "info VARCHAR(255) NOT NULL"
					+ ")";
			statement.executeUpdate(sql);

			sql = "CREATE TABLE usersMachinesConnection ("
					+ "user_id VARCHAR(255) NOT NULL,"
					+ "machine_id VARCHAR(255) NOT NULL,"
					+ "PRIMARY KEY (user_id, machine_id),"
					+ "FOREIGN KEY (user_id) REFERENCES users(user_id),"
					+ "FOREIGN KEY (machine_id) REFERENCES machines(machine_id)"
					+ ")";
			statement.executeUpdate(sql);
			
			String fifteenEarlier = String.valueOf(System.currentTimeMillis() - 15 * 60 * 1000);
			
			sql = "INSERT INTO machines VALUES('10', '1', '" + fifteenEarlier + "')";
			statement.executeUpdate(sql);
			
			String curr = String.valueOf(System.currentTimeMillis());
			
			sql = "INSERT INTO machines VALUES('5', '0', '" + curr + "')";
			statement.executeUpdate(sql);
			
			sql = "INSERT INTO users VALUES('John@laioffer.com', '1234', 'John', 'Smith', '555-666-7788', false)";
			statement.executeUpdate(sql);
			
			sql = "INSERT IGNORE INTO usersMachinesConnection VALUES('John@laioffer.com', '10')";
			statement.executeUpdate(sql);
			
			conn.close();
			System.out.println("Import done successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}