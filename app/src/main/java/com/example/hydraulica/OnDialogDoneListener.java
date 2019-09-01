package com.example.hydraulica;

public interface OnDialogDoneListener {
    //TAG-> Type of DialogFragment: Help, Alert or Prompt
    //      This keep tracks of the
    //Cancelled --> Did the user press the CANCEL button
    //              from the selected
    //              FragmentDialog from OptionsMenu ?
    //              (Yes/No)
    //message -->  Message "WE" want to display in a Toast
    //CharSequence is like a character Buffer
    public void onDialogDone(String tag,
                             boolean cancelled,
                             CharSequence message);
}
