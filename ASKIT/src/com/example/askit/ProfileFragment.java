package com.example.askit;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Session;
import com.facebook.widget.ProfilePictureView;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

public class ProfileFragment extends Fragment{
	protected ProfilePictureView profilePictureView;
	protected TextView userNameView;
	protected TextView friendsView;
	protected Bundle sendBundle;
	protected String id;
	protected ParseObject[] messages;
	protected ListView messageList;
	protected EditText askQuestionView;
	protected LinearLayout askLayout;
	protected Button askButton;
	boolean isMine;

	@Override
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.profile, container, false);

		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);

		// Find the user's name view
		userNameView = (TextView) view.findViewById(R.id.selection_user_name);
		
		messageList= (ListView) view.findViewById(R.id.messageList);

		friendsView = (TextView) view.findViewById(R.id.friends);
		
		askQuestionView=(EditText)view.findViewById(R.id.askQuestion);
		
		askLayout=(LinearLayout)view.findViewById(R.id.askLayout);
		
		askButton=(Button)view.findViewById(R.id.askButton);
		
		sendBundle = new Bundle();
		
		
		friendsView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent newIntent = new Intent(getActivity().getApplicationContext(), FriendChooser.class);
				newIntent.putExtras(sendBundle);
				startActivity(newIntent);
			}
		});

		
		return view;
	}

	protected void getMessages() {
		Log.d("getMessages","g");
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
		Log.d("id",id);
		query.whereEqualTo("facebookId", id);
		query.findInBackground(new FindCallback<ParseObject>() {
		    public void done(List<ParseObject> msg, ParseException e) {
		        if (e == null) {
		        	Log.d("yes","g");
		        	messages=msg.toArray(new ParseObject[msg.size()]);
		        	setMessages();
		        } else {
		        	Log.d("no","g");
		            Log.d("score", "Error: " + e.getMessage());
		        }
		    }

			
		});
		
	}

	protected void setPicName(String id, String name) {
		// Set the id for the ProfilePictureView
		// view that in turn displays the profile picture.
		profilePictureView.setProfileId(id);
		// Set the Textview's text to the user's name.
		userNameView.setText(name);

	}
	
	private void setMessages() {
		Log.d("setMessages",Arrays.toString(messages));
		messageList.setAdapter(new MessageAdapter(getActivity().getApplicationContext(), messages,isMine,new CallBack() {
			
			@Override
			public void call() {
				
				getMessages();
				
			}
		}));
	}
}

