package com.example.evaluacion;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    EditText titulo, descrip, autor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo= findViewById(R.id.titulo);
        descrip= findViewById(R.id.des);
        autor= findViewById(R.id.autor);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.consulta){
            Intent i = new Intent(this, registro.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void guardar(View view) {

        AdminBase admin = new AdminBase(this, "EvalNotas", null,1);
        SQLiteDatabase base = admin.getWritableDatabase();

        String title = titulo.getText().toString();
        String descripcion = descrip.getText().toString();
        String aut = autor.getText().toString();
        int id =0;


        if (!title.isEmpty() && !descripcion.isEmpty() && !aut.isEmpty()) {

            ContentValues registro = new ContentValues();

            /*registro.put("id", id);*/
            registro.put("Titulo", title);
            registro.put("Descripcion", descripcion);
            registro.put("Autor", aut);

            base.insert("tb_notas",null,registro);


            base.close();

            titulo.setText("");
            descrip.setText("");
            autor.setText("");

            Toast.makeText(this, "Guardado", Toast.LENGTH_SHORT).show();
        }

    }
}