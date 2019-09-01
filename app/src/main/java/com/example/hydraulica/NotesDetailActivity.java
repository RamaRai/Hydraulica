package com.example.hydraulica;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NotesDetailActivity extends Activity {


    private Spinner mCategory;      // Designation
    private EditText mFNText;       // Name
    private EditText mSBJText;      // Subject
    private EditText mAddText;      // Street Address
    private Spinner mCountryText;   // Country
    private EditText mRemarksText;  // Remarks
    private EditText mPHText;       // Contact

    private Uri addUri;
    public int emptyflag;


    @Override
    protected void onCreate(Bundle bundle) {

        super.onCreate(bundle);

        setContentView(R.layout.notes_edit);

        mCategory = (Spinner) findViewById(R.id.category);
        mFNText = (EditText) findViewById(R.id.add_fn);
        mSBJText = (EditText) findViewById(R.id.add_sbj);
        mAddText = (EditText) findViewById(R.id.add_stradd);
        mCountryText = (Spinner) findViewById(R.id.scountry);;
        mRemarksText = (EditText) findViewById(R.id.add_remarks);
        mPHText = (EditText) findViewById(R.id.add_phone);   ;

        Button confirmButton = (Button) findViewById(R.id.add_detail_confirm);

        //Creating a Bundle Object to recieve the Intent
        Bundle extras = getIntent().getExtras();

        // Check from the saved Instance Creating new record from Insert Menu Option
        addUri = (bundle == null) ? null : (Uri) bundle.getParcelable(HydraulicaContentProvider.CONTENT_ITEM_TYPE);

        // Or passed from the other activity i.e long press list record opens the same record to edit
        if (extras != null) {
            addUri = extras.getParcelable(HydraulicaContentProvider.CONTENT_ITEM_TYPE);
            //here fillDAta function fetches all the fields from database Table for the record clicked in list
            fillData(addUri);
        }
        //Checking to make sure all the fields in Address are filled
        confirmButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                emptyflag = 0;
                if(mFNText.getText().toString().matches("")){
                    emptyflag = 1;
                }
                if(mSBJText.getText().toString().matches("")){
                    emptyflag = 1;
                }
                if(mAddText.getText().toString().matches("")){
                    emptyflag = 1;
                }
                if(mRemarksText.getText().toString().matches("")){
                    emptyflag = 1;
                }
                if(mPHText.getText().toString().matches("")){
                    emptyflag = 1;
                }
                if (emptyflag == 1){
                    //If any field is NOT filled i.e is empty emptyflag = 1 , Generate the toast
                    makeToast();
                }else {
                    //update the database Table either by appending the new record OR current modified record
                    setResult(RESULT_OK);
                    //Dismissing the current activity to go back to List view
                    finish();
                }
            }

        });

    }


    //fillData here fills the activity with All the fields from Database Table
    private void fillData(Uri uri) {

        //Projection is All the fileds from the Database Table with the names
        String[] projection = { HydraulicaTableHandler.COLUMN_ID, HydraulicaTableHandler.COLUMN_DESIGNATION,
                HydraulicaTableHandler.COLUMN_NAME, HydraulicaTableHandler.COLUMN_SUBJECT, HydraulicaTableHandler.COLUMN_ADDRESS,
                HydraulicaTableHandler.COLUMN_COUNTRY, HydraulicaTableHandler.COLUMN_REMARKS,
                HydraulicaTableHandler.COLUMN_PHONE  };

        //Cursor is the temp table with the projection
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {

            //setting to first record i.e start of the Cursor
            cursor.moveToFirst();

            //Because Designation is Spinner View, get selection
            String category = cursor.getString(cursor.getColumnIndexOrThrow(com.example.hydraulica.HydraulicaTableHandler.COLUMN_DESIGNATION));
            for (int i = 0; i < mCategory.getCount(); i++) {
                String s = (String) mCategory.getItemAtPosition(i);
                if (s.equalsIgnoreCase(category)) {
                    mCategory.setSelection(i);
                }
            }

            mFNText.setText(cursor.getString(cursor.getColumnIndexOrThrow(com.example.hydraulica.HydraulicaTableHandler.COLUMN_NAME)));
            mSBJText.setText(cursor.getString(cursor.getColumnIndexOrThrow(com.example.hydraulica.HydraulicaTableHandler.COLUMN_SUBJECT)));
            mAddText.setText(cursor.getString(cursor.getColumnIndexOrThrow(com.example.hydraulica.HydraulicaTableHandler.COLUMN_ADDRESS)));

            String country = cursor.getString(cursor.getColumnIndexOrThrow(com.example.hydraulica.HydraulicaTableHandler.COLUMN_COUNTRY));
            for (int j = 0; j < mCountryText.getCount(); j++) {
                String w = (String) mCountryText.getItemAtPosition(j);
                if (w.equalsIgnoreCase(country)) {
                    mCountryText.setSelection(j);
                }
            }

            mRemarksText.setText(cursor.getString(cursor.getColumnIndexOrThrow(com.example.hydraulica.HydraulicaTableHandler.COLUMN_REMARKS)));
            mPHText.setText(cursor.getString(cursor.getColumnIndexOrThrow(com.example.hydraulica.HydraulicaTableHandler.COLUMN_PHONE)));

            // Always close the cursor
            cursor.close();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState();
        outState.putParcelable(HydraulicaContentProvider.CONTENT_ITEM_TYPE, addUri);
    }

    //onPause : App goes on pause if some interupptions like e.g a phone call comes
    @Override
    protected void onPause() {
        super.onPause();
        //on pause Call a function to save the state of the App
        saveState();
    }


    //Function saves the state of the App ONLY if the Activity doesn't have any details
    private void saveState() {
        String category = (String) mCategory.getSelectedItem();
        String sname = mFNText.getText().toString();
        String subject = mSBJText.getText().toString();
        String streetaddress = mAddText.getText().toString();
        String country = (String) mCountryText.getSelectedItem();
        String remarks = mRemarksText.getText().toString();
        String contact = mPHText.getText().toString();

        // Only save if details are available (by checking length of each field if it is 0 i.e it is empty then just return)
        if ((sname.length() == 0) && (subject.length() == 0) && (streetaddress.length() == 0) && (remarks.length() == 0) && (contact.length() == 0)) {
            return;
        }

        //Save the Content values
        ContentValues values = new ContentValues();
        values.put(HydraulicaTableHandler.COLUMN_DESIGNATION, category);
        values.put(HydraulicaTableHandler.COLUMN_NAME, sname);
        values.put(HydraulicaTableHandler.COLUMN_SUBJECT, subject);
        values.put(HydraulicaTableHandler.COLUMN_ADDRESS, streetaddress);
        values.put(HydraulicaTableHandler.COLUMN_COUNTRY, country);
        values.put(HydraulicaTableHandler.COLUMN_REMARKS, remarks);
        values.put(HydraulicaTableHandler.COLUMN_PHONE, contact);

        if (addUri == null) {
            // New Address
            addUri = getContentResolver().insert(HydraulicaContentProvider.CONTENT_URI, values);
        } else {
            // Update Address
            getContentResolver().update(addUri, values, null, null);
        }
    }

    private void makeToast() {
        Toast.makeText(NotesDetailActivity.this, "Fields CAN NOT be empty",Toast.LENGTH_LONG).show();
    }


}
