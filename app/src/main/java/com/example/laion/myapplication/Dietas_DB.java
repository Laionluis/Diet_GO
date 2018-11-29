package com.example.laion.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class Dietas_DB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Dietas.db";
    private static final String DIETAS_TABLE_NAME = "Dietas";
    private static final String DIETAS_COLUMN_ID = "id";
    private static final String DIETAS_COLUMN_IDSRECEITA = "IdsReceita";
    private static final String DIETAS_COLUMN_DIASEMANA = "diaSemana";

    public Dietas_DB(Context context) {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table Dietas " +
                        "(id integer primary key, IdsReceita text, diaSemana text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Dietas");
        onCreate(db);
    }

    public boolean insertDieta (String IdsReceita, String diaSemana) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DIETAS_COLUMN_IDSRECEITA, IdsReceita);
        contentValues.put(DIETAS_COLUMN_DIASEMANA, diaSemana);
        db.insert("Dietas", null, contentValues);
        return true;
    }

    public Estrutura_Dieta getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Dietas where id="+id+"", null );
        res.moveToFirst();
        String IdsReceita = res.getString(res.getColumnIndex(DIETAS_COLUMN_IDSRECEITA));
        String diaSemana = res.getString(res.getColumnIndex(DIETAS_COLUMN_DIASEMANA));

        Estrutura_Dieta resultado = new Estrutura_Dieta(id, IdsReceita, diaSemana);

        return resultado;
    }

    public ArrayList<Estrutura_Dieta> ObterDietaPorDia(String dia) {
        ArrayList<Estrutura_Dieta> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] a = new String[1];
        a[0]       = "%"+dia+"%";
        Cursor res =  db.rawQuery( "select * from Dietas where diaSemana LIKE ? ", a );
        res.moveToFirst();

        while(!res.isAfterLast()){
            int Id = res.getInt(res.getColumnIndex(DIETAS_COLUMN_ID));
            String IdsReceita = res.getString(res.getColumnIndex(DIETAS_COLUMN_IDSRECEITA));
            String diaSemana = res.getString(res.getColumnIndex(DIETAS_COLUMN_DIASEMANA));

            array_list.add(new Estrutura_Dieta(Id, IdsReceita, diaSemana));
            res.moveToNext();
        }
        return array_list;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, DIETAS_TABLE_NAME);
        return numRows;
    }

    public boolean updateDieta (Integer id, String IdsReceita, String diaSemana)  {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DIETAS_COLUMN_IDSRECEITA, IdsReceita);
        contentValues.put(DIETAS_COLUMN_DIASEMANA, diaSemana);

        db.update("Dietas", contentValues, "id = ? ", new String[] { Integer.toString(id) } );
        return true;
    }

    public Integer deleteDieta (String nome) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("Dietas", "diaSemana = ? ", new String[] { nome });
    }

    public ArrayList<Estrutura_Dieta> getAllDIETAS() {
        ArrayList<Estrutura_Dieta> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from Dietas", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            int Id = res.getInt(res.getColumnIndex(DIETAS_COLUMN_ID));
            String IdsReceita = res.getString(res.getColumnIndex(DIETAS_COLUMN_IDSRECEITA));
            String diaSemana = res.getString(res.getColumnIndex(DIETAS_COLUMN_DIASEMANA));

            array_list.add(new Estrutura_Dieta(Id, IdsReceita, diaSemana));
            res.moveToNext();
        }
        return array_list;
    }


}
