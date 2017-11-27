package com.codemaicon.agendacontato.database;

/**
 * Created by smaicon on 03/11/2017.
 */
import android.content.Context;
import android.database.sqlite.*;

public class DataBase extends SQLiteOpenHelper {

    public DataBase(Context context){
        super(context, "AGENDA", null, 3);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    db.execSQL(ScriptSQL.getCreateContato());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

