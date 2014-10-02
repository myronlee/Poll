package com.diandian.coolco.poll.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DB extends SQLiteOpenHelper {
	private final String LOG_TAG = getClass().getSimpleName();
	
	private static final String DATABASE_NAME = "ipoll.db";
	private static final int DATABASE_VERSION = 1;
	
	private static final String USER_TABLE_NAME = "user";
	private static final String COLUMN_USER_PK = "pk";
	private static final String COLUMN_USERNAME = "username";
	private static final String CREATE_USER_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + USER_TABLE_NAME + "(" +
			"_id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_USER_PK + " INTEGER, " +
			COLUMN_USERNAME + " TEXT " +
			")";
	
	private static final String POLL_TABLE_NAME = "poll";
	private static final String COLUMN_POLL_PK = "pk";
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
	private static final String CREATE_POLL_TABLE_SQL = "CREATE TABLE IF NOT EXISTS " + POLL_TABLE_NAME + "(" +
			"_id" + " INTEGER PRIMARY KEY AUTOINCREMENT," +
			COLUMN_POLL_PK + " INTEGER, " +
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
	
	private static final String OPTION_TABLE_NAME = "option";
	private static final String COLUMN_OPTION_PK = "pk";
	private static final String COLUMN_OPTION = "option";
	private static final String COLUMN_VOTES = "votes";
	
	public DB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_POLL_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
}
