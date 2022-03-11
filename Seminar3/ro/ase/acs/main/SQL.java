package ro.ase.acs.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ro.ase.acs.contracts.DatabaseOperation;
import ro.ase.acs.contracts.DatabaseParser;

public class SQL implements DatabaseParser, DatabaseOperation{
	
	private static Connection connection;
	private static String tableName;
	private static String firstField, secondField, thirdField;

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
	public void createTable(String tableName) {
		
		this.tableName = tableName;
		
		String sqlDrop = "DROP TABLE IF EXISTS " + tableName;
		
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sqlDrop);
			statement.close();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void insert(String firstField, String secondField, String thirdField, String firstValue, String secondValue,
			String thirdValue) {
		
		this.firstField = firstField;
		this.secondField = secondField;
		this.thirdField = thirdField;
		
		String sqlCreate = "CREATE TABLE " + tableName + "(id INTEGER PRIMARY KEY,"
				+ firstField + " TEXT, " + secondField + " TEXT, " + thirdField + " TEXT)";
		
		String sqlInsert = "INSERT INTO " + tableName + " VALUES(1, '" + firstValue + "', '"+ secondValue + "', '" + thirdValue + "')";
		Statement statement;
		try {
			statement = connection.createStatement();
			statement.executeUpdate(sqlCreate);
			statement.executeUpdate(sqlInsert);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		String sqlInsertWithParams = "INSERT INTO " + tableName + " VALUES (?,?,?,?)";
		PreparedStatement preparedStatement;
		try {
			preparedStatement = connection.prepareStatement(sqlInsertWithParams);
			preparedStatement.setInt(1, 2);
			preparedStatement.setString(2, firstValue);
			preparedStatement.setString(3, secondValue);
			preparedStatement.setString(4, thirdValue);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void select() {
		
		String sqlSelect = "SELECT * FROM " + tableName;
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sqlSelect);
			while(rs.next()) {
				int id = rs.getInt("id");
				System.out.println("id: " + id);
				String first = rs.getString(2);
				System.out.println(firstField + ": " + first);
				String second = rs.getString(3);
				System.out.println(secondField + ": " + second);
				String third = rs.getString(4);
				System.out.println(thirdField + ": " + third);
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
