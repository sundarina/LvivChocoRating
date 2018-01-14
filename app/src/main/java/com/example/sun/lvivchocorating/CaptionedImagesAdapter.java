package com.example.sun.lvivchocorating;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * RecyclerView не работает со встроенными
 * адаптерами — такими, как адаптеры массивов или курсоров. Вместо этого вам при-
 * дется создать собственный адаптер, расширяющий класс RecyclerView.Adapter.
 * Адаптер выполняет две основные функции: он создает все представления, видимые
 * в RecyclerView, и связывает каждое представление с определенным блоком данных.
 * 1.Указать тип данных, с которыми должен работать адаптер.
 * В нашем примере адаптер должен использовать карточки, каждая
 * из которых содержит изображение и текст.
 * 2 Создать представления.
 * Адаптер должен создать все представления, отображаемые на экране.
 * 3 Связать данные с представлениями.
 * Адаптер должен заполнить данными каждое представление,
 * когда оно становится видимым
 */

public class CaptionedImagesAdapter extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private Context context;
    private Listener listener;
    private ArrayList<Candy> candyArrayList;

    /**
     * При щелчке на любой из карточек в RecyclerView будет вызываться метод
     * onClick() интерфейса ListenerFavourite. Затем в PizzaMaterialFragment добавляется
     * код реализации интерфейса; это позволит фрагменту отреагировать на щелчки
     * и запустить активность
     * 1 Пользователь щелкает на карточке в RecyclerView.
     * 2 Вызывается метод onClick() интерфейса ListenerFavourite.
     * 3  Mетод onClick() реализован в PizzaMaterialFragment.
     * Код фрагмента запускает PizzaDetailActivity.
     */

    public interface Listener {
        void onClick(int position);
    }


    /**
     * ViewHolder предоставляет ссылку на представление (или
     * представления) каждого варианта данных в RecyclerView; это
     * своего рода «ячейка» для размещения отображаемых данных
     * В нашем компоненте RecyclerView
     * должны отображаться карточки, поэ-
     * тому мы указываем, что ViewHolder
     * содержит представления CardView.
     * Если вы захотите отображать
     * в  RecyclerView данные другого типа,
     * определите их здесь.
     */
     static class ViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }

     CaptionedImagesAdapter(Context context, List<Candy> candyList) {
        this.candyArrayList = (ArrayList<Candy>) candyList;
        this.context = context;
    }

    void setListener(Listener listener) {
        //Активности и фрагменты используют этот метод для регистрации себя в качестве слушателя
        this.listener = listener;
    }

    //Создание нового представления
    @Override
    public CaptionedImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //какой макет должен истользоваться для ViewHolder
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_captioned_image, parent, false);

        final ViewHolder holder = new ViewHolder(cardView);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    final int adapterPosition = holder.getAdapterPosition();
                    //При щелчке на CardView вызвать метод onClick() интерфейса ListenerFavourite.
                   // if (adapterPosition != RecyclerView.NO_POSITION)
                        listener.onClick(adapterPosition+1);
                }
            }
        });

        return holder;
    }

    //Заполнение заданного представления данными
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Изображение выводится в графическом представлении ImageView.
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);

        int resourceID = context.getResources().getIdentifier(candyArrayList.get(position).getImageId(), "drawable", context.getPackageName());

        /**setImageDrawable получает картинку drawable = cardView.getResources().getDrawable(imageIds[position])
         //imageView.setImageDrawable(cardView.getResources().getDrawable(resourceID));*/

      //  imageView.setImageDrawable(cardView.getResources().getDrawable(resourceID));
        Drawable drawable = ContextCompat.getDrawable(this.context, resourceID);
        imageView.setImageDrawable(drawable);

        imageView.setContentDescription(candyArrayList.get(position).getName());

        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(candyArrayList.get(position).getName());

        TextView textView1 = (TextView) cardView.findViewById(R.id.info_category);
        textView1.setText(candyArrayList.get(position).getCategory());

        CheckBox favorite = (CheckBox) cardView.findViewById(R.id.info_favourite);
        favorite.setChecked(candyArrayList.get(position).isFavourite());

        RatingBar ratingBar = (RatingBar) cardView.findViewById(R.id.info_ratingBar);
        ratingBar.setRating(candyArrayList.get(position).getRating());
        ratingBar.isIndicator();

//        cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (listener != null) {
//                    //При щелчке на CardView вызвать метод onClick() интерфейса ListenerFavourite.
//                    listener.onClick(position+1);
//                }
//            }
//        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    //Возвращает количество вариантов в наборе данных
    // Длинна массива  = количеству элементов данных в RecyclerView
    @Override
    public int getItemCount() {
        return candyArrayList.size();
    }
}


