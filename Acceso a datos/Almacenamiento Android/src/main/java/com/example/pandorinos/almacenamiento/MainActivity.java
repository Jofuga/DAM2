package com.example.pandorinos.almacenamiento;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS = "My preferences";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);

        final EditText et_nombre = (EditText) findViewById (R.id.Et_nombre);
        final EditText et_usuario = (EditText) findViewById (R.id.Et_usuario);
        final EditText et_fecha = (EditText) findViewById (R.id.Et_fecha);
        final EditText et_sexo = (EditText) findViewById (R.id.Et_sexo);
        final Button bt_guardar = (Button) findViewById (R.id.Button_guardar);
        final Button bt_mostrar = (Button) findViewById (R.id.Button_mostrar);

        final String nombre = String.valueOf (et_nombre.getText ());
        final String usuario = String.valueOf (et_usuario.getText ());
        final String fecha = String.valueOf (et_fecha.getText ());
        final String sexo = String.valueOf (et_sexo.getText ());

        bt_guardar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                //Crea objeto de las preferencias
                SharedPreferences myPreferences = getSharedPreferences (PREFS, Activity.MODE_PRIVATE);

                //Crea editor para modificar prefenecias
                SharedPreferences.Editor editor = myPreferences.edit ();

                //Guardamos valores preferencias
                editor.putString (nombre, "");
                editor.putString (usuario, "");
                editor.putString (fecha, "");
                editor.putString (sexo, "");

                //Guarda modificaciones
                editor.commit ();
                }
        });

        //Botón mostrar cuando se hace click
        bt_mostrar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                //Crea objeto de las preferencias
                SharedPreferences myPreferences = getSharedPreferences (PREFS, Activity.MODE_PRIVATE);

                //Recuperamos preferencias
                myPreferences.getString (nombre,"");
                myPreferences.getString (usuario, "");
                String t_fecha = myPreferences.getString (fecha, "");
                String t_sexo = myPreferences.getString (sexo,"");

                Toast.makeText (getApplicationContext (), "PREFS: " + nombre + ", " + usuario +
                ", " + fecha + ", " + sexo + ".", Toast.LENGTH_LONG).show ();

            }
        });

    }
}
