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

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import network.VolleySingleton;

public class MaterialMain extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static boolean logout_button_clicked;
    Toolbar mToolbar;
    NavigationView mDrawer;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ProfilePictureView profileImage;
    HueDatabaseAdapter hueHelper;

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

        requestStuff();


    }

    private void requestStuff() {

        RequestQueue requestQueue = VolleySingleton.getInstance().getRequestQueue();

        StringRequest request = new StringRequest(Request.Method.GET, "http://192.168.25.6:5984/restaurantes", new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Toast.makeText(MaterialMain.this, "R>" + response, Toast.LENGTH_SHORT).show();
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MaterialMain.this, "E>" + error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("JSON_", error.getMessage());
                    }
                }

        );

        requestQueue.add(request);
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
            Log.d("KAKAROTO", hueHelper.getAllData());
        } else if (menuItem.getItemId() == R.id.navigation_item_4) {
            logout_button_clicked = true;
            startActivity(new Intent(getApplicationContext(), LoginScreen.class));
        } else if (menuItem.getItemId() == R.id.navigation_item_5) {
            //DEBUG CODE
            Thread thread = new Thread(new Runnable() {
                URL url_value, url_value2, url_value3, url_value4 = null;

                @Override
                public void run() {
                    try {
                        try {
                            url_value = new URL("https://lh3.googleusercontent.com/-C5WOkwQxbag/AAAAAAAAAAI/AAAAAAAAABg/Ouu9C1Yy8tY/photo.jpg");
                            url_value2 = new URL("https://fbcdn-profile-a.akamaihd.net/hprofile-ak-xpa1/v/t1.0-1/c40.0.160.160/p160x160/10559830_624499194332511_4739612982014301845_n.jpg?oh=06549c47a39b707168d45f6ea089aabd&oe=56BC420A&__gda__=1454802765_f685bcb3e0a52c948bcd2a658e232e85");
                            url_value3 = new URL("http://www.ahturr.com.br/images/content/f6c2e810.jpg");
                            url_value4 = new URL("http://costadouradasm.com.br/wp-content/themes/portolabs/assets/images/logo.png");

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            Bitmap mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());
                            byte[] img1 = DbBitmapUtility.getBytes(mIcon1);

                            hueHelper.insertData("Matsuri", "Rua André Marques, 570", img1);

                            mIcon1 = BitmapFactory.decodeStream(url_value2.openConnection().getInputStream());
                            img1 = DbBitmapUtility.getBytes(mIcon1);

                            hueHelper.insertData("Paiol", "Av. Pres. Vargas, 1892", img1);

                            mIcon1 = BitmapFactory.decodeStream(url_value3.openConnection().getInputStream());
                            img1 = DbBitmapUtility.getBytes(mIcon1);

                            hueHelper.insertData("Vera Cruz", "Av. Nossa Sra. Medianeira, 1600", img1);

                            mIcon1 = BitmapFactory.decodeStream(url_value4.openConnection().getInputStream());
                            img1 = DbBitmapUtility.getBytes(mIcon1);

                            long id = hueHelper.insertData("Costa Dourada", "R. dos Andradas, 1273", img1);

                            if (id < 0) {
                                Log.d("ERRO", "ERRO");
                            } else {
                                Log.d("ERRO", String.valueOf(id));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();
        }
        return false;
    }
}
