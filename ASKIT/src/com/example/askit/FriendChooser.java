package com.example.askit;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.model.GraphObject;
import com.facebook.model.GraphObjectList;
import com.facebook.model.GraphUser;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;

public class FriendChooser extends ListActivity {
	private GraphUser[] friends;
	protected String id;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Session session = Session.getActiveSession();

		Bundle receiveBundle = this.getIntent().getExtras();
		id=receiveBundle.getString("id"); 

		if (session != null && session.isOpened()) {
			// Get the user's data
			getFriends(session);
		}



	}

	private void getFriends(final Session session) {
		new Request(
				session,
				"/"+id+"/friends",
				null,
				HttpMethod.GET,
				new Request.Callback() {
					public void onCompleted(Response response) {

						try {

							GraphUser graphUser = response.getGraphObjectAs(GraphUser.class);

							if(graphUser != null)
							{
								JSONObject jsonObject = graphUser.getInnerJSONObject();
								JSONArray jsonArray = jsonObject.getJSONArray("data");
								
								friends= new GraphUser[jsonArray.length()];								
								
								for (int i = 0; i < jsonArray.length(); i++) {

									JSONObject object = jsonArray.getJSONObject(i);
									friends[i] = GraphObject.Factory.create(object, GraphUser.class);
								}
								
								test();
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}        
					}
				}
				).executeAsync();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		//TO-DO differentiate from myprofile to friends profile
		Bundle sendBundle=new Bundle();
		sendBundle.putString("id", friends[position].getId());
		sendBundle.putString("name",friends[position].getName());
		Log.d("bundle",sendBundle.toString());
		Intent newIntent = new Intent(getApplicationContext(), FriendProfile.class);
		newIntent.putExtras(sendBundle);
		startActivity(newIntent);



	}
	void test(){
		Log.d("name2",friends[0].toString());
		setListAdapter(new FriendAdapter(this, friends));
	}
}
