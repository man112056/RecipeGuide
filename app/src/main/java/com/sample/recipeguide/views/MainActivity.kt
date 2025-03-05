package com.sample.recipeguide.views

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            // Navigate to CategoryActivity
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)

            // Close MainActivity so it doesn't stay in the back stack
            finish()
        }
}