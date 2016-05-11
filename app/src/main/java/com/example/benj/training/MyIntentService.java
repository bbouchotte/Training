package com.example.benj.training;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.widget.Toast;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {

    /* Créé un thread et une file d'attente */

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            Toast.makeText(getApplicationContext(), "IntentService OnHandleIntent()", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "IntentService intent = null!!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(getApplicationContext(), "Service démarré intentService onStartCommand()", Toast.LENGTH_SHORT).show();
        return START_STICKY;
//        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "IntentService onDestroy()", Toast.LENGTH_SHORT).show();
    }
}
