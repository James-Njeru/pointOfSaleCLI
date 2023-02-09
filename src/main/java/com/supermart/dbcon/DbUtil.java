package com.supermart.dbcon;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUtil {
	public static void close(Connection connection) {
		if(connection != null) {
			try {
				connection.close();
			}catch(SQLException e) {}
		}
	}
	
	public static void close(PreparedStatement statement) {
		if(statement != null) {
			try {
				statement.close();
			}catch(SQLException e) {}
		}
	}
	
	public static void close(ResultSet resultSet) {
		if(resultSet != null) {
			try {
				resultSet.close();
			}catch(SQLException e) {}
		}
	}
}
