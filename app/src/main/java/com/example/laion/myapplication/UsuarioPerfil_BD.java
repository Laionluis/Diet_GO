package com.example.laion.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class UsuarioPerfil_BD extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Perfil.db";
    private static final String PERFIL_TABLE_NAME = "perfil";
    public static final String PERFIL_COLUMN_ID = "id";
    private static final String PERFIL_COLUMN_NAME = "name";
    private static final String PERFIL_COLUMN_IDADE = "idade";
    private static final String PERFIL_COLUMN_ALTURA = "altura";
    private static final String PERFIL_COLUMN_PESO = "peso";
    public static final String PERFIL_COLUMN_OBJETIVO = "Objetivo";
    private static final String PERFIL_COLUMN_METAPESO = "metaPeso";
    private static final String PERFIL_COLUMN_EMAIL = "email";


    private HashMap hp;

    public UsuarioPerfil_BD(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table perfil " +
                        "(id integer primary key, name text, idade integer, altura integer, peso integer, Objetivo text, metaPeso integer, email text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS perfil");
        onCreate(db);
    }

    public int insertPerfilUsuario (String name, int idade, int altura, int peso, String Objetivo, int metaPeso, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERFIL_COLUMN_NAME, name);
        contentValues.put(PERFIL_COLUMN_IDADE, idade);
        contentValues.put(PERFIL_COLUMN_ALTURA, altura);
        contentValues.put(PERFIL_COLUMN_PESO, peso);
        contentValues.put(PERFIL_COLUMN_OBJETIVO, Objetivo);
        contentValues.put(PERFIL_COLUMN_METAPESO, metaPeso);
        contentValues.put(PERFIL_COLUMN_EMAIL, email);
        long newRowId = db.insertOrThrow(PERFIL_TABLE_NAME, null, contentValues);
        return (int)(newRowId);
    }

    public Estrutura_Perfil getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from perfil where id="+id+"", null );
        res.moveToFirst();
        String nome = res.getString(res.getColumnIndex(PERFIL_COLUMN_NAME));
        int altura = res.getInt(res.getColumnIndex(PERFIL_COLUMN_ALTURA));
        int peso = res.getInt(res.getColumnIndex(PERFIL_COLUMN_PESO));
        int idade = res.getInt(res.getColumnIndex(PERFIL_COLUMN_IDADE));
        String objetivo = res.getString(res.getColumnIndex(PERFIL_COLUMN_OBJETIVO));
        int metaPeso = res.getInt(res.getColumnIndex(PERFIL_COLUMN_METAPESO));
        String emaiil = res.getString(res.getColumnIndex(PERFIL_COLUMN_EMAIL));

        Estrutura_Perfil resultado = new Estrutura_Perfil(id, nome, idade, altura, peso, objetivo, metaPeso, emaiil);

        return resultado;
    }

    public Estrutura_Perfil ObterPerfilPorEmail(String email) {
        int total = numberOfRows();
        if(total > 0){
            SQLiteDatabase db = this.getReadableDatabase();
            String[] a = new String[1];
            a[0]       = email;
            Cursor res =  db.rawQuery( "select * from perfil where email = ? ", a );
            if(res.getCount() > 0 && res.moveToFirst()){
                int Id = res.getInt(res.getColumnIndex(PERFIL_COLUMN_ID));
                String name = res.getString(res.getColumnIndex(PERFIL_COLUMN_NAME));
                int altura = res.getInt(res.getColumnIndex(PERFIL_COLUMN_ALTURA));
                int peso = res.getInt(res.getColumnIndex(PERFIL_COLUMN_PESO));
                int idade = res.getInt(res.getColumnIndex(PERFIL_COLUMN_IDADE));
                String objetivo = res.getString(res.getColumnIndex(PERFIL_COLUMN_OBJETIVO));
                int metaPeso = res.getInt(res.getColumnIndex(PERFIL_COLUMN_METAPESO));
                String emaiil = res.getString(res.getColumnIndex(PERFIL_COLUMN_EMAIL));
                return  new Estrutura_Perfil(Id, name, idade, altura, peso, objetivo, metaPeso, emaiil);
            }else return null;
        } else return null;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, PERFIL_TABLE_NAME);
        return numRows;
    }

    public boolean updatePerfilUsuario (Integer id, String name, int idade, int altura, int peso, String Objetivo, int metaPeso, String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PERFIL_COLUMN_NAME, name);
        contentValues.put(PERFIL_COLUMN_IDADE, idade);
        contentValues.put(PERFIL_COLUMN_ALTURA, altura);
        contentValues.put(PERFIL_COLUMN_PESO, peso);
        contentValues.put(PERFIL_COLUMN_OBJETIVO, Objetivo);
        contentValues.put(PERFIL_COLUMN_METAPESO, metaPeso);
        contentValues.put(PERFIL_COLUMN_EMAIL, email);
        db.update("perfil", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deletePerfil (String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("perfil", "email = ? ", new String[] { nome });
    }

    public ArrayList<Estrutura_Perfil> getAllPERFIL() {
        ArrayList<Estrutura_Perfil> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from perfil", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            int Id = res.getInt(res.getColumnIndex(PERFIL_COLUMN_ID));
            String name = res.getString(res.getColumnIndex(PERFIL_COLUMN_NAME));
            int altura = res.getInt(res.getColumnIndex(PERFIL_COLUMN_ALTURA));
            int peso = res.getInt(res.getColumnIndex(PERFIL_COLUMN_PESO));
            int idade = res.getInt(res.getColumnIndex(PERFIL_COLUMN_IDADE));
            String objetivo = res.getString(res.getColumnIndex(PERFIL_COLUMN_OBJETIVO));
            int metaPeso = res.getInt(res.getColumnIndex(PERFIL_COLUMN_METAPESO));
            String emaiil = res.getString(res.getColumnIndex(PERFIL_COLUMN_EMAIL));

            array_list.add(new Estrutura_Perfil(Id, name, idade, altura, peso, objetivo, metaPeso,emaiil));
            res.moveToNext();
        }
        return array_list;
    }

    public int getLastAddedRowId() {
        String queryLastRowInserted = "select last_insert_rowid()";
        SQLiteDatabase db = this.getReadableDatabase();
        final Cursor cursor = db.rawQuery(queryLastRowInserted, null);
        int _idLastInsertedRow = 0;
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    _idLastInsertedRow = cursor.getInt(0);
                }
            } finally {
                cursor.close();
            }
        }

        return _idLastInsertedRow;
    }
}
