package com.example.deliverymy.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.deliverymy.R;


public class DialogClasses {

    @Nullable
    private static AlertDialog alertDialogInternet = null;
    @Nullable
    private static AlertDialog alertDialogDeactivate = null;

    public static void showDialogAlert(String msg, @Nullable String ttl, String btn, @NonNull final Activity ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg)
                .setCancelable(false)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(btn, new DialogInterface.OnClickListener() {
                    public void onClick(@NonNull DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialog.dismiss();
                    }
                });
        if (ttl != null && !ttl.equals(""))
            builder.setTitle(ttl);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showDialog1(String msg, @Nullable String ttl, String btn, @NonNull final Context ctx) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setMessage(msg).setCancelable(false).setIcon(android.R.drawable.ic_dialog_alert).setPositiveButton(btn, new DialogInterface.OnClickListener() {
            public void onClick(@NonNull DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        if (ttl != null && !ttl.equals(""))
            builder.setTitle(ttl);
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showDialogInternetAlert(final Context ctx) {
        String msg, ttl = "", pos, neg;
        msg = ctx.getString(R.string.alert_internet_not_connect);
//		ttl = ctx.getString(R.string.title_internet_error);
        pos = ctx.getString(R.string.btn_goto_setting);
        neg = ctx.getString(R.string.btn_close);

        if (alertDialogInternet != null && alertDialogInternet.isShowing()) {
            alertDialogInternet.dismiss();
            alertDialogInternet = null;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setMessage(msg).setCancelable(false).setPositiveButton(pos, new DialogInterface.OnClickListener() {
            public void onClick(@NonNull DialogInterface dialog, int id) {
                ctx.startActivity(new Intent(Settings.ACTION_SETTINGS));
                dialog.cancel();
            }
        }).setNegativeButton(neg, new DialogInterface.OnClickListener() {
            public void onClick(@NonNull DialogInterface dialog, int id) {

                dialog.cancel();
            }
        });
        if (!ttl.isEmpty())
            builder.setTitle(ttl);
        alertDialogInternet = builder.create();
        alertDialogInternet.show();
    }
}
