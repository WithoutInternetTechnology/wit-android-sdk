package test.wit_native.com.weather;

import android.app.Activity;
import java.util.Calendar;
import java.util.Locale;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.text.format.DateFormat;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.witsdk.witcore.RequestListener;
import com.witsdk.witcore.WLog;
import com.witsdk.witcore.Wit;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayWeather extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Wit client;
    private String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_weather);

        city = "Bogota, CO";

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        client = new Wit(this, fm);

        setupUI();


        //Other Stuff
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setupUI(){

        final Button changeCityBtn = (Button) findViewById(R.id.changeCity);
        Button restoreSMSdefault = (Button) findViewById(R.id.restoreSMSdefault);
        Button reload = (Button) findViewById(R.id.reload);
        final EditText cityTv = (EditText) findViewById(R.id.city);

        setDate();

        cityTv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                cityTv.setFocusable(true);
                cityTv.setFocusableInTouchMode(true);
                changeCityBtn.setVisibility(View.VISIBLE);
                return false;
            }
        });

        try {
            getWeather();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    getWeather();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        restoreSMSdefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                client.disableOfflineMode();
            }
        });

        changeCityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = cityTv.getText().toString();
                hideSoftKeyboard(DisplayWeather.this, view);
                changeCityBtn.setVisibility(View.GONE);
                cityTv.setFocusable(false);
                cityTv.setFocusableInTouchMode(false);
                try {
                    getWeather();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    public static void hideSoftKeyboard (Activity activity, View view)
    {
        InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    public void setDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        String mMonth = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());;
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        TextView tv = (TextView) findViewById(R.id.today);
        tv.setText("Today, " + mDay+ " "+mMonth);
    }

    public void getWeather() throws JSONException {

        String url = "http://api.openweathermap.org/data/2.5/weather?q="+city+"&APPID=3563427d4664d99a125c8c163a593faa&units=metric";
        final Activity activity = this;

        client.request(url, "get", null, activity, new RequestListener() {
            @Override
            public void onSuccess(JSONObject json, Integer id) {

                try {
                    String res = json.getString("response");
                    JSONObject resObj = new JSONObject(res);
                    JSONObject sys = resObj.getJSONObject("sys");
                    JSONArray weather = resObj.getJSONArray("weather");
                    final String desc = weather.getJSONObject(0).getString("main");
                    final String icon = weather.getJSONObject(0).getString("icon");
                    final String temp = resObj.getJSONObject("main").getInt("temp") + "°";
                    final String city = resObj.getString("name");
                    final String sunrise = (DateFormat.format("hh:mm", sys.getInt("sunrise")).toString()) + " AM";
                    final String sunset = (DateFormat.format("hh:mm", sys.getInt("sunset")).toString()) + " PM";

                    Log.i("ICON", icon);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            updUI( temp, desc, sunset, sunrise, icon, city );
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int code, String error) {
                Log.d("WIT SDK","GET REQUEST, Error: "+ code +" : " + error);
            }
        });
    }

    public void updUI(String temp, String desc, String sunrise, String sunset, String icon, String city){
        TextView tempTv = (TextView) findViewById(R.id.temp);
        TextView descTv = (TextView) findViewById(R.id.desc);
        EditText cityTv = (EditText) findViewById(R.id.city);
        TextView sunriseTv = (TextView) findViewById(R.id.sunrise);
        TextView sunsetTv = (TextView) findViewById(R.id.sunset);
        ImageView iconIv = (ImageView) findViewById(R.id.icon);

        //D = day
        //N = night
        switch (icon){
            //Fog
            case "50d":
            case "50n":
                iconIv.setImageResource(R.drawable.fog);
                break;
            //Snow
            case "13d":
            case "13n":
                iconIv.setImageResource(R.drawable.snow);
                break;
            //Clear sky
            case "01d":
                iconIv.setImageResource(R.drawable.clear_day);
                break;
            case "01n":
                iconIv.setImageResource(R.drawable.clear_night);
                break;
            //Few clouds
            case "02d":
                iconIv.setImageResource(R.drawable.partly_cloudy_day);
                break;
            case "02n":
                iconIv.setImageResource(R.drawable.partly_cloudy_night);
                break;
            //Cloudy
            case "03d":
            case "03n":
            case "04d":
            case "04n":
                iconIv.setImageResource(R.drawable.cloudy);
                break;
            //Rain
            case "11d":
            case "11n":
            case "10n":
            case "10d":
            case "09d":
            case "09n":
                iconIv.setImageResource(R.drawable.rain);
                break;
        }

        WLog.d("UI Updated");
        cityTv.setText(city);
        tempTv.setText(temp);
        descTv.setText(desc);
        sunriseTv.setText(sunrise);
        sunsetTv.setText(sunset);
    }


    /* NOTE
    * The Activity need to override onActivityResult() by doing this
    * the Activity will be able to intercept the result of asking
    * for SMS permission and communicate the result to (Wit) client.
    * */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        client.onActivityResult(requestCode);
    }

    /* NOTE
    * The Activity need to override onRequestPermissionsResult()
    * by doing this the Activity will be able to intercept the result of asking
    * for SMS permission and communicate the result to (Wit) client.
    * */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        client.onRequestPermissionsResult();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_weather, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}