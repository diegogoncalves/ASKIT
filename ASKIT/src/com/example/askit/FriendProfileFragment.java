package com.example.askit;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseObject;

public class FriendProfileFragment extends ProfileFragment{
	
	String name;
	
	
	public View onCreateView(LayoutInflater inflater, 
			ViewGroup container, Bundle savedInstanceState) {
		View view=super.onCreateView(inflater, container, savedInstanceState);
		isMine=false;
		Bundle bundle = this.getArguments();
		
		name = bundle.getString("name");
		id = bundle.getString("id");
		
		setPicName(id,name);
		sendBundle.putString("id", id);
		sendBundle.putString("name", name);
		
		
		
		askButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseObject gameScore = new ParseObject("Message");
				gameScore.put("facebookId", id);
				gameScore.put("question", askQuestionView.getText().toString());
				gameScore.saveInBackground();
				
				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
				if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
					getActivity().recreate();
				} else{
					Intent intent = getActivity().getIntent();
					getActivity().finish();
					startActivity(intent);
				}
				
				
				
			}
		});
		
		getMessages();
		
		
		return view;
	}
}
