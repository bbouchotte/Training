package com.example.benj.training;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BoundServiceActivity extends AppCompatActivity implements View.OnClickListener {

    private Button boundServiceStartButton;
    private Button boundServiceDetachButton;
    private Button boundGetResponse;
    private Button startMessenger;
    private Button example1;
    private TextView tv;
    private TextView messengerResponse;
    private TextView clientCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_service);

        boundServiceStartButton = (Button) findViewById(R.id.boundServiceStartButton);
        boundServiceStartButton.setOnClickListener(this);
        boundServiceDetachButton = (Button) findViewById(R.id.boundServiceDetachButton);
        boundServiceDetachButton.setOnClickListener(this);
        boundGetResponse = (Button) findViewById(R.id.boundGetResponse);
        boundGetResponse.setOnClickListener(this);
        startMessenger = (Button) findViewById(R.id.startMessenger);
        startMessenger.setOnClickListener(this);
        example1 = (Button) findViewById(R.id.example1);
        example1.setOnClickListener(this);

        tv = (TextView) findViewById(R.id.boundServiceResponseTV);
        messengerResponse = (TextView) findViewById(R.id.messengerResponse);
        clientCount = (TextView) findViewById(R.id.clientCount);

        boundServiceDetachButton.setEnabled(false);

        bindToService();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // utilisation d'une extension de la classe Binder
            case R.id.boundServiceStartButton:
                boundServiceStartButton.setEnabled(false);
                boundServiceDetachButton.setEnabled(true);
                bindBinderBoundService();
                break;
            case R.id.boundServiceDetachButton:
                boundServiceStartButton.setEnabled(true);
                boundServiceDetachButton.setEnabled(false);
                doUnbindBinderBoundService();
                break;
            case R.id.boundGetResponse:
                if (bindBoundService != null) {
                    tv.setText(bindBoundService.getResponse());
                    break;
                }
                //Utilisation d'un messenger:
            case R.id.startMessenger:
                getMessengerResponse();
                break;
            case R.id.example1:
                Intent example1Intent = new Intent(getApplicationContext(), ExampleActivity.class);
                startActivity(example1Intent);
            default:
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        doUnbindBinderBoundService();
        doUnbindService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        doUnbindBinderBoundService();
        try {
            doUnbindService();
        }
        catch (Throwable t) {
            Log.e("MainActivity", "Failed to unbind from the service", t);
        }
    }

    ////////////////////////////////////////////////////////////
    ////////        extension de la classe Binder       ////////
    ////////////////////////////////////////////////////////////
    MyService bindBoundService = null;
    boolean mBound = false;
    Intent bindServiceIntent = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyService.BoundBinder binder= (MyService.BoundBinder) service;
            bindBoundService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bindBoundService = null;
        }
    };

    public void bindBinderBoundService() {
        if (!mBound) {
            bindServiceIntent = new Intent(getApplicationContext(), MyService.class);
            bindService(bindServiceIntent, mConnection, Context.BIND_AUTO_CREATE);
        }
        mBound = true;
    }

    public void doUnbindBinderBoundService() {
        if(mBound) {
            unbindService(mConnection);
            stopService(bindServiceIntent);
        }
        mBound =false;
    }

    ////////////////////////////////////////////////////////
    ////////        Pour utiliser un Messenger      ////////
    ////////////////////////////////////////////////////////
    Messenger messengerService = null;
    /** Flag indicating whether we have called bind on the service. */
    boolean messengerBound;

    final Messenger mMessenger = new Messenger(new ResponseHandler());

    private ServiceConnection messengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            messengerService = new Messenger(service);
            try {
                Message msg = Message.obtain(null, MessengerService.CLIENT_REGISTER);
                msg.replyTo = mMessenger;
                messengerService.send(msg);
                clientCount.setText("Nombre de clients connectés au service: " + MessengerService.getClient_count());
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            messengerService = null;
        }
    };

    public void getMessengerResponse() {
        bindToService();
        String text = (String) messengerResponse.getText();
        Bundle b = new Bundle();
        b.putString("str1", text);
        try {
            Message msg = Message.obtain(null, MessengerService.TO_UPPER_CASE);
            msg.setData(b);
            msg.replyTo = mMessenger;
            messengerService.send(msg);
        } catch (RemoteException e) {

        }
    }

    class ResponseHandler extends Handler {
/*        private final WeakReference<BoundServiceActivity> mActivity;
        public ResponseHandler(BoundServiceActivity activity) {
            mActivity = new WeakReference<BoundServiceActivity>(activity);
        }*/

        @Override
        public void handleMessage(Message msg) {
            switch ( msg.what) {
                case MessengerService.TO_UPPER_CASE:
                    messengerResponse.setText(msg.getData().getString("str1"));
                    break;
                default:
                    Toast.makeText(getApplicationContext(), "Default", Toast.LENGTH_SHORT).show();
                    super.handleMessage(msg);
            }
        }
    }

    void bindToService() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, messengerConnection, Context.BIND_AUTO_CREATE);
        messengerBound = true;
//        Toast.makeText(getApplicationContext(), MessengerService.getClient_count(), Toast.LENGTH_SHORT).show();
    }

    void doUnbindService() {
        if (messengerBound) {
            // If we have received the service, and hence registered with it, then now is the time to unregister.
            if (messengerService != null) {
                try {
                    Message msg = Message.obtain(null, MessengerService.CLIENT_UNREGISTER);
                    msg.replyTo = mMessenger;
                    messengerService.send(msg);
                }
                catch (RemoteException e) {
                    // There is nothing special we need to do if the service has crashed.
                }
            }
            // Detach our existing connection.
            unbindService(messengerConnection);
            messengerBound = false;
        }
    }
}

