package com.techhive.shivamweb.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class db {

	public static void main(String[] args) {

		String connectionUrl = "jdbc:sqlserver://localhost:1433;databaseName=bbb;integratedSecurity=true";

		try {
			// Load SQL Server JDBC driver and establish connection.
			System.out.print("Connecting to SQL Server ... ");
			try (Connection connection = DriverManager.getConnection(connectionUrl)) {
				System.out.println("Done.");
				System.out.print("Dropping and creating database 'SampleDB' ... ");
				String sql = "DROP DATABASE IF EXISTS [SampleDB]; CREATE DATABASE [SampleDB]";
				try (Statement statement = connection.createStatement()) {
					statement.executeUpdate(sql);
					System.out.println("Done.");
				}
			}
		} catch (Exception e) {
			System.out.println();
			// e.printStackTrace();
		}
	}
}
