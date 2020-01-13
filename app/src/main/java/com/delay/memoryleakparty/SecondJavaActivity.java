package com.delay.memoryleakparty;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import kotlin.Unit;

public class SecondJavaActivity extends AppCompatActivity {

    static long DELAY_TIME = 10000L;

    private AppCompatTextView logView;

    private Observable observable = Observable.just("first", "second", "third");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.java_activity_second);

         logView = findViewById(R.id.tv_log);

        findViewById(R.id.tv_test_handler).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logView.append("Handler 작업 시작\n");
                    }
                }, DELAY_TIME);
            }
        });

        findViewById(R.id.tv_test_asynctask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SomethingAsyncTask().execute();
            }
        });

        findViewById(R.id.tv_test_rxjava).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable.subscribe(new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        logView.append(o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        logView.append("RxJava 작업 완료");
                    }
                });
            }
        });

        findViewById(R.id.tv_test_timer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        while(true);
                    }
                }, DELAY_TIME);
            }
        });
    }

    class SomethingAsyncTask extends AsyncTask<Unit, Unit, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            logView.append("AsyncTask 작업 시작\n");
        }

        @Override
        protected String doInBackground(Unit... units) {
            long i = 0;
            while (i < Long.MAX_VALUE) {
                i++;
            }

            return "AsyncTask 작업 완료\n";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            logView.append(s);
        }
    }
}
