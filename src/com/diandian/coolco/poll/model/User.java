package com.diandian.coolco.poll.model;

import com.diandian.coolco.poll.util.Constant.COLUMN_NAME;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user")
public class User {
	
	@DatabaseField(id = true, columnName=COLUMN_NAME.USER_ID_COLUMN_NAME)
	private int id;
	@DatabaseField
	private String username;
	@DatabaseField
	private String password;
	
	public User() {
	}
	
	public User(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
