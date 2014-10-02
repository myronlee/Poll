package com.diandian.coolco.poll.db;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.diandian.coolco.poll.model.Option;
import com.diandian.coolco.poll.model.Poll;
import com.diandian.coolco.poll.model.User;
import com.diandian.coolco.poll.model.UserConcernPoll;
import com.diandian.coolco.poll.model.UserVoteOption;
import com.diandian.coolco.poll.util.Constant.COLUMN_NAME;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class OrmLiteDBHelper extends OrmLiteSqliteOpenHelper {

	// 数据库名称
	private static final String DATABASE_NAME = "ipoll.db";
	// 数据库version
	private static final int DATABASE_VERSION = 1;

	private PreparedQuery<Option> optionsForUserQuery = null;
	private PreparedQuery<User> usersForOptionQuery = null;

	/**
	 * 包含两个泛型: 第一个泛型表DAO操作的类 第二个表示操作类的主键类型
	 */
	private RuntimeExceptionDao<User, Integer> simpleRuntimeUserDao = null;
	private RuntimeExceptionDao<Option, Integer> simpleRuntimeOptionDao = null;
	private RuntimeExceptionDao<Poll, Integer> simpleRuntimePollDao = null;
	private RuntimeExceptionDao<UserVoteOption, Integer> simpleRuntimeUserVoteOptionDao = null;
	private RuntimeExceptionDao<UserConcernPoll, Integer> simpleRuntimeUserConcernPollDao = null;

	public OrmLiteDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
		try {
			Log.i(OrmLiteDBHelper.class.getName(), "onCreate");
			TableUtils.createTable(connectionSource, User.class);
			TableUtils.createTable(connectionSource, Poll.class);
			TableUtils.createTable(connectionSource, Option.class);
			TableUtils.createTable(connectionSource, UserVoteOption.class);
			TableUtils.createTable(connectionSource, UserConcernPoll.class);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't create database", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * 这个方法在你的应用升级以及它有一个更高的版本号时调用。所以需要你调整各种数据来适应新的版本
	 */
	@Override
	public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVersion,
			int newVersion) {
		Log.i("test", "更新....");
		try {
			Log.i(OrmLiteDBHelper.class.getName(), "onUpgrade");
			// 删掉旧版本的数据
			TableUtils.dropTable(connectionSource, User.class, true);
			TableUtils.dropTable(connectionSource, Poll.class, true);
			TableUtils.dropTable(connectionSource, Option.class, true);
			TableUtils.dropTable(connectionSource, UserVoteOption.class, true);
			TableUtils.dropTable(connectionSource, UserConcernPoll.class, true);
			// 创建一个新的版本
			onCreate(sqliteDatabase, connectionSource);
		} catch (SQLException e) {
			Log.e(OrmLiteDBHelper.class.getName(), "Can't drop databases", e);
			throw new RuntimeException(e);
		}
	}
	
	public void insertPolls(ArrayList<Poll> polls) {
		RuntimeExceptionDao<Poll, Integer> dao = getPollDao();
		for (Poll poll : polls) {
			dao.create(poll);
		}
	}

	/**
	 * 插入一条投票数据
	 */
	public void insertPoll(Poll poll) {
		RuntimeExceptionDao<Poll, Integer> dao = getPollDao();
		dao.create(poll);
	}
	
	/**
	 * 查询所有的投票信息
	 */
	public List<Poll> findAllPoll() {
		RuntimeExceptionDao<Poll, Integer> dao = getPollDao();
		return dao.queryForAll();
	}
	
	public RuntimeExceptionDao<Poll, Integer> getPollDao() {
		if (simpleRuntimePollDao == null) {
			simpleRuntimePollDao = getRuntimeExceptionDao(Poll.class);
		}
		return simpleRuntimePollDao;
	}
	
	public List<UserConcernPoll> findAllUserConcernPoll() {
		RuntimeExceptionDao<UserConcernPoll, Integer> dao = getUserConcernPollDao();
		return dao.queryForAll();
	}
	
	public void insertUserConcernPolls(List<UserConcernPoll> userConcernPolls) {
		RuntimeExceptionDao<UserConcernPoll, Integer> dao = getUserConcernPollDao();
		for (UserConcernPoll userConcernPoll : userConcernPolls) {
			dao.create(userConcernPoll);
		}
	}
	
	public void insertUserConcernPoll(UserConcernPoll userConcernPoll) {
		RuntimeExceptionDao<UserConcernPoll, Integer> dao = getUserConcernPollDao();
		dao.create(userConcernPoll);
	}
	
	public RuntimeExceptionDao<UserConcernPoll, Integer> getUserConcernPollDao() {
		if (simpleRuntimeUserConcernPollDao == null) {
			simpleRuntimeUserConcernPollDao = getRuntimeExceptionDao(UserConcernPoll.class);
		}
		return simpleRuntimeUserConcernPollDao;
	}
	
	public User findUserById(int id) {
		RuntimeExceptionDao<User, Integer> dao = getUserDao();
		return dao.queryForId(id);
	}
	
	public void insertUsers(List<User> users) {
		RuntimeExceptionDao<User, Integer> dao = getUserDao();
		for (User user : users) {
			dao.create(user);
		}
	}
	
	/**
	 * 插入一条用户数据
	 */
	public void insertUser(User user) {
		RuntimeExceptionDao<User, Integer> dao = getUserDao();
		// 通过实体对象创建在数据库中创建一条数据，成功返回1，说明插入了一条数据
		Log.i("test", "dao = " + dao + "  user= " + user);
		int returnValue = dao.create(user);
		Log.i("test", "插入数据后返回值：" + returnValue);
	}

	/**
	 * 查询所有的用户信息
	 */
	public List<User> findAllUser() {
		RuntimeExceptionDao<User, Integer> dao = getUserDao();
		return dao.queryForAll();
	}

	public RuntimeExceptionDao<User, Integer> getUserDao() {
		if (simpleRuntimeUserDao == null) {
			simpleRuntimeUserDao = getRuntimeExceptionDao(User.class);
		}
		Log.i("test", "simpleRuntimeDao ======= " + simpleRuntimeUserDao);
		return simpleRuntimeUserDao;
	}

	/**
	 * 插入一条选项数据
	 */
	public void insertOption(Option Option) {
		RuntimeExceptionDao<Option, Integer> dao = getOptionDao();
		// 通过实体对象创建在数据库中创建一条数据，成功返回1，说明插入了一条数据
		int returnValue = dao.create(Option);
		Log.i("test", "插入数据后返回值：" + returnValue);
	}

	public Option findOptionById(int OptionId) {
		RuntimeExceptionDao<Option, Integer> dao = getOptionDao();
		return dao.queryForId(OptionId);
	}

	public RuntimeExceptionDao<Option, Integer> getOptionDao() {
		if (simpleRuntimeOptionDao == null) {
			simpleRuntimeOptionDao = getRuntimeExceptionDao(Option.class);
		}
		return simpleRuntimeOptionDao;
	}

	/**
	 * 插入一条用户选项关联数据
	 */
	public void insertUserVoteOption(UserVoteOption dept) {
		RuntimeExceptionDao<UserVoteOption, Integer> dao = getUserVoteOptionDao();
		// 通过实体对象创建在数据库中创建一条数据，成功返回1，说明插入了一条数据
		int returnValue = dao.create(dept);
		Log.i("test", "插入数据后返回值：" + returnValue);
	}

	/**
	 * 查询某个用户选过的选项
	 */
	public List<Option> getOptionsUserVotes(User user) throws SQLException {
		RuntimeExceptionDao<Option, Integer> dao = getOptionDao();
		if (optionsForUserQuery == null) {
			optionsForUserQuery = makePostsForUserQuery();
		}
		optionsForUserQuery.setArgumentHolderValue(0, user);
		return dao.query(optionsForUserQuery);
	}

	private PreparedQuery<Option> makePostsForUserQuery() throws SQLException {
		RuntimeExceptionDao<UserVoteOption, Integer> UserVoteOptionDao = getUserVoteOptionDao();
		RuntimeExceptionDao<Option, Integer> OptionDao = getOptionDao();
		// 创建一个内关联查询用户选项表
		QueryBuilder<UserVoteOption, Integer> UserVoteOptionQuery = UserVoteOptionDao.queryBuilder();
		// 查询关联表user_vote_option时返回“option_id”如果没有该语句，即返回该表所有字段，相当于“select *
		// from 表名” 拼成sql语句：select option_id from user_vote_option
		UserVoteOptionQuery.selectColumns(COLUMN_NAME.OPTION_ID_COLUMN_NAME);
		// 这相当于一个可变的参数，相当于SQL语句中的“？”,这个参数会在后面的操作中指明
		SelectArg userSelectArg = new SelectArg();
		// 设置条件语句（where user_id=?）
		UserVoteOptionQuery.where().eq(COLUMN_NAME.USER_ID_COLUMN_NAME, userSelectArg);
		// 创建外部查询项目表
		QueryBuilder<Option, Integer> optionQuery = OptionDao.queryBuilder();
		// 设置查询条件（where Option_id in()）;
		optionQuery.where().in(COLUMN_NAME.OPTION_ID_COLUMN_NAME, UserVoteOptionQuery);
		/**
		 * 这里返回时完整的sql语句为 "SELECT * FROM `option` WHERE `option_id` IN (
		 * SELECT `option_id` FROM `user_vote_option` WHERE `user_id` = ? ) "
		 */
		return optionQuery.prepare();
	}

	/**
	 * 查询某个选项的所有选的人
	 */
	public List<User> getUsersVoteThisOption(Option option) throws SQLException {
		RuntimeExceptionDao<User, Integer> dao = getUserDao();
		if (usersForOptionQuery == null) {
			usersForOptionQuery = makeUsersForOptionQuery();
		}
		usersForOptionQuery.setArgumentHolderValue(0, option);
		return dao.query(usersForOptionQuery);
	}

	private PreparedQuery<User> makeUsersForOptionQuery() throws SQLException {
		RuntimeExceptionDao<UserVoteOption, Integer> UserVoteOptionDao = getUserVoteOptionDao();
		RuntimeExceptionDao<User, Integer> userDao = getUserDao();
		QueryBuilder<UserVoteOption, Integer> UserVoteOption = UserVoteOptionDao.queryBuilder();
		UserVoteOption.selectColumns(COLUMN_NAME.USER_ID_COLUMN_NAME);
		SelectArg userSelectArg = new SelectArg();
		UserVoteOption.where().eq(COLUMN_NAME.OPTION_ID_COLUMN_NAME, userSelectArg);
		QueryBuilder<User, Integer> postQb = userDao.queryBuilder();
		postQb.where().in(COLUMN_NAME.USER_ID_COLUMN_NAME, UserVoteOption);
		return postQb.prepare();
	}
	
	public RuntimeExceptionDao<UserVoteOption, Integer> getUserVoteOptionDao() {
		if (simpleRuntimeUserVoteOptionDao == null) {
			simpleRuntimeUserVoteOptionDao = getRuntimeExceptionDao(UserVoteOption.class);
		}
		Log.i("test", "simpleRuntimeDaodeptdept ======= " + simpleRuntimeUserVoteOptionDao);
		return simpleRuntimeUserVoteOptionDao;
	}



}