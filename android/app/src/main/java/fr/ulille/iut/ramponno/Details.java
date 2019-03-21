package fr.ulille.iut.ramponno;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Details extends AppCompatActivity {

    TextView titreView;
    TextView dateView;
    TextView heureView;
    TextView descView;
    BaseAccess base = new BaseAccess(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        descView = (TextView) findViewById(R.id.descView);
        heureView = (TextView) findViewById(R.id.heureView);
        dateView = (TextView) findViewById(R.id.dateView);
        titreView = (TextView) findViewById(R.id.titreView);

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

        titreView.setText(titre + "");
        dateView.setText(date + "");
        heureView.setText(heure + "");
        descView.setText(desc + "");


    }

    public void inscription(View view) {
        Log.d(base.LOG_TAG, "Send started");
        String uri = "http://10.0.2.2:8080/api/v1/events/";
        // /v1/events/label/login

        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject("{ 'login' : 'toto' }");//TODO
            uri+=titreView.getText().toString()+"/"+"toto";
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
