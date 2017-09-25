package com.production.hackr;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Tutorials extends AppCompatActivity {

    RecyclerView recyclerView;
    TutorialsAdapter tutorialsAdapter;
    public static final int CONNECTION_TIMEOUT = 10000;
    public static final int READ_TIMEOUT = 15000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorials);

        new AsyncFetch().execute();
    }

    private  class AsyncFetch extends AsyncTask<String,String,String> {

        ProgressDialog pdLoading = new ProgressDialog(Tutorials.this);
        HttpURLConnection conn;
        URL url=null;
        public AsyncFetch() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected void onPostExecute(String result) {
            pdLoading.dismiss();
            List<TutorialContent> data=new ArrayList<>();
            pdLoading.dismiss();

            try {
                JSONArray jsonArray = new JSONArray(result);

                for(int i=0;i<jsonArray.length();i++){
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    TutorialContent TutData = new TutorialContent();
                    TutData.tut_name= json_data.getString("name");
                    TutData.submitted_by= json_data.getString("submitted_by");
                    TutData.upvotes= json_data.getString("upvotes");
                    TutData.source= json_data.getString("src");
                    TutData.status= json_data.getString("free");
                    data.add(TutData);


                    recyclerView =(RecyclerView)findViewById(R.id.recycler_view);

                    tutorialsAdapter=new TutorialsAdapter(Tutorials.this, data);
                    recyclerView.setLayoutManager(new LinearLayoutManager(Tutorials.this));
                    recyclerView.setAdapter(tutorialsAdapter);

                }



            } catch (JSONException e) {
                Toast.makeText(Tutorials.this,e.toString(), Toast.LENGTH_LONG).show();
            }
        }




        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }





        @Override
        protected String doInBackground(String... params) {

            try {
                /// enter URL here
                url = new URL("    ");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }

            try {
                conn=(HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                conn.setDoOutput(true);

            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }


            try {
                int respons_code= conn.getResponseCode();

                if(respons_code== HttpURLConnection.HTTP_OK){

                    InputStream inputStream = conn.getInputStream();
                    BufferedReader bufferedReader= new BufferedReader( new InputStreamReader(inputStream));

                    StringBuilder result = new StringBuilder();
                    String line;

                    while((line= bufferedReader.readLine()) !=null){
                        result.append(line);
                    }

                    return (result.toString());

                }
                else{
                    return ("Unsuccesful");
                }


            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }

            finally {
                conn.disconnect();
            }
        }
    }
}
