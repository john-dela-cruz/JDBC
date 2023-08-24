package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCSDDemo {
	
	static Connection conn;
	static PreparedStatement pstmt;

	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		
		String url = "jdbc:mysql://localhost:3306/cselec02";
		String user = "root";
		String password = "root";
		
		try{
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("DB Connected");
			//insertData();
			//updateData();
			//readData();
			
			//sampleLogin();
			
			//sampleLoginV2();
			
		} catch (SQLException e){
			e.printStackTrace();
		}

	}
	
	private static void readData(){
		try{
			pstmt = conn.prepareStatement("SELECT * FROM user");
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()){
				System.out.println("ID: " + rs.getInt("id"));
				System.out.println("LAST NAME: " + rs.getString("lastname"));
				System.out.println("FIRST NAME: " + rs.getString("firstname"));
				System.out.println("AGE: " + rs.getInt("age"));
				System.out.println("");
			}
			
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	private static void insertData(){
		try {
			pstmt = conn.prepareStatement("INSERT INTO user(id, lastname, firstname, age) VALUES(?,?,?,?);");
			pstmt.setInt(1,6);
			pstmt.setString(2, "Pendora");
			pstmt.setString(3, "Elira");
			pstmt.setInt(4, 24);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void updateData(){
		try {
			pstmt = conn.prepareStatement("UPDATE user SET age=? WHERE firstname LIKE 'Pomu';");
			pstmt.setInt(1,22);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static void sampleLogin() throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Username: ");
		String username = br.readLine();
		
		System.out.print("Password: ");
		String password = br.readLine();
		
		try{
			pstmt = conn.prepareStatement("SELECT password FROM accounts WHERE username LIKE '" + username + "';");
			ResultSet passwordRS = pstmt.executeQuery();
			
			while(passwordRS.next()){
				if(passwordRS.getString("password").equals(password)){
					System.out.println("Logged in");
				}
				else{
					System.out.println("Invalid Credentials");
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private static void sampleLoginV2() throws IOException{
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		System.out.print("Username: ");
		String username = br.readLine();
		
		System.out.print("Password: ");
		String password = br.readLine();
		
		try {
			pstmt = conn.prepareStatement("SELECT username FROM accounts WHERE username LIKE '" + username + "';");
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next() != false){
				pstmt = conn.prepareStatement("SELECT password FROM accounts WHERE username LIKE '" + username + "';");
				ResultSet rsPass = pstmt.executeQuery();
				
				while(rsPass.next()){
					if(rsPass.getString("password").equals(password)){
						System.out.println("Logged in");
					}
					else{
						System.out.println("Password is incorrect");
					}
				}
			}
			else{
				System.out.println("Username does not exist!");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
