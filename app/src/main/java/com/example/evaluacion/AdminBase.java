package com.example.evaluacion;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class AdminBase extends SQLiteOpenHelper {
    public AdminBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, "EvalNotas.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_notas(id INTEGER not null primary key AUTOINCREMENT, Titulo text, Descripcion text, Autor text)");

        db.execSQL("INSERT INTO tb_notas VALUES (null, 'xd','hola', 'yo')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists tb_notas");
        onCreate(db);

    }
    public ArrayList llenarinfo(){

        SQLiteDatabase base= this.getReadableDatabase();
        ArrayList<String> lista = new ArrayList<>();

        Cursor registros = base.rawQuery ("SELECT * FROM tb_notas",null);

        if (registros.moveToFirst()){
            do {
                lista.add(registros.getString(1));
            }while (registros.moveToNext());
        }

        return lista;
    }
}
