package com.example.laion.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Estatisticas_BD extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Estatisticas.db";
    private static final String Estatisticas_TABLE_NAME = "Estatisticas";
    private static final String Estatisticas_COLUMN_ID = "id";
    private static final String Estatisticas_COLUMN_DATA = "data";
    private static final String Estatisticas_COLUMN_PESO = "peso";

    public Estatisticas_BD(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Estatisticas " +
                        "(id integer primary key, data text, peso integer)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Estatisticas");
        onCreate(db);
    }

    public boolean insertEstatistica (String data, int peso) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estatisticas_COLUMN_DATA, data);
        contentValues.put(Estatisticas_COLUMN_PESO, peso);
        db.insert("Estatisticas", null, contentValues);
        return true;
    }

    public Estrutura_Estatisticas getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Estatisticas where id="+id+"", null );
        res.moveToFirst();
        String data = res.getString(res.getColumnIndex(Estatisticas_COLUMN_DATA));
        int peso = res.getInt(res.getColumnIndex(Estatisticas_COLUMN_PESO));

        Estrutura_Estatisticas resultado = new Estrutura_Estatisticas(id, data, peso);

        return resultado;
    }

    public Estrutura_Estatisticas ObterEstatisticasPorData(String data) {

        int total = numberOfRows();
        if(total > 0){
            SQLiteDatabase db = this.getReadableDatabase();
            String[] a = new String[1];
            a[0]       = data;
            Cursor res =  db.rawQuery( "select * from Estatisticas where data = ? ", a );

            if(res.getCount() > 0 && res.moveToFirst()){
                int Id = res.getInt(res.getColumnIndex(Estatisticas_COLUMN_ID));
                String data1 = res.getString(res.getColumnIndex(Estatisticas_COLUMN_DATA));
                int peso = res.getInt(res.getColumnIndex(Estatisticas_COLUMN_PESO));

                return new Estrutura_Estatisticas(Id, data1, peso);
            }else return null;
        } else return null;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, Estatisticas_TABLE_NAME);
        return numRows;
    }

    public boolean updateEstatistica (Integer id, String data, int peso)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Estatisticas_COLUMN_DATA, data);
        contentValues.put(Estatisticas_COLUMN_PESO, peso);
        db.update("Estatisticas", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteEstatistica (String data) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Estatisticas", "data = ? ", new String[] { data });
    }

    public ArrayList<Estrutura_Estatisticas> getAllEstatisticas() {
        ArrayList<Estrutura_Estatisticas> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Estatisticas", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            int Id = res.getInt(res.getColumnIndex(Estatisticas_COLUMN_ID));
            String data1 = res.getString(res.getColumnIndex(Estatisticas_COLUMN_DATA));
            int peso = res.getInt(res.getColumnIndex(Estatisticas_COLUMN_PESO));
            array_list.add(new Estrutura_Estatisticas(Id, data1, peso));
            res.moveToNext();
        }
        return array_list;
    }

    public Estrutura_Estatisticas getLastItem(){
        int total = numberOfRows();
        if(total > 0){
            SQLiteDatabase db = this.getReadableDatabase();
            String selectQuery = "SELECT * FROM Estatisticas ORDER BY Id DESC LIMIT 1";
            Cursor cursor = db.rawQuery(selectQuery, null);
            cursor.moveToLast();
            int Id = cursor.getInt(cursor.getColumnIndex(Estatisticas_COLUMN_ID));
            String data1 = cursor.getString(cursor.getColumnIndex(Estatisticas_COLUMN_DATA));
            int peso = cursor.getInt(cursor.getColumnIndex(Estatisticas_COLUMN_PESO));
            return new Estrutura_Estatisticas(Id, data1, peso);
        }else return null;
    }

}
