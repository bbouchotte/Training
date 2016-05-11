package com.example.benj.training;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class ProgConcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prog_conc);

        // Pour thread:
        threadLauncherTV = (TextView) findViewById(R.id.threadLauncherTV);
        threadLauncher = (Button) findViewById(R.id.threadLauncher);

        threadLauncher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                threadLaunch();
            }
        });

        // Pour tâche asynchrone :
        bar = (ProgressBar) findViewById(R.id.progressBar);
        asynchButton = (Button) findViewById(R.id.asynchButton);
        imageView = (ImageView) findViewById(R.id.image);
    }

    ///////////////////////////////////////////////////////////////////////////////
    /////////////////////       Lancement d'un thread       ///////////////////////
    ///////////////////////////////////////////////////////////////////////////////

    public TextView threadLauncherTV;
    public Button threadLauncher;

    public void threadLaunch() {
        threadLauncherTV.setText("Je compte jusque 5...");
        Thread thread = new Thread(new Runnable() {
            Integer count = 0;

            @Override
            public void run() {
                final Timer timer = new Timer();
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView tv = (TextView) findViewById(R.id.threadLauncherTV);
                                tv.setText(String.valueOf(count));
                                if (count == 5) {
                                    timer.cancel();
                                }
                                count++;
                            }
                        });
                    }
                };
                timer.schedule(timerTask, 0, 1000);
            }
        });
        thread.start();
    }

    ///////////////////////////////////////////////////////////////////////////////////
    ///////////////////////         Tâches asynchrones          ///////////////////////
    ///////////////////////////////////////////////////////////////////////////////////

    public ProgressBar bar;
    public Button asynchButton;
    ImageView imageView;
    static final int MAX_PROGRESS = 100;
    Boolean mCnamTaskStarded = false;

    static public class MyHandler extends Handler {
        WeakReference<ProgConcActivity> activity;
        public MyHandler(ProgConcActivity act) {
            activity = new WeakReference<ProgConcActivity>(act);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            activity.get().bar.incrementProgressBy(1);

            // Affichage d'un Toast quand c'est fini
            if (activity.get().bar.getProgress() >= MAX_PROGRESS) {
                Toast.makeText(activity.get().getApplicationContext(),
                        "Chargement terminé", Toast.LENGTH_SHORT).show();
                activity.get().asynchButton.setEnabled(true);
            }
        }
    }

    public class CnamTask extends AsyncTask<String, Void, Bitmap> {

        //Button asyncButton = (Button) findViewById(R.id.ButtonAsyncTask);

        @Override
        protected Bitmap doInBackground(String... params) {

            publishProgress();
            try {

                URL url = new URL(params[0]);
                Bitmap result = BitmapFactory.decodeStream(url.openConnection()
                        .getInputStream());

                Thread.sleep(3000);

                if (isCancelled())
                    return null;

                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPreExecute() {
            asynchButton.setEnabled(false);
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Tâche asynchrone lancée",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            Toast.makeText(getApplicationContext(),
                    "Tâche asynchrone en cours", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onCancelled() {

            asynchButton.setEnabled(true);
            mCnamTaskStarded = false;
            Toast.makeText(getApplicationContext(),
                    "Tâche asynchrone : Annulée", Toast.LENGTH_SHORT).show();
            imageView.setImageBitmap(null);

            super.onCancelled();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);
            asynchButton.setEnabled(true);
            mCnamTaskStarded = false;
            if (result != null) {
                Toast.makeText(getApplicationContext(),
                        "Tâche asynchrone terminée", Toast.LENGTH_SHORT)
                        .show();
                imageView.setImageBitmap(result);
            }
        }
    }



}
