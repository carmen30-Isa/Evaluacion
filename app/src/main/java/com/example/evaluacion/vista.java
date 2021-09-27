package com.example.evaluacion;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class vista extends AppCompatActivity {

    public EditText edit1, edit2, edit3;
    public String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vista);

        AdminBase admin = new AdminBase(this, "EvalNotas", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();


        edit1 = findViewById(R.id.titulores);
        edit2 = findViewById(R.id.desre);
        edit3 = findViewById(R.id.autorres);


        String recibe = getIntent().getStringExtra("key");

        edit1.setText(recibe);
        text= edit1.getText().toString();

        Cursor registros = base.rawQuery
                ("select Descripcion, Autor from tb_notas where Titulo = '" + text + "'", null);

        if (registros.moveToFirst()){
            edit2.setText(registros.getString(0));
            edit3.setText(registros.getString(1));
        }
    }

    public void borrar(View view) {
        AdminBase admin = new AdminBase(this, "EvalNotas", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String tit = edit1.getText().toString();
        String des = edit2.getText().toString();
        String aut = edit3.getText().toString();


        AlertDialog.Builder alerta = new AlertDialog.Builder(vista.this);
        alerta.setIcon(R.drawable.ic_baseline_warning_24);
        alerta.setTitle("Eliminar");
        alerta.setMessage("Seguro que quiere borras estos campos de la Base de Datos?");
        alerta.setCancelable(false);

        alerta.setPositiveButton("Si", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                int cantidad = base.delete("tb_notas", "Titulo = '" + text + "'", null);
                base.close();

                edit1.setText("");
                edit2.setText("");
                edit3.setText("");

                Toast.makeText(getApplicationContext(), "Los Datos se han borrado exitosamente", Toast.LENGTH_SHORT).show();

            }
        });

        alerta.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }).show();




    }

    public void back(View view) {

        Intent i = new Intent(this, registro.class);
        startActivity(i);

    }

    public void actualizar(View view) {
        AdminBase admin = new AdminBase(this, "EvalNotas", null, 1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String tit = edit1.getText().toString();
        String des = edit2.getText().toString();
        String aut = edit3.getText().toString();

        ContentValues registro = new ContentValues();

        registro.put("Titulo", tit);
        registro.put("Descripcion", des);
        registro.put("Autor", aut);

        int cantidad = base.update("tb_notas", registro, "Titulo = '" + text + "'", null);
        base.close();

        if (cantidad == 1) {
            Toast.makeText(this, "Se Actualizaron los datos correctamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No se pudo actualizar los datos", Toast.LENGTH_SHORT).show();
        }

    }
}