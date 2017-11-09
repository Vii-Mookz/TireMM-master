package com.hitachi_tstv.mist.it.tiresmanagement;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by musz on 7/25/2016.
 */
public class UserTable {
    private OpenHelper objOpenHelper;
    private SQLiteDatabase writeSQLite, readSQLite;

    public UserTable(Context context) {
        objOpenHelper = new OpenHelper(context);
        writeSQLite = objOpenHelper.getWritableDatabase();
        readSQLite = objOpenHelper.getReadableDatabase();

    }

}
