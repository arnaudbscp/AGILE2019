package fr.ulille.iut.ramponno;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;


public class Agenda extends AppCompatActivity {
    ListView tvDisplay;
    BaseAccess base = new BaseAccess(this);

    String[] nomEvent;
    public static final String SERVER_KEY = "SERVEUR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        tvDisplay = (ListView) findViewById(R.id.tvDisplay);
        doGetEvenementsAsString("");
        tvDisplay.getTag();
    }


    public void showStringResponse(String response) {
        tvDisplay.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2,new String[]{response}));
    }
    public void showEvent(JSONArray response){
        nomEvent = new String[response.length()];
        for (int i = 0 ; i < response.length() ; i++) {
            try {
                String name = response.getString(i);
                String[] data = name.split(",");
                name = data[4].replaceAll("\"", "").replaceAll("\\}", "");
                name = name.substring(name.indexOf(':') + 1);
                nomEvent[i] = name;
            }catch (JSONException e){
                e.printStackTrace();
            }
            tvDisplay.setAdapter(new ArrayAdapter<String>(Agenda.this, android.R.layout.simple_list_item_1, nomEvent));
            tvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(Agenda.this, Details.class);
                    intent.putExtra("nom", nomEvent[position]);
                    intent.putExtra("pos", position);
                    startActivity(intent);
                }
            });


        }
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
}