package fr.ulille.iut.ramponno;

import android.content.Intent;
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
import android.view.MenuItem;import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class Admin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CalendarView cv;
    TextView date;
    TextView heure;
    TextView nom;
    TextView nbPlace;
    EditText editDate;
    EditText editHeureDebut;
    EditText editHeureFin;
    EditText editNom;
    EditText editNbPlace;
    EditText prixField;
    Button valid;
    Button cancel;
    Button descButton;
    Spinner spinner;
    TextView categories;
    Button retour;
    Button validDate;
    String madate;
    ImageButton imgButton;
    BaseAccess base = new BaseAccess(this);
    private String m_Text = "";
    HashMap<String, String> descriptionsDispo = new HashMap<String, String >();
    String selected = "";
    TextView prixTxt;
    String login = "none";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        base.queue = Volley.newRequestQueue(this);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_admin);

        cv = (CalendarView) findViewById(R.id.calendarView);
        date = (TextView) findViewById(R.id.dateField);
        heure = (TextView) findViewById(R.id.heureField);
        nom = (TextView) findViewById(R.id.nomField);
        nbPlace = (TextView) findViewById(R.id.nombreDePlaceField);
        editDate = (EditText) findViewById(R.id.date);
        prixTxt = (TextView) findViewById(R.id.prixTxt);
        editHeureDebut = (EditText) findViewById(R.id.heureDebut);
        editHeureFin = (EditText) findViewById(R.id.heureFin);
        editNom = (EditText) findViewById(R.id.titreEvent);
        editNbPlace = (EditText) findViewById(R.id.maxPlaces);
        prixField = (EditText) findViewById(R.id.prixField);
        valid = (Button) findViewById(R.id.addEvent);
        cancel = (Button) findViewById(R.id.Cancel);
        spinner = (Spinner) findViewById(R.id.spinner);
        categories = (TextView) findViewById(R.id.categorieField);
        retour = (Button) findViewById(R.id.retour);
        validDate = (Button) findViewById(R.id.validerDate);
        imgButton = (ImageButton) findViewById(R.id.calendarButton);
        descButton = (Button) findViewById(R.id.descButton);


        descriptionsDispo.put("Location d`outils",getString(R.string.desc_outil));
        descriptionsDispo.put("Atelier thematique",getString(R.string.desc_alelier));
        descriptionsDispo.put("Cours",getString(R.string.desc_cours));
        descriptionsDispo.put("Tapisserie",getString(R.string.desc_tapisserie));
        descriptionsDispo.put("Stage",getString(R.string.desc_stage));
        initSpinner();


        Intent intent = getIntent();
        if (intent.hasExtra("login")) {
            login = intent.getStringExtra("login");
        }
        if (login.equals("none")){
            Toast toast = Toast.makeText(getApplicationContext(), "Veuillez vous connecter", Toast.LENGTH_LONG);
            toast.show();
            finish();
        }
        Toast toast = Toast.makeText(getApplicationContext(), login, Toast.LENGTH_LONG);
        toast.show();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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


    public void initSpinner() {

        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // On selecting a spinner item
                selected = parent.getItemAtPosition(position).toString();

            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        // Spinner Drop down elements
        String[] nomCategories = (String[]) descriptionsDispo.keySet().toArray(new String[descriptionsDispo.keySet().size()]);
        ArrayList<String> categories = new ArrayList<String>();
        for (int i = 0 ; i < nomCategories.length ; i++)categories.add(nomCategories[i]);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        madate = "";
        cv.setVisibility(View.GONE);
        retour.setVisibility(View.GONE);
        validDate.setVisibility(View.GONE);


        cv.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                madate = year + "-" + (month + 1) + "-" + dayOfMonth;
            }
        });

        editHeureDebut.setOnClickListener(new View.OnClickListener() {
            TimePickerDialog.OnTimeSetListener tmp = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String selectedMinuteText = selectedMinute + "";
                    if (selectedMinuteText.length() < 2)
                        selectedMinuteText = "0" + selectedMinuteText;
                    Log.d("heure fin", selectedMinuteText.length() + "");
                    editHeureDebut.setText(selectedHour + ":" + selectedMinuteText + ":00");
                }
            };

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Admin.this, tmp, 0, 0, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });

        editHeureFin.setOnClickListener(new View.OnClickListener() {
            TimePickerDialog.OnTimeSetListener tmp = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    String selectedMinuteText = selectedMinute + "";
                    if (selectedMinuteText.length() < 2)
                        selectedMinuteText = "0" + selectedMinuteText;
                    Log.d("heure fin", selectedMinuteText.length() + "");
                    editHeureFin.setText(selectedHour + ":" + selectedMinuteText + ":00");
                }
            };

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Admin.this, tmp, 0, 0, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
    }

    public void validerLaDate(View view) {
        editDate.setText(madate);
        closeCalendar(view);
    }

    public void openCalendar(View view) {
        date.setVisibility(View.INVISIBLE);
        heure.setVisibility(View.INVISIBLE);
        nom.setVisibility(View.INVISIBLE);
        nbPlace.setVisibility(View.INVISIBLE);
        categories.setVisibility(View.INVISIBLE);
        editDate.setVisibility(View.INVISIBLE);
        editHeureDebut.setVisibility(View.INVISIBLE);
        editHeureFin.setVisibility(View.INVISIBLE);
        editNom.setVisibility(View.INVISIBLE);
        editNbPlace.setVisibility(View.INVISIBLE);
        valid.setVisibility(View.INVISIBLE);
        cancel.setVisibility(View.INVISIBLE);
        spinner.setVisibility(View.INVISIBLE);
        imgButton.setVisibility(View.INVISIBLE);
        prixField.setVisibility(View.INVISIBLE);
        prixTxt.setVisibility(View.INVISIBLE);
        descButton.setVisibility(View.INVISIBLE);
        validDate.setVisibility(View.VISIBLE);
        cv.setVisibility(View.VISIBLE);
        retour.setVisibility(View.VISIBLE);
    }

    public void closeCalendar(View view) {
        date.setVisibility(View.VISIBLE);
        heure.setVisibility(View.VISIBLE);
        nom.setVisibility(View.VISIBLE);
        nbPlace.setVisibility(View.VISIBLE);
        categories.setVisibility(View.VISIBLE);
        editDate.setVisibility(View.VISIBLE);
        editHeureDebut.setVisibility(View.VISIBLE);
        editHeureFin.setVisibility(View.VISIBLE);
        imgButton.setVisibility(View.VISIBLE);
        editNom.setVisibility(View.VISIBLE);
        editNbPlace.setVisibility(View.VISIBLE);
        valid.setVisibility(View.VISIBLE);
        cancel.setVisibility(View.VISIBLE);
        spinner.setVisibility(View.VISIBLE);
        prixField.setVisibility(View.VISIBLE);
        prixTxt.setVisibility(View.VISIBLE);
        descButton.setVisibility(View.VISIBLE);
        cv.setVisibility(View.GONE);
        retour.setVisibility(View.GONE);
        validDate.setVisibility(View.GONE);
    }

    public void ajoutEvent(String date, String description, String heureDebut, String heureFin, String nom, String places, String prix, String categorie) {
        Log.d(base.LOG_TAG, "Send started");
        final String uri = "http://"+Data.adresse+"/api/v1/events";
        Log.d(base.LOG_TAG, "Uri: " + uri);

        JSONObject jsonRequest;
        try {
            jsonRequest = new JSONObject("{'categorie' : '" + categorie + "', 'date' : '" + date + "' , 'description' : '" + description + "' , 'heure' : '" + heureDebut + "' , 'heureFin' : '" + heureFin + "' , 'nom' : '" + nom + "' , 'place' : " + places + " , 'prix' : " + prix + "}");//TODO
            Log.d("debugparceque", "" + 1);
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    uri,
                    jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("debugparceque", "" + 2);
                            base.getJsonObjectResponse(response);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("debugparceque", "" + 3);
                            base.showError(error);
                        }
                    });
            Log.d("debugparceque", "" + 4);
            request.setTag(base.VOLLEY_TAG);
            Log.d("debugparceque", "" + 5);
            base.queue.add(request);
            Log.d(base.LOG_TAG, "Send done");
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(base.LOG_TAG, e + "");
        }
    }

    public void addEvent(View view) {
        ajoutEvent(editDate.getText().toString(), m_Text, editHeureDebut.getText().toString(), editHeureFin.getText().toString(), editNom.getText().toString(), editNbPlace.getText().toString(), prixField.getText().toString(), selected);
    }

    public void descriptionFieldOpen(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_LONG_MESSAGE | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        input.setText(m_Text);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
}