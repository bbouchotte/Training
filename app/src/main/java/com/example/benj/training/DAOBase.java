package com.example.benj.training;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Benj on 23/04/2016.
 */
public abstract class DAOBase<T> {

    public static final String DATABASE_NAME = "Auditors.db";
    public static final int DATABASE_VERSION = 1;

    protected SQLiteDatabase mDB = null;
    protected DBHelper mHelper = null;

    public DAOBase(Context context) {
        mHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDatabase open() {
        mDB = mHelper.getWritableDatabase();
        return mDB;
    }
    public SQLiteDatabase getDB() {
        return this.mDB;
    }

    public void close() { mDB.close(); }

    public abstract long add(Auditor a);

    public abstract void delete(long id);

    public abstract void deleteAll();

    public abstract void update(Auditor a);

    public abstract Auditor getById(long id);

    public abstract ArrayList<Auditor> getAll();

    public abstract double bestNote (Auditor a);

    public abstract Auditor getRand();
}
