package com.example.sun.lvivchocorating;

/**
 * Created by sun on 13.09.17.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
 * DetailActivity создается прежде всего для вывода названия
 * и изображения продукта, выбранной пользователем. Для этого мы извлекаем
 * идентификатор выбранной пиццы из интента, запустившего активность,
 * и передаем его DetailActivity из MaterialFragment,
 * когда пользователь выбирает один из видов  в RecyclerView.
 */

public class CandyDetailActivity extends Activity {
    ShareActionProvider shareActionProvider;
    //Константа будет использоваться для передачи идентификатора пиццы в дополнительной информации интента.
    public static final String EXTRA_CANDYNO = "candyNo";
    ChocoDatabaseHelper databaseHelper;
    SQLiteDatabase db;
    TextView name;
    TextView description;
    RatingBar ratingBar;
    TextView category;
    ImageView imageView;
    CheckBox favourite;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candy_detail);

        int candyNo = (Integer) getIntent().getExtras().get(EXTRA_CANDYNO);

        //Включение кнопки Вверх
        getActionBar().setDisplayHomeAsUpEnabled(true);

        String nameText;
        String descriptionText;
        String categoryText;
        String photoId;
        boolean isFavourite;
        int ratingNum;

        //Получение candy из интента
        try {
            databaseHelper = new ChocoDatabaseHelper(this);
            databaseHelper.create_db();
            databaseHelper.openDataBase();
            db = databaseHelper.getdb();

            //Создать курсор для получения
            // из таблицы CANDY столбцов NAME, DESCRIPTION, CATEGORY и IMAGE_RESOURCE_ID тех записей,
            // у которых значение _id равно candyNo

            Cursor cursor = db.query("CANDY", new String[]{"NAME", "DESCRIPTION", "CATEGORY", "IMAGE_RESOURCE_ID", "FAVOURITE", "RATING"}, "_id = ?", new String[]{Integer.toString(candyNo)}, null, null, null);
            //Курсор содержит одну запись , но и в этом случае переход необходим
//            if (cursor.moveToFirst()) {


            if (cursor.moveToFirst()) {
             //   do {
                    nameText = cursor.getString(0);
                    descriptionText = cursor.getString(1);
                    categoryText = cursor.getString(2);
                    photoId = cursor.getString(3);
                    isFavourite = (cursor.getInt(4) == 1);
                    ratingNum = cursor.getInt(5);


                    //Заполнение названия напитка
                    name = (TextView) findViewById(R.id.candy_text);
                    name.setText(nameText);

                    //Заполнение изображения напитка
                    int resourceID = this.getResources().getIdentifier(String.valueOf(photoId), "drawable", this.getPackageName());
                    imageView = (ImageView) findViewById(R.id.candy_image);
                    imageView.setImageDrawable(getResources().getDrawable(resourceID));
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

                    Log.v("IM", photoId + "");
           //     } while (cursor.moveToNext());

            }
            //Закрыть курсор и базу данных.
            cursor.close();
            db.close();
        } catch (
                SQLiteException e)

        {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

//
//    //Внутренний класс для обновления конфет.
//    // AsyncTask добавляется в активность в виде внутреннего класса.
//    private class UpdateCandyTask extends AsyncTask<Integer, Void, Boolean> {
//        ContentValues candyValues;
//        int ratingNO = 0;
//
//        /**
//         * Прежде чем запускать код базы данных
//         * на выполнение, добавить значение флажка и рейтинг
//         * в объект ContentValues с именем candyValues.
//         */
//
//        protected void onPreExecute() {
//            CheckBox favourite = (CheckBox) findViewById(R.id.candy_favourite);
//            RatingBar ratingBar = (RatingBar) findViewById(R.id.candy_ratingBar);
//
//            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
//
//                @Override
//                public void onRatingChanged(RatingBar ratingBar, float rating,
//                                            boolean fromUser) {
//                    ratingNO = (int) rating;
//                }
//            });
//
//            candyValues = new ContentValues();
//            //Значение флажка добавляється в обьект ContentValues  с именем candyValues
//            candyValues.put("FAVOURITE", favourite.isChecked());
//            candyValues.put("RATING", ratingNO);
//        }
//
//        /**
//         * Код базы данных запу-
//         * скается на выполнение
//         * в фоновом потоке.
//         */
//        protected Boolean doInBackground(Integer... candies) {
//            int candyNo = candies[0];
//            SQLiteOpenHelper lvivChocoDatabaseHelper = new ChocoDatabaseHelper(CandyDetailActivity.this);
////?????????????????????????????????????????????????????
//            try {
//                SQLiteDatabase db = lvivChocoDatabaseHelper.getWritableDatabase();
//                //Обновить столбец Favorite текущим значением флажка
//                db.update("CANDY", candyValues, "_id = ?", new String[]{Integer.toString(candyNo)});
//                db.close();
//                return true;
//            } catch (SQLiteException e) {
//                return false;
//            }
//        }
//
//        /**
//         * Eсли при выполнении кода базы
//         * данных возникли проблемы,
//         * вывести сообщение для пользователя.
//         */
//        protected void onPostExecute(Boolean success) {
//            if (!success) {
//                Toast toast = Toast.makeText(CandyDetailActivity.this, "Database is unavaliable", Toast.LENGTH_SHORT);
//                toast.show();
//            }
//        }
//    }
//
//    //Обновление базы данных по щелчку на флажке
//    public void onFavouriteClicked(View view) {
//        int candyNo = (Integer) getIntent().getExtras().get("candyNo");
//        new CandyDetailActivity.UpdateCandyTask().execute(candyNo);
//    }
//


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