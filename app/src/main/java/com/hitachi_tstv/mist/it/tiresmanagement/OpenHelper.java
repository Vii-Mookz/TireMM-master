package com.hitachi_tstv.mist.it.tiresmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by musz on 7/25/2016.
 */
public class OpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "tire.db";
    private static  final int DATABASE_VERSION  = 1;
    private static final String DATABASE_CREATE = "create table userTable (u_id integer primary key,"+" username ,password);";

    public OpenHelper(Context context){
        super(null,null,null,0);
    }


    @Override

    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
