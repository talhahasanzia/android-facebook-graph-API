package com.example.graphsample;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;
import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener
{
	
	private ShareDialog shareDialog;
	private String name, surname, imageUrl;
	private String TAG = "MainActivity";
	
	
	@Override
	protected void onCreate( Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );
		
		
		Bundle inBundle = getIntent().getExtras();
		name = inBundle.getString( "name" );
		surname = inBundle.getString( "surname" );
		imageUrl = inBundle.getString( "imageUrl" );
		
		
		TextView nameView = (TextView) findViewById( R.id.nameAndSurname );
		nameView.setText( "" + name + " " + surname );
		
		
		ImageView imageView = (ImageView) findViewById( R.id.profileImage );
		
	
		
		Picasso.with(this).load(imageUrl).into(imageView);
	}
	
	
	@Override
	public void onClick( View view )
	{
		switch ( view.getId() )
		{
			
			case R.id.fb_logo:
			case R.id.share:
				share();
				break;
			
			case R.id.getPosts:
				getPosts();
				break;
			
			
			case R.id.write:
				writePost();
				break;
			
			case R.id.logout:
				logout();
				break;
		}
	}
	
	private void writePost()
	{
		
		startActivity( new Intent( MainActivity.this, WritePostActivity.class ) );
		
	}
	
	private void share()
	{
		shareDialog = new ShareDialog( this );
		List<String> taggedUserIds = new ArrayList<String>();
		taggedUserIds.add( "{USER_ID}" );
		taggedUserIds.add( "{USER_ID}" );
		taggedUserIds.add( "{USER_ID}" );
		
		ShareLinkContent content = new ShareLinkContent.Builder()
				.setContentUrl( Uri.parse( "http://www.evilgeniuses.gg" ) )
				.setShareHashtag( new ShareHashtag.Builder().setHashtag( "#EG" ).setHashtag( " #TI7" ).setHashtag( " #GraphAPISample" ).build() )
				.setPeopleIds( taggedUserIds )
				.setPlaceId( "{PLACE_ID}" )
				.build();
		
		shareDialog.show( content );
	}
	
	
	private void getPosts()
	{
		new GraphRequest(
				AccessToken.getCurrentAccessToken(), "/me/posts", null, HttpMethod.GET,
				new GraphRequest.Callback()
				{
					public void onCompleted( GraphResponse response )
					{
						Log.e( TAG, response.toString() );
					}
				}
		).executeAsync();
	}
	
	
	private void logout()
	{
		LoginManager.getInstance().logOut();
		Intent login = new Intent( MainActivity.this, LoginActivity.class );
		startActivity( login );
		finish();
	}
}
