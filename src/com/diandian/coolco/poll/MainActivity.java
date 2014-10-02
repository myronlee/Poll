package com.diandian.coolco.poll;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.Context;
import android.content.res.Configuration;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;

import com.diandian.coolco.poll.adapter.NavEntryAdapter;
import com.diandian.coolco.poll.db.OrmLiteDBHelper;
import com.diandian.coolco.poll.fragment.MyPollFragment;
import com.diandian.coolco.poll.fragment.MyPollFragment.OnCreatePollListener;
import com.diandian.coolco.poll.fragment.MyPollFragment.OnPollSelectedListener;
import com.diandian.coolco.poll.fragment.PollCreateFragment;
import com.diandian.coolco.poll.fragment.PollFragment;
import com.diandian.coolco.poll.model.Poll;
import com.diandian.coolco.poll.model.User;
import com.diandian.coolco.poll.model.UserConcernPoll;
import com.diandian.coolco.poll.net.NetHelper;
import com.diandian.coolco.poll.util.Constant.URL;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class MainActivity extends FragmentActivity implements OnCreatePollListener, OnPollSelectedListener{

	private final String TAG = getClass().getName();
	
	private ActionBarDrawerToggle drawerToggle;
	private DrawerLayout drawerLayout;
	private ListView drawerList;
	private String[] planetTitles;

	private PollFragment pollFragment;
	private PollCreateFragment pollCreateFragment;
	private MyPollFragment myPollFragment;
	private Fragment showingFragment;
	private ActionBar actionBar;
	private Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		init();
	}

	private void init() {
		drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		planetTitles = new String[] { "投票", "发起投票", "我的投票", "设置" };
		Integer[] navImgs = new Integer[] { R.drawable.ic_nav_poll, R.drawable.ic_nav_create_poll,
				R.drawable.ic_nav_my_poll, R.drawable.ic_nav_setting };

		drawerList = (ListView) findViewById(R.id.left_drawer);
		// Set the adapter for the list view
		// drawerList.setAdapter(new ArrayAdapter<String>(this,
		// R.layout.list_item_drawer, R.id.drawer_list_item_text,
		// planetTitles));
		final NavEntryAdapter adapter = new NavEntryAdapter(this, Arrays.asList(navImgs), Arrays.asList(planetTitles));
		drawerList.setAdapter(adapter);
		drawerList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				adapter.setSelectedPosition(position);
				adapter.notifyDataSetChanged();
				showFragment(position);
			}
		});

		drawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
		drawerLayout, /* DrawerLayout object */
		R.drawable.ic_drawer, /* nav drawer icon to replace 'Up' caret */
		R.string.drawer_open, /* "open drawer" description */
		R.string.drawer_close /* "close drawer" description */
		) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				// getActionBar().setTitle("TO DO");
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				setTitle("iPoll");
			}
		};

		// Set the drawer toggle as the DrawerListener
		drawerLayout.setDrawerListener(drawerToggle);

		actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);

		showFragment(0);
		
		testGetPoll();
	}

	private void testGetPoll() {
		new GetPollAsyncTask().execute();
	}
	
	class GetPollAsyncTask extends AsyncTask<Void, Void, Void>{

		@Override
		protected Void doInBackground(Void... params) {
			String response = NetHelper.request(new DefaultHttpClient(), URL.GET_POLL, "");
			if(response == null){
				Log.e(TAG, "response == null");
				return null;
			}
			Log.e(TAG, response);
			Gson gson = new Gson();
			OrmLiteDBHelper dbHelper = new OrmLiteDBHelper(MainActivity.this);
			try {
				JSONObject responseJson = new JSONObject(response);
				
				JSONArray userJsonArray = responseJson.getJSONArray("users");
				Type userListType = new TypeToken<ArrayList<User>>(){}.getType();
				ArrayList<User> users = gson.fromJson(userJsonArray.toString(), userListType);
				dbHelper.insertUsers(users);
				
				JSONArray pollJsonArray = responseJson.getJSONArray("polls");
				Type pollListType = new TypeToken<ArrayList<Poll>>(){}.getType();
				ArrayList<Poll> polls = gson.fromJson(pollJsonArray.toString(), pollListType);
				
				for (Poll poll : polls) {
					User createUser = dbHelper.findUserById(poll.getCreateUser());
					poll.setCreateUserObj(createUser);
				}
				
				dbHelper.insertPolls(polls);
				
				ArrayList<UserConcernPoll> pollConcernUsers = new ArrayList<UserConcernPoll>();
				for (Poll poll : polls) {
					for(int concernUserId : poll.getConcernUsers()){
						User concernUser = dbHelper.findUserById(concernUserId);
						
						UserConcernPoll pollConcernUser = new UserConcernPoll();
						pollConcernUser.setPoll(poll);
						pollConcernUser.setUser(concernUser);
						
						pollConcernUsers.add(pollConcernUser);
					}
				}
				dbHelper.insertUserConcernPolls(pollConcernUsers);
				
				Log.e(TAG, dbHelper.findAllPoll().get(0).getCreateUserObj().getUsername());
				Log.e(TAG, dbHelper.findAllUserConcernPoll().get(0).getPoll().getTitle());
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		
	}

	/** Swaps fragments in the main content view */
	private void showFragment(int position) {
		switch (position) {
		case 0: {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if (pollFragment == null) {
				pollFragment = new PollFragment();
				transaction.add(R.id.content_frame, pollFragment, "poll");
			}

			if (showingFragment != null) {
				transaction.hide(showingFragment);
			}
			showingFragment = pollFragment;

			transaction.show(pollFragment);
			transaction.commit();
			break;
		}
		case 1: {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if (pollCreateFragment == null) {
				pollCreateFragment = new PollCreateFragment();
				transaction.add(R.id.content_frame, pollCreateFragment, "pollCreate");
			}

			if (showingFragment != null) {
				transaction.hide(showingFragment);
			}
			showingFragment = pollCreateFragment;

			transaction.show(pollCreateFragment);
			transaction.commit();
			break;
		}
		case 2: {
			FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			if (myPollFragment == null) {
				myPollFragment = new MyPollFragment();
				transaction.add(R.id.content_frame, myPollFragment, "myPoll");
			}

			if (showingFragment != null) {
				transaction.hide(showingFragment);
			}
			showingFragment = myPollFragment;

			transaction.show(myPollFragment);
			transaction.commit();
			break;
		}

		default:
			break;
		}
		// // Create a new fragment and specify the planet to show based on
		// // position
		// Fragment fragment = new PlanetFragment();
		// Bundle args = new Bundle();
		// args.putInt(PlanetFragment.ARG_PLANET_NUMBER, position);
		// fragment.setArguments(args);
		//
		// // Insert the fragment by replacing any existing fragment
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction().replace(R.id.content_frame,
		// fragment).commit();

		// Highlight the selected item, update the title, and close the drawer
		setTitle(planetTitles[position]);
		drawerList.setItemChecked(position, true);
		drawerLayout.closeDrawer(drawerList);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		drawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		drawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (drawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		
		this.menu = menu;

		SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		SearchView search = (SearchView) menu.findItem(R.id.action_search_poll).getActionView();
		
		int autoCompleteTextViewID = getResources().getIdentifier("android:id/search_src_text", null, null);
	    AutoCompleteTextView searchAutoCompleteTextView = (AutoCompleteTextView) search.findViewById(autoCompleteTextViewID);
	    searchAutoCompleteTextView.setThreshold(1);
	    
	    int searchImgId = getResources().getIdentifier("android:id/search_button", null, null);
	    ImageView v = (ImageView) search.findViewById(searchImgId);
	    v.setImageResource(R.drawable.ic_action_search); 
	    
	    new SearchViewFormatter()
        .setSearchBackGroundResource(R.drawable.bg_edit_text_search_view)
        .setSearchIconResource(R.drawable.ic_action_search, true, false) //true to icon inside edit text, false to outside
        .setSearchVoiceIconResource(R.drawable.ic_action_search)
        .setSearchTextColorResource(R.color.white)
        .setSearchHintColorResource(R.color.white)
        .setSearchCloseIconResource(R.drawable.edit_text_clear_search_view)
        .setInputType(InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE)
        .format(search);
		
		search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

		search.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextChange(String query) {

				loadHistory(query);

				return true;

			}

			@Override
			public boolean onQueryTextSubmit(String query) {
				return false;
			}

		});

		return super.onCreateOptionsMenu(menu);
	}

	private void loadHistory(String query) {

		// Cursor
		String[] columns = new String[] { "_id", "text" };
		Object[] temp = new Object[] { 0, "default" };

		MatrixCursor cursor = new MatrixCursor(columns);
		
		List<String> items = new ArrayList<String>();
		items.add("a");
		items.add("ab");
		items.add("abc");
		items.add("b");
		items.add("bc");
		items.add("bcd");

		for (int i = 0; i < items.size(); i++) {

			temp[0] = i;
			temp[1] = items.get(i);

			cursor.addRow(temp);

		}

		// SearchView
		SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

		final SearchView search = (SearchView) menu.findItem(R.id.action_search_poll).getActionView();

		search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));

		search.setSuggestionsAdapter(new ExampleAdapter(this, cursor, items));

	}

	@Override
	public void onCreatePoll() {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.rise_from_bottom_right, 0, 0, R.anim.sink_to_bottom_right);
		transaction.addToBackStack(null);
		transaction.add(R.id.container, new PollCreateFragment(), null).commit();
	}

	@Override
	public void onPollSelected() {
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
		transaction.setCustomAnimations(R.anim.right_in, 0, 0, R.anim.right_out);
		transaction.addToBackStack(null);
		transaction.add(R.id.container, new PollCreateFragment(), null).commit();
	}

}
