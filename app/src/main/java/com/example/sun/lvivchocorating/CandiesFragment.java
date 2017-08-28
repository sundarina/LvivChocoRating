package com.example.sun.lvivchocorating;


import android.app.ListFragment;
import android.content.Context;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;



/**
 * A simple {@link Fragment} subclass.
 */
public class CandiesFragment extends ListFragment {


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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.category));
        setListAdapter(adapter);

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
    public void onListItemClick(ListView l, View v, int position, long id) {
        if (listener != null) {
//              Сообщить слушателю о то м, что
//              на одном из вариантов ListView  был сделан щелчок
            listener.itemClicked(id);
        }
    }
}
