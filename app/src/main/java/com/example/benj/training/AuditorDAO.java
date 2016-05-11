package com.example.benj.training;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Benj on 23/04/2016.
 */
public class AuditorDAO extends DAOBase<Auditor> implements BaseColumns {

    public static final String TABLE_NAME = "auditor";
    public static final String KEY = "id";
    public static final String KEY_NAME = "auditor_name";
    public static final String KEY_FIRSTNAME = "auditor_firstname";
    public static final String KEY_NOTE1 = "note1";
    public static final String KEY_NOTE2 = "note2";
    public static final String KEY_NOTE3 = "note3";

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ("
            + _ID + " INTEGER PRIMARY KEY, "
            + KEY_NAME + " TEXT, "
            + KEY_FIRSTNAME + " TEXT, "
            + KEY_NOTE1 + " REAL, "
            + KEY_NOTE2 + " REAL, "
            + KEY_NOTE3 + " REAL "
            + ");"
            ;

    public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public AuditorDAO(Context context) {
        super(context);
    }

    @Override
    public long add(Auditor a) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, a.getName());
        values.put(KEY_FIRSTNAME, a.getFirstname());
        values.put(KEY_NOTE1, a.getNote1());
        values.put(KEY_NOTE2, a.getNote2());
        values.put(KEY_NOTE3, a.getNote3());
        open();
        long id = mDB.insert(TABLE_NAME, KEY_NAME, values);
        close();
        return id;
    }

    @Override
    public void delete(long id) {
        open();
        mDB.delete(TABLE_NAME, KEY + " = ?", new String[] {String.valueOf(id)});
        close();
    }

    @Override
    public void deleteAll() {
        open();
        mDB.delete(TABLE_NAME,null, new String[] { });
        close();
    }

    @Override
    public void update(Auditor a) {
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, a.getName());
        values.put(KEY_FIRSTNAME, a.getFirstname());
        values.put(KEY_NOTE1, a.getNote1());
        values.put(KEY_NOTE2, a.getNote2());
        values.put(KEY_NOTE3, a.getNote3());
        open();
        mDB.update(TABLE_NAME, values, KEY  + " = ?", new String[] {String.valueOf(a.getId())});
        close();
    }

    @Override
    public Auditor getById(long id) {
        open();
        Cursor result = mDB.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id= ?",  new String[] {String.valueOf(id)});
        result.moveToFirst();
//        while (!result.isAfterLast()) {
//            id = result.getLong(0);
            String name = result.getString(1);
            String firstName = result.getString(2);
            double note1 = result.getLong(3);
            double note2 = result.getLong(4);
            double note3 = result.getLong(5);
            close();
        close();
        return new Auditor(id, name, firstName, note1, note2, note3);
//        }

      //  return null;
    }

    @Override
    public ArrayList<Auditor> getAll() {
        ArrayList<Auditor> auditors = new ArrayList<Auditor>();
        open();
        Cursor result = mDB.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        result.moveToFirst();
        while (!result.isAfterLast()) {
            long id = result.getLong(0);
            String name = result.getString(1);
            String firstName = result.getString(2);
            double note1 = result.getLong(3);
            double note2 = result.getLong(4);
            double note3 = result.getLong(5);
            Auditor auditorLoop = new Auditor(id, name, firstName, note1, note2, note3);
            auditors.add(auditorLoop);
            result.moveToNext();
        }
        close();
        return auditors;
    }

    @Override
    public double bestNote(Auditor a) {
        return Math.max(a.getNote1(), Math.max(a.getNote2(), a.getNote3()));
    }

    @Override
    public Auditor getRand() {
        open();
        Cursor result = mDB.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY RANDOM() LIMIT 1", null);
        result.moveToFirst();
        long id = result.getLong(0);
        String name = result.getString(1);
        String firstName = result.getString(2);
        double note1 = result.getLong(3);
        double note2 = result.getLong(4);
        double note3 = result.getLong(5);
        close();
        return new Auditor(id, name, firstName, note1, note2, note3);
    }
}
