package com.example.sun.lvivchocorating;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.sun.lvivchocorating.CandyDetailActivity.EXTRA_CANDYNO;


public class CandiesMaterialFragment extends Fragment {

    ArrayList<Candy> candyList;
    ChocoDatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    public CandiesMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        databaseHelper = new ChocoDatabaseHelper(getActivity().getApplicationContext());

        //использование макета
        RecyclerView candyRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_candies_material, container, false);
        candyList = new ArrayList<>();
     //   int candyNo = (Integer) getIntent().getExtras().get(EXTRA_CANDYNO);
        try {
            databaseHelper.create_db();
            databaseHelper.openDataBase();
            db= databaseHelper.getdb();
            cursor = db.query(ChocoDatabaseHelper.TABLE, new String[]{ChocoDatabaseHelper.COLUMN_NAME, ChocoDatabaseHelper.COLUMN_DESCRIPTION, ChocoDatabaseHelper.COLUMN_CATEGORY, ChocoDatabaseHelper.COLUMN_IMAGE_ID, ChocoDatabaseHelper.COLUMN_FAVOURITE, ChocoDatabaseHelper.COLUMN_RATING}, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    String nameText = cursor.getString(0);
                    String description = cursor.getString(1);
                    String categoryText = cursor.getString(2);
                    int photoId = cursor.getInt(3);
                    boolean isFavorite = (cursor.getInt(4) == 1);
                    int ratingNum = cursor.getInt(5);
                    candyList.add(new Candy(nameText, description, categoryText, photoId, isFavorite, ratingNum));
                } while (cursor.moveToNext());
            }

        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(getActivity(), "Database is unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }


        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(candyList);
        candyRecycler.setAdapter(adapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        candyRecycler.setLayoutManager(gridLayoutManager);

        /**Реализация метода onClick()
         интерфейса Listener запускает
         активность CandyDetailActivity,
         передавая ей идентификатор конфеты, выбранной пользователем */

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), CandyDetailActivity.class);
                intent.putExtra(EXTRA_CANDYNO, position);
                getActivity().startActivity(intent);
            }
        });
        return candyRecycler;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        db.close();
        cursor.close();
    }
}
