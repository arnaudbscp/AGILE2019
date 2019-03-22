package fr.ulille.iut.ramponno;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import java.util.List;
import java.util.Random;


public class Agenda extends AppCompatActivity {
    ListView tvDisplay;
    BaseAccess base = new BaseAccess(this);

    String[] nomEvent;
    public static final String SERVER_KEY = "SERVEUR";
    List<Item> items = new ArrayList<Item>();
    String login = "none";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        tvDisplay = (ListView) findViewById(R.id.tvDisplay);
        doGetEvenementsAsString("");
        tvDisplay.getTag(); Intent intent = getIntent();
        if (intent.hasExtra("mail")) {
            login = intent.getStringExtra("mail");
        }
        if (login.equals("none")){
            Toast toast = Toast.makeText(getApplicationContext(), "Veuillez vous connecter", Toast.LENGTH_LONG);
            toast.show();
        }

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
        String uri = "http://10.0.2.2:8080/api/v1/events";
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
        for (int i = 0 ; i < response.length() ; i++) {
            try {
                JSONObject lesItems = response.getJSONObject(i);
                lesItems.getString("date");

                String date = lesItems.getString("date");
                String heure = lesItems.getString("heure");
                String nomLabel = lesItems.getString("nom");
                int places = lesItems.getInt("place");
                int placesUtilise = lesItems.getJSONArray("reservations").length();

                dates[i]=date;
                heures[i]=heure;
                nomLabels[i]=nomLabel;
                placesMax[i]=places;
                placesUtilises[i]=placesUtilise;

                items.add(new Item(Color.BLUE, nomLabel, "Le "+date+" a "+heure+",   places dispo :"+(places-placesUtilise)+"/"+places));

            }catch (JSONException e){
                Log.e("erreur de merde", e.getMessage());
            }
            MyListAdapter adapter = new MyListAdapter(Agenda.this, items);
            tvDisplay.setAdapter(adapter);
            tvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Agenda.this, Details.class);
                    intent.putExtra("date", dates[position]);
                    intent.putExtra("heure", heures[position]);
                    intent.putExtra("nom", nomLabels[position]);
                    intent.putExtra("places", placesMax[position]);
                    intent.putExtra("placesUtilise", placesUtilises[position]);
                    startActivity(intent);
                }
            });


        }
        return items;
    }
}