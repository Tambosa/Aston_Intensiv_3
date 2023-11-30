package com.example.aston_intensiv_3

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}