package fr.ulille.iut.ramponno;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class Signin extends AppCompatActivity {
    BaseAccess base = new BaseAccess(this);

    EditText name, prename, mailField, passwd, rePasswd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        name = (EditText) findViewById(R.id.name);
        prename = (EditText) findViewById(R.id.prename);
        mailField = (EditText) findViewById(R.id.mailField);
        passwd = (EditText) findViewById(R.id.passwd);
        rePasswd = (EditText) findViewById(R.id.rePasswd);
    }
    public void closeSigninActivity(View view){
        finish();
    }
    public void Signin(View view){
        String login = prename.getText().toString() + name.getText().toString().subSequence(0,1);
        if ( passwd.getText().toString().equals(rePasswd.getText().toString())){
            inscription(mailField.getText().toString(), login, passwd.getText().toString());
            finish();
        }else {
            Toast toast = Toast.makeText(getApplicationContext(), "Les mot de passe ne correspondent pas!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void inscription(String mail, String login, String pass) {
        Log.d(base.LOG_TAG, "Send started");
        final String uri = "http://10.0.2.2:8080/api/v1/users";
        Log.d(base.LOG_TAG, "Uri: " + uri);

        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject( "{'email' : '"+mail+"' , 'login' : '"+login+"' , 'password' : '"+pass+"' , 'role' : 'user'}");//TODO
            Log.d("debugparceque",""+1);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
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
                            Log.d("debugparceque",""+3);
                            base.showError(error);
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
