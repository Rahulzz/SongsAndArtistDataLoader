package com.ias.songs;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException; 

public class DatabaseConnection { 
	// JDBC driver name and database URL 
	static final String JDBC_DRIVER = "org.h2.Driver";   
	static final String DB_URL = "jdbc:h2:tcp://localhost/~/test";

	//  Database credentials 
	static final String USER = "sa"; 
	static final String PASS = "";
	
	public Connection openConnection() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC_DRIVER);
		//STEP 2: Open a connection 
		System.out.println("CONNECTING TO DATABASE");
		Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
		conn.setAutoCommit(false);
        
        return conn;
	}
	
	public void addArtist(Connection conn, Long id, String name) throws SQLException {
		String sql =  "INSERT INTO artist VALUES ("+id+", ?)";
		
		PreparedStatement statement= conn.prepareStatement(sql);
		statement.setString(1,name);
		
		statement.executeUpdate();
		statement.close();
	}
	
	public void addSong(Connection conn, Long id, String title, Long artist, String imagePath, Boolean available, Long songxCount) throws SQLException {
		String sql =  "INSERT INTO song VALUES ("+id+", ?, "+artist+", ?, "+available+", "+songxCount+")";
		
		PreparedStatement statement= conn.prepareStatement(sql);
		statement.setString(1,title);
		statement.setString(2,imagePath);
		
		statement.executeUpdate();
		statement.close();
	}
	
	public void closeConnection(Connection conn) throws SQLException {
		conn.close(); 
		//STEP 2: Open a connection 
		System.out.println("DISCONNECTING FROM DATABASE");
	}
}
