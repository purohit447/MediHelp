package com.check_internet.Utility;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import androidx.appcompat.widget.AppCompatButton;
import com.first.MediHelp.R;


import java.util.Timer;
import java.util.TimerTask;

public class networkChangeListner extends BroadcastReceiver {
    int lostConnection = 0;
    AlertDialog notConnectedDialog;
    AlertDialog.Builder builder;
    @Override
    public void onReceive(Context context, Intent intent) {
        if (!common.isConnected(context) && lostConnection == 0) {
            builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.check_internet_dialoug, null);
            builder.setView(layout_dialog);

            AppCompatButton retry = layout_dialog.findViewById(R.id.retry);

            notConnectedDialog = builder.create();
            notConnectedDialog.show();
            notConnectedDialog.setCancelable(false);
            notConnectedDialog.getWindow().setGravity(Gravity.CENTER);
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    lostConnection = 1;
//                    while(!common.isConnected(context)) {
//                        // infinite delay for connection.
//                    }

//                     destroy page
                   // notConnectedDialog.dismiss();
                    onReceive(context, intent);

                }
            });
        }
        if(lostConnection==1 && common.isConnected(context)) {
            notConnectedDialog.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layout_dialog = LayoutInflater.from(context).inflate(R.layout.back_online_dialoug, null);
            builder.setView(layout_dialog);
            AlertDialog dialog = builder.create();
            dialog.show();
            lostConnection = 0;
            dialog.setCancelable(false  );
            dialog.getWindow().setGravity(Gravity.CENTER);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 2000);
        }
    }
}
