package com.aucegypt.ispend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.aucegypt.ispend.AppContract.*;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EntryDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "EntryDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public EntryDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_ENTRIES_TABLE = "CREATE TABLE " +
                EntriesTable.TABLE_NAME + " ( " +
                EntriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EntriesTable.COLUMN_TITLE + " TEXT NOT NULL, " +
                EntriesTable.COLUMN_NOTES + " TEXT, " +
                EntriesTable.COLUMN_DATE + " TEXT DEFAULT CURRENT_DATE, " +
                EntriesTable.COLUMN_TIME + " TEXT DEFAULT CURRENT_TIME, " +
                EntriesTable.COLUMN_METHOD + " TEXT DEFAULT 'CASH', " +
                EntriesTable.COLUMN_VALUE + " DOUBLE, " +
                EntriesTable.COLUMN_EXPSAV + " TEXT, " +
                EntriesTable.COLUMN_STORE + " TEXT, " +
                EntriesTable.COLUMN_DATES + " DATE DEFAULT CURRENT_DATE, " +
                EntriesTable.COLUMN_ITEMS + " TEXT"
                +" )";

        db.execSQL(SQL_CREATE_ENTRIES_TABLE);
        fillEntriesTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + EntriesTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillEntriesTable()
    {
        EntryItem entry = new EntryItem("Grocery Shopping", "This week's groceries", "21 Jan, 2021", "17:32", "Cash", 23.84, "exp", "Real", "2021-01-21");
        insertEntry(entry);
    }

    public void addEntry(EntryItem entryItem)
    {
        db = getWritableDatabase();
        insertEntry(entryItem);
    }

    private void insertEntry(EntryItem entryItem)
    {
        ContentValues cv = new ContentValues();
        cv.put(EntriesTable.COLUMN_TITLE, entryItem.getTitle());
        cv.put(EntriesTable.COLUMN_NOTES, entryItem.getNotes());
        cv.put(EntriesTable.COLUMN_DATE, entryItem.getDate());
        cv.put(EntriesTable.COLUMN_TIME, entryItem.getTime());
        cv.put(EntriesTable.COLUMN_METHOD, entryItem.getMethod());
        cv.put(EntriesTable.COLUMN_VALUE, entryItem.getValue());
        cv.put(EntriesTable.COLUMN_EXPSAV, entryItem.getExpSav());
        cv.put(EntriesTable.COLUMN_STORE, entryItem.getStore());
        cv.put(EntriesTable.COLUMN_DATES, entryItem.getDateForm());

        db.insert(EntriesTable.TABLE_NAME, null, cv);
    }

    public ArrayList<EntryItem> getAllEntries()
    {
        ArrayList<EntryItem> entryList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EntriesTable.TABLE_NAME, null);
        if (c.moveToFirst())
        {
            do {
                EntryItem entry = new EntryItem();
                entry.setId(c.getInt(c.getColumnIndex(EntriesTable._ID)));
                entry.setTitle(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TITLE)));
                entry.setNotes(c.getString(c.getColumnIndex(EntriesTable.COLUMN_NOTES)));
                entry.setDate(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATE)));
                entry.setTime(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TIME)));
                entry.setMethod(c.getString(c.getColumnIndex(EntriesTable.COLUMN_METHOD)));
                entry.setValue(c.getDouble(c.getColumnIndex(EntriesTable.COLUMN_VALUE)));
                entry.setExpSav(c.getString(c.getColumnIndex(EntriesTable.COLUMN_EXPSAV)));
                entry.setStore(c.getString(c.getColumnIndex(EntriesTable.COLUMN_STORE)));
                entry.setDateForm(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATES)));

                entryList.add(entry);

            } while (c.moveToNext());
        }

        c.close();
        return entryList;
    }

    public EntryItem getEntryWithID(int id)
    {
        EntryItem entry = new EntryItem();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EntriesTable.TABLE_NAME + " WHERE " + EntriesTable._ID + " = " + id, null);
        if (c.moveToFirst())
        {
            do {
                entry.setId(c.getInt(c.getColumnIndex(EntriesTable._ID)));
                entry.setTitle(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TITLE)));
                entry.setNotes(c.getString(c.getColumnIndex(EntriesTable.COLUMN_NOTES)));
                entry.setDate(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATE)));
                entry.setTime(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TIME)));
                entry.setMethod(c.getString(c.getColumnIndex(EntriesTable.COLUMN_METHOD)));
                entry.setValue(c.getDouble(c.getColumnIndex(EntriesTable.COLUMN_VALUE)));
                entry.setExpSav(c.getString(c.getColumnIndex(EntriesTable.COLUMN_EXPSAV)));
                entry.setStore(c.getString(c.getColumnIndex(EntriesTable.COLUMN_STORE)));
                entry.setDateForm(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATES)));

            } while (c.moveToNext());
        }

        c.close();
        return entry;
    }

    public ArrayList<EntryItem> getEntryWithDate(String date)
    {
        ArrayList<EntryItem> entryList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EntriesTable.TABLE_NAME + " WHERE " + EntriesTable.COLUMN_DATES + " = '" + date + "'", null);
        if (c.moveToFirst())
        {
            do {
                EntryItem entry = new EntryItem();
                entry.setId(c.getInt(c.getColumnIndex(EntriesTable._ID)));
                entry.setTitle(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TITLE)));
                entry.setNotes(c.getString(c.getColumnIndex(EntriesTable.COLUMN_NOTES)));
                entry.setDate(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATE)));
                entry.setTime(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TIME)));
                entry.setMethod(c.getString(c.getColumnIndex(EntriesTable.COLUMN_METHOD)));
                entry.setValue(c.getDouble(c.getColumnIndex(EntriesTable.COLUMN_VALUE)));
                entry.setExpSav(c.getString(c.getColumnIndex(EntriesTable.COLUMN_EXPSAV)));
                entry.setStore(c.getString(c.getColumnIndex(EntriesTable.COLUMN_STORE)));
                entry.setDateForm(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATES)));

                entryList.add(entry);

            } while (c.moveToNext());
        }

        c.close();
        return entryList;
    }

    public EntryItem getEntryWithTitleDate(String title, String date)
    {
        EntryItem entry = new EntryItem();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EntriesTable.TABLE_NAME + " WHERE " + EntriesTable.COLUMN_DATES + " = '" + date + "'" + " AND " + EntriesTable.COLUMN_TITLE + " = '" + title + "'", null);
        if (c.moveToFirst())
        {
            do {
                entry.setId(c.getInt(c.getColumnIndex(EntriesTable._ID)));
                entry.setTitle(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TITLE)));
                entry.setNotes(c.getString(c.getColumnIndex(EntriesTable.COLUMN_NOTES)));
                entry.setDate(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATE)));
                entry.setTime(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TIME)));
                entry.setMethod(c.getString(c.getColumnIndex(EntriesTable.COLUMN_METHOD)));
                entry.setValue(c.getDouble(c.getColumnIndex(EntriesTable.COLUMN_VALUE)));
                entry.setExpSav(c.getString(c.getColumnIndex(EntriesTable.COLUMN_EXPSAV)));
                entry.setStore(c.getString(c.getColumnIndex(EntriesTable.COLUMN_STORE)));
                entry.setDateForm(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATES)));

            } while (c.moveToNext());
        }

        c.close();
        return entry;
    }

    public boolean updateEntryWithID(EntryItem entryItem, int id)
    {
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(EntriesTable.COLUMN_TITLE, entryItem.getTitle());
        cv.put(EntriesTable.COLUMN_NOTES, entryItem.getNotes());
        cv.put(EntriesTable.COLUMN_DATE, entryItem.getDate());
        cv.put(EntriesTable.COLUMN_TIME, entryItem.getTime());
        cv.put(EntriesTable.COLUMN_METHOD, entryItem.getMethod());
        cv.put(EntriesTable.COLUMN_VALUE, entryItem.getValue());
        cv.put(EntriesTable.COLUMN_EXPSAV, entryItem.getExpSav());
        cv.put(EntriesTable.COLUMN_STORE, entryItem.getStore());
        cv.put(EntriesTable.COLUMN_DATES, entryItem.getDateForm());

        db.update(EntriesTable.TABLE_NAME, cv, EntriesTable._ID +" = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean updateEntryWithItems(EntryItem entryItem, int id)
    {
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(EntriesTable.COLUMN_TITLE, entryItem.getTitle());
        cv.put(EntriesTable.COLUMN_NOTES, entryItem.getNotes());
        cv.put(EntriesTable.COLUMN_DATE, entryItem.getDate());
        cv.put(EntriesTable.COLUMN_TIME, entryItem.getTime());
        cv.put(EntriesTable.COLUMN_METHOD, entryItem.getMethod());
        cv.put(EntriesTable.COLUMN_VALUE, entryItem.getValue());
        cv.put(EntriesTable.COLUMN_EXPSAV, entryItem.getExpSav());
        cv.put(EntriesTable.COLUMN_STORE, entryItem.getStore());
        cv.put(EntriesTable.COLUMN_DATES, entryItem.getDateForm());
        cv.put(EntriesTable.COLUMN_ITEMS, entryItem.getItems());

        db.update(EntriesTable.TABLE_NAME, cv, EntriesTable._ID +" = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public boolean deleteEntryWithID(int id)
    {
        db = getWritableDatabase();
        db.delete(EntriesTable.TABLE_NAME, EntriesTable._ID +" = ?", new String[]{String.valueOf(id)});
        return true;
    }

    public ArrayList<EntryItem> getEntriesFromLastWeek()
    {
        ArrayList<EntryItem> entryList = new ArrayList<>();

        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + EntriesTable.TABLE_NAME + " WHERE " + EntriesTable.COLUMN_DATES + " BETWEEN GETDATE() AND Date_SUB(GateDate(),Interval + '7' + DAY) ", null);
        if (c.moveToFirst())
        {
            do {
                EntryItem entry = new EntryItem();
                entry.setId(c.getInt(c.getColumnIndex(EntriesTable._ID)));
                entry.setTitle(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TITLE)));
                entry.setNotes(c.getString(c.getColumnIndex(EntriesTable.COLUMN_NOTES)));
                entry.setDate(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATE)));
                entry.setTime(c.getString(c.getColumnIndex(EntriesTable.COLUMN_TIME)));
                entry.setMethod(c.getString(c.getColumnIndex(EntriesTable.COLUMN_METHOD)));
                entry.setValue(c.getDouble(c.getColumnIndex(EntriesTable.COLUMN_VALUE)));
                entry.setExpSav(c.getString(c.getColumnIndex(EntriesTable.COLUMN_EXPSAV)));
                entry.setStore(c.getString(c.getColumnIndex(EntriesTable.COLUMN_STORE)));
                entry.setDateForm(c.getString(c.getColumnIndex(EntriesTable.COLUMN_DATES)));

                entryList.add(entry);

            } while (c.moveToNext());
        }

        c.close();
        return entryList;
    }

}
