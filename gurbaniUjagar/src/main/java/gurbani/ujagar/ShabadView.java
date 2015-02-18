package gurbani.ujagar;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Images;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.EmptyStackException;

import gurbani.ujagar.animated.TransitionViewPager;
import gurbani.ujagar.animated.TransitionViewPager.TransitionEffect;


public class ShabadView extends SlidingFragmentActivity {
    SectionsPagerAdapter mSectionsPagerAdapter;
    static TransitionViewPager mViewPager;
    static SharedPreferences getPrefs;
    public static final String MyPREFERENCES = "MyPrefs";
    static SharedPreferences sharedpreferences;
    ImageView logo;
    TextView textView, txt1, txt2;
    static String html_file = "";
    static Context context;
    public void onBackPresed() {
        finish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.same_pager);
        getSlidingMenu().setBehindOffset(400);
        getSlidingMenu().setMode(SlidingMenu.LEFT_RIGHT);
        getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        setBehindContentView(R.layout.menu);
        getSlidingMenu().setSecondaryMenu(R.layout.menu2);
        context = ShabadView.this;

        final ListView sggs = (ListView) findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.simplerow, ConstantsMethods.SGGS);
        sggs.setAdapter(adapter);
        final ConstantsMethods constant = new ConstantsMethods();

        sggs.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                constant.sggsMethod(parent, view, position, id, ShabadView.this);
            }
        });

        ListView general = (ListView) findViewById(R.id.general_menu);
        ArrayAdapter<String> adapter2 = new GeneralArrayAdapter(this, R.layout.simplerow, ConstantsMethods.GENERAL);
        general.setAdapter(adapter2);

        general.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggle();
                constant.generalMethod(parent, view, position, id, ShabadView.this);
            }
        });
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getSupportFragmentManager());
        mViewPager = (TransitionViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        if (Shabad.shabad != 0) {
            mViewPager.setCurrentItem(Shabad.shabad);
            Shabad.shabad = 0;
        }

        getPrefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        for (int i = 1; i <= 10; i++) {
            String transitionEffect = getPrefs.getString("prefTransitionEffect", "" + i);
            if (transitionEffect.equals("" + 1)) {
                mViewPager.setTransitionEffect(TransitionEffect.Standard);
            } else if (transitionEffect.equals("" + 2)) {
                mViewPager.setTransitionEffect(TransitionEffect.Tablet);
            } else if (transitionEffect.equals("" + 3)) {
                mViewPager.setTransitionEffect(TransitionEffect.CubeIn);
            } else if (transitionEffect.equals("" + 4)) {
                mViewPager.setTransitionEffect(TransitionEffect.CubeOut);
            } else if (transitionEffect.equals("" + 5)) {
                mViewPager.setTransitionEffect(TransitionEffect.FlipVertical);
            } else if (transitionEffect.equals("" + 6)) {
                mViewPager.setTransitionEffect(TransitionEffect.FlipHorizontal);
            } else if (transitionEffect.equals("" + 7)) {
                mViewPager.setTransitionEffect(TransitionEffect.ZoomIn);
            } else if (transitionEffect.equals("" + 8)) {
                mViewPager.setTransitionEffect(TransitionEffect.RotateUp);
            } else if (transitionEffect.equals("" + 9)) {
                mViewPager.setTransitionEffect(TransitionEffect.RotateDown);
            } else if (transitionEffect.equals("" + 10)) {
                mViewPager.setTransitionEffect(TransitionEffect.Accordion);
            } else {
                mViewPager.setTransitionEffect(TransitionEffect.Standard);
            }
        }


    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int position) {
            Fragment shabadView = new ShabadViewAng(position);
            mViewPager.setObjectForPosition(shabadView, position);
            return shabadView;
        }

        @Override
        public int getCount() {
            return 1431;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            for (int i = 1; i <= 1431; i++) {
                if (position == i) {
                    return "Ang " + position;
                }
            }
            return null;
        }
    }

    @SuppressLint("ValidFragment")
    public static class ShabadViewAng extends Fragment {
        private static int x = 0;
        private int position, num;
        Button search;
        ImageButton maxmin;
        EditText number;

        public ShabadViewAng() {

        }

        public ShabadViewAng(int position) {
            this.position = position;
        }


        public void process_html(int pos){
            String line = "";
            html_file = "";
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open("html_text")));

                String mLine = reader.readLine();
                while (mLine != null) {

                    html_file = html_file + "\n" + mLine;
                    mLine = reader.readLine();
                }
            } catch (IOException e) {
            }




            try {
                reader = new BufferedReader(new InputStreamReader(context.getAssets().open("gurmukhi/"+pos)));
                String mLine = reader.readLine();
                while (mLine != null) {
                    if(mLine.equals(Shabad.line_search)){
                        mLine = "<mark>" + mLine + "</mark>";
                    }
                    html_file = html_file + "<br><br>\n" + mLine;
                    mLine = reader.readLine();
                }
            } catch (IOException e) {
            }
            html_file = html_file + "<br><br><br>";
            html_file = html_file + "\n" + "</p>\n</body>\n</html>";

        }


        @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
        @SuppressWarnings("deprecation")
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.above_one_page_web, container, false);
            final WebView webview = (WebView) rootView.findViewById(R.id.webview);
            search = (Button) rootView.findViewById(R.id.clicktosearch);
            number = (EditText) rootView.findViewById(R.id.number);
            maxmin = (ImageButton) rootView.findViewById(R.id.maxmin);

            final ImageButton screenshot = (ImageButton) rootView.findViewById(R.id.imageButton1);


            screenshot.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    rootView.setDrawingCacheEnabled(true);
                    Bitmap bitmap = rootView.getDrawingCache();

                    Toast.makeText(getActivity(), "Image has been saved to Gallery.", Toast.LENGTH_SHORT).show();
                    ContentValues values = new ContentValues();
                    values.put(Images.Media.DATE_ADDED, System.currentTimeMillis());
                    values.put(Images.Media.DATE_TAKEN, System.currentTimeMillis());
                    values.put(Images.Media.DESCRIPTION, "Image");
                    values.put(Images.Media.MIME_TYPE, "image/jpg");


                    Uri url = null;
                    try {
                        url = rootView.getContext().getContentResolver().insert(Images.Media.EXTERNAL_CONTENT_URI, values);

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
                            values.put(Images.Thumbnails.KIND, Images.Thumbnails.MICRO_KIND);
                            values.put(Images.Thumbnails.IMAGE_ID, (int) id);
                            values.put(Images.Thumbnails.HEIGHT, thumb.getHeight());
                            values.put(Images.Thumbnails.WIDTH, thumb.getWidth());

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


            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!(number.getText().toString().equals(""))) {
                        num = Integer.parseInt(number.getText().toString());
                        ShabadView.mViewPager.setCurrentItem(num);
                    }

                    webview.reload();
                    number.setText("");
                }
            });

            try {

                process_html(position);
                webview.loadDataWithBaseURL("x-data://base", html_file,"text/html", "UTF-8", null);
                maxmin.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (x == 0) {
                            search.setVisibility(View.GONE);
                            number.setVisibility(View.GONE);
                            screenshot.setVisibility(View.GONE);
                            maxmin.setImageResource(R.drawable.minimize);
                            x = 1;
                        } else {
                            search.setVisibility(View.VISIBLE);
                            number.setVisibility(View.VISIBLE);
                            screenshot.setVisibility(View.VISIBLE);
                            maxmin.setImageResource(R.drawable.maximize);
                            x = 0;
                        }
                    }
                });


                webview.getSettings().setBuiltInZoomControls(true);
                WebSettings webSettings = webview.getSettings();
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
                    webview.getSettings().setDisplayZoomControls(false);
                }
                webSettings.setTextSize(WebSettings.TextSize.SMALLEST);

                for (int i = 1; i <= 6; i++) {
                    String textSize = getPrefs.getString("prefWebviewSize", "" + i);
                    if (textSize.equals("" + 1)) {
                        webSettings.setTextSize(WebSettings.TextSize.SMALLEST);
                    } else if (textSize.equals("" + 2)) {
                        webSettings.setTextSize(WebSettings.TextSize.SMALLER);
                    } else if (textSize.equals("" + 3)) {
                        webSettings.setTextSize(WebSettings.TextSize.NORMAL);
                    } else if (textSize.equals("" + 4)) {
                        webSettings.setTextSize(WebSettings.TextSize.LARGER);
                    } else if (textSize.equals("" + 5)) {
                        webSettings.setTextSize(WebSettings.TextSize.LARGEST);
                    } else {
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
