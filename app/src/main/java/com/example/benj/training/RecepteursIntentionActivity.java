package com.example.benj.training;

import android.Manifest;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RecepteursIntentionActivity extends AppCompatActivity {

    IntentFilter intentFilter = new IntentFilter();
    MyBroadcastReceiver myBroadcastReceiver = new MyBroadcastReceiver();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepteurs_intention);

        /* 2 façons de s'inscrire à un BroadcastReceiver
            De manière statique dans le fichier manifest
            Dynamiquement: */
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        registerReceiver(myBroadcastReceiver, intentFilter);

        /* Déclenchement d'un récepteur à chaque mise à jour de la position */
        Intent intentProximity = new Intent(this, MyBroadcastReceiver.class);
        PendingIntent mPendingProximity = PendingIntent.getBroadcast(this, 0, intentProximity, PendingIntent.FLAG_UPDATE_CURRENT);

        LocationManager mLocationManager = null;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 50, mPendingProximity);

    }

    // Désinscription (dans le cas d'un enregistrement dynamique):
    void unsubscribe() {
        unregisterReceiver(myBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            // Un BroadcastReceiver ne vit que le temps d'éxecution de cette méthode

            /* Démarrage d'un service */
                // l'usage d'un IntentService n'est pas recommandé puisqu'il gère son propre thread
            Intent service = new Intent(context, BroadcastService.class);
            context.startService(service);
            Toast.makeText(context, "Broadcast", Toast.LENGTH_SHORT).show();

            // Traitement asynchrone :
            final BroadcastReceiver.PendingResult result = goAsync();
            Thread thread = new Thread() {
                public void run() {
                    Log.i("Thread", "En cours");
                    result.finish();
                }
            };
        }
    }

    public class BroadcastService extends Service {

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Toast.makeText(this, "service", Toast.LENGTH_SHORT).show();
            switch (flags) {
                case 0:
                    Toast.makeText(this, "service: 0", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
            return super.onStartCommand(intent, flags, startId);
        }

        @Nullable
        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }
    }
}
