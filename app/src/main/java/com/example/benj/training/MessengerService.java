package com.example.benj.training;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.os.Handler;

import java.util.ArrayList;

public class MessengerService extends Service {

    protected static int client_count = 0;

    ArrayList<Messenger> clients = new ArrayList<Messenger>();

    static final int TO_UPPER_CASE = 1;
    static final int CLIENT_REGISTER = 2;
    static final int CLIENT_UNREGISTER = 3;

    final Messenger messenger = new Messenger(new IncomingHandler());

    public MessengerService() { }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(getApplicationContext(), "binding (Handler)", Toast.LENGTH_SHORT).show();
        return messenger.getBinder();
    }

    public static int getClient_count() {
        return client_count;
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLIENT_REGISTER:
                    clients.add(msg.replyTo);
                    Log.i("msg nbre clients:", String.valueOf(clients.size()));
                    Toast.makeText(getApplicationContext(), String.valueOf(clients.size()), Toast.LENGTH_SHORT).show();
                    client_count = clients.size();
                    break;
                case TO_UPPER_CASE:
                    //Send data as a String
                    Bundle b = new Bundle();
                    b.putString("str1", String.valueOf(msg.getData().get("str1")).toUpperCase());
                    msg = Message.obtain(null, TO_UPPER_CASE);
                    msg.setData(b);
                    for (int i=clients.size()-1; i>=0; i--) {
                        try {
                            clients.get(i).send(msg);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                case CLIENT_UNREGISTER:
                    clients.remove(msg.replyTo);
                    client_count = clients.size();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
