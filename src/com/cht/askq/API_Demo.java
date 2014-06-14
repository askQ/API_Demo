package com.cht.askq;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

import com.cht.askq.API.OnFailListener;
import com.cht.askq.API.OnSuccessListener;

public class API_Demo extends Activity {

	private String sessionid = "";

	private LinearLayout ll;
	private TableLayout tl;
	private EditText textarea;

	private Button register_btn;
	private Button login_btn;
	private Button edit_info_btn;
	private Button query_info_btn;
	private Button query_type_btn;
	private Button query_question_btn;
	private Button query_content_btn;
	private Button ask_question_btn;
	private Button add_choice_btn;
	private Button delete_question_btn;
	private Button finish_question_btn;
	private Button vote_question_btn;

	private List<View> view_list = new ArrayList<View>();

	private OnSuccessListener defaultOnSuccess = new OnSuccessListener() {
		@Override
		public void onSucess(JSONObject result) {
			if (result.has("sessionid")) {
				try {
					sessionid = result.getString("sessionid");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			textarea.setText(result.toString());
		}
	};

	private OnFailListener defaultOnFail = new OnFailListener() {
		@Override
		public void onFail(String errorMsg) {
			textarea.setText(errorMsg);
		}
	};

	private void show() {

		ll = new LinearLayout(this);
		ll.setOrientation(LinearLayout.VERTICAL);
		tl = new TableLayout(this);
		textarea = new EditText(this);
		textarea.setFocusable(false);

		TableRow row = null;
		for (int i = 0; i < view_list.size(); i++) {
			View v = view_list.get(i);
			if (i % 2 == 0)
				row = new TableRow(this);
			row.addView(v, new TableRow.LayoutParams(0, LayoutParams.WRAP_CONTENT, 1f));
			if (i % 2 == 1)
				tl.addView(row);
		}
		ll.addView(tl);
		ll.addView(textarea, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
		setContentView(ll);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		register_btn = new Button(this);
		login_btn = new Button(this);
		edit_info_btn = new Button(this);
		query_info_btn = new Button(this);
		query_type_btn = new Button(this);
		query_question_btn = new Button(this);
		query_content_btn = new Button(this);
		ask_question_btn = new Button(this);
		add_choice_btn = new Button(this);
		delete_question_btn = new Button(this);
		finish_question_btn = new Button(this);
		vote_question_btn = new Button(this);

		// register
		register_btn.setText("Register");
		register_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_REGISTER);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "account", "password", "name", "sex", "email","birthtime",
				// "pic", "extension" },
				api.start("ªü¨½¥¬¹F", "£u£u", "Tom5566", "00", "tom@cht.com", "1999/01/01", "", "");
			}
		});
		view_list.add(register_btn);

		// login
		login_btn.setText("Login");
		login_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_LOGIN);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "account", "password", "type" },
				api.start("ªü¨½¥¬¹F", "£u£u", "01");
			}
		});
		view_list.add(login_btn);

		// edit_info
		edit_info_btn.setText("Edit info");
		edit_info_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_EDIT_INFO);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "sessionid", "name", "password", "sex", "email",
				// "birthtime", "pic", "extension" },
				api.start(sessionid, "", "", "", "tom@cht.commm", "", "", "");
			}
		});
		view_list.add(edit_info_btn);

		// query_info
		query_info_btn.setText("Query info");
		query_info_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_QUERY_INFO);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "sessionid" },
				api.start(sessionid);
			}
		});
		view_list.add(query_info_btn);

		// query_type
		query_type_btn.setText("Query type");
		query_type_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_QUERY_TYPE);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// {},
				api.start();
			}
		});
		view_list.add(query_type_btn);

		// query_question
		query_question_btn.setText("Query question");
		query_question_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_QUERY_QUESTION);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "typeid", "starttime", "endtime" },
				api.start("", "2014/05/26", "2014/05/28");
			}
		});
		view_list.add(query_question_btn);

		// query_content
		query_content_btn.setText("Query content");
		query_content_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_QUERY_CONTENT);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "questionid" },
				api.start("1");
			}
		});
		view_list.add(query_content_btn);

		// ask_question
		ask_question_btn.setText("Ask question");
		ask_question_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_ASK_QUESTION);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "sessionid", "title", "typeid", "content", "endtime" },
				api.start(sessionid, "ABC", "1", "CCCCC", "2014/12/31");
			}
		});
		view_list.add(ask_question_btn);

		// add_choice
		add_choice_btn.setText("Add choice");
		add_choice_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_ADD_CHOICE);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "sessionid", "questionid", "choice" },
				api.start(sessionid, "1", "{\"title\":\"ttttt\"}");
			}
		});
		view_list.add(add_choice_btn);

		// delete_question
		delete_question_btn.setText("Delete question");
		delete_question_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_DELETE_QUESTION);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "sessionid", "questionid" },
				api.start(sessionid, "1");
			}
		});
		view_list.add(delete_question_btn);

		// finish_question
		finish_question_btn.setText("Finish question");
		finish_question_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_FINISH_QUESTION);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "sessionid", "questionid", "choiceid", "command" },
				api.start(sessionid, "1", "1", "oooo");
			}
		});
		view_list.add(finish_question_btn);

		// vote_question
		vote_question_btn.setText("Vote question");
		vote_question_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				API api = new API();
				api.setOperation(API.OP_VOTE_QUESTION);
				api.setOnSuccessListener(defaultOnSuccess);
				api.setOnFailListener(defaultOnFail);
				// { "sessionid", "questionid", "choiceid", "command" }
				api.start(sessionid, "1", "1", "xxxxxxx");
			}
		});
		view_list.add(vote_question_btn);

		show();
	}
}
