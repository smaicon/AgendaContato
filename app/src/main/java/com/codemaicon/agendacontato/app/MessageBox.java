package com.codemaicon.agendacontato.app;

import android.content.Context;
import android.support.v7.app.AlertDialog;

/**
 * Created by smaicon on 07/11/2017.
 classe que agrupa as mensagens
 */

public class MessageBox  {


    public  static void showInfo (Context context, String title, String msg){
        show(context, title, msg, android.R.drawable.ic_dialog_info);

    }
    public  static void showAlert (Context context, String title, String msg){
        show(context, title, msg, android.R.drawable.ic_dialog_alert);

    }
    public  static void show (Context context, String title, String msg){
        show(context, title, msg, 0);

        //android.R.Drawable.ic_ ...
    }
    public static void show(Context context, String title, String msg, int iconId) {
        AlertDialog.Builder dlg = new AlertDialog.Builder(context);
        dlg.setIcon(iconId);
        dlg.setTitle(title);
        dlg.setMessage(msg);
        dlg.setNeutralButton("OK", null);
        dlg.show();
    }
}
