package gurbani.ujagar;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

/**
 * Created by Waheguru on 6/6/15.
 */
public class Gurbani extends SlidingActivity implements Animation.AnimationListener {

    LinearLayout options, textLayout, downview;
    Button previous, settings, next, search;
    EditText number;
    CheckBox punjabi, english, teeka, light;
    TextView gurbani, textChange, ang;
    SeekBar textSeekBar;
    StringBuffer gurbani_text = new StringBuffer();
    boolean eng, pun, tee, low_light;
    static int granth_num = 1, visibility = 1, touch = 1, japji_num = 1, sukhmani_num = 1,
            anand_num = 1, page_num = 1, times = 0;
    static final String CURRENT_BANI = "current_bani", BANI_NAME = "bani_name", ANG = "ang",
            POS = "pos", SHABAD = "shabad";

    SharedPreferences sp;
    SharedPreferences.Editor spe;
    Animation animSlideIn, animSlideOut;
    ScrollView scroll;
    ImageButton screenshot, bookmark;
    RelativeLayout screen_layout;
    View view;
    DatabaseHelper bookmarkDB;
    Cursor res;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.multi_page_web);

        // Initializing all the components.
        initialize();

        //save_data_to_db();

        // Slide Menu buttons' functions.
        slide_menu();

        // SharedPreference variables and cases.
        times = sp.getInt("button_pointer", 0);
        if (times <= 1) {
            final Animation animation = new AlphaAnimation(1, 0); // Change alpha from fully visible to invisible
            animation.setDuration(500); // duration - half a second
            animation.setInterpolator(new LinearInterpolator()); // do not alter animation rate
            animation.setRepeatCount(Animation.INFINITE); // Repeat animation infinitely
            animation.setRepeatMode(Animation.REVERSE); // Reverse animation at the end so the button will fade back in
            settings.startAnimation(animation);
        }

        if (savedInstanceState != null) {
            ConstantsMethods.bani = savedInstanceState.getString(CURRENT_BANI, "guru_granth");
            ConstantsMethods.bani_name = savedInstanceState.getString(BANI_NAME, "bani_name");
            ang.setText(ConstantsMethods.bani_name);
            ConstantsMethods.ang = savedInstanceState.getInt(ANG, 1);
            ConstantsMethods.pos = savedInstanceState.getInt(POS, 0);
            Shabad.shabad = savedInstanceState.getInt(SHABAD, 1);
        }

        if (ConstantsMethods.ang == 1) {
            number.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
            previous.setEnabled(false);
            next.setEnabled(false);
        }


        int num = sp.getInt("seekNum", 3);

        if (ConstantsMethods.bani.equals("guru_granth")) {
            granth_num = sp.getInt("granth_num", 1);
            page_num = granth_num;


            if (Shabad.shabad != 0) {
                page_num = Shabad.shabad;

                spe.putInt("granth_num", page_num);
                spe.commit();

                Shabad.shabad = 0;


            } else if (Bookmark.shabad != 0) {
                page_num = Bookmark.shabad;

                spe.putInt("granth_num", page_num);
                spe.commit();

                Bookmark.shabad = 0;

            } else if (AuthorExpandableAdapter.authorNum != 0) {
                page_num = AuthorExpandableAdapter.authorNum;

                spe.putInt("granth_num", page_num);
                spe.commit();

                AuthorExpandableAdapter.authorNum = 0;

            } else if (RaagExpandableAdapter.raagNum != 0) {
                page_num = RaagExpandableAdapter.raagNum;

                spe.putInt("granth_num", page_num);
                spe.commit();

                RaagExpandableAdapter.raagNum = 0;

            }

            if (ConstantsMethods.pos == 0) {
                pun = eng = tee = false;

            } else if (ConstantsMethods.pos == 1) {
                tee = true;
                eng = pun = false;

            } else if (ConstantsMethods.pos == 2) {
                pun = true;
                eng = tee = false;

            } else if (ConstantsMethods.pos == 3) {
                eng = true;
                tee = pun = false;

            }


        } else if (ConstantsMethods.bani.equals("japji_sahib")) {
            japji_num = sp.getInt("japji_num", 1);
            page_num = japji_num;
            pun = eng = tee = false;

        } else if (ConstantsMethods.bani.equals("sukhmani_sahib")) {
            sukhmani_num = sp.getInt("sukhmani_num", 1);
            page_num = sukhmani_num;
            pun = eng = tee = false;

        } else if (ConstantsMethods.bani.equals("anand_sahib")) {
            anand_num = sp.getInt("anand_num", 1);
            page_num = anand_num;
            pun = eng = tee = false;

        } else if (ConstantsMethods.bani.equals("6_paudi_anand_sahib")
                || ConstantsMethods.bani.equals("rehraas_sahib")
                || ConstantsMethods.bani.equals("kirtan_sohila") || ConstantsMethods.bani.equals("salok_mahalla_9")
                || ConstantsMethods.bani.equals("laavan")) {
            page_num = 1;
            pun = eng = tee = false;

        } else {
            pun = eng = tee = false;
        }


        low_light = sp.getBoolean("low_light", false);

        if (low_light) {
            light.setChecked(true);
            gurbani.setBackgroundColor(Color.BLACK);
            options.setBackgroundColor(Color.BLACK);
            ang.setTextColor(Color.WHITE);
            number.setTextColor(Color.WHITE);
        }


        // Checks to see what translation to view.

        check();

        conditions();

        if (ConstantsMethods.bani.equals("rehraas_sahib")) {
            Toast.makeText(getApplicationContext(), "Due to translation/teeka problems, rehraas sahib is incomplete."
                    , Toast.LENGTH_LONG).show();
        }
        // Text Size Seek Bar.
        textSeekBar.setProgress(num);
        gurbani.setTextSize(TypedValue.COMPLEX_UNIT_PX, (textSeekBar.getProgress() + 5) * getResources().getDisplayMetrics().density);
        textChange.setText(String.valueOf(textSeekBar.getProgress()) + "pt");
        number.setText("" + page_num);

        // Functions when button clicked from the downview.
        downview_options();

        // Functions when next, previous, search is clicked to update the granth_num.
        open_ang();

        // Functions when buttons from main layout is clicked.
        main_layout_options();

    }

    public void conditions() {

        // View All 3
        if (tee && pun && eng) {
            teeka.setChecked(true);
            punjabi.setChecked(true);
            english.setChecked(true);
            readGurbaniTeekaPunjabiEnglish();

        }

        // View Only 2
        if (tee && pun && !eng) {
            teeka.setChecked(true);
            punjabi.setChecked(true);
            english.setChecked(false);
            readGurbaniPunjabiTeeka();

        }
        if (tee && eng && !pun) {
            teeka.setChecked(true);
            punjabi.setChecked(false);
            english.setChecked(true);
            readGurbaniEnglishTeeka();

        }
        if (pun && eng && !tee) {
            teeka.setChecked(false);
            punjabi.setChecked(true);
            english.setChecked(true);
            readGurbaniPunjabiEnglish();

        }

        // View Only 1
        if (tee && !pun && !eng) {
            teeka.setChecked(true);
            punjabi.setChecked(false);
            english.setChecked(false);
            readGurbaniTeeka();

        }
        if (pun && !tee && !eng) {
            teeka.setChecked(false);
            punjabi.setChecked(true);
            english.setChecked(false);
            readGurbaniPunjabi();

        }
        if (eng && !pun && !tee) {
            teeka.setChecked(false);
            punjabi.setChecked(false);
            english.setChecked(true);
            readGurbaniEnglish();

        }

        // View None
        if (!eng && !pun && !tee) {
            teeka.setChecked(false);
            punjabi.setChecked(false);
            english.setChecked(false);
            readGurbani();

        }
    }

    public void readGurbaniTeekaPunjabiEnglish() {
        gurbani_text.setLength(0);
        BufferedReader reader, reader2, reader3, reader4, reader5;
        try {

            reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/manmohan_arth_new/" + page_num)));
            reader3 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sant_khalsa_internet/" + page_num)));
            reader4 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_pad_arth_new/" + page_num)));
            reader5 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_arth_new/" + page_num)));


            String mLine = reader.readLine(), mLine2 = reader2.readLine(), mLine3 = reader3.readLine(),
                    mLine4 = reader4.readLine(), mLine5 = reader5.readLine();
            while (mLine != null) {
                mLine = mLine.replace("<>", "&lt&gt");
                mLine2 = mLine2.replace("<>", "&lt&gt");
                mLine3 = mLine3.replace("<>", "&lt&gt");
                mLine4 = mLine4.replace("<>", "&lt&gt");
                mLine5 = mLine5.replace("<>", "&lt&gt");


                if (mLine.equals(Shabad.line_search)) {
                    gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br>");
                } else {
                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");
                }

                if ((mLine4.contains("xxx") || mLine4.contains("XXX")) && (mLine5.contains("xxx") || mLine5.contains("XXX"))) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#08575E face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#4AB825 face=\"verdana\">" + mLine4 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine5 + "</font><br><br>");

                } else if ((mLine4.contains("xxx") || mLine4.contains("XXX")) && (!mLine5.contains("xxx") || !mLine5.contains("XXX"))) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#08575E face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#4AB825 face=\"verdana\">" + mLine4 +
                            "</font><br><font color=#2447B2>" + mLine5 + "</font><br><br>");
                } else if ((!mLine4.contains("xxx") || !mLine4.contains("XXX")) && (mLine5.contains("xxx") || mLine5.contains("XXX"))) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#08575E face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#4AB825>" + mLine4 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine5 + "</font><br><br>");

                } else if (!mLine4.contains("xxx") && !mLine4.contains("XXX") && !mLine5.contains("xxx") && !mLine5.contains("XXX")) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#08575E face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#4AB825>" + mLine4 +
                            "</font><br><font color=#2447B2>" + mLine5 + "</font><br><br>");
                }
                mLine = reader.readLine();
                mLine2 = reader2.readLine();
                mLine3 = reader3.readLine();
                mLine4 = reader4.readLine();
                mLine5 = reader5.readLine();
            }
            gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
        } catch (IOException e) {
        }
    }

    public void readGurbaniPunjabiEnglish() {
        gurbani_text.setLength(0);
        BufferedReader reader, reader2, reader3;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/manmohan_arth_new/" + page_num)));
            reader3 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sant_khalsa_internet/" + page_num)));
            String mLine = reader.readLine(), mLine2 = reader2.readLine(), mLine3 = reader3.readLine();
            while (mLine != null) {
                mLine = mLine.replace("<>", "&lt&gt");
                mLine2 = mLine2.replace("<>", "&lt&gt");
                mLine3 = mLine3.replace("<>", "&lt&gt");

                if (mLine.equals(Shabad.line_search)) {
                    gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br>");
                } else {
                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");
                }

                gurbani_text.append("<font color=#996633>" + mLine2 +
                        "</font><br><font color=#08575E face=\"verdana\">" + mLine3
                        + "</font><br><br>");
                mLine = reader.readLine();
                mLine2 = reader2.readLine();
                mLine3 = reader3.readLine();
            }
            gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
        } catch (IOException e) {
        }
    }

    public void readGurbaniPunjabiTeeka() {
        gurbani_text.setLength(0);
        BufferedReader reader, reader2, reader3, reader4;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/manmohan_arth_new/" + page_num)));
            reader3 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_pad_arth_new/" + page_num)));
            reader4 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_arth_new/" + page_num)));

            String mLine = reader.readLine(), mLine2 = reader2.readLine(), mLine3 = reader3.readLine(), mLine4 = reader4.readLine();
            while (mLine != null) {
                mLine = mLine.replace("<>", "&lt&gt");
                mLine2 = mLine2.replace("<>", "&lt&gt");
                mLine3 = mLine3.replace("<>", "&lt&gt");
                mLine4 = mLine4.replace("<>", "&lt&gt");

                if (mLine.equals(Shabad.line_search)) {
                    gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br>");
                } else {
                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");
                }

                if ((mLine3.equals("xxx") || mLine3.equals("XXX")) && (mLine4.contains("xxx") || mLine4.contains("XXX"))) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#4AB825 face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine4 + "</font><br><br>");

                } else if ((mLine3.equals("xxx") || mLine3.equals("XXX")) && (!mLine4.contains("xxx") || !mLine4.contains("XXX"))) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#4AB825 face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#2447B2>" + mLine4 + "</font><br><br>");

                } else if ((!mLine3.equals("xxx") || !mLine3.equals("XXX")) && (mLine4.contains("xxx") || mLine4.contains("XXX"))) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#4AB825>" + mLine3 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine4 + "</font><br><br>");

                } else if (!mLine3.equals("xxx") && !mLine3.equals("XXX") && !mLine4.contains("xxx") && !mLine4.contains("XXX")) {

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><font color=#4AB825>" + mLine3 +
                            "</font><br><font color=#2447B2>" + mLine4 + "</font><br><br>");
                }


                mLine = reader.readLine();
                mLine2 = reader2.readLine();
                mLine3 = reader3.readLine();
                mLine4 = reader4.readLine();
            }
            gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
        } catch (IOException e) {
        }
    }

    public void readGurbaniEnglishTeeka() {
        gurbani_text.setLength(0);
        BufferedReader reader, reader2, reader3, reader4;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sant_khalsa_internet/" + page_num)));
            reader3 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_pad_arth_new/" + page_num)));
            reader4 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_arth_new/" + page_num)));

            String mLine = reader.readLine(), mLine2 = reader2.readLine(), mLine3 = reader3.readLine(), mLine4 = reader4.readLine();
            while (mLine != null) {
                mLine = mLine.replace("<>", "&lt&gt");
                mLine2 = mLine2.replace("<>", "&lt&gt");
                mLine3 = mLine3.replace("<>", "&lt&gt");
                mLine4 = mLine4.replace("<>", "&lt&gt");

                if (mLine.equals(Shabad.line_search)) {
                    gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br>");
                } else {
                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");
                }

                if ((mLine3.equals("xxx") || mLine3.equals("XXX")) && (mLine4.contains("xxx") || mLine4.contains("XXX"))) {

                    gurbani_text.append("<font color=#08575E face=\"verdana\">" + mLine2 +
                            "</font><br><font color=#4AB825 face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine4 + "</font><br><br>");

                } else if ((mLine3.equals("xxx") || mLine3.equals("XXX")) && (!mLine4.contains("xxx") || !mLine4.contains("XXX"))) {
                    gurbani_text.append("<font color=#08575E face=\"verdana\">" + mLine2 +
                            "</font><br><font color=#4AB825 face=\"verdana\">" + mLine3 +
                            "</font><br><font color=#2447B2>" + mLine4 + "</font><br><br>");

                } else if ((!mLine3.equals("xxx") || !mLine3.equals("XXX")) && (mLine4.contains("xxx") || mLine4.contains("XXX"))) {

                    gurbani_text.append("<font color=#08575E face=\"verdana\">" + mLine2 +
                            "</font><br><font color=#4AB825>" + mLine3 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine4 + "</font><br><br>");

                } else if (!mLine3.equals("xxx") && !mLine3.equals("XXX") && !mLine4.contains("xxx") && !mLine4.contains("XXX")) {

                    gurbani_text.append("<font color=#08575E face=\"verdana\">" + mLine2 +
                            "</font><br><font color=#4AB825>" + mLine3 +
                            "</font><br><font color=#2447B2>" + mLine4 + "</font><br><br>");
                }


                mLine = reader.readLine();
                mLine2 = reader2.readLine();
                mLine3 = reader3.readLine();
                mLine4 = reader4.readLine();
            }
            gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
        } catch (IOException e) {
        }
    }

    public void readGurbani() {
        gurbani_text.setLength(0);
        BufferedReader reader;
        try {
            if (ConstantsMethods.bani.equals("jaap_sahib") || ConstantsMethods.bani.equals("chaupai_sahib")
                    || ConstantsMethods.bani.equals("tav_parsad")) {
                reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi")));
                String mLine = reader.readLine();
                while (mLine != null) {
                    mLine = mLine.replace("<>", "&lt&gt");
                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br><br>");

                    mLine = reader.readLine();
                }
                gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
                gurbani.setMovementMethod(LinkMovementMethod.getInstance());
                // gurbani.scrollTo(0,0);

            } else {
                reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
                String mLine = reader.readLine();
                while (mLine != null) {
                    mLine = mLine.replace("<>", "&lt&gt");

                    if (mLine.equals(Shabad.line_search)) {
                        gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br><br>");
                    } else {
                        gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br><br>");
                    }
                    mLine = reader.readLine();
                }
                gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
                gurbani.setMovementMethod(LinkMovementMethod.getInstance());
                //gurbani.scrollTo(0,0);
            }


        } catch (IOException e) {

        }
    }

    public void readGurbaniPunjabi() {
        gurbani_text.setLength(0);
        BufferedReader reader, reader2;

        try {

            if (ConstantsMethods.bani.equals("jaap_sahib") || ConstantsMethods.bani.equals("chaupai_sahib")
                    || ConstantsMethods.bani.equals("tav_parsad")) {
                reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi")));
                reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/kulwant_singh")));

//                Toast.makeText(getApplication(), "Please wait while loading.", Toast.LENGTH_SHORT).show();
                String mLine = reader.readLine(), mLine2 = reader2.readLine();
                while (mLine != null) {
                    mLine = mLine.replace("<>", "&lt&gt");
                    mLine2 = mLine2.replace("<>", "&lt&gt");

                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><br>");
                    mLine = reader.readLine();
                    mLine2 = reader2.readLine();
                }

                gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
            } else {
                reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
                reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/manmohan_arth_new/" + page_num)));

                String mLine = reader.readLine(), mLine2 = reader2.readLine();
                while (mLine != null) {
                    mLine = mLine.replace("<>", "&lt&gt");
                    mLine2 = mLine2.replace("<>", "&lt&gt");

                    if (mLine.equals(Shabad.line_search)) {
                        gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br>");
                    } else {
                        gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");
                    }

                    gurbani_text.append("<font color=#996633>" + mLine2 +
                            "</font><br><br>");
                    mLine = reader.readLine();
                    mLine2 = reader2.readLine();
                }
                gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
            }

        } catch (IOException e) {

        }
    }

    public void readGurbaniTeeka() {
        gurbani_text.setLength(0);
        BufferedReader reader, reader2, reader3;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_pad_arth_new/" + page_num)));
            reader3 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sahib_arth_new/" + page_num)));
            String mLine = reader.readLine(), mLine2 = reader2.readLine(), mLine3 = reader3.readLine();
            while (mLine != null) {
                mLine = mLine.replace("<>", "&lt&gt");
                mLine2 = mLine2.replace("<>", "&lt&gt");
                mLine3 = mLine3.replace("<>", "&lt&gt");

                if (mLine.equals(Shabad.line_search)) {
                    gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br>");
                } else {
                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");
                }

                if ((mLine2.contains("xxx") || mLine2.contains("XXX")) && (mLine3.equals("xxx") || mLine3.equals("XXX"))) {

                    gurbani_text.append("<font color=#4AB825 face=\"verdana\">" + mLine2 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine3 + "</font><br><br>");

                } else if ((mLine2.contains("xxx") || mLine2.contains("XXX")) && (!mLine3.equals("xxx") || !mLine3.equals("XXX"))) {
                    gurbani_text.append("<font color=#4AB825 face=\"verdana\">" + mLine2 +
                            "</font><br><font color=#2447B2>" + mLine3 + "</font><br><br>");

                } else if ((!mLine2.contains("xxx") || !mLine2.contains("XXX")) && (mLine3.equals("xxx") || mLine3.equals("XXX"))) {

                    gurbani_text.append("<font color=#4AB825>" + mLine2 +
                            "</font><br><font color=#2447B2 face=\"verdana\">" + mLine3 + "</font><br><br>");

                } else if (!mLine2.contains("xxx") && !mLine2.contains("XXX") && !mLine3.equals("xxx") && !mLine3.equals("XXX")) {

                    gurbani_text.append("<font color=#4AB825>" + mLine2 +
                            "</font><br><font color=#2447B2>" + mLine3 + "</font><br><br>");
                }

                mLine = reader.readLine();
                mLine2 = reader2.readLine();
                mLine3 = reader3.readLine();
            }
            gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
        } catch (IOException e) {
        }
    }

    public void readGurbaniEnglish() {
        gurbani_text.setLength(0);
        BufferedReader reader, reader2;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/gurmukhi/" + page_num)));
            reader2 = new BufferedReader(new InputStreamReader(getAssets().open(ConstantsMethods.bani + "/sant_khalsa_internet/" + page_num)));

            String mLine = reader.readLine(), mLine2 = reader2.readLine();
            while (mLine != null) {
                mLine = mLine.replace("<>", "&lt&gt");
                mLine2 = mLine2.replace("<>", "&lt&gt");

                if (mLine.equals(Shabad.line_search)) {
                    gurbani_text.append("<b><big><font color=#FF0000>" + mLine + "</font></big></b><br>");
                } else {
                    gurbani_text.append("<big><font color=#B24242>" + mLine + "</font></big><br>");
                }

                gurbani_text.append("<font color=#08575E face=\"verdana\">" + mLine2 +
                        "</font><br><br>");
                mLine = reader.readLine();
                mLine2 = reader2.readLine();
            }
            gurbani.setText(Html.fromHtml(gurbani_text.toString() + "<br><br>"));
        } catch (IOException e) {
        }
    }

    @Override
    public void onBackPressed() {
        if (visibility == 0) {
            textLayout.startAnimation(animSlideIn);
            textLayout.setVisibility(View.GONE);
            visibility = 1;
        } else {
            finish();
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        textLayout.clearAnimation();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onAnimationStart(Animation animation) {
        // TODO Auto-generated method stub

    }

    public void take_screenshot() {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();

        Toast.makeText(getApplicationContext(), "Image has been saved to Gallery.", Toast.LENGTH_SHORT).show();
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.DESCRIPTION, "Image");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpg");


        Uri url = null;
        try {
            url = view.getContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            if (bitmap != null) {
                OutputStream imageOut = view.getContext().getContentResolver().openOutputStream(url);
                try {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, imageOut);
                } finally {
                    imageOut.close();
                }

                long id = ContentUris.parseId(url);
                Bitmap miniThumb = MediaStore.Images.Thumbnails.getThumbnail(view.getContext().getContentResolver(), id, MediaStore.Images.Thumbnails.MINI_KIND, null);
                Matrix matrix = new Matrix();
                float scaleX = 50f / miniThumb.getWidth();
                float scaleY = 50f / miniThumb.getHeight();

                matrix.setScale(scaleX, scaleY);

                Bitmap thumb = Bitmap.createBitmap(miniThumb, 0, 0, miniThumb.getWidth(), miniThumb.getHeight(), matrix, true
                );

                ContentValues valuess = new ContentValues(4);
                values.put(MediaStore.Images.Thumbnails.KIND, MediaStore.Images.Thumbnails.MICRO_KIND);
                values.put(MediaStore.Images.Thumbnails.IMAGE_ID, (int) id);
                values.put(MediaStore.Images.Thumbnails.HEIGHT, thumb.getHeight());
                values.put(MediaStore.Images.Thumbnails.WIDTH, thumb.getWidth());

                Uri urll = view.getContext().getContentResolver().insert(MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI, valuess);

                try {
                    OutputStream thumbOut = view.getContext().getContentResolver().openOutputStream(urll);
                    thumb.compress(Bitmap.CompressFormat.JPEG, 100, thumbOut);
                    thumbOut.close();
                    return;
                } catch (FileNotFoundException ex) {
                    return;
                } catch (IOException ex) {
                    return;
                }

            } else {
                view.getContext().getContentResolver().delete(url, null, null);
                url = null;
            }
        } catch (Exception e) {
            if (url != null) {
                view.getContext().getContentResolver().delete(url, null, null);
            }
        }
    }

    public boolean check() {
        res = bookmarkDB.getAllData();
        while (res.moveToNext()) {
            if (res.getString(2).toString().equals("" + page_num)) {
                bookmark.setImageResource(R.drawable.bookmark_add);
                return true;
            }
        }
        bookmark.setImageResource(R.drawable.bookmark_del);
        return false;
    }

    public void initialize() {
        gurbani = (TextView) findViewById(R.id.gurbani);
        textChange = (TextView) findViewById(R.id.textChange);
        ang = (TextView) findViewById(R.id.ang);

        punjabi = (CheckBox) findViewById(R.id.punjabi);
        teeka = (CheckBox) findViewById(R.id.teeka);
        english = (CheckBox) findViewById(R.id.english);
        light = (CheckBox) findViewById(R.id.light);

        number = (EditText) findViewById(R.id.number);

        previous = (Button) findViewById(R.id.previous);
        next = (Button) findViewById(R.id.next);
        search = (Button) findViewById(R.id.search);
        settings = (Button) findViewById(R.id.settings);

        scroll = (ScrollView) findViewById(R.id.SCROLLER_ID);

        screenshot = (ImageButton) findViewById(R.id.screenshot);
        bookmark = (ImageButton) findViewById(R.id.bookmark);

        textSeekBar = (SeekBar) findViewById(R.id.textSeekBar);

        textLayout = (LinearLayout) findViewById(R.id.textLayout);
        options = (LinearLayout) findViewById(R.id.options);
        downview = (LinearLayout) findViewById(R.id.downview);
        screen_layout = (RelativeLayout) findViewById(R.id.screen_layout);

        view = findViewById(R.id.screen_layout);

        animSlideIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        animSlideOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        animSlideIn.setAnimationListener(this);
        animSlideOut.setAnimationListener(this);

        Typeface face = Typeface.createFromAsset(getAssets(), "fonts/anmollipinumbers.ttf");
        gurbani.setTypeface(face);
        textLayout.setVisibility(View.GONE);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        spe = sp.edit();


        bookmarkDB = new DatabaseHelper(this);
        ang.setText(ConstantsMethods.bani_name);

        if (!ConstantsMethods.bani.equals("guru_granth")) {
            bookmark.setVisibility(View.GONE);
        } else {
            bookmark.setVisibility(View.VISIBLE);
        }

        /*
         Chaupai, Jaap, Tav - 1 Page  => Punjabi only
         Rehraas, Salok, Laavan, 6 Anand Sahib, kirtan - 1 Page => All


         */

        if (ConstantsMethods.bani.equals("jaap_sahib")
                || ConstantsMethods.bani.equals("chaupai_sahib") || ConstantsMethods.bani.equals("tav_parsad")) {
            teeka.setVisibility(View.GONE);
            english.setVisibility(View.GONE);
        }
        if (ConstantsMethods.bani.equals("jaap_sahib") || ConstantsMethods.bani.equals("chaupai_sahib")
                || ConstantsMethods.bani.equals("tav_parsad") || ConstantsMethods.bani.equals("6_paudi_anand_sahib")
                || ConstantsMethods.bani.equals("rehraas_sahib")
                || ConstantsMethods.bani.equals("kirtan_sohila") || ConstantsMethods.bani.equals("salok_mahalla_9")
                || ConstantsMethods.bani.equals("laavan")) {
            number.setVisibility(View.GONE);
            search.setVisibility(View.GONE);
        }

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
                constant.sggsMethod(parent, view, position, id, Gurbani.this);
            }
        });

        ListView general = (ListView) findViewById(R.id.general_menu);
        ArrayAdapter<String> adapter2 = new GeneralArrayAdapter(this, R.layout.simplerow, ConstantsMethods.GENERAL);
        general.setAdapter(adapter2);

        general.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                toggle();
                constant.generalMethod(parent, view, position, id, Gurbani.this);
            }
        });
    }

    public void downview_options() {
        // View GurbaniPunjabi
        punjabi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (punjabi.isChecked()) {
                    pun = true;
                } else {
                    pun = false;
                }

                conditions();
            }
        });

        // View GurbaniTeeka
        teeka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (teeka.isChecked()) {
                    tee = true;
                } else {
                    tee = false;
                }


                conditions();
            }
        });

        // View GurbaniEnglish
        english.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (english.isChecked()) {
                    eng = true;
                } else {
                    eng = false;
                }

                conditions();
            }
        });

        light.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (light.isChecked()) {
                    gurbani.setBackgroundColor(Color.BLACK);
                    options.setBackgroundColor(Color.BLACK);
                    ang.setTextColor(Color.WHITE);
                    number.setTextColor(Color.WHITE);

                } else {
                    gurbani.setBackgroundColor(Color.WHITE);
                    options.setBackgroundColor(Color.WHITE);
                    ang.setTextColor(Color.BLACK);
                    number.setTextColor(Color.BLACK);


                }

                spe.putBoolean("low_light", light.isChecked());
                spe.commit();

            }
        });


        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.clearAnimation();
                times++;
                spe.putInt("button_pointer", times);
                spe.commit();
                if (visibility == 0) {
                    textLayout.startAnimation(animSlideIn);
                    textLayout.setVisibility(View.GONE);
                    visibility = 1;
                } else {
                    textLayout.startAnimation(animSlideOut);
                    textLayout.setVisibility(View.VISIBLE);
                    visibility = 0;
                    textSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                            textChange.setText(String.valueOf(progress) + "pt");
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                            gurbani.setTextSize(TypedValue.COMPLEX_UNIT_PX, (seekBar.getProgress() + 5) * getResources().getDisplayMetrics().density);
                            spe.putInt("seekNum", textSeekBar.getProgress());
                            spe.commit();
                        }
                    });
                }
            }
        });

        screenshot.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                take_screenshot();
            }
        });

    }

    public void open_ang() {
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (page_num != 1) {
                    page_num -= 1;
                    check();
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                    number.setText("" + page_num);
                    conditions();
                    Shabad.line_search = "";
                    bani_case();
                }


            }
        });

        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (page_num != ConstantsMethods.ang) {
                    page_num += 1;
                    check();
                    scroll.fullScroll(ScrollView.FOCUS_UP);
                    number.setText("" + page_num);
                    conditions();
                    Shabad.line_search = "";
                    bani_case();
                }

            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(number.getText().toString().equals(""))) {

                    page_num = Integer.parseInt(number.getText().toString());

                    if (page_num <= ConstantsMethods.ang) {
                        check();
                        scroll.fullScroll(ScrollView.FOCUS_UP);
                        conditions();
                        Shabad.line_search = "";

                        bani_case();
                    } else {
                        // Doesn't work on Sharedpreference.
                        Toast.makeText(getApplicationContext(), ConstantsMethods.bani_name + " " + page_num + " does not exist.", Toast.LENGTH_SHORT).show();
                    }

                }
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

            }
        });
    }

    public void bani_case() {
        if (ConstantsMethods.bani.equals("guru_granth")) {
            spe.putInt("granth_num", page_num);
            spe.commit();

        } else if (ConstantsMethods.bani.equals("japji_sahib")) {
            spe.putInt("japji_num", page_num);
            spe.commit();

        } else if (ConstantsMethods.bani.equals("sukhmani_sahib")) {
            spe.putInt("sukhmani_num", page_num);
            spe.commit();

        } else if (ConstantsMethods.bani.equals("anand_sahib")) {
            spe.putInt("anand_num", page_num);
            spe.commit();

        }
    }

    public void main_layout_options() {
        gurbani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibility == 0) {
                    textLayout.startAnimation(animSlideIn);
                    textLayout.setVisibility(View.GONE);
                    visibility = 1;
                }
            }
        });


        final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.SimpleOnGestureListener() {

            public boolean onDoubleTap(MotionEvent e) {
                if (touch == 1) {
                    options.setVisibility(View.GONE);
                    downview.setVisibility(View.GONE);
                    touch = 0;
                } else {
                    options.setVisibility(View.VISIBLE);
                    downview.setVisibility(View.VISIBLE);
                    touch = 1;
                }
                return true;
            }

        });

        gurbani.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);

            }

        });


        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!check()) {
                    boolean isInserted;
                    if (!Shabad.line_search.equals("")) {
                        isInserted = bookmarkDB.insertData(Shabad.line_search, "" + page_num);

                    } else {
                        isInserted = bookmarkDB.insertData("AMg " + page_num, "" + page_num);
                    }
                    if (isInserted) {
                        Toast.makeText(getApplicationContext(), "Ang added to bookmarks.", Toast.LENGTH_LONG).show();
                        bookmark.setImageResource(R.drawable.bookmark_add);
                    }
                } else {
                    res = bookmarkDB.getAllData();
                    while (res.moveToNext()) {
                        if (res.getString(2).toString().equals("" + page_num)) {
                            bookmarkDB.deleteData(res.getString(0));
                        }
                    }
                    bookmark.setImageResource(R.drawable.bookmark_del);
                    Toast.makeText(getApplicationContext(), "Ang/Shabad removed from bookmarks.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString(CURRENT_BANI, ConstantsMethods.bani);
        savedInstanceState.putString(BANI_NAME, ConstantsMethods.bani_name);
        savedInstanceState.putInt(ANG, ConstantsMethods.ang);
        savedInstanceState.putInt(POS, ConstantsMethods.pos);
        savedInstanceState.putInt(SHABAD, Shabad.shabad);

    }
}



