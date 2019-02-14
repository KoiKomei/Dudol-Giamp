package com.ilmale.doodlejump;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.ilmale.doodlejump.database.User;

public class MyAlertDialog {

    private Context context;

    private static final MyAlertDialog ourInstance = new MyAlertDialog();

    public static MyAlertDialog getInstance() {
        return ourInstance;
    }

    private MyAlertDialog() { }

    public void setContext(Context context){
        this.context = context;
    }

    public void notifyToPlay(User user){
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.Theme_AppCompat_Dialog);
        builder.setMessage("The player "+user.getUsername()+"\nhas the best score in 30 km \nScore: " + user.getPunteggio() + "\nDo you want to try to beat him?");
        builder.setCancelable(true);

        builder.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Intent intent = new Intent(context, GameActivity.class);
                        context.startActivity(intent);
                    }
                });

        builder.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }
}
