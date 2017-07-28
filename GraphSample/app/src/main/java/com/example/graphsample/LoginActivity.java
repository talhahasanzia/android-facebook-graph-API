package com.example.graphsample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity
{
	
	private static final String TAG = "LoginActivity";
	LoginButton loginButton;
	CallbackManager callbackManager;
	
	
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
