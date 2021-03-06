package fr.ulille.iut.ramponno;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class BaseAccess {

    private static String base_uri;
    public static final String VOLLEY_TAG = "TEST_RAMPONNO+-";
    public static final String LOG_TAG = "APPLI";
    private String getFullHostname() {
        return "http://"+Data.adresse+"/api/v1/";
    }
    protected RequestQueue queue;
    Context context;

    public BaseAccess(Context context) {

        this.context=context;
    }

    public String getJsonObjectResponse(JSONObject response) {
        try {
            Log.d(LOG_TAG, response.toString(4));
            return (response.toString(4));
        } catch (JSONException e) {
            return (e.getMessage());
        }
    }
    public String getJsonArrayResponse(JSONArray response) {
        try {
            for (int i = 0; i < response.length() ; i++) {
                JSONObject obj = response.getJSONObject(i);
                Log.d(LOG_TAG, obj.toString());
            }
            return (response.toString(4));
        } catch (Exception e) {
            return (e.getMessage());
        }
    }

    public void showError(VolleyError error) {
        Log.e(LOG_TAG, "Errored Request: " + error.toString());
        if (error.networkResponse == null) {
            Log.e("Erreur",error.toString());
        } else {
            Cache.Entry header = HttpHeaderParser.parseCacheHeaders(error.networkResponse);
            String charset = HttpHeaderParser.parseCharset(header.responseHeaders);
            try {
                Log.e("Erreur",new String(error.networkResponse.data, charset));

            } catch (UnsupportedEncodingException e) {
                Log.e(LOG_TAG, e+"");
            }
        }
    }
}
