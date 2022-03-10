package ro.ase.acs.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ro.ase.acs.contracts.DatabaseOperation;
import ro.ase.acs.contracts.DatabaseParser;

public class SQL implements DatabaseParser<Connection>, DatabaseOperation{
	
	private Connection connection;

	@Override
	public void openConnection() {
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			connection.setAutoCommit(false);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void createTable(Connection tableName) {
		
		String sqlDrop = "DROP TABLE IF EXISTS employees";
		String sqlCreate = "CREATE TABLE employees(id INTEGER PRIMARY KEY,"
				+ "name TEXT, address TEXT, salary REAL)";
		
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sqlDrop);
			statement.executeUpdate(sqlCreate);
			statement.close();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void insert(String firstField, String secondField, String thirdField, String firstValue, String secondValue,
			String thirdValue) {
		
		String sqlInsert = "INSERT INTO employees VALUES(1, " + firstValue + ", "+ secondValue + ", " + thirdValue + ")";
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sqlInsert);
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		String sqlInsertWithParams = "INSERT INTO employees VALUES (?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sqlInsertWithParams);
			preparedStatement.setInt(1, 2);
			preparedStatement.setString(2, firstValue);
			preparedStatement.setString(3, secondValue);
			preparedStatement.setDouble(4, Double.valueOf(thirdValue));
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void select() {
		
		String sqlSelect = "SELECT * FROM employees";
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqlSelect);
			while(rs.next()) {
				int id = rs.getInt("id");
				System.out.println("id: " + id);
				String name = rs.getString(2);
				System.out.println("name: " + name);
				String address = rs.getString("address");
				System.out.println("address: " + address);
				double salary = rs.getDouble("salary");
				System.out.println("salary: " + salary);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
