package fr.ulille.iut.ramponno;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class MainActivity extends AppCompatActivity {
    private static String base_uri;
    public static final String VOLLEY_TAG = "TEST_PIZZALAND";
    public static final String LOG_TAG = "APPLI";
    private RequestQueue queue;
    BaseAccess base = new BaseAccess(this);
    TextView mailField;
    TextView passField;


    public static final String SERVER_KEY = "SERVEUR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mailField = (TextView) findViewById(R.id.mailField);
        passField = (TextView) findViewById(R.id.passField);

        queue = Volley.newRequestQueue(MainActivity.this);

    }
    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    public void openSigninActivity(View view){
        Intent intent = new Intent(this, Signin.class);
        startActivity(intent);
    }

    public void connect(View view){
        Log.d("connect","Lauching connect");
        connectTask();
    }
    private void init(){
    EditText mail = (EditText)findViewById(R.id.mailField);
    mail.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    });

    }

    public void connectTask() {
        Log.d(base.LOG_TAG, "Send started");
        String uri = "http://10.0.2.2:8080/api/v1/users";
        Log.d(base.LOG_TAG, "Uri: " + uri);

        JsonArrayRequest arrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                uri,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("coucou","coucou");
                        String retour = verifLogs(response);
                        Log.d("retour",retour);
                        Toast toast = Toast.makeText(getApplicationContext(), retour, Toast.LENGTH_SHORT);
                        toast.show();
                        if (retour.equals("noMail")){

                        }else if (retour.equals("noPass")){

                        }else{
                            Intent intent = new Intent(MainActivity.this, Agenda.class);
                            intent.putExtra("mail",retour);
                            startActivity(intent);
                        }


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

    public String verifLogs(JSONArray response){
        int i=0;
        String enteredMail = mailField.getText().toString();
        String enteredPass = passField.getText().toString();
        boolean mailFound = false;
        while ( i < response.length()) {
            try {
                String dataString = response.getString(i);
                dataString = dataString.replaceAll("\"", "").replaceAll("\\}", "").replaceAll("\\{","");
                String[] data = dataString.split(",");
                String mailVerif = (data[0].substring(data[0].indexOf(":")+1));
                String passVerif = (data[3].substring(data[3].indexOf(":")+1));
                if (mailVerif.equals(enteredMail)){
                    mailFound = true;
                    if (passVerif.equals(enteredPass)){
                        return enteredMail;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            i++;

        }
        if (mailFound)return "noPass";
        return  "noMail";
    }
}