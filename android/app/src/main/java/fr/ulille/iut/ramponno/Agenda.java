package fr.ulille.iut.ramponno;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.Image;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Agenda extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ListView tvDisplay;
    BaseAccess base = new BaseAccess(this);

    String[] nomEvent;
    public static final String SERVER_KEY = "SERVEUR";
    List<Item> items = new ArrayList<Item>();
    String login = "none";

    String userRole = "user";
    HashMap<String, Integer> logos = new HashMap<String, Integer>();

    SharedPreferences.Editor edit;
    @Override
    protected void onStart() {
        super.onStart();


        doGetEvenementsAsString("");
        tvDisplay.getTag();
        if (login.equals("none")){
            Toast toast = Toast.makeText(getApplicationContext(), "Veuillez vous connecter", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        tvDisplay = (ListView) findViewById(R.id.tvDisplay);
        Intent intent = getIntent();
        if (intent.hasExtra("login")) {
            login = intent.getStringExtra("login");
        }
        if (intent.hasExtra("role")) {
            userRole = intent.getStringExtra("role");
        }
        if (login.equals("none")){
            Toast toast = Toast.makeText(getApplicationContext(), "Veuillez vous connecter", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        logos.put("Location d`outils", R.drawable.outils);
        logos.put("Atelier thematique", R.drawable.atelier);
        logos.put("Cours", R.drawable.cours);
        logos.put("Tapisserie", R.drawable.tapisserie);
        logos.put("Stage", R.drawable.stage);

        Menu nav_Menu = navigationView.getMenu();
        if (!userRole.equals("admin"))nav_Menu.findItem(R.id.nav_share).setVisible(false);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            SharedPreferences settings;
            settings = getSharedPreferences("Test", Context.MODE_PRIVATE);
            edit = settings.edit();
            edit.putString("username", "none");
            edit.apply();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_gallery) {
            // Lorsque je clique sur "Mes inscriptions".
            Intent intent = new Intent(this, MesInscriptions.class);
            intent.putExtra("login", login);
            intent.putExtra("role", userRole);
            startActivity(intent);
        }else if(id == R.id.nav_share){
            //Lorsque je clique sur le mode admin
            Intent intent = new Intent(this, Admin.class);
            intent.putExtra("login", login);
            intent.putExtra("role", userRole);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showStringResponse(JSONArray response) {
        MyListAdapter adapter = new MyListAdapter(Agenda.this, genererItems(response));
        tvDisplay.setAdapter(adapter);
    }
    public void showEvent(JSONArray response){
        genererItems(response);
    }

    public void doGetEvenementsAsString(String path) {
        Log.d(base.LOG_TAG, "Send started");
        String uri = "http://"+Data.adresse+"/api/v1/events";
        Log.d(base.LOG_TAG, "Uri: " + uri);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        items = genererItems(response);
                        showEvent(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        base.showError(error);
                    }
                });

        arrayRequest.setTag(base.VOLLEY_TAG);
        base.queue.add(arrayRequest);
        Log.d(base.LOG_TAG, "Send done");
    }
    @Override
    public void onStop() {
        super.onStop();
        if (base.queue != null) {
            base.queue.cancelAll(base.VOLLEY_TAG);
        }
    }


    private List<Item> genererItems(JSONArray response){
        List<Item> items = new ArrayList<Item>();
        items.clear();
        nomEvent = new String[response.length()];
        final String[] dates = new String[response.length()];
        final String[] heures = new String[response.length()];
        final String[] nomLabels = new String[response.length()];
        final int[] placesMax = new int[response.length()];
        final int[] placesUtilises = new int[response.length()];
        final String[] descriptions = new String[response.length()];
        final ArrayList<String[]> inscrits = new ArrayList<String[]>();
        for (int i = 0 ; i < response.length() ; i++) {
            try {
                JSONObject lesItems = response.getJSONObject(i);
                lesItems.getString("date");

                String date = lesItems.getString("date");
                String heure = lesItems.getString("heure");
                String nomLabel = lesItems.getString("nom");
                String description = lesItems.getString("description");
                int places = lesItems.getInt("place");
                int placesUtilise = lesItems.getJSONArray("reservations").length();
                String[] inscritIci = new String[placesUtilise];

                for (int idx=0 ; idx < placesUtilise ; idx++){
                    inscritIci[idx]=lesItems.getJSONArray("reservations").getJSONObject(idx).getString("login");
                }

                dates[i]=date;
                heures[i]=heure;
                nomLabels[i]=nomLabel;
                placesMax[i]=places;
                placesUtilises[i]=placesUtilise;
                descriptions[i]=description;




                items.add(new Item(logos.get(lesItems.getString("categorie")), nomLabel, "Le "+date+" a "+heure+",   places dispo :"+(places-placesUtilise)+"/"+places));



                lesItems.getJSONArray("reservations");
                inscrits.add(inscritIci);

            }catch (JSONException e){
                Log.e("erreur de merde", e.getMessage());
            }
            MyListAdapter adapter = new MyListAdapter(Agenda.this, items);
            tvDisplay.setAdapter(adapter);//Create an array of elements
            //Create adapter for ArrayList
            tvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Agenda.this, Details.class);
                    intent.putExtra("date", dates[position]);
                    intent.putExtra("heure", heures[position]);
                    intent.putExtra("nom", nomLabels[position]);
                    intent.putExtra("places", placesMax[position]);
                    intent.putExtra("placesUtilise", placesUtilises[position]);
                    intent.putExtra("desc", descriptions[position]);
                    intent.putExtra("login", login);
                    intent.putExtra("role", userRole);
                    intent.putExtra("inscrits", inscrits.get(position));
                    startActivity(intent);
                }
            });


        }
        return items;
    }


    public void startAdmin(View view){
        Intent intent = new Intent(this, Agenda.class);
        intent.putExtra("login", login);
        intent.putExtra("role", userRole);
        startActivity(intent);
    }

    public void finishActivity(View view){
        SharedPreferences settings;
        settings = getSharedPreferences("Test", Context.MODE_PRIVATE);
        edit = settings.edit();
        edit.putString("username", "none");
        edit.apply();
        finish();
    }

}