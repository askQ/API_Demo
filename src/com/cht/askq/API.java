package com.cht.askq;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class API {

	/* interface */
	public interface OnSuccessListener {
		void onSucess(JSONObject result);
	}

	public interface OnFailListener {
		void onFail(String errorMsg);
	}

	/* static variables and functions */
	private static String serverURL = "http://askq.lionfree.net/";
	private static String TAG = "API";

	public static int OP_REGISTER = 0;
	public static int OP_LOGIN = 1;
	public static int OP_EDIT_INFO = 2;
	public static int OP_QUERY_INFO = 3;
	public static int OP_QUERY_TYPE = 4;
	public static int OP_QUERY_QUESTION = 5;
	public static int OP_QUERY_CONTENT = 6;
	public static int OP_ASK_QUESTION = 7;
	public static int OP_ADD_CHOICE = 8;
	public static int OP_DELETE_QUESTION = 9;
	public static int OP_FINISH_QUESTION = 10;
	public static int OP_VOTE_QUESTION = 11;

	private static String php_list[] = {
		/* OP_REGISTER */        "register.php",
		/* OP_LOGIN */           "login.php",
		/* OP_EDIT_INFO */       "edit_info.php",
		/* OP_QUERY_INFO */      "query_info.php",
		/* OP_QUERY_TYPE */      "query_type.php",
		/* OP_QUERY_QUESTION */  "query_question.php",
		/* OP_QUERY_CONTENT */   "query_content.php",
		/* OP_ASK_QUESTION */    "ask_question.php",
		/* OP_ADD_CHOICE */      "add_choice.php",
		/* OP_DELETE_QUESTION */ "delete_question.php",
		/* OP FINISH_QUESTION */ "finish_question.php",
		/* OP_VOTE_QUESTION */   "vote_question.php" 
	};

	private static String keys_list[][] = {
		/* OP_REGISTER */        { "account", "password", "name", "sex", "email", "birthtime", "pic", "extension" },
		/* OP_LOGIN */           { "account", "password", "type" },
		/* OP_EDIT_INFO */       { "sessionid", "name", "password", "sex", "email", "birthtime", "pic", "extension" },
		/* OP_QUERY_INFO */      { "sessionid" },
		/* OP_QUERY_TYPE */      {},
		/* OP_QUERY_QUESTION */  { "typeid", "starttime", "endtime" },
		/* OP_QUERY_CONTENT */   { "questionid" },
		/* OP_ASK_QUESTION */    { "sessionid", "title", "typeid", "content", "endtime" },
		/* OP_ADD_CHOICE */      { "sessionid", "questionid", "choice" },
		/* OP_DELETE_QUESTION */ { "sessionid", "questionid" },
		/* OP FINISH_QUESTION */ { "sessionid", "questionid", "choiceid", "command" },
		/* OP_VOTE_QUESTION */   { "sessionid", "questionid", "choiceid", "command" } 
	};

	private static JSONObject sendRequest(String php, String dataString) throws Exception {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost request = new HttpPost(serverURL + php);
		List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("data", dataString));
		UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(postParameters, HTTP.UTF_8);
		request.setEntity(formEntity);

		HttpResponse response = httpClient.execute(request);
		return new JSONObject(EntityUtils.toString(response.getEntity(), HTTP.UTF_8));
	}

	private static String genRequestByOperation(int op, String... values) throws Exception {
		int key_length = keys_list[op].length;
		int value_length = values.length;

		if (key_length != value_length) {
			Log.e(TAG, "keys and values are not match");
			return null;
		} else {
			JSONObject obj = new JSONObject();

			for (int i = 0; i != key_length; i++) {
				obj.put(keys_list[op][i], values[i]);
			}
			return obj.toString();
		}
	}

	private class Task extends AsyncTask<String, Void, JSONObject> {
		@Override
		protected JSONObject doInBackground(String... params) {
			try {
				String reqString = genRequestByOperation(op, params);
				return sendRequest(php_list[op], reqString);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			try {
				if (result != null) {
					if (result.getString("code").equals("0000")) {
						success_callback.onSucess(result);
					} else {
						fail_callback.onFail(result.getString("message"));
					}
				} else {
					fail_callback.onFail("Unknown Error");
				}
			} catch (JSONException e) {
				e.printStackTrace();
				fail_callback.onFail("Unknown Error");
			}

			super.onPostExecute(result);
		}
	}

	/* variables */
	private int op;
	private Task task;
	private OnSuccessListener success_callback;
	private OnFailListener fail_callback;

	/* public functions */
	public void setOperation(int op) {
		this.op = op;
	}

	public void setOnSuccessListener(OnSuccessListener callback) {
		this.success_callback = callback;
	}

	public void setOnFailListener(OnFailListener callback) {
		this.fail_callback = callback;
	}

	public void start(String... params) {
		task = new Task();
		task.execute(params);
	}

}
