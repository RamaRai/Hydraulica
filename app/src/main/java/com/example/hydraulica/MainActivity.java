package com.example.hydraulica;

// Mobile App Development Course - Seneca College 2019
// Project Hydraulica (Paid App )
// Submitted to : Paulo Treves
// Submitted by Rama K. Rai ( Student Id : 125423194 )

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DownloadImageTask diTask;
    private static final int REQUEST_SELECT_CONTACT = 1;

    // LOGTAG used to log in LogCat for debugging purpose
    public static final String TAG = "Hydraulica2019_RamaRai";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Logging in the LOCat
        Log.v(TAG, "1. Rama Rai student ID: 125 423 194");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

        Log.v(TAG, "Floating Button Snack Bar ON");

        Snackbar.make(view, "Control Transfers to Google email : GMAIL", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show();

                //Here Implicit Intent is used to do the action -> Will open Gmail
                Intent intent=new Intent(Intent.ACTION_SEND);
                String[] recipients={"mailto@gmail.com"};
                intent.putExtra(Intent.EXTRA_EMAIL, recipients);
                intent.putExtra(Intent.EXTRA_SUBJECT,"Subject text here...");
                intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
                intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");
                intent.setType("text/html");
                intent.setPackage("com.google.android.gm");
                startActivity(Intent.createChooser(intent, "Send mail"));

                Log.v(TAG, "Invoked Google Gmail");
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        //To set navigation icons default color null so that the colors from xml files are displayed
        navigationView.setItemIconTintList(null);

        Log.v(TAG, "initiating AsyncTask");

        if(diTask != null) {
            AsyncTask.Status diStatus = diTask.getStatus();
            Log.v("doClick", "diTask status is " + diStatus);
            if(diStatus != AsyncTask.Status.FINISHED) {
                Log.v("doClick", "... no need to start a new task");
                return;
            }
            // Since diStatus must be FINISHED, we can try again.
        }

        diTask = new DownloadImageTask(this);

        Log.v(TAG, "Execute Network Call to Google Chart");

        //This is where we create pie chart with the pie slices and thier values
        diTask.execute("http://chart.apis.google.com/chart?&" +
                "cht=p&" +
                "chs=460x250&" +
                "chd=t:15,23,10,12,5,15,20&" +
                "chl=North%20America%7C" +
                "Europe%7C" +
                "South%20America%7C" +
                "Australia%7C" +
                "Middle%20East%7C" +
                "Africa%7C" +
                "Asia&" +
                "chco=FFFF00,004411");

        Log.v(TAG, "Google Chart Done");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            finish();
            Log.v(TAG, "Getting out of the App from Option Menu");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calculate) {

            Log.v(TAG, "Starting Calculation Activity");

            Intent contactusIntent1 = new Intent(this, calcuinput.class);
            startActivity(contactusIntent1);

        } else if (id == R.id.nav_supplier) {

            Log.v(TAG, "Executing RunTime Permission on Contacts");

                // RunTime Permissions Framework on Android Contacts
                int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);

                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_CONTACTS}, REQUEST_SELECT_CONTACT);

                }else{
                    moveToContacts();
                }

        } else if (id == R.id.nav_contactus) {

            Log.v(TAG, "Starting Contact Us Activity to show Ext Link to website");

            //Contact Us Activity to show Links
            Intent contactusIntent = new Intent(this, ContactUs.class);
            startActivity(contactusIntent);

        } else if (id == R.id.nav_database) {

            Log.v(TAG, "Starting Database System to take Notes");
            //List View of Notes, if any
            Intent k = new Intent(MainActivity.this, NotesList.class);
            startActivity(k);

        } else if (id == R.id.nav_about) {

            Log.v(TAG, "Starting About Activity with Dialog theme");
            //About Activity with Dialog theme
            Intent j = new Intent(MainActivity.this, About.class);
            startActivity(j);

        } else if (id == R.id.nav_map) {

            Log.v(TAG, "Starting Google Maps Activity");
            //Google Maps Activity
            Intent i = new Intent(MainActivity.this, MapActivity.class);
            startActivity(i);

        }else if (id == R.id.nav_exit) {
            //Destroys the current Activity
            finish();
            Log.v(TAG, "Getting out of the App from Navigation Drawer");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //RunTime Permission Verification for 'Granted'
    public void onRquestPermissionsResult(int requestCode,String permission[],int[] grantResults){
        switch(requestCode){
            case REQUEST_SELECT_CONTACT: {
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    moveToContacts();
                }
            }
        }
    }

    //RunTime Permission 'Granted' Then move to Android Contacts
    public void moveToContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
        }
    }
}
