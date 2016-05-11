package com.example.benj.training;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button mainStartedServiceButton;
    protected Button mainBoundServiceButton;
    protected Button mainProgConcButton;
    protected Button mainPersistanceButton;
    protected Button mainPersistance2Button;
    protected Button mainBCRButton;
    protected Button mainConnResButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainStartedServiceButton = (Button) findViewById(R.id.mainStartedServiceButton);
        mainStartedServiceButton.setOnClickListener(this);
        mainBoundServiceButton = (Button) findViewById(R.id.mainBoundServiceButton);
        mainBoundServiceButton.setOnClickListener(this);
        mainProgConcButton = (Button) findViewById(R.id.mainProgConcButton);
        mainProgConcButton.setOnClickListener(this);
        mainPersistanceButton = (Button) findViewById(R.id.mainPersistanceButton);
        mainPersistanceButton.setOnClickListener(this);
        mainPersistance2Button = (Button) findViewById(R.id.mainPersistance2Button);
        mainPersistance2Button.setOnClickListener(this);
        mainBCRButton = (Button) findViewById(R.id.mainBCRButton);
        mainBCRButton.setOnClickListener(this);
        mainConnResButton = (Button) findViewById(R.id.mainConnResButton);
        mainConnResButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mainStartedServiceButton:
                Intent intent = new Intent(this, StartedServiceActivity.class);
                startActivity(intent);
                break;
            case R.id.mainBoundServiceButton:
                Intent intentBoundService = new Intent(this, BoundServiceActivity.class);
                startActivity(intentBoundService);
                break;
            case R.id.mainProgConcButton:
                Intent intentProgConcService = new Intent(this, ProgConcActivity.class);
                startActivity(intentProgConcService);
                break;
            case R.id.mainPersistanceButton:
                Intent intentPersitance = new Intent(this, PersistanceActivity.class);
                startActivity(intentPersitance);
                break;
            case R.id.mainPersistance2Button:
                Intent intentPersitance2 = new Intent(this, Persistance2Activity.class);
                startActivity(intentPersitance2);
                break;
            case R.id.mainBCRButton:
                Intent intentBCR = new Intent(this, RecepteursIntentionActivity.class);
                startActivity(intentBCR);
                break;
            case R.id.mainConnResButton:
                Intent intentConnRes = new Intent(this, ConnResActivity.class);
                startActivity(intentConnRes);
                break;
            default:
                break;
        }
    }
}
