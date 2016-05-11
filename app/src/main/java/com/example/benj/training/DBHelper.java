package com.example.benj.training;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Benj on 23/04/2016.
 * Pour faciliter la création et les maj de la bdd, on dispose d'une classe d'aide SQLiteOpenHelper qu'il faut dériver.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME = "auditor";
    public static final String KEY_NAME = "auditor_name";
    public static final String KEY_FIRSTNAME = "auditor_firstname";
    public static final String KEY_NOTE1 = "note1";
    public static final String KEY_NOTE2 = "note2";
    public static final String KEY_NOTE3 = "note3";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (id INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_FIRSTNAME + " TEXT, "
            + KEY_NOTE1 + " REAL, "
            + KEY_NOTE2 + " REAL, "
            + KEY_NOTE3 + " REAL "
            + ");"
            ;


    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, databaseName, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE);
        onCreate(db);
    }

    /*
    Méthode dispo dans le cas où le n° de version de la base de donnée est inférieur à l'actuel
     */
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
        super.onDowngrade(db, oldVersion, newVersion);
    }
}