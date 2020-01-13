package com.delay.memoryleakparty

import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_second.*
import java.util.*
import kotlin.concurrent.timerTask

class SecondActivity : AppCompatActivity() {

    companion object {
        const val DELAY_TIME = 10000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        tv_test_handler.setOnClickListener {
            Handler().postDelayed({
                tv_log.append("Handler 작업 시작\n")
            }, DELAY_TIME)
        }

        tv_test_asynctask.setOnClickListener {
            SomethingAsyncTask().execute()
        }

        tv_test_rxjava.setOnClickListener {
            observable.subscribe({
                tv_log.append(it)
            }, {}, {
                tv_log.append("RxJava 작업 완료\n")
            })
        }

        tv_test_timer.setOnClickListener {
            Timer().schedule(timerTask {
                tv_log.append("TimerTask 작업 시\n")
            }, DELAY_TIME)
        }
    }

    inner class SomethingAsyncTask : AsyncTask<Unit, Unit, String>() {

        override fun onPreExecute() {
            tv_log.append("AsyncTask 작업 시작\n")
        }

        override fun doInBackground(vararg params: Unit?): String {
            while (true) { }

            return "AsyncTask 작업 완료\n"
        }

        override fun onPostExecute(result: String?) {
            tv_log.append(result)
        }
    }

    private val observable = Observable.just("first", "second", "third")
        .map { "RxJava $it 작업 시작\n" }

}
