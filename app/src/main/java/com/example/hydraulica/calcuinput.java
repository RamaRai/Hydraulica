package com.example.hydraulica;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.text.InputFilter;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

public class calcuinput extends AppCompatActivity implements OnDialogDoneListener {

    public int ok =0;
    public int emptyflag;

    //Local Variables these will get attached to the view in layout
    EditText pressureText;
    EditText areaText;

    private static String TAG = "Hydraulica2019_RamaRai";
    public static final String ALERT_DIALOG_TAG = "ALERT_DIALOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calcuinput);
        Toolbar toolbar = findViewById(R.id.toolbar);

        Log.i(TAG, " Calculation variables attached with layout");

        //Local EditText Variables getting attached to the views in layout
        //Implementing input filter of variables to make sure the number are entered
        //and numbers can ONLY be in range of 1-1000
        pressureText = findViewById(R.id.epressure);
        pressureText.setFilters(new InputFilter[]{new MinMaxFilter("1", "1000")});

        areaText = findViewById(R.id.earea);
        areaText.setFilters(new InputFilter[]{new MinMaxFilter("1", "1000")});

        //render images in animation format

        ImageView imgView = (ImageView)findViewById(R.id.imageView);
        imgView.setVisibility(ImageView.VISIBLE);
        imgView.setBackgroundResource(R.drawable.frame_animation);

        AnimationDrawable frameAnimation = (AnimationDrawable) imgView.getBackground();

        frameAnimation.start();
        Log.i(TAG, " Animation Started");
        //OneShot runs the animation ONLY for Once, for this example
        // I copied the 3 lines in frame_animation.xml 5 time to make a longer animation
        //frameAnimation.setOneShot(true);
    }

    public void myresult(View v) {

        emptyflag = 1;
        int force;

        switch(v.getId()){

            case R.id.submit:

                //Checking if any field in the view is empty
                if(pressureText.getText().toString().matches("")){
                    emptyflag = 0;
                    ok=0;
                }
                if(areaText.getText().toString().matches("")){
                    emptyflag = 0;
                    ok=0;
                }
                if(emptyflag == 0){
                    //If any view is empty invoke the TOAST
                 Log.i(TAG," Testing field(s) is empty");
                 Toast.makeText(this,"The fields can not be empty",Toast.LENGTH_LONG).show();
                }else{
                    ok=1;
                }

                if(ok==1) {
                    int mypress = Integer.parseInt(pressureText.getText().toString());
                    int myarea = Integer.parseInt(areaText.getText().toString());
                    //OK =1 means all the fields have data

                    //Create a Toast to acknowledge Calculation
                    Toast.makeText(this, "Displaying Calculation Alert Fragment",Toast.LENGTH_LONG).show();
                    // Calculating Force
                    force=(mypress*myarea);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    //Passing calculated Force to AlertDialogFragment
                    AlertDialogFragment adf = AlertDialogFragment.newInstance(force);

                    // Log to log the statement in LogCat for debugging purpose
                    Log.v(TAG, "Calculated in AlertDialogFragment");

                    adf.show(ft, ALERT_DIALOG_TAG);
                }
                ok=0;
                break;
        }
    }

    @Override
    public void onDialogDone(String tag, boolean cancelled, CharSequence message) {
        String s = tag + " respond with:" + message;
        if (cancelled) s = tag + " was cancelled by the user";

        //Toast used to display information for the short period of time
        Toast.makeText(this, s, Toast.LENGTH_LONG).show();
        // Log to log the statement in LogCat for debugging purpose
        Log.v(TAG, s);
    }
}
