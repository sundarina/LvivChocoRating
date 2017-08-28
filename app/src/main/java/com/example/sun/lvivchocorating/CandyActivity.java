package com.example.sun.lvivchocorating;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class CandyActivity extends Activity {
    public static final String EXTRA_CANDYNO = "candyNo";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy);

        int candyNo = (Integer) getIntent().getExtras().get(EXTRA_CANDYNO);

        //Получение напитка из интента
        try {
            SQLiteOpenHelper lvivChocoDatabaseHelper = new ChocoDatabaseHelper(this);
            SQLiteDatabase db = lvivChocoDatabaseHelper.getWritableDatabase();

            //Создать курсор для получения
            // из таблицы CANDY столбцов NAME, DESCRIPTION, CATEGORY и IMAGE_RESOURCE_ID тех записей,
            // у которых значение _id равно candyNo

            Cursor cursor = db.query("CANDY", new String[]{"NAME", "DESCRIPTION", "CATEGORY", "IMAGE_RESOURCE_ID", "FAVORITE", "RATING"}, "_id = ?", new String[]{Integer.toString(candyNo)}, null, null, null);
            //Курсор содержит одну запись , но и в этом случае переход необходим
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                String categoryText = cursor.getString(2);
                int photoId = cursor.getInt(3);
                boolean isFavorite = (cursor.getInt(4) == 1);
                int ratingNum = cursor.getInt(5);


                //Заполнение названия напитка
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);
                //Заполнение описания напитка
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);

                //Заполнение категории
                TextView category = (TextView) findViewById(R.id.category);
                category.setText(categoryText);

                //Заполнение изображения напитка
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                //Заполнение флажка любимого напитка
                CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
                favorite.setChecked(isFavorite);

                //Заполнение рейтингa
                RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                ratingBar.setRating(ratingNum);

            }
            //Закрыть курсор и базу данных.
            cursor.close();
            db.close();
        } catch (SQLiteException e) {

            //Если будет выдано исключение SQLiteException,
            // значит, при работе с базой данных возникли проблемы.
            // В этом случае объект Toast используется для выдачи сообщения для пользователя
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Обновление базы данных по щелчку на флажке
    public void onFavoriteClicked(View view) {
        int candyNo = (Integer) getIntent().getExtras().get("candyNo");
        new UpdateCandyTask().execute(candyNo);
    }

    //Внутренний класс для обновления напитка.AsyncTask добавляется в активность в виде внутреннего класса.
    private class UpdateCandyTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues candyValues;
        int ratingNO = 0;

        /**
         * Прежде чем запускать код базы данных
         * на выполнение, добавить значение флажка и рейтинг
         * в объект ContentValues с именем candyValues.
         */

        protected void onPreExecute() {
            CheckBox favorite = (CheckBox) findViewById(R.id.favorite);
            RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {
                    ratingNO = (int) rating;
                }
            });


            candyValues = new ContentValues();
            //Значение флажка добавляється в обьект ContentValues  с именем candyValues
            candyValues.put("FAVORITE", favorite.isChecked());
            candyValues.put("RATING", ratingNO);
        }

        /**
         * Код базы данных запу-
         * скается на выполнение
         * в фоновом потоке.
         */
        protected Boolean doInBackground(Integer... candies) {
            int candyNo = candies[0];
            SQLiteOpenHelper lvivChocoDatabaseHelper = new ChocoDatabaseHelper(CandyActivity.this);

            try {
                SQLiteDatabase db = lvivChocoDatabaseHelper.getWritableDatabase();
                //Обновить столбец Favorite текущим значением флажка
                db.update("CANDY", candyValues, "_id = ?", new String[]{Integer.toString(candyNo)});
                db.close();
                return true;
            } catch (SQLiteException e) {
                return false;
            }
        }


        /**
         * Eсли при выполнении кода базы
         * данных возникли проблемы,
         * вывести сообщение для пользователя.
         */
        protected void onPostExecute(Boolean success) {
            if (!success) {
                Toast toast = Toast.makeText(CandyActivity.this, "Database is unavaliable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}
