package gurbani.ujagar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;

import com.icshqtcqgrphsd.AdController;


public class ConstantsMethods {
    static final String[] SGGS = new String[]{"Gurbani Only", "English Translation", "Punjabi Translation",
            "Punjabi Teeka", "Raag Index", "Author Index", "Shabad", "Japji Sahib", "Jaap Sahib", "Tav Parsad Swaiye", "Chaupai Sahib", "Anand Sahib",
            "Rehraas Sahib", "Sukhmani Sahib", "Sohila Sahib", "Asa Dee Vaar", "Salok Mahalla 9", "Laavan"};
    static final String[] GENERAL = new String[]{"Daily Inspiration", "Settings", "Feedback", "Apps By IveSingh",
            "Rate This App", "Follow on Facebook"};
    private AdController ad;
    WebView webview;
    static SharedPreferences getPrefs;

    public void sggsMethod(AdapterView<?> parent, View view, int position, long id, Context context) {
        if (position == 0) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.GurbaniOnly");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else if(position == 1) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.SantSingh");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else if (position == 2) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.ManmohanSingh");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 3) {
            try {
                Class ourClass = Class
                        .forName("gurbani.ujagar.SahibSingh");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 4) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.RaagIndex");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 5) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.AuthorIndex");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 6) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.ShabadActivity");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 7) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.JapjiSahib");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 8) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.JaapSahib");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 9) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.TavParsad");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 10) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.ChaupaiSahib");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 11) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.AnandSahib");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else if (position == 12) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.RehraasSahib");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 13) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.SukhmaniSahib");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 14) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.KirtanSohila");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 15) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.AsaDeeVaar");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 16) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.Mahalla9");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 17) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.Laavan");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void generalMethod(AdapterView<?> parent, View view, int position,
                              long id, Context context) {

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
                Class ourClass = Class.forName("gurbani.ujagar.Settings");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 2) {
            try {
                Class ourClass = Class.forName("gurbani.ujagar.Feedback");
                Intent ourIntent = new Intent(context, ourClass);
                context.startActivity(ourIntent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if (position == 3) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=IveSingh ");
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));

        } else if (position == 4) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=gurbani.ujagar&hl=en");
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } else if (position == 5) {
            Uri uri = Uri.parse("https://www.facebook.com/pages/IveSingh-Apps/1413125452234300");
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }


}
