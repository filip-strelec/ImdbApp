package com.filipstrelec.mashina.imdbapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.TagLostException;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<String> listOfObjectsTitle;
    List<String> listOfObjectsYear;
    List<String> listOfObjectsUrlPoster;
    List<String> listOfObjectsSimplePlot;
    List<String> listOfObjectsRating;

    DownloadTask downloadTask;
    TextView tv;
    LinearLayout descriptionLayout;
    int random;
    static int switcher;
    String execute;
    JSONObject jsonObjectMain;
    JSONObject jsonObjectData;
    JSONObject jsonObjectMovie;
    JSONArray jsonArrayMain;
    RecyclerView recyclerView;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    TinyDB tinydb;


    private  class DownloadTask extends AsyncTask<String, Integer, StringBuffer> {


        private ProgressDialog progressDialog;
        private Context context;


        private DownloadTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(context);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Molimo pričekajte...");
            progressDialog.setCanceledOnTouchOutside(false);


            progressDialog.show();
        }


        @Override
        protected StringBuffer doInBackground(String... urls) {


            StringBuffer buffer = new StringBuffer();

            try {
                URL url = new URL(urls[0]);

                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                int resCode = connection.getResponseCode();


                if (resCode == HttpURLConnection.HTTP_OK) {

                    InputStream is = connection.getInputStream();


                    BufferedReader br = new BufferedReader(new InputStreamReader(is));

                    String line;
                    while ((line = br.readLine()) != null) {


                        buffer.append(line);

                    }


                    is.close();
                    br.close();
                    connection.disconnect();
                }


            } catch (Exception e) {
                e.printStackTrace();
                if (e instanceof UnknownHostException) {

                }
            }
            return buffer;

        }


        @Override
        protected void onPostExecute(StringBuffer html) {


            Log.i("proba", html.toString());
            if (html.length() > 0) {
                progressDialog.dismiss();

                try {
                    jsonObjectMain = new JSONObject(html.toString());
                    jsonObjectData = jsonObjectMain.getJSONObject("data");


                    listOfObjectsTitle = new ArrayList<String>();
                    listOfObjectsYear = new ArrayList<String>();
                    listOfObjectsUrlPoster = new ArrayList<String>();
                    listOfObjectsSimplePlot = new ArrayList<String>();

                    if (switcher == 1) {


                        listOfObjectsRating = new ArrayList<String>();
                        jsonArrayMain = jsonObjectData.getJSONArray("movies");
//                    jsonObjectMovie =jsonArrayMain.getJSONObject("title")

                        for (int i = 0; i < jsonArrayMain.length(); i++) {
                            jsonObjectMovie = (jsonArrayMain.getJSONObject(i));

                            String jsonObjectForTitle = jsonObjectMovie.getString("title");
                            String jsonObjectForYear = jsonObjectMovie.getString("year");
                            String jsonObjectForUrlPoster = jsonObjectMovie.getString("urlPoster");
                            String jsonObjectForSimplePlot = jsonObjectMovie.getString("simplePlot");
                            String jsonObjectForRating = jsonObjectMovie.getString("rating");


                            listOfObjectsTitle.add(jsonObjectForTitle);
                            listOfObjectsYear.add(jsonObjectForYear);
                            listOfObjectsUrlPoster.add(jsonObjectForUrlPoster);
                            listOfObjectsSimplePlot.add(jsonObjectForSimplePlot);
                            listOfObjectsRating.add(jsonObjectForRating);


                        }


                    } else if (switcher == 2) {

                        jsonArrayMain = jsonObjectData.getJSONArray("inTheaters");


                        for (int i = 0; i < jsonArrayMain.length(); i++) {
                            JSONObject movies_a = jsonArrayMain.getJSONObject(i);

                            JSONArray movies_aArray = movies_a.getJSONArray("movies");


                            for (int j = 0; j < movies_aArray.length(); j++) {


                                jsonObjectMovie = (movies_aArray.getJSONObject(j));




                                String jsonObjectForTitle = jsonObjectMovie.getString("title");
                                String jsonObjectForYear = jsonObjectMovie.getString("year");
                                String jsonObjectForUrlPoster = jsonObjectMovie.getString("urlPoster");
                                String jsonObjectForSimplePlot = jsonObjectMovie.getString("simplePlot");


                                listOfObjectsTitle.add(jsonObjectForTitle);
                                listOfObjectsYear.add(jsonObjectForYear);
                                listOfObjectsUrlPoster.add(jsonObjectForUrlPoster);
                                listOfObjectsSimplePlot.add(jsonObjectForSimplePlot);


                            }


                        }


                    }
                    print(html.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }


//        @Override
//        protected void onProgressUpdate(Integer... values) {
//
//
//        }


    }


//    public void clickTest(View view) {
//
//        /*
//          Za debug
//         */
//
//
////
////        Log.i("random", String.valueOf(random));
////        Log.i("ABCtitlee", String.valueOf(listOfObjectsTitle));
////        Log.i("ABCyear", String.valueOf(listOfObjectsYear));
////        Log.i("ABCurlPoster", String.valueOf(listOfObjectsUrlPoster));
////        Log.i("ABCsimplePlot", String.valueOf(listOfObjectsSimplePlot));
////        Log.i("ABCrating", String.valueOf(listOfObjectsRating));
////
////        Log.i("switcher", String.valueOf(switcher));
//
////        Log.i("Prefs", String.valueOf(prefs.getStringSet("setTitle", null)));
//
//
//        Log.i("GLAVNITEST", String.valueOf(tinydb.getListString("title")));
//        Log.i("GLAVNITEST", String.valueOf(tinydb.getListString("image")));
//        Log.i("GLAVNITEST", String.valueOf(tinydb.getListString("desc")));
//        Log.i("GLAVNITESTSWITCHER", String.valueOf(switcher));
//
//    }


    private void print(String printS) {


        MyRecyclerViewAdapter adapter = (new MyRecyclerViewAdapter(this, listOfObjectsTitle, listOfObjectsUrlPoster, listOfObjectsSimplePlot));

        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("Myprefs", MODE_PRIVATE);
        switcher = prefs.getInt("switcher", 1);
        tinydb = new TinyDB(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        descriptionLayout = findViewById(R.id.description_layout);

        downloadTask = new DownloadTask(this);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        random = new Random().nextInt(10) + 1;
        if (switcher == 1) {

            execute = "http://api.myapifilms.com/imdb/top?start=" + ((random * 25) - 24) + "&end=" + random * 25 + "&token=b4d10753-9bce-4f2d-8a4f-78a5874dc966&format=json&data=1";


            try {
                downloadTask.execute(execute);

            } catch (Exception e) {


                Context context = getApplicationContext();
                CharSequence text = "Error (provjerite internetsku vezu)";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                e.printStackTrace();
            }


        } else if (switcher == 2) {

            execute = "http://api.myapifilms.com/imdb/inTheaters?token=b4d10753-9bce-4f2d-8a4f-78a5874dc966&format=json&language=en-us";


            try {
                downloadTask.execute(execute);

            } catch (Exception e) {


                Context context = getApplicationContext();
                CharSequence text = "Error (provjerite internetsku vezu)";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

                e.printStackTrace();
            }

        } else if (switcher == 3) {

            listOfObjectsTitle = tinydb.getListString("title");

            listOfObjectsUrlPoster = tinydb.getListString("image");
            listOfObjectsSimplePlot = tinydb.getListString("desc");

            MyRecyclerViewAdapter adapter = (new MyRecyclerViewAdapter(this, listOfObjectsTitle, listOfObjectsUrlPoster, listOfObjectsSimplePlot));

            recyclerView.setAdapter(adapter);

        }


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
//        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.top_movies) {
            // Handle the camera action
            editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();

            editor.putInt("switcher", 1);


            editor.apply();

            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.in_cinemas) {
            editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();
            editor.putInt("switcher", 2);
            editor.apply();
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        } else if (id == R.id.wishlist) {
            editor = getSharedPreferences("Myprefs", MODE_PRIVATE).edit();

            editor.putInt("switcher", 3);


            editor.apply();
            Intent i = getBaseContext().getPackageManager()
                    .getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
        }

//        else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
