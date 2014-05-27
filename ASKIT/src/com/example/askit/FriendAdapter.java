package com.example.askit;



import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FriendAdapter extends ArrayAdapter<GraphUser> {
	private final Context context;
	private ProfilePictureView profileFriendPictureView;
	private TextView friendNameView;
	private final GraphUser[] friends;
	
	public FriendAdapter(Context context, GraphUser[] friends) {
		super(context, R.layout.friends_row, friends);
		this.context = context;
		this.friends = friends;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.friends_row, parent, false);
		Log.d("name",friends[0].toString());
		// Find the user's profile picture custom view
		profileFriendPictureView = (ProfilePictureView) rowView.findViewById(R.id.friend_profile_pic);
		profileFriendPictureView.setCropped(true);

		// Find the user's name view
		friendNameView = (TextView) rowView.findViewById(R.id.friend_name);
		
		friendNameView.setText(friends[position].getName());
		profileFriendPictureView.setProfileId(friends[position].getId());

		return rowView;
	}
}