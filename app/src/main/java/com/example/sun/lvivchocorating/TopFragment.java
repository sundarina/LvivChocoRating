package com.example.sun.lvivchocorating;

import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

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
        ListView listFavourites = (ListView) layout.findViewById(R.id.list_favourites);
        try {
            chocoDatabaseHelper = new ChocoDatabaseHelper(getActivity().getApplicationContext());
            chocoDatabaseHelper.create_db();
            chocoDatabaseHelper.openDataBase();
            //получили бд
            db = chocoDatabaseHelper.getdb();

            //????????????????????????????
            //выборка из бд
            cursor = db.query("CANDY", new String[]{"_id", "NAME"}, "FAVOURITE = 1", null, null, null, null);

            CursorAdapter favouriteAdapter = new SimpleCursorAdapter(layout.getContext(),
                    android.R.layout.simple_list_item_1, cursor,
                    new String[]{"NAME"}, new int[]{android.R.id.text1}, 0);
            listFavourites.setAdapter(favouriteAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database is unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        //Переход к CandyDetailActivity при выборе напитка
        //метод вызываеться при выборе варианта спискового представления
        listFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CandyDetailActivity.class);
                intent.putExtra(CandyDetailActivity.EXTRA_CANDYNO, (int) id);
                startActivity(intent);
            }
        });

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



//    //Метод  вызываеться при возвращении пользователя к ТОп
//    @Override
//    public void onRestart() {
//        super.onRestart();
//        //  new RestartTask().execute(favoritesCursor);
//        try{
//            SQLiteOpenHelper chocoDatabaseHelper = new StarbuzzDatabaseHelper(this);
//            db = chocoDatabaseHelper.getReadableDatabase();
//            Cursor newCursor = db.query("CANDY", new String[]{"_id", "NAME"}, "FAVORITE = 1",
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

