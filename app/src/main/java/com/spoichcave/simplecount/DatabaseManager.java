package com.spoichcave.simplecount;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Counter.db";
    private static final int DATABASE_VERSION = 2;

    public DatabaseManager(Context context){
        super( context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String strSql = "create table T_Counters (" +
                        "   id integer primary key autoincrement," +
                        "   name text,"+
                        "   value integer"+
                        ")";
        sqLiteDatabase.execSQL(strSql);
        Log.i("DATABASE", "database created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i("DATABASE","database upgraded");
        String strSql = "DROP TABLE T_Counters";
        sqLiteDatabase.execSQL(strSql);
        onCreate(sqLiteDatabase);
    }

    private void insertNewCounter(Counter counter){
        Log.i("DATABASE","database insertion");
        String name = counter.getName().replace("'", "''");
        String strSql = "insert into T_Counters (name, value) values ("+
                        "   '" + name + "'," + counter.getValue() + ")";
        this.getWritableDatabase().execSQL(strSql);
    }

    private int lastInsertedId(){
        String strSql = "SELECT last_insert_rowid();";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        int id = cursor.getInt(0);
        cursor.close();
        return id;
    }

    private void updateCounter(Counter counter){
        String name = counter.getName().replace("'", "''");
        String strSql = "UPDATE T_Counters SET "+
                        "name = '" + name + "', value = " + counter.getValue() +
                        " WHERE id = " + counter.getId();
        this.getWritableDatabase().execSQL(strSql);
    }

    public void insertCounter(Counter counter){
        if(counter.getId() < 0){
            insertNewCounter(counter);
            counter.setId(lastInsertedId());
        } else {
            updateCounter(counter);
        }
    }

    public void deleteCounter(Counter counter){
        if(counter.getId() > 0) {
            String strSql = "DELETE FROM T_Counters WHERE id = " + counter.getId();
            this.getWritableDatabase().execSQL(strSql);
            counter.setId(-1);
        }
    }

    public List<Counter> fetchAllCounter(){
        List<Counter> counterList = new ArrayList<>();
        String strSql = "select * from T_Counters";
        Cursor cursor = this.getReadableDatabase().rawQuery(strSql, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Counter counter = new Counter(cursor.getInt(0), cursor.getString(1), cursor.getInt(2));
            counterList.add(counter);
            cursor.moveToNext();
        }
        cursor.close();

        return counterList;
    }

    public void resetCounters(){
        String strSql = "DELETE FROM T_Counters";
        this.getWritableDatabase().execSQL(strSql);
    }
}
