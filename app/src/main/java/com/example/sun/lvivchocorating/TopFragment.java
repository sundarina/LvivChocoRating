package com.example.sun.lvivchocorating;

import android.app.Fragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class TopFragment extends Fragment {
    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_top, container, false);
    }
}



//
//    //Метод  вызываеться при возвращении пользователя к ТОп
//    @Override
//    public void onRestart() {
//        super.onRestart();
//        //  new RestartTask().execute(favoritesCursor);
//        try{
//            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
//            db = starbuzzDatabaseHelper.getReadableDatabase();
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