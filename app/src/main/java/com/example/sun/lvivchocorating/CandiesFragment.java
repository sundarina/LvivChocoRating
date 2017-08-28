package com.example.sun.lvivchocorating;


import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandiesFragment extends ListFragment {


    private SQLiteDatabase db;
    private Cursor cursor;

    interface CandyListListener {
        void itemClicked(long id);
        //  Добавить слушателя  к фрагменту.
    }


    private CandyListListener listener;

    public CandiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listCandy = getListView();

        try {
            SQLiteOpenHelper candyDatabaseHelper = new ChocoDatabaseHelper(getActivity());
            //Получить ссылку на базу данных.
            db = candyDatabaseHelper.getReadableDatabase();
            //Создать курсор
            cursor = db.query("CANDY", new String[]{"_id", "CATEGORY"}, null, null, null, null, null);
            //Создать адаптер курсора.
            //Связать содержимое столбца CATEGORY с текстом в ListView
            CursorAdapter listAdapter = new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1, cursor, new String[]{"CATEGORY"}, new int[]{android.R.id.text1}, 0);
            listCandy.setAdapter(listAdapter);

        } catch (SQLiteException e) {
            //Вывести сообщение для пользователя, если будет выдано исключение SQLiteException.
            Toast toast = Toast.makeText(getActivity(), "Database is unavaliable", Toast.LENGTH_SHORT);
            toast.show();

//            //адаптер массива
//            //получение контекста от инфлатера
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, names);
//            setListAdapter(adapter);
        }
        //связать адаптер массива со списковым представлением
        //Вызов метода onCreateView() суперкласса предоставляет макет по умолчанию для ListFragment
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    //Вызывается при присоединении фрагмента к активности.
    public void onAttach(Context context) {

        super.onAttach(context);
        this.listener = (CandyListListener) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }


    //переписать
    //

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
            //  Сообщить слушателю о то м, что
            //  на одном из вариантов ListView  был сделан щелчок
                    listener.itemClicked(id);

            Intent intent = new Intent(getActivity(), CandyActivity.class);
            intent.putExtra(CandyActivity.EXTRA_CANDYNO, (int) id);
            startActivity(intent);
        }
    }

}
