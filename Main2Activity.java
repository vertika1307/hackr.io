package com.production.hackr;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SearchView.OnQueryTextListener {
    ListView list;
    ListViewAdapter adapter;
    SearchView editsearch;
    String[] langNameList;
    ArrayList<LangName> arraylist = new ArrayList<LangName>();
    private List<String> a;
    private SQLiteHandler db;
    public ProgressDialog pDialog;
    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtName = (TextView) findViewById(R.id.name);
        TextView txtEmail = (TextView) findViewById(R.id.email);

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);


        db = new SQLiteHandler(getApplicationContext());
        // Session manager
        session = new SessionManager(getApplicationContext());


        if (session.isLoggedIn()) {

            // Fetching user details from sqlite
            HashMap<String, String> user = db.getUserDetails();

            String name = user.get("name");
            String email = user.get("email");

            // Displaying the user details on the screen
            txtName.setText(name);
            txtEmail.setText(email);



            /*pDialog.setMessage("Fetching Data ....");
            showDialog();

            StringRequest stringRequest = new StringRequest(Request.Method.POST,AppConfig.URL_LANGUAGE, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    showJSON(response);
                }
            },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Main2Activity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                        }
                    });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);*/



            // Generate sample data

            String[] langNameList = new String[]{"Ada", "Android Development","Ansible","Angular","Apache Spark",
                    "Arduino", "Bootstrap", "C++", "C", "CSS", "Django", "Emacs", "Firebase", "Google Analytics", "Hadoop", "HTML", "iOS and Swift", "Java", "Kotlin", "Lisp", "Matlab", "Perl", "PHP", "R", "SQL", "Typescript", "Unity", "Visual Basic", "Wordpress", "Yii"};

            String[] langNameList2=new String[]{"Android Development","C","C++","Java","Python"};
            // Locate the ListView in listview_main.xml
            list = (ListView) findViewById(R.id.listview);

            for (int i = 0; i < langNameList2.length; i++) {
                LangName langName = new LangName(langNameList2[i]);
                // Binds all strings into an array
                arraylist.add(langName);
            }

            // Pass results to ListViewAdapter Class
            adapter = new ListViewAdapter(this, arraylist);

            // Binds the Adapter to the ListView
            list.setAdapter(adapter);

            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Bundle bundle=new Bundle();
                    bundle.putInt("id",position+1);
                    Intent i=new Intent(Main2Activity.this,Tutorials.class);
                    i.putExtras(bundle);
                    startActivity(i);

                }
            });

            // Locate the EditText in listview_main.xml
            editsearch = (SearchView) findViewById(R.id.search);
            editsearch.setOnQueryTextListener(this);

            /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });*/

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    /*private void showJSON(String response){
        String lang1="";

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray(AppConfig.JSON_ARRAY);
            JSONObject collegeData = result.getJSONObject(0);
            lang1 = collegeData.getString(AppConfig._NAME);
            a.add(lang1);
            String[] aArray= (String[]) a.toArray();

            for (int i = 0; i < aArray.length; i++) {
                LangName langName = new LangName(aArray[i]);
                // Binds all strings into an array
                arraylist.add(langName);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        list = (ListView) findViewById(R.id.listview);


        // Pass results to ListViewAdapter Class
        adapter = new ListViewAdapter(this, arraylist);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);
    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
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
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
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

        if (id == R.id.learn) {
            Intent intent = new Intent(Main2Activity.this, Main2Activity.class);
            startActivity(intent);
        } else if (id == R.id.submit) {
            Intent intent = new Intent(Main2Activity.this, Submit.class);
            startActivity(intent);

        } else if (id == R.id.tests) {
            Intent intent = new Intent(Main2Activity.this, Tests.class);
            startActivity(intent);

        } else if (id == R.id.logout) {
            logoutUser();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logoutUser() {
        session.setLogin(false);

        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(Main2Activity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
