package gurbani.ujagar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;


public class ConstantsMethods {
    static final String[] SGGS = new String[]{"Gurbani Only", "Punjabi Teeka", "Punjabi Translation", "English Translation", "Bookmark",
            "Raag Index", "Author Index", "Shabad", "Japji Sahib", "Sukhmani Sahib", "Jaap Sahib", "Chaupai Sahib", "Anand Sahib",
            "6 Paudi Anand Sahib", "Rehraas Sahib", "Tav Parsad Swaiye", "Kirtan Sohila", "Salok Mahalla 9", "Laavan"};
    static final String[] GENERAL = new String[]{"Daily Inspiration", "Feedback", "Apps By IveSingh",
            "Rate This App", "Follow on Facebook"};
    static SharedPreferences getPrefs;
    static String bani = "";
    static int ang = 0, pos;
    static String bani_name = "";

    public void sggsMethod(AdapterView<?> parent, View view, int position, long id, Context context) {

        if (position >= 0 && position <= 3) {
            bani = "guru_granth";
            ang = 1430;
            bani_name = "Ang";
            pos = position;

        } else if (position == 4) {
            bani = "guru_granth";
            ang = 1430;
            bani_name = "Ang";
            try {
                Class ourClass = Class.forName("gurbani.ujagar.Bookmark");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 5) {
            bani = "guru_granth";
            ang = 1430;
            bani_name = "Ang";
            try {
                Class ourClass = Class.forName("gurbani.ujagar.RaagIndex");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 6) {
            bani = "guru_granth";
            ang = 1430;
            bani_name = "Ang";
            try {
                Class ourClass = Class.forName("gurbani.ujagar.AuthorIndex");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 7) {
            bani = "guru_granth";
            ang = 1430;
            bani_name = "Ang";
            try {
                Class ourClass = Class.forName("gurbani.ujagar.ShabadActivity");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 8) {
            bani = "japji_sahib";
            ang = 38;
            bani_name = "Pauri";

        } else if (position == 9) {
            bani = "sukhmani_sahib";
            ang = 24;
            bani_name = "Ashtpadi";

        } else if (position == 10) {
            bani = "jaap_sahib";
            bani_name = "Jaap Sahib";
            ang = 1;

        } else if (position == 11) {
            bani = "chaupai_sahib";
            bani_name = "Chaupai Sahib";
            ang = 1;

        } else if (position == 12) {
            bani = "anand_sahib";
            ang = 40;
            bani_name = "Pauri";

        } else if (position == 13) {
            bani = "6_paudi_anand_sahib";
            ang = 1;
            bani_name = "Anand Sahib";

        }  else if (position == 14) {

            bani = "rehraas_sahib";
            ang = 1;
            bani_name = "Rehraas Sahib";

        } else if (position == 15) {
            bani = "tav_parsad";
            ang = 1;
            bani_name = "Tav Parsad";

        } else if (position == 16) {
            bani = "kirtan_sohila";
            ang = 1;
            bani_name = "Kirtan Sohila";


        }else if (position == 17) {
            bani = "salok_mahalla_9";
            ang = 1;
            bani_name = "Salok Mahalla 9";


        } else if (position == 18) {
            bani = "laavan";
            ang = 1;
            bani_name = "Laavan";


        }

        if((position >= 0 && position <= 3) || position >= 8){
            try {
                Class ourClass = Class.forName("gurbani.ujagar.Gurbani");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    public void generalMethod(AdapterView<?> parent, View view, int position, long id, Context context) {

        if (position == 0) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.Inspiration");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 1) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.Feedback");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 2) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=IveSingh ");
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));

        } else if (position == 3) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=gurbani.ujagar&hl=en");
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));

        } else if (position == 4) {
            Uri uri = Uri.parse("https://www.facebook.com/pages/IveSingh-Apps/1413125452234300");
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }

}
