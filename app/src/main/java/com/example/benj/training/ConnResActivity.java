package com.example.benj.training;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class ConnResActivity extends AppCompatActivity {
        /* Permissions:
            <uses-permission android:name="android.permission.INTERNET" />
            <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
         */

    // Gestionnaire de connection permettant de vérifier l'état du réseau:
    ConnectivityManager connectivityManager;
    // Récupérer un objet NetworkInfo:
    NetworkInfo networkInfo;

    // )

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_res);

        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            Toast.makeText(this, networkInfo.getSubtypeName(), Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Réseau disponible", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Réseau non disponible", Toast.LENGTH_LONG).show();
        }
        // Vérifier si une connexion est en cours d'établissement:
        //boolean connecting = networkInfo.isConnectedOrConnecting();
    }

    @Override
    protected void onResume() {
        super.onResume();
        /****    Surveiller le réseau    ****/
        /* Dans le manifest:
        <receiver android:name="com.example.benj.training.NetworkReceiver">
            <intent-filter>
                <action android:name="android.net.conn.CONNECTIVITY_CHANGE" />
            </intent-filter>
        </receiver>
        */
        // ou en dynamique:
        IntentFilter connActionIntentFilter = new IntentFilter();
        connActionIntentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkReceiver, connActionIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(networkReceiver);
    }

    BroadcastReceiver networkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String key = intent.getAction();
            switch (key) {
                case ConnectivityManager.CONNECTIVITY_ACTION:
                Toast.makeText(getApplicationContext(), "L'état de la connexion a changé", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }

        }
    };

}


