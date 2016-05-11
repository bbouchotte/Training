package com.example.benj.training;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.prefs.Preferences;

public class PersistanceActivity extends AppCompatActivity {

    // GetSharedPreferences
    EditText persistanceET;
    Button persButton;
    // getPreferences
    EditText getPrefET;
    Button getPrefButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*
       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        ////        Utilisation de SharedPreferences        ////
        persistanceET = (EditText) findViewById(R.id.persistanceET);
        persButton = (Button) findViewById(R.id.persButton);

        SharedPreferences sharedPreferences = getSharedPreferences("TrainingSharedPref", Context.MODE_APPEND);
        final SharedPreferences.Editor editor = sharedPreferences.edit();

        String text = sharedPreferences.getString("ET1", "Entrez un texte à sauvegarder");
        persistanceET.setText(text);

        persButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("ET1", String.valueOf(persistanceET.getText()));
                editor.commit();
            }
        });

        ////        Utilisation de GetPreferences        ////
        getPrefET = (EditText) findViewById(R.id.getPrefET);
        getPrefButton = (Button) findViewById(R.id.getPrefButton);

        SharedPreferences preferences = getPreferences(Context.MODE_APPEND);
        final SharedPreferences.Editor prefEditor = preferences.edit();
        text = preferences.getString("ET2", "Entrez un texte à sauvegarder");
        getPrefET.setText(text);

        getPrefButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefEditor.putString("ET2", String.valueOf(getPrefET.getText()));
                prefEditor.commit();
            }
        });

        ////        Les préférences utilisateur     ////
            /*
                Fichier res.xml.preferences.xml
                activité SettingsActivity
                Méthode onOptionsItemSelected(...)
                Méthode onCreateOptionsMenu(...)
              */


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(getApplicationContext(), MySettingsActivity.class);
            startActivity(intent);
            return false;
        }
        return super.onOptionsItemSelected(item);
    }


}
