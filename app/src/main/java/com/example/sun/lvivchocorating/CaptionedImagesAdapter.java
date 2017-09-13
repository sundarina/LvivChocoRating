package com.example.sun.lvivchocorating;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

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

    private String[] captions;
    private int[] imageIds;
    private String [] category;
    private int [] rating;
    private Listener listener;

    /**При щелчке на любой из карточек в RecyclerView будет вызываться метод
     onClick() интерфейса Listener. Затем в PizzaMaterialFragment добавляется
     код реализации интерфейса; это позволит фрагменту отреагировать на щелчки
     и запустить активность
     1 Пользователь щелкает на карточке в RecyclerView.
     2 Вызывается метод onClick() интерфейса Listener.
     3  Mетод onClick() реализован в PizzaMaterialFragment.
     Код фрагмента запускает PizzaDetailActivity.
     */

    public static interface  Listener{
        public void onClick(int position);
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

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

        private CardView cardView;

        public ViewHolder(CardView view) {
            super(view);
            cardView = view;
        }
    }

    //Данные передаються через конструктор
    public CaptionedImagesAdapter(String[] captions, int[] imageIds, String[] category, int[] rating) {
        this.captions = captions;
        this.imageIds = imageIds;
        this.category = category;
        this.rating = rating;
    }

    public void setListener(Listener listener){
        //Активности и фрагменты используют этот метод для регистрации себя в качестве слушателя
        this.listener = listener;
    }

    //Создание нового представления
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //какой макет должен истользоваться для ViewHolder
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_captioned_image, parent, false);
        return new ViewHolder(cv);
    }

    //Заполнение заданного представления данными
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Изображение выводится в графическом представлении ImageView.
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.info_image);
        // Drawable drawable = cardView.getResources().getDrawable(imageIds[position]); // deprecated
        //imageView.setImageDrawable(drawable);

        //  Drawable drawable = ContextCompat.getDrawable(getActivity(), imageIds[position]);

        imageView.setImageDrawable(cardView.getResources().getDrawable(imageIds[position]));
        imageView.setContentDescription(captions[position]);

        TextView textView = (TextView) cardView.findViewById(R.id.info_text);
        textView.setText(captions[position]);

        TextView textView1 = (TextView) cardView.findViewById(R.id.info_category);
        textView1.setText(category[position]);

        RatingBar ratingBar = (RatingBar) cardView.findViewById(R.id.info_ratingBar);
        ratingBar.setRating(rating[position]);


        cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if (listener != null){
                    //При щелчке на CardView вызвать метод onClick() интерфейса Listener.
                    listener.onClick(position);
                }
            }
        });
    }


    //Возвращает количество вариантов в наборе данных
    // Длинна массива  = количеству элементов данных в RecyclerView
    @Override
    public int getItemCount() {
        return captions.length;
    }


}
