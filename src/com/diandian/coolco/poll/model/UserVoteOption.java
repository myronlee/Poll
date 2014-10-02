package com.diandian.coolco.poll.model;

import com.diandian.coolco.poll.util.Constant.COLUMN_NAME;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_vote_option")
public class UserVoteOption {
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(foreign = true, columnName = COLUMN_NAME.USER_ID_COLUMN_NAME, foreignAutoRefresh = true)
	private User user;
	@DatabaseField(foreign = true, columnName = COLUMN_NAME.OPTION_ID_COLUMN_NAME, foreignAutoRefresh = true)
	private Option option;
	
	public UserVoteOption() {
	}

	public UserVoteOption(int id, User user, Option option) {
		super();
		this.id = id;
		this.user = user;
		this.option = option;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}
	
}
