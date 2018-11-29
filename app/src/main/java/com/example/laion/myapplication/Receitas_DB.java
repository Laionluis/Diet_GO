package com.example.laion.myapplication;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class Receitas_DB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Receitas.db";
    private static final String RECEITAS_TABLE_NAME = "RECEITAS";
    private static final String RECEITAS_COLUMN_ID = "id";
    private static final String RECEITAS_COLUMN_NAME = "name";
    private static final String RECEITAS_COLUMN_DATA = "data";
    private static final String RECEITAS_COLUMN_QUANTIDADE = "quantidade";
    private static final String RECEITAS_COLUMN_MEDIDA = "um";
    private static final String RECEITAS_COLUMN_HORA = "horario";
    private static final String RECEITAS_COLUMN_OBJETIVO = "objetivo";
    private static final String RECEITAS_COLUMN_COMIDA1 = "comida1";
    private static final String RECEITAS_COLUMN_COMIDA2 = "comida2";
    private static final String RECEITAS_COLUMN_COMIDA3 = "comida3";


    private HashMap hp;

    public Receitas_DB(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table RECEITAS " +
                        "(id integer primary key, name text, data text, quantidade integer, " +
                        "um text, horario text, objetivo text, comida1 text, comida2 text, comida3 text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS RECEITAS");
        onCreate(db);
    }

    public boolean insertReceita (String name, String data, int quantidade, String um, String horario, String objetivo, String comida1, String comida2, String comida3) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECEITAS_COLUMN_NAME, name);
        contentValues.put(RECEITAS_COLUMN_DATA, data);
        contentValues.put(RECEITAS_COLUMN_QUANTIDADE, quantidade);
        contentValues.put(RECEITAS_COLUMN_MEDIDA, um);
        contentValues.put(RECEITAS_COLUMN_HORA, horario);
        contentValues.put(RECEITAS_COLUMN_OBJETIVO, objetivo);
        contentValues.put(RECEITAS_COLUMN_COMIDA1, comida1);
        contentValues.put(RECEITAS_COLUMN_COMIDA2, comida2);
        contentValues.put(RECEITAS_COLUMN_COMIDA3, comida3);
        db.insert("RECEITAS", null, contentValues);
        return true;
    }

    public Estrutura_Receita getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from RECEITAS where id="+id+"", null );
        res.moveToFirst();
        String name = res.getString(res.getColumnIndex(RECEITAS_COLUMN_NAME));
        String data = res.getString(res.getColumnIndex(RECEITAS_COLUMN_DATA));
        int quantidade = res.getInt(res.getColumnIndex(RECEITAS_COLUMN_QUANTIDADE));
        String um = res.getString(res.getColumnIndex(RECEITAS_COLUMN_MEDIDA));
        String horario = res.getString(res.getColumnIndex(RECEITAS_COLUMN_HORA));
        String objetivo = res.getString(res.getColumnIndex(RECEITAS_COLUMN_OBJETIVO));
        String comida1 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA1));
        String comida2 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA2));
        String comida3 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA3));

        Estrutura_Receita resultado = new Estrutura_Receita(id, name, data, quantidade, horario, um, objetivo, comida1, comida2, comida3);

        return resultado;
    }

    public ArrayList<Estrutura_Receita> ObterReceitasPorNome(String nome) {
        ArrayList<Estrutura_Receita> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] a = new String[1];
        a[0]       = "%"+nome+"%";
        Cursor res =  db.rawQuery( "select * from RECEITAS where name LIKE ? ", a );
        res.moveToFirst();

        while(!res.isAfterLast()){
            int Id = res.getInt(res.getColumnIndex(RECEITAS_COLUMN_ID));
            String name = res.getString(res.getColumnIndex(RECEITAS_COLUMN_NAME));
            String data = res.getString(res.getColumnIndex(RECEITAS_COLUMN_DATA));
            int quantidade = res.getInt(res.getColumnIndex(RECEITAS_COLUMN_QUANTIDADE));
            String um = res.getString(res.getColumnIndex(RECEITAS_COLUMN_MEDIDA));
            String horario = res.getString(res.getColumnIndex(RECEITAS_COLUMN_HORA));
            String objetivo = res.getString(res.getColumnIndex(RECEITAS_COLUMN_OBJETIVO));
            String comida1 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA1));
            String comida2 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA2));
            String comida3 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA3));
             ;
            array_list.add(new Estrutura_Receita(Id, name, data, quantidade, horario, um, objetivo, comida1, comida2, comida3));
            res.moveToNext();
        }
        return array_list;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, RECEITAS_TABLE_NAME);
        return numRows;
    }

    public boolean updateFilme (Integer id, String name, String data, int quantidade, String um, String horario, String objetivo, String comida1, String comida2, String comida3)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(RECEITAS_COLUMN_NAME, name);
        contentValues.put(RECEITAS_COLUMN_DATA, data);
        contentValues.put(RECEITAS_COLUMN_QUANTIDADE, quantidade);
        contentValues.put(RECEITAS_COLUMN_MEDIDA, um);
        contentValues.put(RECEITAS_COLUMN_HORA, horario);
        contentValues.put(RECEITAS_COLUMN_OBJETIVO, objetivo);
        contentValues.put(RECEITAS_COLUMN_COMIDA1, comida1);
        contentValues.put(RECEITAS_COLUMN_COMIDA2, comida2);
        contentValues.put(RECEITAS_COLUMN_COMIDA3, comida3);
        db.update("RECEITAS", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteReceita (String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("RECEITAS", "name = ? ", new String[] { nome });
    }

    public ArrayList<Estrutura_Receita> getAllReceitas() {
        ArrayList<Estrutura_Receita> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from RECEITAS", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            int Id = res.getInt(res.getColumnIndex(RECEITAS_COLUMN_ID));
            String name = res.getString(res.getColumnIndex(RECEITAS_COLUMN_NAME));
            String data = res.getString(res.getColumnIndex(RECEITAS_COLUMN_DATA));
            int quantidade = res.getInt(res.getColumnIndex(RECEITAS_COLUMN_QUANTIDADE));
            String um = res.getString(res.getColumnIndex(RECEITAS_COLUMN_MEDIDA));
            String horario = res.getString(res.getColumnIndex(RECEITAS_COLUMN_HORA));
            String objetivo = res.getString(res.getColumnIndex(RECEITAS_COLUMN_OBJETIVO));
            String comida1 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA1));
            String comida2 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA2));
            String comida3 = res.getString(res.getColumnIndex(RECEITAS_COLUMN_COMIDA3));

            array_list.add(new Estrutura_Receita(Id, name, data, quantidade, horario, um, objetivo, comida1, comida2, comida3));
            res.moveToNext();
        }
        return array_list;
    }
}

