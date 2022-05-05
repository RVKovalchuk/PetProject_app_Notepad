package com.example.petproject_app_notepad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onClickAddNew()
    }

    private fun onClickAddNew() {
        val fabAddNew = findViewById<FloatingActionButton>(R.id.floatingActionButton_add_new)
        fabAddNew.setOnClickListener {
            startActivity(Intent(this, AddActivity::class.java))
        }
    }
}
