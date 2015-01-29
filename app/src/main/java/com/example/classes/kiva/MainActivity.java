package com.example.classes.kiva;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends ActionBarActivity {

    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();


        String url = "http://api.kivaws.org/v1/loans/newest.json&per_page=1";




        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("loans");
                    TextView tv = (TextView)findViewById(R.id.idTitulo);
                    tv.setText(array.getJSONObject(0).get("name").toString());

                    tv = (TextView)findViewById(R.id.idDescripcion);
                    tv.setText(array.getJSONObject(0).get("use").toString());

                    int idImagen = array.getJSONObject(0).getJSONObject("image").getInt("id");

                    String UrlImagen = String.format("http://www.kiva.org/img/240/%d.jpg",idImagen);
                    NetworkImageView niv = (NetworkImageView) findViewById(R.id.idImagen);
                    niv.setImageUrl(UrlImagen,VolleySingleton.getInstance().getImageLoader());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {

            }
        }
        );

        VolleySingleton.getInstance().getRequestQueue().add(jor);
    }

    public static Context getAppContext()
    {
        return MainActivity.context;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
