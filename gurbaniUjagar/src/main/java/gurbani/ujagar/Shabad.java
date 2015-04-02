package gurbani.ujagar;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Shabad extends ListActivity {
    static public int shabad = 0;
    static String keyword;
    static String[] gurmukhi;
    static String line_search = "";

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
            font = Typeface.createFromAsset(getAssets(), "fonts/gurbaniwebthick.ttf");
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setListAdapter(new MyCustomAdapter(Shabad.this, R.layout.mylist, ShabadActivity.arr));
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        keyword = o.toString();
        shabad = ShabadActivity.list_array.get(keyword);
        line_search = keyword;
        CharSequence colors[] = new CharSequence[]{"Shabad", "English Translation", "Punjabi Translation", "Punjabi Teeka"};

        AlertDialog.Builder builder = new AlertDialog.Builder(Shabad.this);
        builder.setTitle("Open Ang " + shabad + " with: ");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // English Translation

                if(which == 0){
                    try {
                        Class ourClass = Class.forName("gurbani.ujagar.ShabadView");
                        Intent i = new Intent(Shabad.this, ourClass);
                        i.putExtra("Position", shabad);
                        startActivity(i);

                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }else if (which == 1) {
                    try {
                        Class ourClass = Class.forName("gurbani.ujagar.SantSingh");
                        Intent i = new Intent(Shabad.this, ourClass);
                        i.putExtra("Position", shabad);
                        startActivity(i);

                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (which == 2) {
                    try {
                        Class ourClass = Class.forName("gurbani.ujagar.ManmohanSingh");
                        Intent i = new Intent(Shabad.this, ourClass);
                        i.putExtra("Position", shabad);
                        startActivity(i);

                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else if (which == 3) {
                    try {
                        Class ourClass = Class.forName("gurbani.ujagar.SahibSingh");
                        Intent i = new Intent(Shabad.this, ourClass);
                        i.putExtra("Position", shabad);
                        startActivity(i);

                    } catch (ClassNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        builder.show();
    }
}
