package com.example.tiget.databasetest;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class Database {

    private Context mContext;
    private List<StringConstructor> mStrings = new ArrayList<>();
    /**
     * Переменная хранит слушатель изменений в базе данных.
     */
    private ChangeListener mChangeListener = null;
    public Database(Context context) {
        mContext = context;
    }

    /**
     * Метод загружает из локального хранилища сохранённые ранее будильники.
     */
    public void load() {
        // получаем будильники в виде строки
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        final String s = preferences.getString("ALARMS", "");
        // десериализуем строку
        final Gson gson = new Gson();
        mStrings = gson.fromJson(s, new TypeToken<List<StringConstructor>>(){}.getType());
        if (mChangeListener != null) {
            mChangeListener.onChange(mStrings);
        }
    }
    /**
     * Метод сохраняет в локальное хранилище будильники из mAlarms.
     */
    private void save() {
        // открываем для редактирования SP
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        final SharedPreferences.Editor editor = preferences.edit();
        // превращаем в строку (сериализуем)
        final Gson gson = new Gson();
        final String s = gson.toJson(mStrings, new TypeToken<List<StringConstructor>>(){}.getType());
        // сохраняем по ключу ALARMS
        editor.putString("ALARMS", s);
        editor.apply();
    }

    /**
     * Метод добавляет будильник в базу данных (и сохраняет изменения в локальное хранилище).
     * @param stringConstructor Будильник, который нужно добавить.
     */
    public void addAlarm(StringConstructor stringConstructor) {
        mStrings.add(stringConstructor);
        if (mChangeListener != null) {
            mChangeListener.onChange(mStrings);
        }
        save();
    }
    /**
     * Метод удаляет из базы данных будильник по id (и сохраняет изменения в локальное хранилище).
     * @param id Идентификатор будильника, который нужно удалить.
     */
    public void removeAlarm(long id) {
        boolean needsSave = false;
        for (int i = 0; i < mStrings.size(); ++i) {
            if (mStrings.get(i).getId() == id) {
                mStrings.remove(i);
                needsSave = true;
                break;
            }
        }
        if (needsSave) {
            if (mChangeListener != null) {
                mChangeListener.onChange(mStrings);
            }
            save();
        }
    }

    /**
     * Возвращает список будильников.
     */
    public List<StringConstructor> getAlarms() {
        return mStrings;
    }

    /**
     * Очищает будильники (только из оперативной памяти!).
     */
    public void clear() {
        mStrings.clear();
        if (mChangeListener != null) {
            mChangeListener.onChange(mStrings);
        }
    }
    public void setChangeListener(ChangeListener listener) {
        mChangeListener = listener;
    }
    interface ChangeListener {
        /**
         * Будет вызываться после каждого изменения списка будильников.
         * @param stringConstructors Актуальный список будильников.
         */
        void onChange(List<StringConstructor> stringConstructors);
    }
}