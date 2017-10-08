package com.example.sun.lvivchocorating;

import android.app.Fragment;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.io.IOException;

public class TopFragment extends Fragment {

    Cursor cursor;
    SQLiteDatabase db;
    ChocoDatabaseHelper chocoDatabaseHelper;

    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_top, container, false);
        ListView listFavotites = (ListView) layout.findViewById(R.id.list_favorites);
        try {
            chocoDatabaseHelper = new ChocoDatabaseHelper(getActivity().getApplicationContext());
            chocoDatabaseHelper.create_db();
            chocoDatabaseHelper.openDataBase();



           // cursor = db.query("CANDY", new String[]{"_id", "NAME"}, null, null, null, null, null);

            cursor = chocoDatabaseHelper.query("CANDY", new String[]{"_id", "NAME"}, null, null, null, null, null);

//            if (c.moveToFirst()) {
//                do {
//                    Toast.makeText(CopyDbActivity.this,
//                            "_id: " + c.getString(0) + "\n" +
//                                    "E_NAME: " + c.getString(1) + "\n" +
//                                    "E_AGE: " + c.getString(2) + "\n" +
//                                    "E_DEPT:  " + c.getString(3),
//                            Toast.LENGTH_LONG).show();
//                } while (c.moveToNext());
//            }

            CursorAdapter favoriteAdapter = new SimpleCursorAdapter(layout.getContext(),
                    android.R.layout.simple_list_item_1, cursor,
                    new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            listFavotites.setAdapter(favoriteAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database is unavalieble", Toast.LENGTH_SHORT);
            toast.show();
        }
        return layout;
    }


    //Закрытие курсора и базы данных в методе onDestroy()
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
//    //Переход к DrinkActivity при выборе напитка
//    //метод вызываеться при выборе варианта спискового представления
//        listFavotites.setOnItemClickListener(new AdapterView.OnItemClickListener()
//
//    {
//        @Override
//        public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
//        Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
//
//        //Если пользователь выбирает один из вариантов спискового представления любимых напитков,
//        // создать интент для запуска DrinkActivity и передать id  напитка
//
//        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
//        startActivity(intent);
//    }
//    });


//
//    //Метод  вызываеться при возвращении пользователя к ТОп
//    @Override
//    public void onRestart() {
//        super.onRestart();
//        //  new RestartTask().execute(favoritesCursor);
//        try{
//            SQLiteOpenHelper chocoDatabaseHelper = new StarbuzzDatabaseHelper(this);
//            db = chocoDatabaseHelper.getReadableDatabase();
//            Cursor newCursor = db.query("DRINK", new String[]{"_id", "NAME"}, "FAVORITE = 1",
//                    null, null, null, null);
//            ListView listFavorite = (ListView) findViewById(R.id.list_favorites);
//            //Получить адаптер спискового представления
//            CursorAdapter adapter = (CursorAdapter) listFavorite.getAdapter();
//            //Заменить курсор, используемый адаптером, на новый
//            adapter.changeCursor(newCursor);
//            favoritesCursor = newCursor;
//
//        }catch (SQLiteException e){
//            Toast toast = Toast.makeText(this, "Database is unavalieble", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//
//    }