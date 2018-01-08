package com.example.majid_fit5.al_rajhitakaful.utility;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.majid_fit5.al_rajhitakaful.AlRajhiTakafulApplication;
import com.example.majid_fit5.al_rajhitakaful.R;

/**
 * Created by Eng. Abdulmajid Alyafey on 1/8/2018.

 */

public class AlertDialogUtility {
    private AlertDialog.Builder alBuilder;

    public AlertDialogUtility(Context context,
                              String title,
                              String message,
                              String positiveBtnTxt,
                              String negativeBtnTxt,
                              DialogInterface.OnClickListener positiveButtonListener,
                              DialogInterface.OnClickListener negativeButtonListener)

    {
      new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(positiveBtnTxt, positiveButtonListener)
                .setNegativeButton(negativeBtnTxt, negativeButtonListener)
                .create().show();
    }
}
