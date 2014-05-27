package com.example.askit;



import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

public class MainActivity extends ActionBarActivity {
	private static final int REAUTH_ACTIVITY_CODE = 100;
	private ProfilePictureView profilePictureView;
	private TextView userNameView;
	private UiLifecycleHelper uiHelper;
	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(final Session session, final SessionState state, final Exception exception) {
			Log.d("debug","here2");
			onSessionStateChange(session, state, exception);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_main);
		Log.d("debug","here3");


		// Find the user's profile picture custom view
		profilePictureView = (ProfilePictureView) findViewById(R.id.selection_profile_pic);
		profilePictureView.setCropped(true);

		// Find the user's name view
		userNameView = (TextView) findViewById(R.id.selection_user_name);
		Log.d("debug","here4");

		// start Facebook Login
		Session.openActiveSession(this, true,new Session.StatusCallback() {
			@Override
			public void call(final Session session, final SessionState state, final Exception exception) {
				if (session.isOpened()) {
					// Get the user's data.
					Log.d("debug","here5");
					makeMeRequest2(session);
				}
				if(session != null)Log.d("debug","yes");;
				if(session.isOpened())Log.d("debug","no");;
			}
		});

	}

	//	@Override
	//	public void onResume() {
	//		super.onResume();
	//		uiHelper.onResume();
	//	}
	//
	//	@Override
	//	public void onSaveInstanceState(Bundle bundle) {
	//		super.onSaveInstanceState(bundle);
	//		uiHelper.onSaveInstanceState(bundle);
	//	}
	//
	//	@Override
	//	public void onPause() {
	//		super.onPause();
	//		uiHelper.onPause();
	//	}
	//
	//	@Override
	//	public void onDestroy() {
	//		super.onDestroy();
	//		uiHelper.onDestroy();
	//	}
	//
	//
	//
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//		if (requestCode == REAUTH_ACTIVITY_CODE) {
		//			uiHelper.onActivityResult(requestCode, resultCode, data);
		//		}
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	private void makeMeRequest(final Session session) {
		// Make an API call to get user data and define a 
		// new callback to handle the response.
		Log.d("debug","here7");
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				// If the response is successful
				if (session == Session.getActiveSession()) {
					if (user != null) {
						// Set the id for the ProfilePictureView
						// view that in turn displays the profile picture.
						Log.d("debug","here");
						profilePictureView.setProfileId(user.getId());
						// Set the Textview's text to the user's name.
						userNameView.setText(user.getName());
					}
				}
				if (response.getError() != null) {
					// Handle errors, will do so later.
				}
			}
		});
		request.executeAsync();
	} 
	private void makeMeRequest2(final Session session) {
		// Make an API call to get user data and define a 
		// new callback to handle the response.
		Log.d("debug","here7");
		Request request = Request.newMeRequest(session, 
				new Request.GraphUserCallback() {
			@Override
			public void onCompleted(GraphUser user, Response response) {
				// If the response is successful

				if (user != null) {
					// Set the id for the ProfilePictureView
					// view that in turn displays the profile picture.
					Log.d("debug","here");
					profilePictureView.setProfileId(user.getId());
					// Set the Textview's text to the user's name.
					userNameView.setText(user.getName());
				}

				if (response.getError() != null) {
					// Handle errors, will do so later.
				}
			}
		});
		request.executeAsync();
	} 
	private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
		if (session != null && session.isOpened()) {
			// Get the user's data.
			Log.d("debug","here5");
			makeMeRequest(session);
		}
		Log.d("debug","here6");
	}


}
