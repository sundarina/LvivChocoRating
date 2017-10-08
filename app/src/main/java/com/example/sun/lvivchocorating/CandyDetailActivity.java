package com.example.sun.lvivchocorating;

/**
 * Created by sun on 13.09.17.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

/**
 * PizzaDetailActivity создается прежде всего для вывода названия
 * и изображения пиццы, выбранной пользователем. Для этого мы извлекаем
 * идентификатор выбранной пиццы из интента, запустившего активность,
 * и передаем его PizzaDetailActivity из PizzaMaterialFragment,
 * когда пользователь выбирает один из видов пиццы в RecyclerView.
 */

public class CandyDetailActivity extends Activity {
    ShareActionProvider shareActionProvider;
    //Константа будет использоваться для передачи идентификатора пиццы в дополнительной информации интента.
    public static final String EXTRA_CANDYNO = "candyNo";
    TextView name;
    TextView description;
    RatingBar ratingBar;
    TextView category;
    ImageView imageView;
    CheckBox favourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy_detail);

        int candyNo = (Integer) getIntent().getExtras().get(EXTRA_CANDYNO);

        //Включение кнопки Вверх
        getActionBar().setDisplayHomeAsUpEnabled(true);


        //Получение candy из интента
        try {
            SQLiteOpenHelper lvivChocoDatabaseHelper = new ChocoDatabaseHelper(this);
            SQLiteDatabase db = lvivChocoDatabaseHelper.getWritableDatabase();

            //Создать курсор для получения
            // из таблицы CANDY столбцов NAME, DESCRIPTION, CATEGORY и IMAGE_RESOURCE_ID тех записей,
            // у которых значение _id равно candyNo

            Cursor cursor = db.query("CANDY", new String[]{"NAME", "DESCRIPTION", "CATEGORY", "IMAGE_RESOURCE_ID", "FAVOURITE", "RATING"}, "_id = ?", new String[]{Integer.toString(candyNo)}, null, null, null);
            //Курсор содержит одну запись , но и в этом случае переход необходим
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                String categoryText = cursor.getString(2);
                int photoId = cursor.getInt(3);
                boolean isFavourite = (cursor.getInt(4) == 1);
                int ratingNum = cursor.getInt(5);

                //Заполнение названия напитка
                name = (TextView) findViewById(R.id.candy_text);
                name.setText(nameText);


                //???????????????????
                //Заполнение изображения напитка
                imageView = (ImageView) findViewById(R.id.candy_image);
                imageView.setImageDrawable(getResources().getDrawable(photoId));
                imageView.setContentDescription(nameText);

                description = (TextView) findViewById(R.id.candy_description);
                //Заполнение флажка любимого напитка
                //Заполнение описания напитка
                description.setText(descriptionText);

                //Заполнение категории
                category = (TextView) findViewById((R.id.candy_category));
                category.setText(categoryText);


                favourite = (CheckBox) findViewById(R.id.candy_favourite);
                favourite.setChecked(isFavourite);

                //Заполнение рейтингa
                ratingBar = (RatingBar) findViewById(R.id.candy_ratingBar);
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


    //Внутренний класс для обновления конфет.
    // AsyncTask добавляется в активность в виде внутреннего класса.
    private class UpdateCandyTask extends AsyncTask<Integer, Void, Boolean> {
        ContentValues candyValues;
        int ratingNO = 0;

        /**
         * Прежде чем запускать код базы данных
         * на выполнение, добавить значение флажка и рейтинг
         * в объект ContentValues с именем candyValues.
         */

        protected void onPreExecute() {
            CheckBox favourite = (CheckBox) findViewById(R.id.candy_favourite);
            RatingBar ratingBar = (RatingBar) findViewById(R.id.candy_ratingBar);

            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating,
                                            boolean fromUser) {
                    ratingNO = (int) rating;
                }
            });

            candyValues = new ContentValues();
            //Значение флажка добавляється в обьект ContentValues  с именем candyValues
            candyValues.put("FAVOURITE", favourite.isChecked());
            candyValues.put("RATING", ratingNO);
        }

        /**
         * Код базы данных запу-
         * скается на выполнение
         * в фоновом потоке.
         */
        protected Boolean doInBackground(Integer... candies) {
            int candyNo = candies[0];
            SQLiteOpenHelper lvivChocoDatabaseHelper = new ChocoDatabaseHelper(CandyDetailActivity.this);
//?????????????????????????????????????????????????????
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
                Toast toast = Toast.makeText(CandyDetailActivity.this, "Database is unavaliable", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    //Обновление базы данных по щелчку на флажке
    public void onFavouriteClicked(View view) {
        int candyNo = (Integer) getIntent().getExtras().get("candyNo");
        new CandyDetailActivity.UpdateCandyTask().execute(candyNo);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        //Использование названия конфеты в действии Share
        TextView textView = (TextView) findViewById(R.id.candy_text);
        CharSequence candyName = textView.getText();
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, candyName);
        //Назначить текст по умолчанию для действия Share.
        shareActionProvider.setShareIntent(intent);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                //Запустить OrderActivity, когда пользователь выбирает элемент на панели действий
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}