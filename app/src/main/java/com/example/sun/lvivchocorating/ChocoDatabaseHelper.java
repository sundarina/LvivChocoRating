package com.example.sun.lvivchocorating;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class ChocoDatabaseHelper extends SQLiteOpenHelper {

    private static String DB_PATH = null; // полный путь к базе данных
    private static String DB_NAME = "lvivchoco.db";
    private static final int SCHEMA = 1; // версия базы данных
    static final String TABLE = "CANDY"; // название таблицы в бд
    static final String COLUMN_ID = "_id";
    static final String COLUMN_NAME = "NAME";
    static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    static final String COLUMN_CATEGORY = "CATEGORY";
    static final String COLUMN_IMAGE_ID = "IMAGE_RESOURCE_ID";
    static final String COLUMN_FAVOURITE = "FAVOURITE";
    static final String COLUMN_RATING = "RATING";
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    ChocoDatabaseHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext = context;
        DB_PATH = context.getFilesDir().getPath() + DB_NAME;
        Log.e("Path 1", DB_PATH);
        create_db();
        openDataBase();
    }

    void create_db() {

        boolean dbExist = checkDataBase();
        if (dbExist) {
            //ничего не делать , если база уже есть
        } else {
            this.getWritableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }


    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

//    public void insertCandy(SQLiteDatabase db, String name,
//                            String description, String category, int resourceId, int favourite, int rating) throws SQLException {
//        ContentValues candyValues = new ContentValues();
//        candyValues.put("NAME", name);
//        candyValues.put("DESCRIPTION", description);
//        candyValues.put("CATEGORY", category);
//        candyValues.put("IMAGE_RESOURCE_ID", resourceId);
//        candyValues.put("FAVOURITE", favourite);
//        candyValues.put("RATING", rating);
//        db.insert("CANDY", null, candyValues);
//    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }

    /**
     * Метод onCreate() вызывается при создании базы данных;
     * мы используем его для создания таблицы и вставки данных.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    /**
     * Метод onUpgrade() вызывается тогда, когда воз-
     * никает необходимость в обновлении базы данных.
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

//    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
//        return myDataBase.query("CANDY", null, null, null, null, null, null);
//    }


    public SQLiteDatabase getdb() {
        return myDataBase;
    }
}

