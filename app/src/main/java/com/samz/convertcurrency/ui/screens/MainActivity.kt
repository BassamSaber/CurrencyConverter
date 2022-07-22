package com.samz.convertcurrency.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.samz.convertcurrency.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main Activity View That Contain Navigation Graph with the need fragments
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}