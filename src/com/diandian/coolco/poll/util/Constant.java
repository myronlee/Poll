package com.diandian.coolco.poll.util;

public class Constant {
	public class URL {
		public static final String HOST = "http://192.168.1.106:8000/";
		public static final String CREATE_POLL = HOST + "createPoll/";
		public static final String GET_POLL = HOST + "getPoll/";
	}
	
	public class COLUMN_NAME {
		public static final String USER_ID_COLUMN_NAME = "user_id";
		public static final String POLL_ID_COLUMN_NAME = "poll_id";
		public static final String OPTION_ID_COLUMN_NAME = "option_id";
	}
}
