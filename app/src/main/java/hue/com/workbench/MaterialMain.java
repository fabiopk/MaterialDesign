package hue.com.workbench;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.widget.ProfilePictureView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import network.VolleySingleton;

public class MaterialMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean logout_button_clicked;
    Toolbar mToolbar;
    NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ProfilePictureView profileImage;
    HueDatabaseAdapter hueHelper;
    ArrayList<JSONObject> menuRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material);
        mToolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(mToolbar);
        mDrawer = (NavigationView) findViewById(R.id.main_drawer);
        mDrawer.setNavigationItemSelectedListener(this);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        profileImage = (ProfilePictureView) findViewById(R.id.profPicture);
        profileImage.setPresetSize(ProfilePictureView.SMALL);
        profileImage.setProfileId(LoginFragment.profile.getId());
        TextView welcome = (TextView) findViewById(R.id.welcome);
        welcome.setText("Welcome back " + LoginFragment.profile.getFirstName());
        hueHelper = new HueDatabaseAdapter(this);
        logout_button_clicked = false;
        menuRequest = new ArrayList<JSONObject>();

        requestStuff();


    }

    private void displayStuff() throws JSONException, MalformedURLException {

        for (final JSONObject jsonObject : menuRequest) {
            Thread thread = new Thread(new Runnable() {

                URL url = new URL(jsonObject.getString("Image"));

                @Override
                public void run() {
                    try {
                        Bitmap mIcon = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        byte[] img = DbBitmapUtility.getBytes(mIcon);

                        hueHelper.insertData(jsonObject.getString("Name"), jsonObject.getString("Address"), img);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }

    }

    private void requestStuff() {
        final ArrayList<String> indexList = new ArrayList<String>();

        final RequestQueue queue = Volley.newRequestQueue(this);

        final StringBuilder output = new StringBuilder();
        String url = "http://192.168.25.6:5984/restaurantes/_all_docs";
        Log.d("Sera", output.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                Request.Method.GET, url, (String) null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        // TODO Auto-generated method stub
                        try {
                            JSONArray jsonArray = response.getJSONArray("rows");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject childJSONObject = jsonArray
                                        .getJSONObject(i);
                                indexList.add(childJSONObject.getString("id"));
                                Log.d("Sera", output.toString());
                            }

                            // Second Request stuff
                            String baseURL = "http://192.168.25.6:5984/restaurantes/";
                            for (String id : indexList) {

                                final JsonObjectRequest jsObjRequest = new JsonObjectRequest(
                                        Request.Method.GET, baseURL + id, (String) null,
                                        new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                output.append("Response => "
                                                        + response.toString()
                                                        + "\r\n\r\n");
                                                Log.d("Sera", output.toString());

                                                menuRequest.add(response);

                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        //nothing

                                    }
                                });
                                VolleySingleton.getInstance().getRequestQueue().add(jsObjRequest);

                            }

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

    private void setStuff() {
//        final ArrayList<String> indexList = new ArrayList<String>();
//
//        final RequestQueue queue = Volley.newRequestQueue(this);
//
//        final StringBuilder output = new StringBuilder();
//
//        String url = "http://192.168.25.6:5984/reservas/b8f005f84373a48a8c5b9d978d000eef";
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(
//                Request.Method.GET, url, (String) null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        // TODO Auto-generated method stub
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("Book");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                indexList.add(String.valueOf(jsonArray.getInt(i)));
//                                Log.d("QUACK", indexList.toString());
//                            }
//
//                            String url = "http://192.168.25.6:5984/reservas/b8f005f84373a48a8c5b9d978d000eef";
//                            StringRequest putRequest = new StringRequest(Request.Method.PUT,url,
//                                    new Response.Listener<String>() {
//                                        @Override
//                                        public void onResponse(String response) {
//                                            // response
//                                            Log.d("Response", response);
//                                        }
//                                    },
//                                    new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            // error
//                                            Log.d("Error.Response", error.toString());
//                                        }
//                                    }
//                            ) {
//
//                                @Override
//                                protected Map<String, String> getParams() {
//                                    Map<String, String> params = new HashMap<String, String>();
//                                    JSONArray horario = new JSONArray();
//                                    horario.put(12);
//                                    horario.put(34);
//                                    horario.put(56);
//                                    horario.put(78);
//
//                                    params.put("Book", horario.toString());
//
//
//                                    return params;
//                                }
//
//                            };
//
//                            VolleySingleton.getInstance().getRequestQueue().add(putRequest);
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        Log.i("VolleyTestSimple", "Here we go!");
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                // TODO Auto-generated method stub
//                Log.e("VolleyTestSimple", "Didn't get shit!");
//            }
//        });
//
//        VolleySingleton.getInstance().getRequestQueue().add(jsObjRequest);
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
        if (id == R.id.action_refresh) {
            try {
                displayStuff();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == R.id.navigation_item_1) {
            startActivity(new Intent(this, MapsActivity.class));
        } else if (menuItem.getItemId() == R.id.navigation_item_2) {
            try {
                displayStuff();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else if (menuItem.getItemId() == R.id.navigation_item_3) {
            setStuff();
        } else if (menuItem.getItemId() == R.id.navigation_item_4) {
            logout_button_clicked = true;
            startActivity(new Intent(getApplicationContext(), LoginScreen.class));
        } else if (menuItem.getItemId() == R.id.navigation_item_5) {
            //DEBUG CODE
            setStuff();
        }
        return false;
    }
}
