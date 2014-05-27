package com.example.askit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.Request.Callback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

public class MessageAdapter extends ArrayAdapter<ParseObject> {

	private Context context;
	private ParseObject[] messages;
	private boolean isMine;
	int position;
	CallBack callback2; 

	public MessageAdapter(Context context, ParseObject[] messages,boolean isMine,CallBack callback) {
		super(context,R.layout.messages_row, messages);
		this.context = context;
		this.messages = messages;
		this.isMine=isMine;
		callback2=callback;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.messages_row, parent, false);

		TextView questionView = (TextView) rowView.findViewById(R.id.question);
		TextView answerView = (TextView) rowView.findViewById(R.id.answer);
		EditText writeAnswer=(EditText)  rowView.findViewById(R.id.writeAnswer);
		LinearLayout answerLayout=(LinearLayout)  rowView.findViewById(R.id.answerLayout);
		Button buttonAnswer=(Button)rowView.findViewById(R.id.buttonAnswer);

		questionView.setText(messages[position].getString("question"));

		String answer=messages[position].getString("answer");
		//Log.d("answer",answer+"chuh");

		if(answer==null && isMine){
			answerLayout.setVisibility(View.VISIBLE);
			answerView.setVisibility(View.GONE);
		}
		if(answer==null)Log.d("answer","entrou2");
		else answerView.setText(answer);

		//buttonAnswer.setTag(new String("aaaa"));

		buttonAnswer.setTag(new Wrapper(messages[position],new MyCallBack(writeAnswer,callback2)));
		//Log.d("1",rowView.getTag().toString()+"sad");


		
		buttonAnswer.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				//Log.d("2",vv.getTag().toString()+"sad");
				//	final EditText writeAnswer=(EditText)  vwParentRow.findViewById(R.id.writeAnswer);
				//Log.d("2",(String)v.getTag());
				//				
				ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
				Wrapper w=(Wrapper) v.getTag();
				Log.d("3",w.toString()+"sad");
				// Retrieve the object by id
				query.getInBackground(w.obj.getObjectId(),w.callback);
				
				
				
				
//				Activity activity = (Activity) context;
//				int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//				if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB){
//					
//					activity.recreate();
//				} else{
//					Intent intent = activity.getIntent();
//					activity.finish();
//					activity.startActivity(intent);
//				}
				
			}
		});

		return rowView;
	}
	
	
}
class Wrapper{
	ParseObject obj;
	GetCallback<ParseObject> callback;
	Wrapper(ParseObject messages, GetCallback<ParseObject> callback){
		obj=messages;
		this.callback=callback;
	}

}

class MyCallBack extends GetCallback<ParseObject>{
	EditText txt;
	CallBack callback2;
	public MyCallBack(EditText writeAnswer,CallBack c) {
		txt=writeAnswer;
		callback2=c;
	}
	
	@Override
	public void done(ParseObject gameScore, ParseException e) {
		if (e == null) {
			// Now let's update it with some new data. In this case, only cheatMode and score
			// will get sent to the Parse Cloud. playerName hasn't changed.
			gameScore.put("answer", txt.getText().toString());
			gameScore.put("cheatMode", true);
			//gameScore.saveInBackground();
			gameScore.saveInBackground(new SaveCallback() {
				
				@Override
				public void done(ParseException arg0) {
					callback2.call();
					
				}
			});
			
			
			
		}
	}	

}

