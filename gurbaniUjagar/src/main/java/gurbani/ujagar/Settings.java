package gurbani.ujagar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceActivity;


public class Settings extends PreferenceActivity{

	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			addPreferencesFromResource(R.xml.prefs2);
		}else{
			addPreferencesFromResource(R.xml.prefs);
		}
		getListView().setBackgroundColor(Color.WHITE);
	}


	public void onBackPressed(){
		try {
			Class ourClass = Class
					.forName("gurbani.ujagar.MainPage");
			Intent ourIntent = new Intent(Settings.this,
					ourClass);
			startActivity(ourIntent);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	} 
}
