package com.example.benj.training;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.security.PrivateKey;

public class AuditorActivity extends AppCompatActivity {

    AuditorDAO auditorDAO = null;

    private EditText auditorNameET;
    private EditText auditorFirstnameET;
    private EditText auditorNote1ET;
    private EditText auditorNote2ET;
    private EditText auditorNote3ET;

    Button saveAuditorButton;
    Button auditorBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auditor);

        auditorNameET = (EditText) findViewById(R.id.auditorNameET);
        auditorFirstnameET = (EditText) findViewById(R.id.auditorFirstnameET);
        auditorNote1ET = (EditText) findViewById(R.id.auditorNote1ET);
        auditorNote2ET = (EditText) findViewById(R.id.auditorNote2ET);
        auditorNote3ET = (EditText) findViewById(R.id.auditorNote3ET);

        saveAuditorButton = (Button) findViewById(R.id.saveAuditorButton);
        auditorBackButton = (Button) findViewById(R.id.auditorBackButton);

        auditorDAO = new AuditorDAO(getApplicationContext());

        long id = getIntent().getLongExtra("auditor", 0);
        final Auditor auditor;

        if (id > 0) {
            Log.i("auditor.getId()", String.valueOf(id));
            auditor = auditorDAO.getById(id);
           // Log.i("auditorActivityName", auditor.getName());

            // Remplissage des EditText
            auditorNameET.setText(auditor.getName());
            auditorFirstnameET.setText(auditor.getFirstname());
            auditorNote1ET.setText(Double.toString((float) auditor.getNote1()));
            auditorNote2ET.setText(Double.toString(auditor.getNote2()));
            auditorNote3ET.setText(Double.toString(auditor.getNote3()));
            saveAuditorButton.setEnabled(true);

            // Sauvegarde des modifications:
            saveAuditorButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean error = false;
                    String name = String.valueOf(auditorNameET.getText());
                    String firstname = String.valueOf(auditorFirstnameET.getText());
                    double note1 = -1;
                    double note2 = -1;
                    double note3 = -1;
                    if (name == "" || firstname == "") {
                        error = true;
                        Toast.makeText(getApplicationContext(), "Le nom et le prénom ne doivent pas être vides", Toast.LENGTH_LONG).show();
                    }
                    try {
                        note1 = Double.parseDouble(String.valueOf(auditorNote1ET.getText()));
                        note2 = Double.parseDouble(String.valueOf(auditorNote2ET.getText()));
                        note3 = Double.parseDouble(String.valueOf(auditorNote3ET.getText()));
                    } catch (NumberFormatException e) {
                        error = true;
                        Toast.makeText(getApplicationContext(), "La note saisie n'est pas valide.", Toast.LENGTH_LONG).show();
                    }
                    if (!error) {
                        Toast.makeText(getApplicationContext(), name + " " + firstname + "-" + String.valueOf(note1), Toast.LENGTH_LONG).show();
                        auditor.setName(name);
                        auditor.setFirstname(firstname);
                        auditor.setNote1(note1);
                        auditor.setNote2(note2);
                        auditor.setNote3(note3);
                        auditorDAO.update(auditor);
                    }
                }
            });
        } else {
            saveAuditorButton.setEnabled(false);
        }

        auditorBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Persistance2Activity.class);
                startActivity(intent);
            }
        });
    }
}
