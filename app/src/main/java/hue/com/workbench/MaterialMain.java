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

import com.facebook.login.widget.ProfilePictureView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

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
/*
long id = hueHelper.insertData("Matsuri", "Rua André Marques, 570");
if (id < 0 ){
Toast.makeText(this, "FAIL!!!", Toast.LENGTH_LONG).show();

} else {
Toast.makeText(this, "SUCESSO!!!" + id, Toast.LENGTH_LONG).show();
}
*/


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
        if (menuItem.getItemId() == R.id.navigation_item_1){
                startActivity(new Intent(this, MapsActivity.class));
        } else if (menuItem.getItemId() == R.id.navigation_item_2){
            Log.d("KAKAROTO", hueHelper.getAllData());
        } else if (menuItem.getItemId() == R.id.navigation_item_4){
            logout_button_clicked = true;
            startActivity(new Intent(getApplicationContext(), LoginScreen.class));
        } else if (menuItem.getItemId() == R.id.navigation_item_5){
            //DEBUG CODE

            Thread thread = new Thread(new Runnable(){
                URL url_value = null;
                @Override
                public void run() {
                    try {
                        try {
                            url_value = new URL("http://www.aimisushibar.com.br/Images/aimi_logo.png");
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }
                        try {
                            Bitmap mIcon1 = BitmapFactory.decodeStream(url_value.openConnection().getInputStream());

                            byte[] img1= DbBitmapUtility.getBytes(mIcon1);

                            hueHelper.insertData("Matsuri", "Rua André Marques, 570",img1);
                            hueHelper.insertData("Paiol", "Av. Pres. Vargas, 1892",img1);
                            hueHelper.insertData("Vera Cruz", "Av. Nossa Sra. Medianeira, 1600",img1);
                            long id = hueHelper.insertData("Costa Dourada", "R. dos Andradas, 1273", img1);
                            if (id < 0) {
                                Log.d("ERRO", "ERRO");
                            } else {
                                Log.d("ERRO", String.valueOf(id));
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            thread.start();


        }
        return false;
    }
}
