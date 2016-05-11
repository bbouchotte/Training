package com.example.benj.training;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class StartedServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button startStartedServiceButton;
    private Button stopStartedServiceButton;
    private Button startStartedIntentServiceButton;
    private Button stopStartedIntentServiceButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_started_service);

        startStartedServiceButton = (Button) findViewById(R.id.startStartedServiceButton);
        startStartedServiceButton.setOnClickListener(this);
        stopStartedServiceButton = (Button) findViewById(R.id.stopStartedServiceButton);
        stopStartedServiceButton.setOnClickListener(this);
        startStartedIntentServiceButton = (Button) findViewById(R.id.startStartedIntentServiceButton);
        startStartedIntentServiceButton.setOnClickListener(this);
        stopStartedIntentServiceButton = (Button) findViewById(R.id.stopStartedIntentServiceButton);
        stopStartedIntentServiceButton.setOnClickListener(this);

        stopStartedServiceButton.setEnabled(false);
        stopStartedIntentServiceButton.setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        Intent intentMyService = new Intent(this, MyService.class);
        Intent intentMyIntentService = new Intent(this, MyIntentService.class);
        switch (v.getId()) {
            case R.id.startStartedServiceButton:
                startStartedServiceButton.setEnabled(false);
                stopStartedServiceButton.setEnabled(true);
                startService(intentMyService);
                break;
            case R.id.stopStartedServiceButton:
                startStartedServiceButton.setEnabled(true);
                stopStartedServiceButton.setEnabled(false);
                stopService(intentMyService);
                break;
            case R.id.startStartedIntentServiceButton:
                startStartedIntentServiceButton.setEnabled(false);
                stopStartedIntentServiceButton.setEnabled(true);
                startService(intentMyIntentService);
                break;
            case R.id.stopStartedIntentServiceButton:
                startStartedIntentServiceButton.setEnabled(true);
                stopStartedIntentServiceButton.setEnabled(false);
                stopService(intentMyIntentService);
                break;
        }
    }
}

