package com.example.sun.lvivchocorating;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ChocoDatabaseHelper extends SQLiteOpenHelper {


    private static final String DB_NAME = "lvivchoco";
    private static final int DB_VERSION = 2;


    public ChocoDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }



    /**
     * Метод onCreate() вызывается при создании базы данных;
     * мы используем его для создания таблицы и вставки данных.
     */

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }


    /**
     * Метод onUpgrade() вызывается тогда, когда воз-
     * никает необходимость в обновлении базы данных.
     */

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE CANDY(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, " + "DESCRIPTION TEXT, " + "CATEGORY TEXT, " + "IMAGE_RESOURCE_ID INTEGER);");
            //Вставить данные каждой конфеты в отдельную строку
            insertDrink(db, "Веселі драчки (м'ята)", "Вершкова м'яка карамель з додаванням білого шоколаду з ароматом м'яти у чорному шоколаді", "Шоколадно-карамельні", R.drawable.veseli_drachky_myatni);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE CANDY ADD COLUMN FAVORITE NUMERIC, RATING INTEGER;");
        }
    }

    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, String category, int resourceId) {
        ContentValues candyValues = new ContentValues();
        candyValues.put("NAME", name);
        candyValues.put("DESCRIPTION", description);
        candyValues.put("CATEGORY", category);
        candyValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("CANDY", null, candyValues);
    }
}

