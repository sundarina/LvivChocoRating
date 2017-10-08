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
        // this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }




    void create_db() {

        boolean dbExist = checkDataBase();
        if (dbExist) {
            //ничего не делать , если база уже есть
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }
//        InputStream myInput = null;
//        OutputStream myOutput = null;
//        try {
//            File file = new File(DB_PATH);
//            if (!file.exists()) {
//                this.getReadableDatabase();
//                //получаем локальную бд как поток
//                myInput = myContext.getAssets().open(DB_NAME);
//                // Путь к новой бд
//                String outFileName = DB_PATH;
//
//                // Открываем пустую бд
//                myOutput = new FileOutputStream(outFileName);
//
//                // побайтово копируем данные
//                byte[] buffer = new byte[1024];
//                int length;
//                while ((length = myInput.read(buffer)) > 0) {
//                    myOutput.write(buffer, 0, length);
//                }
//
//                myOutput.flush();
//                myOutput.close();
//                myInput.close();
//            }
//        } catch (IOException ex) {
//            Log.d("DatabaseHelper", ex.getMessage());
//        }


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
        byte[] buffer = new byte[10];
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

//    public SQLiteDatabase open() throws SQLException {
//
//        return SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
//    }


//    public ChocoDatabaseHelper(Context context) {
//        super(context, DB_NAME, null, DB_VERSION);
//    }

    /**
     * Метод onCreate() вызывается при создании базы данных;
     * мы используем его для создания таблицы и вставки данных.
     */


    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("CANDY", null, null, null, null, null, null);
    }


//    @Override
//    public void onCreate(SQLiteDatabase db) {
////        updateMyDatabase(db, 0, DB_VERSION);
//    }
//

    /**
     * Метод onUpgrade() вызывается тогда, когда воз-
     * никает необходимость в обновлении базы данных.
     */

//    @Override
//    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
////        updateMyDatabase(db, oldVersion, newVersion);
//    }

//    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
//        if (oldVersion < 1) {
//            db.execSQL("CREATE TABLE CANDY(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
//                    + "NAME TEXT, " + "DESCRIPTION TEXT, " + "CATEGORY TEXT, " + "IMAGE_RESOURCE_ID INTEGER);");
//            //Вставить данные каждой конфеты в отдельную строку
//            insertCandy(db, "Веселі драчки (м'ята)", "Вершкова м'яка карамель з додаванням білого шоколаду з ароматом м'яти у чорному шоколаді", "Шоколадно-карамельні", R.drawable.veseli_drachky_myatni);
//            //   this.onCreate(db);
//        }
//        if (oldVersion < 2) {
//            db.execSQL("ALTER TABLE CANDY ADD COLUMN FAVORITE NUMERIC," + " RATING INTEGER;");
//            //  this.onCreate(db);
//        }
//    }


}

