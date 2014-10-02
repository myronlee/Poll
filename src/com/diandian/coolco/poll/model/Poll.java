package com.diandian.coolco.poll.model;

import java.util.ArrayList;

import com.diandian.coolco.poll.util.Constant.COLUMN_NAME;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "poll")
public class Poll {
	@DatabaseField(id = true, columnName = COLUMN_NAME.POLL_ID_COLUMN_NAME)
	private int id;
	
	@DatabaseField(foreign = true, columnName = COLUMN_NAME.USER_ID_COLUMN_NAME, foreignAutoRefresh = true)
	private User createUserObj;
	
	/** Used when Gson convert json String to object */
	@DatabaseField
	private int createUser;
	
	/** Used when Gson convert json String to object */
	@DatabaseField(id = false, foreign = false, dataType = DataType.SERIALIZABLE)
	private ArrayList<Integer> concernUsers;
	
	public ArrayList<Integer> getConcernUsers() {
		return concernUsers;
	}

	public void setConcernUsers(ArrayList<Integer> concernUsers) {
		this.concernUsers = concernUsers;
	}

	//	/** The id here equals to PK in the server database */
//	@DatabaseField
//	public int pk;
	@DatabaseField
	private String title;
	@DatabaseField
	private String description;
//	@DatabaseField
//	public String options;
	@DatabaseField
	private int legalOptionNum;
	@DatabaseField
	private int isAnonymous;
	@DatabaseField
	private int canSeeResultFirst;
	@DatabaseField
	private int canChooseAgain;
	@DatabaseField
	private int canChooseAnonymously;
	@DatabaseField
	private String closeDateTime; 
	@DatabaseField
	private String createDateTime;

	public Poll() {
	}
	
	public Poll(int id, String title, String description, int legalOptionNum, int isAnonymous, int canSeeResultFirst,
			int canChooseAgain, int canChooseAnonymously, String closeDateTime, String createDateTime) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.legalOptionNum = legalOptionNum;
		this.isAnonymous = isAnonymous;
		this.canSeeResultFirst = canSeeResultFirst;
		this.canChooseAgain = canChooseAgain;
		this.canChooseAnonymously = canChooseAnonymously;
		this.closeDateTime = closeDateTime;
		this.createDateTime = createDateTime;
	}

	
	public User getCreateUserObj() {
		return createUserObj;
	}

	public void setCreateUserObj(User createUserObj) {
		this.createUserObj = createUserObj;
	}

	public void setCreateUser(int createUser) {
		this.createUser = createUser;
	}

	public int getCreateUser() {
		return createUser;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLegalOptionNum() {
		return legalOptionNum;
	}

	public void setLegalOptionNum(int legalOptionNum) {
		this.legalOptionNum = legalOptionNum;
	}

	public int getIsAnonymous() {
		return isAnonymous;
	}

	public void setIsAnonymous(int isAnonymous) {
		this.isAnonymous = isAnonymous;
	}

	public int getCanSeeResultFirst() {
		return canSeeResultFirst;
	}

	public void setCanSeeResultFirst(int canSeeResultFirst) {
		this.canSeeResultFirst = canSeeResultFirst;
	}

	public int getCanChooseAgain() {
		return canChooseAgain;
	}

	public void setCanChooseAgain(int canChooseAgain) {
		this.canChooseAgain = canChooseAgain;
	}

	public int getCanChooseAnonymously() {
		return canChooseAnonymously;
	}

	public void setCanChooseAnonymously(int canChooseAnonymously) {
		this.canChooseAnonymously = canChooseAnonymously;
	}

	public String getCloseDateTime() {
		return closeDateTime;
	}

	public void setCloseDateTime(String closeDateTime) {
		this.closeDateTime = closeDateTime;
	}

	public String getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(String createDateTime) {
		this.createDateTime = createDateTime;
	}
	
}
