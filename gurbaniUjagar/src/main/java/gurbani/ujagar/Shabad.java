package gurbani.ujagar;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingListActivity;

import java.util.ArrayList;

public class Shabad extends SlidingListActivity {
    static public int shabad = 0;
    static String keyword;
    static String line_search = "";
    private final String SHABAD_LIST = "shabad_list", SHABAD_ARR = "shabad_arr",
            SHABAD_ANG = "shabad_ang";
    private ArrayList<String> shabad_arr = new ArrayList<String>();
    private ArrayList<Integer> shabad_ang = new ArrayList<Integer>();

    @Override
    public void onBackPressed() {
        try {
            Class ourClass = Class.forName("gurbani.ujagar.ShabadActivity");
            Intent ourIntent = new Intent(Shabad.this, ourClass);
            startActivity(ourIntent);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        ShabadActivity.arr.clear();
        finish();
    }

    public class MyCustomAdapter extends ArrayAdapter<String> {
        private Typeface font;
        private TextView tt;

        public MyCustomAdapter(Context context, int textViewResourceId, ArrayList<String> arr) {

            super(context, textViewResourceId, arr);
            font = Typeface.createFromAsset(getAssets(), "fonts/anmollipinumbers.ttf");
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.mylist, null);
            }
            tt = (TextView) v.findViewById(R.id.text);
            tt.setTypeface(font);
            tt.setTextSize(24);
            tt.setText("" + ShabadActivity.arr.get(position));

            return v;
        }
    }

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        slide_menu();
        if(savedInstanceState != null){
            ShabadActivity.arr = savedInstanceState.getStringArrayList(SHABAD_LIST);
            shabad_arr = savedInstanceState.getStringArrayList(SHABAD_ARR);
            shabad_ang = savedInstanceState.getIntegerArrayList(SHABAD_ANG);

        }
        setListAdapter(new MyCustomAdapter(Shabad.this, R.layout.mylist, ShabadActivity.arr));
        final ListView list = getListView();
        list.setBackgroundColor(Color.rgb(85, 129, 136));
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        keyword = o.toString();

        for(String line: ShabadActivity.list_array.keySet()){
            shabad_arr.add(line);
            shabad_ang.add(ShabadActivity.list_array.get(line));
        }

        for(int i = 0; i < shabad_arr.size(); i++){
            if(shabad_arr.get(i).equals(keyword)){
                shabad = shabad_ang.get(i);
            }
        }

        line_search = keyword;

        CharSequence options[] = new CharSequence[]{"Gurbani Only", "Punjabi Teeka", "Punjabi Translation", "English Translation"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Shabad.this);
        builder.setTitle("Open Ang " + shabad + " with: ");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which >= 0 && which <= 3){
                    ConstantsMethods.bani = "guru_granth";
                    ConstantsMethods.ang = 1430;
                    ConstantsMethods.bani_name = "Ang";
                    ConstantsMethods.pos = which;

                    try {
                        Class ourClass = Class.forName("gurbani.ujagar.Gurbani");
                        Intent i = new Intent(Shabad.this, ourClass);
                        startActivity(i);

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        builder.show();

    }

    public void slide_menu() {
        getSlidingMenu().setBehindOffset(150);
        getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        setBehindContentView(R.layout.menu);
        getSlidingMenu().setSecondaryMenu(R.layout.menu2);

        final ListView sggs = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simplerow, ConstantsMethods.SGGS);
        sggs.setAdapter(adapter);
        final ConstantsMethods constant = new ConstantsMethods();

        sggs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constant.sggsMethod(parent, view, position, id, Shabad.this);
            }
        });

        ListView general = (ListView) findViewById(R.id.general_menu);
        ArrayAdapter<String> adapter2 = new GeneralArrayAdapter(this, R.layout.simplerow, ConstantsMethods.GENERAL);
        general.setAdapter(adapter2);

        general.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggle();
                constant.generalMethod(parent, view, position, id, Shabad.this);
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putStringArrayList(SHABAD_LIST, ShabadActivity.arr);
        savedInstanceState.putStringArrayList(SHABAD_ARR, shabad_arr);
        savedInstanceState.putIntegerArrayList(SHABAD_ANG, shabad_ang);

    }
}
