package hue.com.workbench;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import network.VolleySingleton;

public class Reserva extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserva);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imageView = (ImageView) findViewById(R.id.reserva_img);
        TextView textView = (TextView) findViewById(R.id.reserva_txt);
        spinner = (Spinner) findViewById(R.id.reserva_spinner);

        textView.setText(RecyclerFragment.current_restaurante);
        imageView.setImageResource(R.drawable.bg_abstract);


        requestStuff();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void requestStuff() {
        final ArrayList<String> indexList = new ArrayList<String>();

        final RequestQueue queue = Volley.newRequestQueue(this);

        final StringBuilder output = new StringBuilder();

        String url = "http://192.168.25.6:5984/reservas/" + RecyclerFragment.current_restaurante.replaceAll("\\s","");

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {
                            JSONArray jsonArray = response.getJSONArray("Book");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject childJSONObject = jsonArray
                                        .getJSONObject(i);
                                indexList.add(childJSONObject.getString("Name"));
                                Log.d("Sera", output.toString());
                            }
                            setupSpinner(indexList);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("VolleyTestSimple", "Here we go!");
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO Auto-generated method stub
                Log.e("VolleyTestSimple", "Didn't get shit!");
            }
        });

        VolleySingleton.getInstance().getRequestQueue().add(jsObjRequest);
    }

    private void setupSpinner(ArrayList<String> spinnerArray) {
        spinner.setOnItemSelectedListener(this);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
    }
}
