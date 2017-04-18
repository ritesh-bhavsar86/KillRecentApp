package com.riteshb.killrecentapp;

/**
 * Created by riteshb on 4/17/2017.
 */
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ActivityMain extends Activity {

    private Button btn_launch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_launch = (Button)findViewById(R.id.button1);

        btn_launch.setOnClickListener(new OnClickListener() {

            @SuppressWarnings("unchecked")
            @Override
            public void onClick(View v) {
                Class serviceManagerClass;
                //int i = 1000 / 0;
                try {
                    serviceManagerClass = Class
                            .forName("android.os.ServiceManager");

                    Method getService = serviceManagerClass.getMethod(
                            "getService", String.class);
                    IBinder retbinder = (IBinder) getService.invoke(
                            serviceManagerClass, "statusbar");
                    Class statusBarClass = Class.forName(retbinder
                            .getInterfaceDescriptor());
                    Object statusBarObject = statusBarClass.getClasses()[0]
                            .getMethod("asInterface", IBinder.class).invoke(
                                    null, new Object[] { retbinder });
                    Method clearAll = statusBarClass
                            .getMethod("toggleRecentApps");
                    clearAll.setAccessible(true);
                    clearAll.invoke(statusBarObject);
                } catch (ClassNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (RemoteException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

}

