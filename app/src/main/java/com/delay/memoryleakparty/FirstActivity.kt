package com.delay.memoryleakparty

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_first.*

class FirstActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first)

        initializeListener()
    }

    private fun initializeListener() {
        tv_hello.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

}
