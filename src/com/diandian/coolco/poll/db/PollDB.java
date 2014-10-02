package com.diandian.coolco.poll.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.diandian.coolco.poll.model.Poll;

public class PollDB {
	
	private final String LOG_TAG = getClass().getSimpleName();
	
	private static final String COLUMN_PK = "pk";
	private static final String COLUMN_TITLE = "title";
	private static final String COLUMN_DESCRIPTION = "description";
	private static final String COLUMN_OPTION_LIST = "option_list";
	private static final String COLUMN_LEGAL_OPTION_NUM = "legal_option_num";
	private static final String COLUMN_IS_ANONYMOUS = "is_anonymous";
	private static final String COLUMN_CAN_SEE_RESULT_FIRST = "can_see_result_first";
	private static final String COLUMN_CAN_CHOOSE_AGAIN = "can_choose_again";
	private static final String COLUMN_CAN_CHOOSE_ANONYMOUSLY = "can_choose_annoymously";
	private static final String COLUMN_CLOSE_DATETIME = "close_datetime";
	private static final String COLUMN_CREATE_DATETIME = "create_datetime";
	
	private static final String DATABASE_NAME = "poll.db";
	
	private static final int DATABASE_VERSION = 1;
	
	private static final String TABLE_NAME = "poll";
	
	private static final String CREATE_TABLE_SQL="CREATE TABLE IF NOT EXISTS "+ TABLE_NAME + "(" +
			"_id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_PK + " INTEGER, " +
			COLUMN_TITLE + " TEXT, " +
			COLUMN_DESCRIPTION + " TEXT, " +
			COLUMN_OPTION_LIST + " TEXT, " +
			COLUMN_LEGAL_OPTION_NUM + " SMALLINT DEFAULT 1, " +	
			COLUMN_IS_ANONYMOUS + " SMALLINT DEFAULT 0, " +	
			COLUMN_CAN_SEE_RESULT_FIRST + " SMALLINT DEFAULT 0, " +	
			COLUMN_CAN_CHOOSE_AGAIN + " SMALLINT DEFAULT 0, " +	
			COLUMN_CAN_CHOOSE_ANONYMOUSLY + " SMALLINT DEFAULT 1, " +	
			COLUMN_CREATE_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP, " +
			COLUMN_CLOSE_DATETIME + " DATETIME DEFAULT CURRENT_TIMESTAMP " +
			")";
	
	private static class DBHelper extends SQLiteOpenHelper{

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			//Add alter sentence here when the structure of the table changes
		}
		
	}
	
	private DBHelper helper;
	private SQLiteDatabase db;
	private Context context;
	
	public PollDB(Context context) {
		this.context = context;
	}
	
	public void open() {
		helper = new DBHelper(context);
		//因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);
		//所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}
	
	public boolean save(List<Poll> polls) {
        db.beginTransaction();
        try {
        	for (Poll poll : polls) {
        		if (!save(poll)) {
					return false;
				}
        	}
        	db.setTransactionSuccessful();
        } finally {
        	db.endTransaction();
        }
        return true;
	}
	
	public boolean save(Poll poll) {
		ContentValues cv = new ContentValues();
//		cv.put(COLUMN_TITLE, poll.title);
//		cv.put(COLUMN_DESCRIPTION, poll.description);
////		cv.put(COLUMN_OPTION_LIST, poll.options);
//		cv.put(COLUMN_LEGAL_OPTION_NUM, poll.legalOptionNum);
//		cv.put(COLUMN_IS_ANONYMOUS, poll.isAnonymous);
//		cv.put(COLUMN_CAN_SEE_RESULT_FIRST, poll.canSeeResultFirst);
//		cv.put(COLUMN_CAN_CHOOSE_AGAIN, poll.canChooseAgain);
//		cv.put(COLUMN_CAN_CHOOSE_ANONYMOUSLY, poll.canChooseAnonymously);
//		cv.put(COLUMN_CLOSE_DATETIME, poll.closeDateTime);
//		cv.put(COLUMN_CREATE_DATETIME, poll.createDateTime);
		return db.insert(TABLE_NAME, null, cv) > 0;
	}

	public List<Poll> getAll() {
		ArrayList<Poll> polls = new ArrayList<Poll>();
		Cursor c = db.rawQuery("SELECT "
				+ COLUMN_PK +", " 
				+ COLUMN_TITLE +", " 
				+ COLUMN_DESCRIPTION +", " 
				+ COLUMN_OPTION_LIST +", " 
				+ COLUMN_LEGAL_OPTION_NUM +", " 
				+ COLUMN_IS_ANONYMOUS +", " 
				+ COLUMN_CAN_SEE_RESULT_FIRST +", " 
				+ COLUMN_CAN_CHOOSE_AGAIN +", " 
				+ COLUMN_CAN_CHOOSE_ANONYMOUSLY +", " 
				+ "datetime(" + COLUMN_CLOSE_DATETIME + ",'localtime') " 
				+ "datetime(" + COLUMN_CREATE_DATETIME + ",'localtime') " 
				+ "FROM " + TABLE_NAME, new String []{});
		while (c.moveToNext()) {
			Poll poll = new Poll();
//			poll.pk = c.getInt(0);
//			poll.title = c.getString(1);
//			poll.description = c.getString(2);
////			poll.options = c.getString(3);
//			poll.legalOptionNum = c.getInt(4);
//			poll.isAnonymous = c.getInt(5);
//			poll.canSeeResultFirst = c.getInt(6);
//			poll.canChooseAgain = c.getInt(7);
//			poll.canChooseAnonymously = c.getInt(8);
//			poll.closeDateTime = c.getString(9);
//			poll.createDateTime = c.getString(10);
			polls.add(poll);
		}
		c.close();
		return polls;
	}
	
	public String getLatestPollCreateTime() {
		Cursor c = db.rawQuery("SELECT "
				+ "MAX( datetime( " + COLUMN_CREATE_DATETIME + ",'localtime') ) " 
				+ "FROM " + TABLE_NAME, new String []{});
		String latestMsgPubTime = "2000-11-11 11:11:11";
		if (c.moveToFirst()) {
			latestMsgPubTime = c.getString(0);
		}
		c.close();
		return latestMsgPubTime;
		
	}

	public int delAll() {
		return db.delete(TABLE_NAME, "1", new String[]{});
	}
	
	public void close() {
		db.close();
	}
	
	public Boolean isOpen() {
		return db.isOpen();
	}

	private String list2string(List<String> optionList) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(optionList.get(0));
		for (int i = 1; i < optionList.size(); i++) {
			stringBuilder.append("-").append(optionList.get(i));
		}
		return stringBuilder.toString();
	}
	
	private List<String> string2list(String saveString) {
		String[] tmpStrings = saveString.split("-");
		List<String> optionList = new ArrayList<String>(Arrays.asList(tmpStrings));
		return optionList;
	}
}
