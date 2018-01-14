package com.example.sun.lvivchocorating;



import android.app.Fragment;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static com.example.sun.lvivchocorating.CandyDetailActivity.EXTRA_CANDYNO;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {
    Cursor cursor;
    SQLiteDatabase db;
    ChocoDatabaseHelper chocoDatabaseHelper;
    List<Candy> candyFavouriteList;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_favourite, container, false);
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_favourite, container, false);
        //использование макета
//        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.recycle_item, container, false);
//
//        candyFavouriteList = new ArrayList<>();
//
//
//        try {
//            chocoDatabaseHelper = new ChocoDatabaseHelper(getActivity().getApplicationContext());
//            //получили бд
//            db = chocoDatabaseHelper.getdb();
//            //выборка из бд
//            cursor = db.query("CANDY", new String[]{"NAME", "CATEGORY", "IMAGE_RESOURCE_ID", "FAVOURITE", "RATING"}, "FAVOURITE = 1", null, null, null, null);
//
//            String nameText;
//            String description;
//            String categoryText;
//            String photoId;
//            boolean isFavorite;
//            int ratingNum;
//
//            if (cursor.moveToFirst()) {
//                do {
//                    nameText = cursor.getString(0);
//                    description = cursor.getString(1);
//                    categoryText = cursor.getString(2);
//                    photoId = cursor.getString(3);
//                    isFavorite = (cursor.getInt(4) == 1);
//                    ratingNum = cursor.getInt(5);
//                    candyFavouriteList.add(new Candy(nameText, description, categoryText, photoId, isFavorite, ratingNum));
//                } while (cursor.moveToNext());
//            }
//
//        } catch (SQLiteException e) {
//            Toast toast = Toast.makeText(getActivity(), "Database is unavailable", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//
//
//
//        FavouriteImageAdapter favouriteImageAdapter = new FavouriteImageAdapter(getActivity().getApplicationContext(), candyFavouriteList);
//
//        recyclerView.setLayoutManager(new VegaLayoutManager());
//        RecyclerView.Adapter adapter = favouriteImageAdapter.getAdapter();
//        recyclerView.setAdapter(adapter);
//
        ListView listFavourites = (ListView) layout.findViewById(R.id.list_favour);
        try {
            chocoDatabaseHelper = new ChocoDatabaseHelper(getActivity().getApplicationContext());


            //получили бд
            db = chocoDatabaseHelper.getdb();
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


        //Переход к CandyDetailActivity при выборе конфеты
        //метод вызываеться при выборе варианта спискового представления
        listFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), CandyDetailActivity.class);
                intent.putExtra(CandyDetailActivity.EXTRA_CANDYNO, (int) id);
                startActivity(intent);
            }
        });

        /**Реализация метода onClick()
         интерфейса ListenerFavourite запускает
         активность CandyDetailActivity,
         передавая ей идентификатор конфеты, выбранной пользователем */

//        adapter.setListenerFavourite(new FavouriteImageAdapter.ListenerFavourite() {
//            @Override
//            public void onClick(int position) {
//                Intent intent = new Intent(getActivity(), CandyDetailActivity.class);
//                intent.putExtra(EXTRA_CANDYNO, position);
//                getActivity().startActivity(intent);
//            }
//        });


      //  return candyRecycler;

        return layout;
    }

//    //Метод  вызываеться при возвращении пользователя к ТОп
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        //  new RestartTask().execute(favoritesCursor);
//        try {
//            SQLiteOpenHelper chocoDatabaseHelper = new ChocoDatabaseHelper(getActivity().getApplicationContext());
//            db = chocoDatabaseHelper.getReadableDatabase();
//            Cursor newCursor = db.query("CANDY", new String[]{"_id", "NAME"}, "FAVORITE = 1",
//                    null, null, null, null);
//
//            ListView listFavorite = (ListView) getView().findViewById(R.id.list_favour);
//            //Получить адаптер спискового представления
//            CursorAdapter adapter = (CursorAdapter) listFavorite.getAdapter();
//            //Заменить курсор, используемый адаптером, на новый
//            adapter.changeCursor(newCursor);
//            cursor = newCursor;
//
//        } catch (SQLiteException e) {
//            Toast toast = Toast.makeText(getActivity(), "Database is unavalieble 2", Toast.LENGTH_SHORT);
//            toast.show();
//        }
//    }


    //Закрытие курсора и базы данных в методе onDestroy()
    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

}
