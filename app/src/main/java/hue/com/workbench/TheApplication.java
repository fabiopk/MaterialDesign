package hue.com.workbench;

import android.app.Application;
import android.content.Context;

/**
 * Created by Fabio on 09/11/2015.
 */
public class TheApplication extends Application {

    private static TheApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static TheApplication getsInstance(){
        return sInstance;
    }

    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }
}
