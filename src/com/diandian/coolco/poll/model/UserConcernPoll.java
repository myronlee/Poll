package com.diandian.coolco.poll.model;

import com.diandian.coolco.poll.util.Constant.COLUMN_NAME;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "user_concern_poll")
public class UserConcernPoll {
	
	@DatabaseField(generatedId = true)
	private int id;
	@DatabaseField(foreign = true, columnName = COLUMN_NAME.USER_ID_COLUMN_NAME, foreignAutoRefresh = true)
	private User user;
	@DatabaseField(foreign = true, columnName = COLUMN_NAME.POLL_ID_COLUMN_NAME, foreignAutoRefresh = true)
	private Poll poll;
	
	public UserConcernPoll() {
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

	public Poll getPoll() {
		return poll;
	}

	public void setPoll(Poll poll) {
		this.poll = poll;
	}
	
}
