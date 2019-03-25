package fr.ulille.iut.ramponno;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Details extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    TextView titreView;
    TextView dateView;
    TextView heureView;
    TextView descView;
    ListView inscritList;
    BaseAccess base = new BaseAccess(this);
    String login = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        descView = (TextView) findViewById(R.id.descView);
        heureView = (TextView) findViewById(R.id.heureView);
        dateView = (TextView) findViewById(R.id.dateView);
        titreView = (TextView) findViewById(R.id.titreView);
        inscritList = (ListView) findViewById(R.id.inscritList);

        Intent intent = getIntent();
        String titre = "Titre not found";
        if (intent.hasExtra("nom")) {
            titre = intent.getStringExtra("nom");
        }
        String date = "Date not found";
        if (intent.hasExtra("date")) {
            date = intent.getStringExtra("date");
        }
        String heure = "Heure not found";
        if (intent.hasExtra("heure")) {
            heure = intent.getStringExtra("heure");
        }
        String desc = "Description not found";
        if (intent.hasExtra("desc")) {
            desc = intent.getStringExtra("desc");
        }
        int placesMax = -1;
        if (intent.hasExtra("places")) {
            placesMax = intent.getIntExtra("places",-1);
        }
        int placeUtilise = -1;
        if (intent.hasExtra("placesUtilise")) {
            placeUtilise = intent.getIntExtra("placesUtilise",-1);
        }
        String[] inscrits = new String[]{"Aucun parcicipant pour l'instant"};
        if (intent.hasExtra("inscrits")) {
            inscrits = intent.getStringArrayExtra("inscrits");
        }

        titreView.setText(titre + "");
        dateView.setText(date + "");
        heureView.setText(heure + "");
        descView.setText(desc + "\nIl reste encore "+(placesMax-placeUtilise)+" places sur les "+placesMax+" disponibles");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (intent.hasExtra("login")) {
            login = intent.getStringExtra("login");
        }
        if (login.equals("none")){
            Toast toast = Toast.makeText(getApplicationContext(), "Veuillez vous connecter", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        Toast toast = Toast.makeText(getApplicationContext(), login, Toast.LENGTH_LONG);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, inscrits);

        //Insert Adapter into List
        inscritList.setAdapter(adapter2);
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
            startActivity(intent);
            finish();
        }else if(id == R.id.nav_share){
            //Lorsque je clique sur le mode admin
            Intent intent = new Intent(this, Admin.class);
            intent.putExtra("login", login);
            startActivity(intent);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void inscription(View view) {
        Log.d(base.LOG_TAG, "Send started");
        String uri = "http://"+Data.adresse+"/api/v1/events/";
        // /v1/events/label/login

        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject("{ 'login' : '"+login+"' }");//TODO
            uri+=titreView.getText().toString()+"/"+login;
            Log.d(base.LOG_TAG, "Uri: " + uri);
            Log.d("debugparceque",""+1);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.PUT,
                    uri,
                    jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("debugparceque",""+2);
                            base.getJsonObjectResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    });
            Log.d("debugparceque",""+4);
            request.setTag(base.VOLLEY_TAG);
            Log.d("debugparceque",""+5);
            base.queue.add(request);
            Log.d(base.LOG_TAG, "Send done");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(base.LOG_TAG, e+"");
        }
    }


}