package gurbani.ujagar;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ShabadActivity extends SlidingFragmentActivity {
	static ArrayList<String> arr;
	static int i;
	static char[] search_word_arr;
	EditText shabad;
	TextView text_shabad,keyword;
	static TextView example;
	static Map<String, Integer> list_array = new TreeMap<String, Integer>();
    static Map<String, Integer> line_num_arr = new HashMap<String, Integer>();
    static int line_num = 0;
	public void onBackPressed() {
		try{
			Class ourClass = Class.forName("gurbani.ujagar.MainPage");
			Intent ourIntent = new Intent(ShabadActivity.this, ourClass);
			startActivity(ourIntent);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);


		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.shabad_activity);
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


				constant.sggsMethod(parent, view, position, id, ShabadActivity.this);
			}
		}); 

		ListView general = (ListView)findViewById(R.id.general_menu);
		ArrayAdapter<String> adapter2 = new GeneralArrayAdapter(this, R.layout.simplerow, ConstantsMethods.GENERAL);
		general.setAdapter(adapter2);

		general.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toggle();
				constant.generalMethod(parent, view, position, id, ShabadActivity.this);


			}
		}); 
		shabad = (EditText) findViewById(R.id.shabad);
		text_shabad = (TextView) findViewById(R.id.text_shabad);
		keyword = (TextView) findViewById(R.id.keyword);
		arr = new ArrayList<String>();

		Typeface face = Typeface.createFromAsset(getAssets(),"fonts/GURAKHAR.TTF");
		shabad.setTypeface(face);
		text_shabad.setTypeface(face);
		keyword.setTypeface(face);
		shabad.setInputType(InputType.TYPE_NULL);
		Button Button07 = (Button) findViewById(R.id.Button07);
		Button Button06 = (Button) findViewById(R.id.Button06);
		Button Button05 = (Button) findViewById(R.id.Button05);
		Button Button04 = (Button) findViewById(R.id.Button04);
		Button Button03 = (Button) findViewById(R.id.Button03);

		Button07.setTypeface(face);
		Button06.setTypeface(face);
		Button05.setTypeface(face);
		Button04.setTypeface(face);
		Button03.setTypeface(face);

		Button Button44 = (Button) findViewById(R.id.Button44);
		Button Button43 = (Button) findViewById(R.id.Button43);
		Button Button41 = (Button) findViewById(R.id.Button41);
		Button Button42 = (Button) findViewById(R.id.Button42);
		Button Button40 = (Button) findViewById(R.id.Button40);		

		Button44.setTypeface(face);
		Button43.setTypeface(face);
		Button41.setTypeface(face);
		Button42.setTypeface(face);
		Button40.setTypeface(face);

		Button Button48 = (Button) findViewById(R.id.Button48);
		Button Button47 = (Button) findViewById(R.id.Button47);
		Button Button49 = (Button) findViewById(R.id.Button49);
		Button Button46 = (Button) findViewById(R.id.Button46);		
		Button Button45 = (Button) findViewById(R.id.Button45);

		Button48.setTypeface(face);
		Button47.setTypeface(face);
		Button49.setTypeface(face);
		Button46.setTypeface(face);
		Button45.setTypeface(face);	

		Button Button54 = (Button) findViewById(R.id.Button54);
		Button Button52 = (Button) findViewById(R.id.Button52);
		Button Button53 = (Button) findViewById(R.id.Button53);
		Button Button50 = (Button) findViewById(R.id.Button50);		
		Button Button51 = (Button) findViewById(R.id.Button51);

		Button54.setTypeface(face);
		Button52.setTypeface(face);
		Button53.setTypeface(face);
		Button50.setTypeface(face);
		Button51.setTypeface(face);

		Button Button56 = (Button) findViewById(R.id.Button56);
		Button Button58 = (Button) findViewById(R.id.Button58);
		Button Button59 = (Button) findViewById(R.id.Button59);
		Button Button55 = (Button) findViewById(R.id.Button55);
		Button Button57 = (Button) findViewById(R.id.Button57);

		Button56.setTypeface(face);
		Button58.setTypeface(face);
		Button59.setTypeface(face);
		Button55.setTypeface(face);
		Button57.setTypeface(face);

		Button Button08 = (Button) findViewById(R.id.Button08);
		Button Button10 = (Button) findViewById(R.id.Button10);
		Button Button09 = (Button) findViewById(R.id.Button09);
		Button Button02 = (Button) findViewById(R.id.Button02);
		Button Button01 = (Button) findViewById(R.id.Button01);

		Button08.setTypeface(face);
		Button10.setTypeface(face);
		Button09.setTypeface(face);
		Button02.setTypeface(face);
		Button01.setTypeface(face);

		Button Button13 = (Button) findViewById(R.id.Button13);
		Button Button11 = (Button) findViewById(R.id.Button11);
		Button Button14 = (Button) findViewById(R.id.Button14);
		Button Button12 = (Button) findViewById(R.id.Button12);
		Button Button15 = (Button) findViewById(R.id.Button15);

		Button13.setTypeface(face);
		Button11.setTypeface(face);
		Button14.setTypeface(face);
		Button12.setTypeface(face);
		Button15.setTypeface(face);

		Button search = (Button)findViewById(R.id.search);		
		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(shabad.getText().toString().matches("")){
					Toast.makeText(getApplicationContext(), "Textfield cannot be empty.", Toast.LENGTH_SHORT).show();
				}else{
					perform_search();
				}
			}
		});

		ImageButton back_space = (ImageButton)findViewById(R.id.back_space);
		back_space.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!shabad.getText().toString().matches("")){
					String shabad_word = shabad.getText().toString();
					shabad.setText(shabad_word.substring(0, (shabad_word.length() - 1)));
				}
			}
		});

	}
	public void onClick(View v){
		switch(v.getId()){
		case R.id.Button07: shabad.setText(shabad.getText()+"a"); break;
		case R.id.Button06:	shabad.setText(shabad.getText()+"A"); break;
		case R.id.Button05: shabad.setText(shabad.getText()+"e"); break;
		case R.id.Button04: shabad.setText(shabad.getText()+"s"); break;
		case R.id.Button03: shabad.setText(shabad.getText()+"h"); break;

		case R.id.Button44:	shabad.setText(shabad.getText()+"k"); break;
		case R.id.Button43:	shabad.setText(shabad.getText()+"K"); break;
		case R.id.Button41:	shabad.setText(shabad.getText()+"g"); break;
		case R.id.Button42:	shabad.setText(shabad.getText()+"G"); break;
		case R.id.Button40:	shabad.setText(shabad.getText()+"|"); break;

		case R.id.Button48:	shabad.setText(shabad.getText()+"c"); break;
		case R.id.Button47:	shabad.setText(shabad.getText()+"C"); break;
		case R.id.Button49:	shabad.setText(shabad.getText()+"j"); break;
		case R.id.Button46:	shabad.setText(shabad.getText()+"J"); break;
		case R.id.Button45:	shabad.setText(shabad.getText()+"\\"); break;

		case R.id.Button54: shabad.setText(shabad.getText()+"t"); break;
		case R.id.Button52: shabad.setText(shabad.getText()+"T"); break;
		case R.id.Button53: shabad.setText(shabad.getText()+"f"); break;
		case R.id.Button50: shabad.setText(shabad.getText()+"F"); break;
		case R.id.Button51: shabad.setText(shabad.getText()+"x"); break;

		case R.id.Button56: shabad.setText(shabad.getText()+"q"); break;
		case R.id.Button58: shabad.setText(shabad.getText()+"Q"); break;
		case R.id.Button59: shabad.setText(shabad.getText()+"d"); break;
		case R.id.Button55: shabad.setText(shabad.getText()+"D"); break;
		case R.id.Button57: shabad.setText(shabad.getText()+"n"); break;

		case R.id.Button08: shabad.setText(shabad.getText()+"p"); break;
		case R.id.Button10: shabad.setText(shabad.getText()+"P"); break;
		case R.id.Button09: shabad.setText(shabad.getText()+"b"); break;
		case R.id.Button02: shabad.setText(shabad.getText()+"B"); break;
		case R.id.Button01: shabad.setText(shabad.getText()+"m"); break;

		case R.id.Button13: shabad.setText(shabad.getText()+"X"); break;
		case R.id.Button11: shabad.setText(shabad.getText()+"r"); break;
		case R.id.Button14: shabad.setText(shabad.getText()+"l"); break;
		case R.id.Button12: shabad.setText(shabad.getText()+"v"); break;
		case R.id.Button15: shabad.setText(shabad.getText()+"V"); break;

		}

	}

	private void perform_search() {
		search_word_arr = shabad.getText().toString().toCharArray();
		for (int i = 1; i <=1430; i++){   
			try {
				BufferedReader reader2 = new BufferedReader(new InputStreamReader(getAssets().open("gurmukhi/"+i)));
				String mLine = reader2.readLine(), mLine2;

				while (mLine != null) {
                    line_num++;
					mLine2 = mLine;
					mLine = mLine.replace(" i", " ");
					if (mLine.charAt(0) == 'i'){
						mLine = mLine.substring(1,mLine.length());
					}

					if (search_word_arr.length == 5){
						if((mLine.charAt(0) == search_word_arr[0]) && (mLine.charAt(mLine.indexOf(' ')+1) == search_word_arr[1])
								&& (mLine.charAt(mLine.indexOf(' ', (mLine.indexOf(' ') + 1)) + 1) == search_word_arr[2])
								&& (mLine.charAt(mLine.indexOf(' ', (mLine.indexOf(' ', (mLine.indexOf(' ') + 1)) + 1)) + 1) == search_word_arr[3])
								&& (mLine.charAt(mLine.indexOf(' ', (mLine.indexOf(' ', (mLine.indexOf(' ', mLine.indexOf(' ') + 1) + 1)) + 1)) + 1) == search_word_arr[4])

								){		
							arr.add(mLine2);
							list_array.put(mLine2, i);
                            line_num_arr.put(mLine2, line_num);
						}
					}else if (search_word_arr.length == 4){
						if((mLine.charAt(0) == search_word_arr[0]) && (mLine.charAt(mLine.indexOf(' ')+1) == search_word_arr[1])
								&& (mLine.charAt(mLine.indexOf(' ', (mLine.indexOf(' ') + 1)) + 1) == search_word_arr[2])
								&& (mLine.charAt(mLine.indexOf(' ', (mLine.indexOf(' ', (mLine.indexOf(' ') + 1)) + 1)) + 1) == search_word_arr[3])){		
							arr.add(mLine2);
							list_array.put(mLine2, i);
                            line_num_arr.put(mLine2, line_num);
						}
					}else if (search_word_arr.length == 3){
						if((mLine.charAt(0) == search_word_arr[0]) && (mLine.charAt(mLine.indexOf(' ')+1) == search_word_arr[1])
								&& (mLine.charAt(mLine.indexOf(' ', (mLine.indexOf(' ') + 1)) + 1) == search_word_arr[2])){		
							arr.add(mLine2);
							list_array.put(mLine2, i);
                            line_num_arr.put(mLine2, line_num);
						}
					}else if (search_word_arr.length == 2){
						if((mLine.charAt(0) == search_word_arr[0]) && (mLine.charAt(mLine.indexOf(' ')+1) == search_word_arr[1])){
							arr.add(mLine2);
							list_array.put(mLine2, i);
                            line_num_arr.put(mLine2, line_num);
						}
					}else if(search_word_arr.length == 1){
						if(mLine.charAt(0) == search_word_arr[0]){
							arr.add(mLine2);
							list_array.put(mLine2, i);
                            line_num_arr.put(mLine2, line_num);
						}
					}
					mLine = reader2.readLine();
				}
                line_num = 0;
			} catch (IOException e) {

			} 
		}
		if(arr.size() !=0){

			Toast.makeText(getApplicationContext(), arr.size()+" Result(s)", Toast.LENGTH_LONG).show();
			try{
				Class ourClass = Class.forName("gurbani.ujagar.Shabad");
				Intent ourIntent = new Intent(ShabadActivity.this, ourClass);
				startActivity(ourIntent);

			}catch(ClassNotFoundException e){
				e.printStackTrace();

			}

		}else{
			Toast.makeText(getApplicationContext(), "Sorry, no results.", Toast.LENGTH_LONG).show();
		}
	}
}


