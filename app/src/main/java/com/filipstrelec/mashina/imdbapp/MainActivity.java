package com.filipstrelec.mashina.imdbapp;

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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    List<String> listOfObjectsTitle;
    List<String> listOfObjectsYear;
    List<String> listOfObjectsUrlPoster;
    List<String> listOfObjectsSimplePlot;
    List<String> listOfObjectsRating;
    Button testGumb;
    DownloadTask downloadTask;
    TextView tv;
    LinearLayout descriptionLayout;
    int random;
    int switcher;
    String execute;
    JSONObject jsonObjectMain;
    JSONObject jsonObjectData;
    JSONObject jsonObjectMovie;
    JSONArray jsonArrayMain;
    RecyclerView recyclerView;
    SharedPreferences.Editor editor;
    SharedPreferences prefs;

    public class DownloadTask extends AsyncTask<String, Void, StringBuffer> {


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
                    //BufferedReader br = new BufferedReader(new InputStreamReader(new GZIPInputStream(is)));
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
                    //errorResponse = "Check your internet connection";
                }
            }
            return buffer;

        }

        @Override
        protected void onPostExecute(StringBuffer html) {


            Log.i("proba", html.toString());
            if (html.length() > 0) {


                try {
                    jsonObjectMain = new JSONObject(html.toString());
                    jsonObjectData = jsonObjectMain.getJSONObject("data");

                    jsonArrayMain = null;
                    jsonObjectMovie = null;
                    listOfObjectsTitle = null;
                    listOfObjectsYear = null;
                    listOfObjectsUrlPoster = null;
                    listOfObjectsSimplePlot = null;
                    listOfObjectsRating = null;
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

    }


    public void clickTest(View view) {

//        Log.i("testko", "testić");
////        downloadTask.execute("http://api.myapifilms.com/imdb/top?start=1&end=50&token=b4d10753-9bce-4f2d-8a4f-78a5874dc966&format=json&data=1");
////        downloadTask.execute("http://www.monitor.hr");
//        random=new Random().nextInt(10)+1;
//        execute = "http://api.myapifilms.com/imdb/top?start="+((random*25)-24)+"&end="+random*25+"&token=b4d10753-9bce-4f2d-8a4f-78a5874dc966&format=json&data=1";
//        Log.i("randomTest", String.valueOf(random));
//        Log.i("randomTestE", execute);
//     Log.i("randomTestE", String.valueOf(jsonArrayMain));
//        Log.i("randomTestA", String.valueOf(listOfObjectsTitle));
        Log.i("random", String.valueOf(random));
        Log.i("ABCtitlee", String.valueOf(listOfObjectsTitle));
        Log.i("ABCyear", String.valueOf(listOfObjectsYear));
        Log.i("ABCurlPoster", String.valueOf(listOfObjectsUrlPoster));
        Log.i("ABCsimplePlot", String.valueOf(listOfObjectsSimplePlot));
        Log.i("ABCrating", String.valueOf(listOfObjectsRating));

        Log.i("switcher", String.valueOf(switcher));

        if(switcher==1){

            descriptionLayout.setVisibility(View.VISIBLE);


        }

        else {

            descriptionLayout.setVisibility(View.GONE);
        }

    }


    private void print(String printS) {
        Log.i("testtt", printS);

        MyRecyclerViewAdapter adapter =(new MyRecyclerViewAdapter(this, listOfObjectsTitle, listOfObjectsUrlPoster));

        recyclerView.setAdapter(adapter);

//        tv.setText(printS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefs = getSharedPreferences("Myprefs", MODE_PRIVATE);
        switcher = prefs.getInt("switcher", 1);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        descriptionLayout=findViewById(R.id.description_layout);
        testGumb = findViewById(R.id.gumb);
        downloadTask = new DownloadTask();
//        tv = findViewById(R.id.ispis);
        random = new Random().nextInt(10) + 1;
        if (switcher == 1) {

            execute = "http://api.myapifilms.com/imdb/top?start=" + ((random * 25) - 24) + "&end=" + random * 25 + "&token=b4d10753-9bce-4f2d-8a4f-78a5874dc966&format=json&data=1";
        } else if (switcher == 2) {

            execute = "http://api.myapifilms.com/imdb/inTheaters?token=b4d10753-9bce-4f2d-8a4f-78a5874dc966&format=json&language=en-us";
        }


//        downloadTask.execute("http://api.myapifilms.com/imdb/top?start=1&end=50&token=b4d10753-9bce-4f2d-8a4f-78a5874dc966&format=json&data=1");
        Log.i("2", "2");

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


//        downloadTask.execute(execute);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);


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
