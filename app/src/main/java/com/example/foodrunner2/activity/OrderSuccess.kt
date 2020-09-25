package com.example.foodrunner2.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Button
import com.example.foodrunner2.R
import kotlinx.android.synthetic.main.activity_order_success.*

class OrderSuccess : AppCompatActivity() {
    lateinit var success_button :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_success)

        success_button = findViewById(R.id.success_button)

        success_button.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onBackPressed() {
        finish()
    }

    override fun onPause() {
        finish()
        super.onPause()
    }
}
