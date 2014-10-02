package com.diandian.coolco.poll.model;

import com.diandian.coolco.poll.util.Constant.COLUMN_NAME;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName="option")
public class Option {
	
	@DatabaseField(id = true, columnName = COLUMN_NAME.OPTION_ID_COLUMN_NAME)
	private int id;
	@DatabaseField(foreign = true, columnName = COLUMN_NAME.POLL_ID_COLUMN_NAME, foreignAutoRefresh = true)
	private Poll belongPoll;
	@DatabaseField
	private String optionContent;
	@DatabaseField
	private int voteNum;
	
	public Option(){
	}

	public Option(int id, Poll poll, String option, int votes) {
		super();
		this.id = id;
		this.belongPoll = poll;
		this.optionContent = option;
		this.voteNum = votes;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Poll getPoll() {
		return belongPoll;
	}

	public void setPoll(Poll poll) {
		this.belongPoll = poll;
	}

	public String getOption() {
		return optionContent;
	}

	public void setOption(String option) {
		this.optionContent = option;
	}

	public int getVotes() {
		return voteNum;
	}

	public void setVotes(int votes) {
		this.voteNum = votes;
	}
	
}
