package com.example.sun.lvivchocorating;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;


public class MainActivity extends Activity {

    private String[] titles;
    private ListView drawerList;
    private ShareActionProvider shareActionProvider;
    private DrawerLayout drawerLayout;
    //Класс ActionBarDrawerToggle позволяет использовать
    // кнопку Вверх на панели действий для открытия
    // и закрытия выдвижной панели

    private ActionBarDrawerToggle drawerToggle;
    private int currentPosition = 0;


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles = getResources().getStringArray(R.array.titles);
        drawerList = (ListView) findViewById(R.id.drawer);
        //Получить ссылку на DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //Для заполненияListView используется класс ArrayAdapter.
        drawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, titles));
        //Добавить новый экземпляр OnItemClickListener к списковому представлению выдвижной панели
        drawerList.setOnItemClickListener(new DrawerItemClickListener());


        //Если активность была уничтoжена и создается заново,
        // взять  значение currentPosition из предыдущего состояния активности
        // и использовать его для назначения заголовка панели действий
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            //Если активность только что создана, использовать TopFragment.
            selectItem(0);
        }

        //Создание ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open_drawer, R.string.close_drawer) {
            //Вызывать invalidateOptionsMenu()
            // при открытии или закрытии выдвижной панели

            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);

        //Включить кнопку Ввверх, чтобы она могла использоваться
        // обьетом ActionBarDrawerToggle.

        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    //Вызывается при изменении стека возврата.
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragMan = getFragmentManager();
                        Fragment fragment = fragMan.findFragmentByTag("visible_fragment");

                        //Проверить, экземпляром какого класса является фрагмент,
                        // связанный с активностью, и присвоить currentPosition
                        // соответствующее значение.

                        if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                        }

                        if (fragment instanceof CandiesMaterialFragment) {
                            currentPosition = 1;
                        }

                        if (fragment instanceof ContactsFragment) {
                            currentPosition = 2;
                        }

                        setActionBarTitle(currentPosition);
                        //Вывести текст на панели действий
                        // и выделить правильный вариант в списке на выдвижной панели.
                        drawerList.setItemChecked(currentPosition, true);
                    }
                }
        );
    }


    private void selectItem(int position) {
        //Обновить currentPosition при выборе варианта.
        currentPosition = position;
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new CandiesMaterialFragment();
                break;
            case 2:
                fragment = new ContactsFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        //Для замены текущего фрагмента используется транзакция фрaгмента.
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        //Вызвать метод setActionTitle()
        // и передать ему позицию варианта, на котором был сдела щелчек
        setActionBarTitle(position);

        //drawerList — выдвижная панель, связанная с DrawerLayout.
        // Вызов приказывает объекту DrawerLayout закрыть панель drawerList.
        drawerLayout.closeDrawer(drawerList);
    }


    //Вызывается при каждом вызове invalidateOptionsMenu()

    //Синхронизировать состояние ActionBarDrawerToggle с состоянием выдвижной панели
    @Override
    protected void onPostCreate(Bundle saveInstatseState) {
        super.onPostCreate(saveInstatseState);
        drawerToggle.syncState();
    }

    //newConfig для передачи состояния о любых изменеиях конфигурации ActionBarDrawerToggle

    @Override

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }


    private void setActionBarTitle(int position) {
        String title;

        //Если пользователь выбрал вариант “Home”,
        // в качестве текста заголовка используется имя приложения.
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            //В противном случае получить из массива
            // titles строку, соответствующую позиции
            // выбранного варианта, и использовать ее.
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Если выдвижная панель открыта, скрыть элементы, связанные с контентом
        //Задать видимость действия Share при открытии и закрытии выдвижной панели
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("I love ChocoRate");
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Чтобы объект ActionBarDrawerToggle реагирвал на щелчки

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
    }

}
