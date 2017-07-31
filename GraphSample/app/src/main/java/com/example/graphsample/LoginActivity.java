package com.example.graphsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{

	
	private CallbackManager callbackManager;
	private AccessTokenTracker accessTokenTracker;
	private ProfileTracker profileTracker;
	private LoginButton loginButton;
	private String firstName,lastName, email,birthday,gender;
	private URL profilePicture;
	private String userId;
	private String TAG = "LoginActivity";
	
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_login );
		
		
		callbackManager = CallbackManager.Factory.create();
		loginButton = (LoginButton) findViewById( R.id.login_button );
		loginButton.setReadPermissions( "email" );
		
		loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
			@Override
			public void onSuccess(LoginResult loginResult) {
				// App code
				
				
				Log.d( TAG, "onSuccess: "+loginResult.toString() );
				
				GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
					@Override
					public void onCompleted(JSONObject object, GraphResponse response) {
						Log.e(TAG,object.toString());
						Log.e(TAG,response.toString());
						
						try {
							userId = object.getString("id");
							profilePicture = new URL("https://graph.facebook.com/" + userId + "/picture?width=500&height=500");
							if(object.has("first_name"))
								firstName = object.getString("first_name");
							if(object.has("last_name"))
								lastName = object.getString("last_name");
							if (object.has("email"))
								email = object.getString("email");
							if (object.has("birthday"))
								birthday = object.getString("birthday");
							if (object.has("gender"))
								gender = object.getString("gender");
							
							Intent main = new Intent(LoginActivity.this,MainActivity.class);
							main.putExtra("name",firstName);
							main.putExtra("surname",lastName);
							main.putExtra("imageUrl",profilePicture.toString());
							startActivity(main);
							finish();
						} catch (JSONException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						}
					
					}
				});
				//Here we put the requested fields to be returned from the JSONObject
				Bundle parameters = new Bundle();
				parameters.putString("fields", "id, first_name, last_name, email, birthday, gender");
				request.setParameters(parameters);
				request.executeAsync();
			}
			
			@Override
			public void onCancel() {
				// App code
			}
			
			@Override
			public void onError(FacebookException exception) {
				// App code
			}
		});
		
		
		
		LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile"));
	}
	
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
}
