package com.example.dailymotivator;

import androidx.fragment.app.DialogFragment;

public interface NoticeDialogListener {
    public void onDialogPositiveClick(DialogFragment dialog);
    public void onDialogNegativeClick(DialogFragment dialog);
}