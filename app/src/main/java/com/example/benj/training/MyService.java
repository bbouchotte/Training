package com.example.benj.training;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {

    public MyService() {
    }

    ////   Pour un service distant/lié   ////

    private int bindCount = 0;
    public class BoundBinder extends Binder {
        MyService getService() {
            return MyService.this;
        }
    }

    IBinder mBinder = new BoundBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "onBind()", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    public String getResponse() {
        bindCount++;
        return "Voici le retour d'un service distant: compteur = " + String.valueOf(bindCount) + ".";
    }

    ////    Pour un service local/démarré    ////
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        return super.onStartCommand(intent, flags, startId);
        // effectuer une tâche, elle sera éxecutée dans l'UIThread
        Toast.makeText(getApplicationContext(), "service onStartCommand()", Toast.LENGTH_SHORT).show();
        return START_STICKY;
        /* Si le système tue le service:
        START_STICKY: Le service sera recréé, onStartCommand sera de nouveau appelé, la dernière intention ne sera pas fournie (null)
        START_NOT_STICKY: le service ne sera pas recréé
        START_REDELIVER_INTENT:  Le service sera recréé, onStartCommand sera de nouveau appelé, la dernière intention sera fournie
        */
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "service onDestroy()", Toast.LENGTH_SHORT).show();
    }

}
