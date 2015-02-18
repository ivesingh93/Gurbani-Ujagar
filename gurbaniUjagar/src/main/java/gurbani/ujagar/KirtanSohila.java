package gurbani.ujagar;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EmptyStackException;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class KirtanSohila extends SlidingFragmentActivity {
	SectionsPagerAdapter mSectionsPagerAdapter;
	static ViewPager mViewPager;
	static SharedPreferences getPrefs;
	ImageView logo;
	TextView textView, txt1, txt2;

	public void onBackPresed(){
		finish();
	} 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		getSlidingMenu().setBehindOffset(400);
		getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		setBehindContentView(R.layout.menu);
		getSlidingMenu().setSecondaryMenu(R.layout.menu2);

		
		
		final ListView sggs = (ListView)findViewById(R.id.listView1);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simplerow, ConstantsMethods.SGGS);
		sggs.setAdapter(adapter);
		final ConstantsMethods constant = new ConstantsMethods();

		sggs.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				constant.sggsMethod(parent, view, position, id, KirtanSohila.this);
			}
		}); 

		ListView general = (ListView)findViewById(R.id.general_menu);
		ArrayAdapter<String> adapter2 = new GeneralArrayAdapter(this, R.layout.simplerow, ConstantsMethods.GENERAL);
		general.setAdapter(adapter2);

		general.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toggle();
				constant.generalMethod(parent, view, position, id, KirtanSohila.this);		
			}
		}); 
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {
		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public Fragment getItem(int position) {

			Fragment kirtan = new Kirtan();
			return kirtan;
		}

		@Override
		public int getCount() {
			return 1;
		}

		@Override
		public CharSequence getPageTitle(int position) {	
			return "Sohila Sahib";
		}
	}

	@SuppressLint("ValidFragment")
	public static class Kirtan extends Fragment {
		public Kirtan() {

		}

		@SuppressWarnings("deprecation")
		@SuppressLint({ "SetJavaScriptEnabled", "NewApi" })
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.one_page_web, container, false);
			final WebView webview = (WebView) rootView .findViewById(R.id.webview);

			final ImageButton screenshot = (ImageButton)rootView.findViewById(R.id.imageButton1);


			screenshot.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					rootView.setDrawingCacheEnabled(true);
					Bitmap bitmap = rootView.getDrawingCache();

					Toast.makeText(getActivity(), "Image has been saved to Gallery.", Toast.LENGTH_SHORT).show();
					ContentValues values = new ContentValues();
					values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
					values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
					//						values.put(Images.Media.TITLE, dat+"_"+mon);
					//						values.put(Images.Media.DISPLAY_NAME, dat+"_"+mon);
					values.put(Images.Media.DESCRIPTION, "Image");
					values.put(Images.Media.MIME_TYPE, "image/jpg");


					Uri url = null;		 
					try {
						url = rootView.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

						if (bitmap != null) {
							OutputStream imageOut = rootView.getContext().getContentResolver().openOutputStream(url);
							try {
								bitmap.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
							} finally {
								imageOut.close();
							}

							long id = ContentUris.parseId(url);
							Bitmap miniThumb = Images.Thumbnails.getThumbnail(rootView.getContext().getContentResolver(), id, Images.Thumbnails.MINI_KIND, null);		                		                
							Matrix matrix = new Matrix();  
							float scaleX = 50f / miniThumb.getWidth();
							float scaleY = 50f / miniThumb.getHeight();

							matrix.setScale(scaleX, scaleY);

							Bitmap thumb = Bitmap.createBitmap(miniThumb, 0, 0,
									miniThumb.getWidth(),
									miniThumb.getHeight(), matrix,
									true
									);

							ContentValues valuess = new ContentValues(4);
							values.put(Images.Thumbnails.KIND,Images.Thumbnails.MICRO_KIND);
							values.put(Images.Thumbnails.IMAGE_ID,(int)id);
							values.put(Images.Thumbnails.HEIGHT,thumb.getHeight());
							values.put(Images.Thumbnails.WIDTH,thumb.getWidth());

							Uri urll = rootView.getContext().getContentResolver().insert(Images.Thumbnails.EXTERNAL_CONTENT_URI, valuess);

							try {
								OutputStream thumbOut = rootView.getContext().getContentResolver().openOutputStream(urll);
								thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
								thumbOut.close();
								return;
							} catch (FileNotFoundException ex) {
								return;
							} catch (IOException ex) {
								return;
							}




						} else {
							rootView.getContext().getContentResolver().delete(url, null, null);
							url = null;
						}
					} catch (Exception e) {
						if (url != null) {
							rootView.getContext().getContentResolver().delete(url, null, null);
							url = null;
						}
					}
				}
			});


			
			
			
			try {
				webview.loadUrl("file:///android_asset/OtherBanis/Sohila Sahib.html");
				webview.getSettings().setBuiltInZoomControls(true);
				if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
					webview.getSettings().setDisplayZoomControls(false);
				}
				WebSettings webSettings = webview.getSettings();

				for(int i = 1; i <= 6; i++){
					String textSize = getPrefs.getString("prefWebviewSize", ""+i);
					if(textSize.equals("" + 1)){
						webSettings.setTextSize(WebSettings.TextSize.SMALLEST);					
					}else if(textSize.equals("" + 2)){
						webSettings.setTextSize(WebSettings.TextSize.SMALLER);					
					}else if(textSize.equals("" + 3)){
						webSettings.setTextSize(WebSettings.TextSize.NORMAL);
					}else if(textSize.equals("" + 4)){
						webSettings.setTextSize(WebSettings.TextSize.LARGER);
					}else if(textSize.equals("" + 5)){
						webSettings.setTextSize(WebSettings.TextSize.LARGEST);
					}else{
						webSettings.setTextSize(WebSettings.TextSize.SMALLER);				
					}
				}

				
			} catch (EmptyStackException e) {
				e.printStackTrace();
			}
			return rootView;

		}
	}
}