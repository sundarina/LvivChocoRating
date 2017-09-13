package com.example.sun.lvivchocorating;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class CandiesMaterialFragment extends Fragment {


    public CandiesMaterialFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //использование макета
        RecyclerView candyRecycler;
        candyRecycler = (RecyclerView) inflater.inflate(R.layout.fragment_candies_material, container, false);

        //название пиццы добавляеться в массив строк, а изображения - в массив с элементами те
        String[] pizzaNames = new String[Pizza.pizzas.length];
        for (int i = 0; i < pizzaNames.length; i++) {
            pizzaNames[i] = Pizza.pizzas[i].getName();
        }

        int[] pizzaImages = new int[Pizza.pizzas.length];
        for (int i = 0; i < pizzaImages.length; i++) {
            pizzaImages[i] = Pizza.pizzas[i].getImageResourceId();
        }

        CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(pizzaNames, pizzaImages);
        candyRecycler.setAdapter(adapter);
        //чтобы карточка отображалась в линейном списке
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        candyRecycler.setLayoutManager(layoutManager);

        /**Реализация метода onClick()
         интерфейса Listener запускает
         активность PizzaDetailActivity,
         передавая ей идентификатор пиццы, выбранной пользователем */

        adapter.setListener(new CaptionedImagesAdapter.Listener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getActivity(), PizzaDetailActivity.class);
                intent.putExtra(PizzaDetailActivity.EXTRA_PIZZANO, position);
                getActivity().startActivity(intent);
            }
        });
        return candyRecycler; }

}
