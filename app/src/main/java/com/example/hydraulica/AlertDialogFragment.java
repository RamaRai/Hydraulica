package com.example.hydraulica;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

public class AlertDialogFragment extends DialogFragment
                                    implements DialogInterface.OnClickListener {

    private static String TAG = "Hydraulica2019_RamaRai";
    public static int mParam1;

    TextView tv;

    public static AlertDialogFragment newInstance (int message){

        AlertDialogFragment adf = new AlertDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("alert-message", String.valueOf(message));

        Log.i(TAG,message+" Total Force calculated");

        adf.setArguments(bundle);
        mParam1 = message;
        Log.i(TAG,mParam1+" local variable received Force Value");
        return adf;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        this.setCancelable(true);
        int style = DialogFragment.STYLE_NORMAL,theme = 0;
        setStyle(style,theme);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.alert,container,false);
        tv = (TextView)v.findViewById(R.id.alertmessage);

       // tv.setText(getActivity().getResources().getText(getArguments().getInt("alert-message")));
        tv.setText(getArguments().getString("alert_message"));

        Button okBtn = (Button)v.findViewById(R.id.btn_ok);
        okBtn.setOnClickListener(new View.OnClickListener(){
            @Override
                    public void onClick(View view){
                dismiss();
            }
        });
        return v;
    }
    @Nullable
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);
        //Static object AlertDialog.Builder

        AlertDialog.Builder adf = new AlertDialog.Builder(getActivity())
                .setTitle("Cylinder Force in pounds = "+mParam1)
                .setPositiveButton("OK",this);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(style,theme);
        return adf.create();
    }

    @Override
        public void onAttach(Activity act){
        super.onAttach(act);
        try{
            OnDialogDoneListener oddl = (OnDialogDoneListener)act;
        }catch (ClassCastException cce){
            Log.e(TAG,"Activity is not listening");
        }
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        dismiss();
    }
}
