package com.example.benj.training;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Typeface;
import android.provider.BaseColumns;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

// TODO question :
// si plusieurs tables, faut-il 1 DAOBase par table?

public class Persistance2Activity extends AppCompatActivity {

    /*
    SQLite
    voir:
        DBHelper.java
        DAOBase.java
        AuditorDAO.java
        Auditor.java
     */

    private Button addExampleListButton;
    private Button deleteListButton;
    private Button randAuditor;

    private LinearLayout auditorsList;

    AuditorDAO auditorDAO = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_persistance2);

        addExampleListButton = (Button) findViewById(R.id.addExampleListButton);
        addExampleListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auditorDAO.add(new Auditor(0, "Lenoble", "Dimitri", 2.01, 10, 17.98));
                auditorDAO.add(new Auditor(0, "Bouchotte", "Benjamin", 19.99, -1, -1));
                auditorDAO.add(new Auditor(0, "Pompidou", "Georges", 0.23, -1, -1));
                displayAll();
            }
        });

        deleteListButton = (Button) findViewById(R.id.deleteListButton);
        deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auditorDAO.deleteAll();
                displayAll();
            }
        });

        auditorsList = (LinearLayout) findViewById(R.id.auditorsList);

        auditorDAO = new AuditorDAO(getApplicationContext());

        randAuditor = (Button) findViewById(R.id.randAuditor);
        randAuditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Auditor> auditors = new ArrayList<Auditor>();
                auditors.add(auditorDAO.getRand());
                displayAuditorsList(auditors);
            }
        });

        displayAll();
    }

    void displayAuditorsList(ArrayList<Auditor> auditors) {
        auditorsList.removeAllViews();

        // Titres de la liste
        TextView titleTV = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.width = LinearLayout.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.LEFT;
        titleTV.setLayoutParams(params);
        titleTV.setText("Nom Prénom - Meilleure note");
        titleTV.setTypeface(null, Typeface.BOLD);
        auditorsList.addView(titleTV);

         // Affichage des auditeurs
        for (final Auditor auditor : auditors) {
            // Affichage nom prénom - meilleur note
            TextView tv = new TextView(this);
            tv.setId((int) auditor.getId());
            params.setMargins(0, 5, 0, 5);
            tv.setLayoutParams(params);
            tv.setClickable(true);
            tv.setText( auditor.getName() + " " + auditor.getFirstname() + " - " + auditorDAO.bestNote(auditor));
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), AuditorActivity.class);
                    intent.putExtra("auditor", auditor.getId());
                    startActivity(intent);
                }
            });
            auditorsList.addView(tv);
        }
    }

    private void displayAll() {
        ArrayList<Auditor> auditors = auditorDAO.getAll();
        displayAuditorsList(auditors);
    }
}
